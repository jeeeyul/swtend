package net.jeeeyul.swtend.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class GradientEdit extends Canvas {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new GradientEdit(shell);
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Point preferredSize = new Point(250, 25);
	private Gradient selection;
	private boolean itemsInvaldate = false;
	private Point dragBegin = null;
	private boolean lockOrder = false;
	private int state = 0;

	private GradientEditItem selectedItem = null;
	private GradientEditItem hotItem = null;

	private Rectangle dragBeginBounds = null;
	List<GradientEditItem> items = new ArrayList<GradientEditItem>();

	public GradientEdit(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);

		Menu menu = new Menu(parent);
		parent.setMenu(menu);

		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				draw(event.gc);
			}
		});

		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				layoutItems();
			}
		});

		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleMouseDown(event);
			}
		});

		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleMouseMove(event);

			}
		});

		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleMouseUp(event);

			}
		});

		addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleDoubleClick(event);
			}
		});

		Gradient gradient = new Gradient(new HSB(255, 255, 255), new HSB(255, 0, 0));
		gradient.add(1, new ColorStop(new HSB(255, 255, 0), 50));
		setSelection(gradient, false);
	}

	private void handleDoubleClick(Event event) {
		GradientEditItem item = getItemAt(event);
		if (item != null) {
			final ColorStop colorStop = (ColorStop) item.getData();
			HSB original = colorStop.color;

			final ColorPicker picker = new ColorPicker(getShell());
			picker.setSelection(colorStop.color);

			picker.setContinuosSelectionHandler(new Procedure1<HSB>() {
				@Override
				public void apply(HSB t) {
					colorStop.color = picker.getSelection();
					redraw();
				}
			});

			if (picker.open() == IDialogConstants.OK_ID) {
				colorStop.color = picker.getSelection();
			} else {
				colorStop.color = original;
			}
			redraw();
		} else {
			int percent = toPercent(event.x);
			ColorStop from = selection.get(0);
			ColorStop to = selection.get(selection.size() - 1);

			for (int i = 0; i < selection.size(); i++) {
				ColorStop each = selection.get(i);
				if (each.percent > percent) {
					break;
				} else {
					from = each;
				}
			}

			for (int i = selection.size() - 1; i >= 0; i--) {
				ColorStop each = selection.get(i);
				if (each.percent < percent) {
					break;
				} else {
					to = each;
				}
			}

			int insertIndex = selection.indexOf(to);
			HSB newHSB = from.color.getCopy();
			selection.add(insertIndex, new ColorStop(newHSB.mixWith(to.color, 0.5f), percent));
			rebuildItems();
		}
	}

	private int toPercent(int x) {
		Rectangle barArea = getBarArea();
		int result = (int) ((x - barArea.x) / (double) barArea.width * 100);
		result = Math.max(result, 0);
		result = Math.min(result, 100);
		return result;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(Math.max(preferredSize.x, wHint), Math.max(preferredSize.y, hHint));
	}

	private void draw(GC gc) {
		if (itemsInvaldate) {
			rebuildItems();
		}

		drawBar(gc);

		for (GradientEditItem item : items) {
			item.draw(gc);
		}
	}

	private void sort() {
		Collections.sort(selection, new Comparator<ColorStop>() {
			@Override
			public int compare(ColorStop a, ColorStop b) {
				return a.percent - b.percent;
			}
		});
		redraw();
	}

	private void drawBar(GC gc) {
		Rectangle barArea = getBarArea();
		int offset = 0;
		int gradientWidth = 0;

		for (int i = 0; i <= selection.size(); i++) {
			ColorStop from = (i > 0) ? selection.get(i - 1) : selection.get(i);
			ColorStop to = (i < selection.size()) ? selection.get(i) : selection.get(i - 1);
			Color fromColor = new Color(getDisplay(), from.color.toRGB());
			Color toColor = new Color(getDisplay(), to.color.toRGB());
			gradientWidth = (i < selection.size()) ? (int) (barArea.width * (to.percent / 100d) - offset) : barArea.width - offset;
			gc.setForeground(fromColor);
			gc.setBackground(toColor);
			gc.fillGradientRectangle(barArea.x + offset, barArea.y, gradientWidth, barArea.height, false);

			fromColor.dispose();
			toColor.dispose();
			offset += gradientWidth;
		}

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));
		gc.drawRectangle(barArea);
	}

	private Rectangle getBarArea() {
		return SWTExtensions.INSTANCE.shrink(getClientArea(), 0, 15, 0, 0);
	}

	@Override
	public Rectangle getClientArea() {
		Point size = getSize();
		return SWTExtensions.INSTANCE.shrink(new Rectangle(0, 0, size.x, size.y), 5, 0, 6, 1);
	}

	private GradientEditItem getItemFor(ColorStop stop) {
		for (GradientEditItem each : items) {
			if (each.getData() == stop) {
				return each;
			}
		}
		return null;
	}

	public Gradient getSelection() {
		return selection;
	}

	private void handleMouseDown(Event event) {
		if (state == 0 && event.button == 1) {
			GradientEditItem target = getItemAt(event);
			setSelectedItem(target);

			if (target != null) {
				dragBegin = new Point(event.x, event.y);
				dragBeginBounds = new Rectangle(target.bounds.x, target.bounds.y, target.bounds.width, target.bounds.height);
				state = 1;
			}
		}
	}

	private void handleMouseMove(Event event) {
		if (state == 1) {
			Point delta = new Point(dragBegin.x - event.x, dragBegin.y - event.y);
			Rectangle newBounds = SWTExtensions.INSTANCE.getTranslated(dragBeginBounds, -delta.x, 0);
			newBounds.x = Math.max(getBarArea().x - GradientEditItem.SIZE.x / 2, newBounds.x);
			newBounds.x = Math.min(getBarArea().x - GradientEditItem.SIZE.x / 2 + getBarArea().width, newBounds.x);
			selectedItem.bounds = newBounds;
			ColorStop colorStop = (ColorStop) selectedItem.getData();
			if (lockOrder) {
				int index = selection.indexOf(colorStop);
				if (index > 0) {
					GradientEditItem prevItem = getItemFor(selection.get(index - 1));
					newBounds.x = Math.max(prevItem.bounds.x, newBounds.x);
				}
				if (index < items.size() - 1) {
					GradientEditItem nextItem = getItemFor(selection.get(index + 1));
					newBounds.x = Math.min(nextItem.bounds.x, newBounds.x);
				}
			}
			colorStop.percent = (int) (((newBounds.x - getBarArea().x + GradientEditItem.SIZE.x / 2) / (double) getBarArea().width) * 100 + .5);

			if (!lockOrder) {
				sort();
			}

			notifyListeners(SWT.Modify, new Event());

			redraw();
		}

		else if (state == 0) {
			GradientEditItem target = getItemAt(event);
			setHotItem(target);
		}
	}

	private GradientEditItem getItemAt(Event event) {
		return getItemAt(event.x, event.y);
	}

	private GradientEditItem getItemAt(int x, int y) {
		GradientEditItem target = null;
		List<GradientEditItem> reverseOrder = new ArrayList<GradientEditItem>(items);
		Collections.reverse(reverseOrder);
		for (GradientEditItem each : reverseOrder) {
			if (SWTExtensions.INSTANCE.contains(each.bounds, x, y)) {
				target = each;
				break;
			}
		}
		return target;
	}

	private void setHotItem(GradientEditItem target) {
		if (target == hotItem) {
			return;
		}

		if (hotItem != null) {
			hotItem.state ^= SWT.HOT;
		}
		hotItem = target;
		if (hotItem != null) {
			hotItem.state |= SWT.HOT;
		}

		redraw();
	}

	private void handleMouseUp(Event event) {
		if (state == 1) {
			selectedItem.state ^= SWT.SELECTED;
			selectedItem = null;
			dragBegin = null;
			dragBeginBounds = null;
			state = 0;
			layoutItems();
			notifyListeners(SWT.Modify, new Event());
			System.out.println(selection.toSWTCSSString());
		}
	}

	public boolean isLockOrder() {
		return lockOrder;
	}

	void layoutItems() {
		for (GradientEditItem each : items) {
			ColorStop colorStop = (ColorStop) each.getData();
			Rectangle barArea = getBarArea();
			int x = barArea.x + (int) (barArea.width * (colorStop.percent / 100d)) - GradientEditItem.SIZE.x / 2;
			each.bounds = new Rectangle(x, barArea.y - 15, GradientEditItem.SIZE.x, GradientEditItem.SIZE.y);
		}
		redraw();
	}

	private void rebuildItems() {
		GradientEditItem[] itemArray = items.toArray(new GradientEditItem[items.size()]);
		for (GradientEditItem eachItem : itemArray) {
			eachItem.dispose();
		}

		if (selection != null) {
			for (ColorStop each : selection) {
				GradientEditItem newItem = new GradientEditItem(this);
				newItem.setData(each);
			}
		}

		layoutItems();
	}

	public void setLockOrder(boolean lockOrder) {
		this.lockOrder = lockOrder;
	}

	private void setSelectedItem(GradientEditItem item) {
		if (selectedItem == item) {
			return;
		}

		if (selectedItem != null) {
			selectedItem.state ^= SWT.SELECTED;
		}
		selectedItem = item;
		selectedItem.state |= SWT.SELECTED;

		if (selectedItem != null) {
			items.remove(selectedItem);
			items.add(selectedItem);
		}
		redraw();
	}

	public void setSelection(Gradient selection, boolean notify) {
		if (this.selection == selection) {
			return;
		}
		this.selection = selection;

		rebuildItems();

		if (notify) {
			notifyListeners(SWT.Selection, new Event());
		}
	}

}
