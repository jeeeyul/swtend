package net.jeeeyul.swtend.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ColorWell extends Canvas {
	private static final int NONE = 0;
	private static final int PRESSED = 1;
	private static final int EXIT = 2;

	private HSB selection = new HSB();
	private int state = NONE;

	private void setState(int state) {
		if (this.state == state || isDisposed()) {
			return;
		}
		this.state = state;
		redraw();
	}

	public ColorWell(Composite parent, int style) {
		super(parent, style | SWT.BORDER);
		addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				onPaint(event);
			}
		});

		addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.button == 1) {
					setState(PRESSED);
				}
			}
		});

		addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (state == PRESSED || state == EXIT) {
					Rectangle clientArea = getClientArea();
					if (clientArea.x <= event.x && event.x <= clientArea.x + clientArea.width && clientArea.y <= event.y
							&& event.y <= clientArea.y + clientArea.height) {
						setState(PRESSED);
					} else {
						setState(EXIT);
					}
				}
			}
		});

		addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (state == PRESSED) {
					notifyListeners(SWT.Selection, new Event());
				}
				if (event.button == 1) {
					setState(NONE);
				}
			}
		});
	}

	public HSB getSelection() {
		return selection;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return new Point(30, 20);
	}

	protected void onPaint(Event event) {
		if (selection == null) {
			return;
		}

		Color color = new Color(getDisplay(), selection.toRGB());
		event.gc.setBackground(color);
		Rectangle clientArea = getClientArea();
		event.gc.fillRectangle(clientArea);
		color.dispose();

		if (state == PRESSED) {
			event.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
			event.gc.setAlpha(100);
			event.gc.setLineWidth(3);

			int[] shadow = new int[] { clientArea.x, clientArea.y + clientArea.height, clientArea.x, clientArea.y, clientArea.x + clientArea.width,
					clientArea.y };
			event.gc.drawPolyline(shadow);
		}
	}

	public void setSelection(HSB selection, boolean notify) {
		if (selection == null) {
			throw new IllegalArgumentException();
		}
		if (this.selection == selection || this.selection.equals(selection)) {
			return;
		}

		this.selection = selection;
		redraw();
		if (notify) {
			Event event = new Event();
			event.widget = this;
			notifyListeners(SWT.Modify, event);
		}
	}

	public void setSelection(HSB selection) {
		setSelection(selection, true);
	}

}
