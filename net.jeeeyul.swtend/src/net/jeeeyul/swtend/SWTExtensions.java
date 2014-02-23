package net.jeeeyul.swtend;

import java.util.HashSet;
import java.util.Iterator;

import net.jeeeyul.swtend.internal.WidgetIterator;
import net.jeeeyul.swtend.sam.Procedure0;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.progress.UIJob;

public class SWTExtensions {
	private static final String KEY_AUTO_RELASE_SCHEDULED = "swtend-auto-relase-scheduled";
	private static final String KEY_AUTO_RELASE_QUEUE = "swtend-auto-relase-queue";
	private static Integer MENU_BAR_HEIGHT = null;

	public static final SWTExtensions INSTANCE = new SWTExtensions();

	private Runnable autoRelease = new Runnable() {
		@Override
		public void run() {
			getDisplay().setData(KEY_AUTO_RELASE_SCHEDULED, Boolean.FALSE);

			HashSet<Resource> queue = getAutoReleaseQueue();
			Resource[] array = queue.toArray(new Resource[queue.size()]);
			queue.clear();

			for (Resource r : array) {
				safeDispose(r);
			}
		}
	};

	private GC sharedGC;

	public Path addArc(Path path, Rectangle box, int startAngle, int angle) {
		path.addArc(box.x, box.y, box.width, box.height, startAngle, angle);
		return path;
	}

	public Path addRectangle(Path path, Rectangle rectangle) {
		path.addRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		return path;
	}

	public Path addRoundRectangle(Path path, Rectangle rectangle, int radius) {
		Rectangle box = new Rectangle(0, 0, radius * 2, radius * 2);
		relocateTopRightWith(box, rectangle);

		moveTo(path, getTopRight(rectangle));
		addArc(path, box, 0, 90);

		relocateTopLeftWith(box, rectangle);
		lineTo(path, getTop(box));
		addArc(path, box, 90, 90);

		relocateBottomLeftWith(box, rectangle);
		lineTo(path, getLeft(box));
		addArc(path, box, 180, 90);

		relocateBottomRightWith(box, rectangle);
		lineTo(path, getBottom(box));
		addArc(path, box, 270, 90);
		lineTo(path, translate(getTopRight(rectangle), 0, radius));

		return path;
	}

	public Path addString(Path me, String text, Point location, Font font) {
		me.addString(text, location.x, location.y, font);
		return me;
	}

	public void asyncExec(final Procedure1<Void> procedure) {
		getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				procedure.apply(null);
			}
		});
	}

	public <T extends Resource> T autoDispose(T resource) {
		scheduleAutoRelease(resource);
		return resource;
	}

	public <T extends Resource> T autoRelease(T resource) {
		scheduleAutoRelease(resource);
		return resource;
	}

	public <T extends Widget> T chainDispose(T widget, final Resource... resources) {
		widget.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				for (Resource each : resources) {
					safeDispose(each);
				}
			}
		});

		return widget;
	}

	/**
	 * @return a black {@link Color} object.
	 * 
	 * @since 1.1.0
	 */
	public Color COLOR_BLACK() {
		return getDisplay().getSystemColor(SWT.COLOR_BLACK);
	}

	/**
	 * 
	 * @return a blue {@link Color} Object
	 * 
	 * @since 1.1.0
	 */
	public Color COLOR_BLUE() {
		return getDisplay().getSystemColor(SWT.COLOR_BLUE);
	}

	/**
	 * 
	 * @return a cyan {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_CYAN() {
		return getDisplay().getSystemColor(SWT.COLOR_CYAN);
	}

	/**
	 * 
	 * @return a dark blue {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_BLUE() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE);
	}

	/**
	 * 
	 * @return a dark cyan {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_CYAN() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_CYAN);
	}

	/**
	 * 
	 * @return a dark gray {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_GRAY() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	/**
	 * 
	 * @return a dark green {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_GREEN() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN);
	}

	/**
	 * 
	 * @return a dark magenta {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_MARGENTA() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_MAGENTA);
	}

	/**
	 * 
	 * @return a dark red {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_RED() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_RED);
	}

	/**
	 * 
	 * @return a dark yellow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_DARK_YELLOW() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW);
	}

	/**
	 * 
	 * @return a gray {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_GRAY() {
		return getDisplay().getSystemColor(SWT.COLOR_GRAY);
	}

	/**
	 * 
	 * @return a green {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_GREEN() {
		return getDisplay().getSystemColor(SWT.COLOR_GREEN);
	}

	/**
	 * 
	 * @return info background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_INFO_BACKGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
	}

	/**
	 * 
	 * @return info foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_INFO_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
	}

	/**
	 * 
	 * @return link foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_LINK_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_LINK_FOREGROUND);
	}

	/**
	 * 
	 * @return list background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_LIST_BACKGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	/**
	 * 
	 * @return list foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_LIST_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	}

	/**
	 * 
	 * @return a magenta {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_MARGENTA() {
		return getDisplay().getSystemColor(SWT.COLOR_MAGENTA);
	}

	/**
	 * 
	 * @return a red {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_RED() {
		return getDisplay().getSystemColor(SWT.COLOR_RED);
	}

	/**
	 * 
	 * @return title background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_BACKGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	}

	/**
	 * 
	 * @return title background gradient {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_BACKGROUND_GRADIENT() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
	}

	/**
	 * 
	 * @return title foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
	}

	/**
	 * 
	 * @return inactive title background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_INACTIVE_BACKGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
	}

	/**
	 * 
	 * @return inactive title background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);
	}

	/**
	 * 
	 * @return inactive title foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_TITLE_INACTIVE_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
	}

	/**
	 * 
	 * @return a white {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WHITE() {
		return getDisplay().getSystemColor(SWT.COLOR_WHITE);
	}

	/**
	 * 
	 * @return widget background {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_BACKGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	}

	/**
	 * 
	 * @return widget border {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_BORDER() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER);
	}

	/**
	 * 
	 * @return widget dark shadow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_DARK_SHADOW() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
	}

	/**
	 * 
	 * @return widget foreground {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_FOREGROUND() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
	}

	/**
	 * 
	 * @return widget highlight shadow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_HIGHLIGHT_SHADOW() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
	}

	/**
	 * 
	 * @return widget light shadow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_LIGHT_SHADOW() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
	}

	/**
	 * 
	 * @return widget normal shadow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_WIDGET_NORMAL_SHADOW() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
	}

	/**
	 * 
	 * @return An yellow {@link Color} object.
	 * @since 1.1.0
	 */
	public Color COLOR_YELLOW() {
		return getDisplay().getSystemColor(SWT.COLOR_YELLOW);
	}

	public Point computeTextExtent(String text, Font font) {
		GC gc = getSharedGC();
		gc.setFont(font);
		return gc.textExtent(text);
	}

	public Point computeTextExtent(String text, Font font, int flags) {
		GC gc = getSharedGC();
		gc.setFont(font);
		return gc.textExtent(text, flags);
	}

	public boolean contains(Point size, Point targetSize) {
		return size.x >= targetSize.x && size.y >= targetSize.y;
	}

	public boolean contains(Rectangle rect, Point point) {
		return rect.x <= point.x && rect.y <= point.y && point.x < rect.x + rect.width && point.y < rect.y + rect.height;
	}

	public boolean contains(Rectangle me, Rectangle other) {
		return me.x <= other.x && me.y <= other.y && (me.x + me.width) >= (other.x + other.width) && (me.y + me.height) >= (other.y + other.height);
	}

	public Path cubicTo(Path path, Point c1, Point c2, Point destination) {
		path.cubicTo(c1.x, c1.y, c2.x, c2.y, destination.x, destination.y);
		return path;
	}

	/**
	 * @deprecated Depricated at 1.1.0, Use {@link #COLOR_DARK_GRAY()} instead.
	 * @return
	 */
	public Color darkGrayColor() {
		return getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
	}

	public GC drawImage(GC gc, Image image, Point location) {
		gc.drawImage(image, location.x, location.y);
		return gc;
	}

	public GC draw(GC gc, Point... polygon) {
		int[] array = toIntArray(polygon);
		gc.drawPolygon(array);
		return gc;
	}

	public GC drawLine(GC gc, Point... polygon) {
		int[] array = toIntArray(polygon);
		gc.drawPolyline(array);
		return gc;
	}

	public GC fill(GC gc, Point... polygon) {
		gc.fillPolygon(toIntArray(polygon));
		return gc;
	}

	private int[] toIntArray(Point... polygon) {
		int[] array = new int[polygon.length * 2];
		int index = 0;
		for (Point each : polygon) {
			array[index++] = each.x;
			array[index++] = each.y;
		}
		return array;
	}

	public void drawImage(GC gc, Image image, Rectangle sourceArea, Rectangle targetArea) {
		gc.drawImage(image, sourceArea.x, sourceArea.y, sourceArea.width, sourceArea.height, targetArea.x, targetArea.y, targetArea.width, targetArea.height);
	}

	public GC drawOval(GC gc, Rectangle rectangle) {
		gc.drawOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		return gc;
	}

	public GC drawRoundRectangle(GC gc, Rectangle rectangle, int radius) {
		gc.drawRoundRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, radius, radius);
		return gc;
	}

	public void drawString(GC gc, String string, Point location) {
		gc.drawString(string, location.x, location.y, true);
	}

	public GC drawTestGrid(GC gc, Rectangle bounds, int gridSize, Color gridColor, int gridAlpha) {
		gc.setForeground(gridColor);
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setLineWidth(1);
		gc.setAlpha(gridAlpha);
		for (int x = bounds.x; x < bounds.x + bounds.width; x += gridSize) {
			gc.drawLine(x, bounds.y, x, bounds.y + bounds.height);
		}

		for (int y = bounds.y; y < bounds.y + bounds.height; y += gridSize) {
			gc.drawLine(bounds.x, y, bounds.x + bounds.width, y);
		}

		return gc;
	}

	public GC drawTestSize(GC gc, Rectangle bounds, int horizontalAlign, int verticalAlign, Color textColor, int textAlpha) {

		gc.setForeground(textColor);
		gc.setAlpha(textAlpha);
		String text = bounds.width + "x" + bounds.height;
		Rectangle textArea = newRectangle(new Point(0, 0), gc.textExtent(text));

		if ((horizontalAlign & SWT.LEFT) != 0) {
			textArea.x = bounds.x;
		} else if ((horizontalAlign & SWT.CENTER) != 0) {
			textArea.x = bounds.x + (bounds.width - textArea.width) / 2;
		} else if ((horizontalAlign & SWT.RIGHT) != 0) {
			textArea.x = bounds.x + bounds.width - textArea.width;
		}

		if ((verticalAlign & SWT.TOP) != 0) {
			textArea.y = bounds.y;
		} else if ((verticalAlign & SWT.CENTER) != 0) {
			textArea.y = bounds.y + (bounds.height - textArea.height) / 2;
		} else if ((verticalAlign & SWT.BOTTOM) != 0) {
			textArea.y = bounds.y + bounds.height - textArea.height;
		}

		drawString(gc, text, getTopLeft(textArea));

		return gc;
	}

	public Rectangle expand(Rectangle rectangle, int amount) {
		return expand(rectangle, amount, amount, amount, amount);
	}

	public Rectangle expand(Rectangle rectangle, int horizontal, int vertical) {
		return expand(rectangle, horizontal, vertical, horizontal, vertical);
	}

	public Rectangle expand(Rectangle rectangle, int left, int top, int right, int bottom) {
		rectangle.x -= left;
		rectangle.y -= top;
		rectangle.width += left + right;
		rectangle.height += top + bottom;
		return rectangle;
	}

	public Rectangle expand(Rectangle rectangle, Point delta) {
		return expand(rectangle, delta.x, delta.y);
	}

	public Rectangle expand(Rectangle rectangle, Rectangle inset) {
		return expand(rectangle, inset.x, inset.y, inset.width, inset.height);
	}

	public GC fill(GC gc, Rectangle rectangle) {
		gc.fillRectangle(rectangle);
		return gc;
	}

	public GC fill(GC gc, Path path) {
		gc.fillPath(path);
		return gc;
	}

	public GC fill(GC gc, int[] pointArray) {
		gc.fillPolygon(pointArray);
		return gc;
	}

	public GC draw(GC gc, Rectangle rectangle) {
		gc.drawRectangle(rectangle);
		return gc;
	}

	public GC draw(GC gc, Path path) {
		gc.drawPath(path);
		return gc;
	}

	public GC draw(GC gc, int[] pointArray) {
		gc.drawPolygon(pointArray);
		return gc;
	}

	public GridData FILL_BOTH() {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		return gridData;
	}

	public GridData FILL_BOTH(final Procedure1<? super GridData> initializer) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		if (initializer != null)
			initializer.apply(gridData);
		return gridData;
	}

	public GridData FILL_HORIZONTAL() {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		return gridData;
	}

	public GridData FILL_HORIZONTAL(final Procedure1<? super GridData> initializer) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		if (initializer != null)
			initializer.apply(gridData);
		return gridData;
	}

	public GC fillOval(GC gc, Rectangle rectangle) {
		gc.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		return gc;
	}

	public GC fillRoundRectangle(GC gc, Rectangle rectangle, int radius) {
		gc.fillRoundRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, radius, radius);
		return gc;
	}

	public Iterator<? extends Widget> getAllContent(Composite root) {
		return new WidgetIterator(root, true);
	}

	private HashSet<Resource> getAutoReleaseQueue() {
		@SuppressWarnings("unchecked")
		HashSet<Resource> queue = (HashSet<Resource>) getDisplay().getData(KEY_AUTO_RELASE_QUEUE);
		if (queue == null) {
			queue = new HashSet<Resource>();
			getDisplay().setData(KEY_AUTO_RELASE_QUEUE, queue);
		}
		return queue;
	}

	public Point getBottom(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height);
	}

	public Point getBottomLeft(Rectangle rectangle) {
		return new Point(rectangle.x, rectangle.y + rectangle.height);
	}

	public Point getBottomRight(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
	}

	public Rectangle getBounds(Event e) {
		return new Rectangle(e.x, e.y, e.width, e.height);
	}

	public Point getCenter(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
	}

	public Point getCopy(Point point) {
		return new Point(point.x, point.y);
	}

	public Rectangle getCopy(Rectangle rectangle) {
		return new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public Point getDifference(Point a, Point b) {
		return new Point(b.x - a.x, b.y - a.y);
	}

	public Display getDisplay() {
		Display display = Display.getDefault();
		return display;
	}

	public double getDistanceTo(Point me, Point to) {
		return Math.sqrt(Math.pow(to.x - me.x, 2) + Math.pow(to.y - me.y, 2));
	}

	public Rectangle getExpanded(Rectangle rectangle, int amount) {
		return expand(getCopy(rectangle), amount);
	}

	public Rectangle getExpanded(Rectangle rectangle, int horizontal, int vertical) {
		return expand(getCopy(rectangle), horizontal, vertical);
	}

	public Rectangle getExpanded(Rectangle rectangle, int left, int top, int right, int bottom) {
		return expand(getCopy(rectangle), left, top, right, bottom);
	}

	public Rectangle getExpanded(Rectangle rectangle, Point delta) {
		return expand(getCopy(rectangle), delta);
	}

	public Rectangle getExpanded(Rectangle rectangle, Rectangle insets) {
		return expand(getCopy(rectangle), insets);
	}

	public Image ICON_CANCEL() {
		return getDisplay().getSystemImage(SWT.ICON_CANCEL);
	}

	public Image ICON_ERROR() {
		return getDisplay().getSystemImage(SWT.ICON_ERROR);
	}

	public Image ICON_INFORMATION() {
		return getDisplay().getSystemImage(SWT.ICON_INFORMATION);
	}

	public Image ICON_QUESTION() {
		return getDisplay().getSystemImage(SWT.ICON_QUESTION);
	}

	public Image ICON_SEARCH() {
		return getDisplay().getSystemImage(SWT.ICON_SEARCH);
	}

	public Image ICON_WARNING() {
		return getDisplay().getSystemImage(SWT.ICON_WARNING);
	}

	public Image ICON_WORKING() {
		return getDisplay().getSystemImage(SWT.ICON_WORKING);
	}

	public ImageRegistry getImageRegistry() {
		ImageRegistry result = (ImageRegistry) Display.getDefault().getData("imageRegistry");
		if (result == null) {
			result = new ImageRegistry();
			Display.getDefault().setData("imageRegistry", result);
		}
		return result;
	}

	public Point getLeft(Rectangle rectangle) {
		return new Point(rectangle.x, rectangle.y + rectangle.height / 2);
	}

	public Point getLocation(Event e) {
		return new Point(e.x, e.y);
	}

	public Point getLocation(Rectangle rectangle) {
		return new Point(rectangle.x, rectangle.y);
	}

	public int getMenubarHeight() {
		if (MENU_BAR_HEIGHT != null) {
			return MENU_BAR_HEIGHT;
		}

		if (Display.getCurrent() == null) {
			throw new SWTException("Invalid Thread Exception");
		}

		Shell dummy = new Shell();
		Menu menu = new Menu(dummy, SWT.BAR);
		dummy.setMenuBar(menu);
		Rectangle boundsWithMenu = dummy.computeTrim(0, 0, 0, 0);

		dummy.setMenuBar(null);
		Rectangle boundsWithoutMenu = dummy.computeTrim(0, 0, 0, 0);

		dummy.dispose();

		MENU_BAR_HEIGHT = boundsWithMenu.height - boundsWithoutMenu.height;

		return MENU_BAR_HEIGHT;
	}

	public Point getNegated(Point me) {
		return negate(getCopy(me));
	}

	public Rectangle getRelocatedBottomLeftWith(Rectangle me, Point BottomLeft) {
		return relocateBottomLeftWith(getCopy(me), BottomLeft);
	}

	public Rectangle getRelocatedBottomLeftWith(Rectangle me, Rectangle offset) {
		return relocateBottomLeftWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedBottomRightWith(Rectangle me, Point BottomRight) {
		return relocateBottomRightWith(getCopy(me), BottomRight);
	}

	public Rectangle getRelocatedBottomRightWith(Rectangle me, Rectangle offset) {
		return relocateBottomRightWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedBottomTo(Rectangle me, Rectangle offset) {
		return relocateBottomWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedBottomWith(Rectangle me, Point Bottom) {
		return relocateBottomWith(getCopy(me), Bottom);
	}

	public Rectangle getRelocatedBottomWith(Rectangle me, Rectangle offset) {
		return relocateBottomWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedCenterWith(Rectangle me, Point Center) {
		return relocateCenterWith(getCopy(me), Center);
	}

	public Rectangle getRelocatedCenterWith(Rectangle me, Rectangle offset) {
		return relocateCenterWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedCetnerWith(Rectangle me, Rectangle offset) {
		return relocateCenterWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedLeftWith(Rectangle me, Point Left) {
		return relocateLeftWith(getCopy(me), Left);
	}

	public Rectangle getRelocatedLeftWith(Rectangle me, Rectangle offset) {
		return relocateLeftWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedRightWith(Rectangle me, Point right) {
		return relocateRightWith(getCopy(me), right);
	}

	public Rectangle getRelocatedRightWith(Rectangle me, Rectangle offset) {
		return relocateRightWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedTopLeftWith(Rectangle me, Point topLeft) {
		return relocateTopLeftWith(getCopy(me), topLeft);
	}

	public Rectangle getRelocatedTopLeftWith(Rectangle me, Rectangle offset) {
		return relocateTopLeftWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedTopRightWith(Rectangle me, Point topRight) {
		return relocateTopRightWith(getCopy(me), topRight);
	}

	public Rectangle getRelocatedTopRightWith(Rectangle me, Rectangle offset) {
		return relocateTopRightWith(getCopy(me), offset);
	}

	public Rectangle getRelocatedTopWith(Rectangle me, Point topLeft) {
		return relocateTopWith(getCopy(me), topLeft);
	}

	public Rectangle getRelocatedTopWith(Rectangle me, Rectangle offset) {
		return relocateTopWith(getCopy(me), offset);
	}

	public Rectangle getResized(Rectangle rectangle, int widthDelta, int heightDelta) {
		return resize(getCopy(rectangle), widthDelta, heightDelta);
	}

	public Rectangle getResized(Rectangle rectangle, Point sizeDelta) {
		return getResized(rectangle, sizeDelta.x, sizeDelta.y);
	}

	public Point getRight(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height / 2);
	}

	public Point getScaled(Point p, double scale) {
		return scale(getCopy(p), scale);
	}

	public Rectangle getScaled(Rectangle r, double scale) {
		return scale(getCopy(r), scale);
	}

	public GC getSharedGC() {
		if (sharedGC == null || sharedGC.isDisposed()) {
			sharedGC = new GC(getDisplay());
		}
		return sharedGC;
	}

	public Rectangle getShrinked(Rectangle rectangle, int amount) {
		return shrink(getCopy(rectangle), amount);
	}

	public Rectangle getShrinked(Rectangle rectangle, int horizontal, int vertical) {
		return shrink(getCopy(rectangle), horizontal, vertical);
	}

	public Rectangle getShrinked(Rectangle rectangle, int left, int top, int right, int bottom) {
		return shrink(getCopy(rectangle), left, top, right, bottom);
	}

	public Rectangle getShrinked(Rectangle rectangle, Point delta) {
		return shrink(getCopy(rectangle), delta);
	}

	public Rectangle getShrinked(Rectangle rectangle, Rectangle insets) {
		return shrink(getCopy(rectangle), insets);
	}

	public Point getSize(Event e) {
		return new Point(e.width, e.height);
	}

	public Point getSize(ImageData imageData) {
		return new Point(imageData.width, imageData.height);
	}

	public Point getSize(Rectangle rect) {
		return new Point(rect.width, rect.height);
	}

	public Point getTop(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width / 2, rectangle.y);
	}

	public Point getTopLeft(Rectangle rectangle) {
		return new Point(rectangle.x, rectangle.y);
	}

	public Point getTopRight(Rectangle rectangle) {
		return new Point(rectangle.x + rectangle.width, rectangle.y);
	}

	public Point getTranslated(Point point, int dx, int dy) {
		return translate(getCopy(point), dx, dy);
	}

	public Point getTranslated(Point point, Point delta) {
		return getTranslated(point, delta.x, delta.y);
	}

	public Rectangle getTranslated(Rectangle source, int dx, int dy) {
		return translate(getCopy(source), dx, dy);
	}

	public Rectangle getTranslated(Rectangle source, Point delta) {
		return translate(getCopy(source), delta);
	}

	public Point getTransposed(Point me) {
		return new Point(me.y, me.x);
	}

	public Rectangle getTransposed(Rectangle me) {
		return new Rectangle(me.y, me.x, me.height, me.width);
	}

	public Rectangle getUnionized(Rectangle me, int x, int y) {
		return union(getCopy(me), x, y);
	}

	public Rectangle getUnionized(Rectangle me, int x, int y, int w, int h) {
		return union(getCopy(me), x, y, w, h);
	}

	public Rectangle getUnionized(Rectangle me, Point point) {
		return union(getCopy(me), point);
	}

	public Rectangle getUnionized(Rectangle me, Rectangle other) {
		return union(getCopy(me), other);
	}

	public <T extends Object> T init(T widget, Procedure1<T> initializer) {
		if (initializer != null)
			initializer.apply(widget);
		return widget;
	}

	public Path lineTo(Path path, Point location) {
		path.lineTo(location.x, location.y);
		return path;
	}

	/**
	 * 
	 * @param path
	 * @param location
	 * @since 1.2.0
	 * @return current context.
	 */
	public Path moveTo(Path path, Point location) {
		path.moveTo(location.x, location.y);
		return path;
	}

	public Point negate(Point point) {
		return new Point(-point.x, -point.y);
	}

	public Button newButton(final Composite parent, int style, Procedure1<? super Button> initializer) {
		Button button = new Button(parent, style);
		if (initializer != null)
			initializer.apply(button);
		return button;
	}

	public Button newCheckbox(final Composite parent, final Procedure1<? super Button> initializer) {
		Button button = new Button(parent, SWT.CHECK);
		if (initializer != null)
			initializer.apply(button);
		return button;
	}

	public CLabel newCLabel(final Composite parent, final Procedure1<? super CLabel> initializer) {
		CLabel label = new CLabel(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Combo newCombo(final Composite parent, int style, final Procedure1<? super Combo> initializer) {
		Combo combo = new Combo(parent, style);
		if (initializer != null)
			initializer.apply(combo);
		return combo;
	}

	public Canvas newCanvas(final Composite parent, Procedure1<Canvas> initializer) {
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		if (initializer != null) {
			initializer.apply(canvas);
		}
		return canvas;
	}

	public Composite newComposite(final Composite parent, int style, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(parent, style);
		composite.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(composite);
		return composite;
	}

	public Composite newComposite(final Composite parent, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(parent, SWT.NORMAL);
		composite.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(composite);
		return composite;
	}

	public Composite newComposite(final CTabItem ctabItem, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(ctabItem.getParent(), SWT.NORMAL);
		composite.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(composite);
		ctabItem.setControl(composite);
		return composite;
	}

	public Composite newComposite(final TabItem tabItem, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(tabItem.getParent(), SWT.NORMAL);
		composite.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(composite);
		tabItem.setControl(composite);
		return composite;
	}

	public CTabFolder newCTabFolder(Composite parent, int style, final Procedure1<? super CTabFolder> initializer) {
		CTabFolder tabFolder = new CTabFolder(parent, style);
		if (initializer != null)
			initializer.apply(tabFolder);
		return tabFolder;
	}

	public CTabFolder newCTabFolder(Composite parent, final Procedure1<? super CTabFolder> initializer) {
		CTabFolder tabFolder = new CTabFolder(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(tabFolder);
		return tabFolder;
	}

	public CTabItem newCTabItem(CTabFolder tabFolder, Procedure1<? super CTabItem> initializer) {
		CTabItem item = new CTabItem(tabFolder, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public FillLayout newFillLayout() {
		return new FillLayout();
	}

	public Font newFont(String fontName, int height) {
		Font font = new Font(getDisplay(), fontName, height, SWT.NORMAL);
		return font;
	}

	public Font newFont(String fontName, int height, int style) {
		Font font = new Font(getDisplay(), fontName, height, style);
		return font;
	}

	public Pattern newGradient(Point from, Point to, Color fromColor, Color toColor) {
		return new Pattern(getDisplay(), from.x, from.y, to.x, to.y, fromColor, toColor);
	}

	public Pattern newGradient(Point from, Point to, Color fromColor, int fromAlpha, Color toColor, int toAlpha) {
		return new Pattern(getDisplay(), from.x, from.y, to.x, to.y, fromColor, fromAlpha, toColor, toAlpha);
	}

	public GridData newGridData(final Procedure1<? super GridData> initializer) {
		GridData gridData = new GridData();
		if (initializer != null)
			initializer.apply(gridData);
		return gridData;
	}

	public GridLayout newGridLayout() {
		return new GridLayout();
	}

	public GridLayout newGridLayout(Procedure1<GridLayout> initializer) {
		GridLayout gridLayout = new GridLayout();
		if (initializer != null)
			initializer.apply(gridLayout);
		return gridLayout;
	}

	public Group newGroup(final Composite parent, final Procedure1<? super Group> initializer) {
		Group group = new Group(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(group);
		return group;
	}

	public Label newHorizontalSeparator(final Composite parent, final Procedure1<? super Label> initializer) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		if (initializer != null)
			initializer.apply(separator);

		Layout layout = parent.getLayout();
		if (layout instanceof GridLayout) {
			GridLayout gridLayout = (GridLayout) layout;
			separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, gridLayout.numColumns, 1));
		}
		return separator;
	}

	public Label newHorizontalSeperator(final Composite parent, final Procedure1<? super Label> initializer) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		if (initializer != null)
			initializer.apply(separator);
		return separator;
	}

	public Rectangle newInsets(int insets) {
		return new Rectangle(insets, insets, insets, insets);
	}

	public Rectangle newInsets(int left, int top, int right, int bottom) {
		return new Rectangle(left, top, right, bottom);
	}

	public Label newLabel(final Composite parent, final Procedure1<? super Label> initializer) {
		Label label = new Label(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Link newLink(final Composite parent, final Procedure1<? super Link> initializer) {
		Link link = new Link(parent, SWT.CHECK);
		if (initializer != null)
			initializer.apply(link);
		return link;
	}

	public PageBook newPageBook(final Composite parent, final Procedure1<? super PageBook> initializer) {
		PageBook pageBook = new PageBook(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(pageBook);
		return pageBook;
	}

	public Text newPasswordField(final Composite parent, final Procedure1<? super Text> initializer) {
		Text _text = new Text(parent, SWT.BORDER | SWT.PASSWORD);
		Text label = _text;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Path newPath(Procedure1<Path> initializer) {
		Path path = new Path(getDisplay());
		if (initializer != null) {
			initializer.apply(path);
		}
		return path;
	}

	public Button newPushButton(final Composite parent, final Procedure1<? super Button> initializer) {
		Button _button = new Button(parent, SWT.PUSH);
		Button label = _button;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Button newRadioButton(final Composite parent, final Procedure1<? super Button> initializer) {
		Button _button = new Button(parent, SWT.RADIO);
		Button label = _button;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Text newReadOnlyTextField(final Composite parent, final Procedure1<? super Text> initializer) {
		Text _text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		Text label = _text;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public Rectangle newRectangle() {
		return new Rectangle(0, 0, 0, 0);
	}

	public Rectangle newRectangle(Point location, Point size) {
		return new Rectangle(location.x, location.y, size.x, size.y);
	}

	public Rectangle newRectangleWithSize(Point size) {
		return new Rectangle(0, 0, size.x, size.y);
	}

	public TreeItem newRootItem(Tree tree, final Procedure1<TreeItem> initializer) {
		TreeItem item = new TreeItem(tree, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public Path newRoundRectanglePath(int x, int y, int width, int height, int radius) {
		Path path = new Path(getDisplay());

		return path;
	}

	public Scale newScale(final Composite parent, final Procedure1<? super Scale> initializer) {
		Scale scale = new Scale(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(scale);
		return scale;
	}

	public Text newSearchField(final Composite parent, final Procedure1<? super Text> initializer) {
		Text _text = new Text(parent, SWT.BORDER | SWT.SEARCH);
		Text label = _text;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	/**
	 * 
	 * @param parent
	 * @param initializer
	 * @return
	 * 
	 * @deprecated deprecated at 1.1.0 use
	 *             {@link #newHorizontalSeparator(Composite, Procedure1)}
	 *             instead.
	 */
	public Label newSeparator(final Composite parent, final Procedure1<? super Label> initializer) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		if (initializer != null)
			initializer.apply(separator);
		Layout layout = parent.getLayout();
		if (layout instanceof GridLayout) {
			GridLayout gridLayout = (GridLayout) layout;
			separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, gridLayout.numColumns, 1));
		}
		return separator;
	}

	public Shell newShell(int style, Procedure1<? super Shell> initializer) {
		Shell shell = new Shell(getDisplay(), style);
		shell.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(shell);
		return shell;
	}

	public Shell newShell(Procedure1<? super Shell> initializer) {
		Shell shell = new Shell(getDisplay());
		shell.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(shell);
		return shell;
	}

	public Shell newShell(Shell parentShell, int style, Procedure1<? super Shell> initializer) {
		Shell shell = new Shell(parentShell, style);
		shell.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(shell);
		return shell;
	}

	public Shell newShell(Shell parentShell, Procedure1<? super Shell> initializer) {
		Shell shell = new Shell(parentShell);
		shell.setLayout(new FillLayout());
		if (initializer != null)
			initializer.apply(shell);
		return shell;
	}

	public Point newSize(int width, int height) {
		return new Point(width, height);
	}

	public TreeItem newSubItem(TreeItem parent, final Procedure1<TreeItem> initializer) {
		TreeItem item = new TreeItem(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public TabFolder newTabFolder(Composite parent, int style, final Procedure1<? super TabFolder> initializer) {
		TabFolder tabFolder = new TabFolder(parent, style);
		if (initializer != null)
			initializer.apply(tabFolder);
		return tabFolder;
	}

	public TabFolder newTabFolder(Composite parent, final Procedure1<? super TabFolder> initializer) {
		TabFolder tabFolder = new TabFolder(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(tabFolder);
		return tabFolder;
	}

	public TabItem newTabItem(TabFolder tabFolder, Procedure1<? super TabItem> initializer) {
		TabItem item = new TabItem(tabFolder, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public Table newTable(Composite parent, final Procedure1<Table> initializer) {
		Table table = new Table(parent, SWT.BORDER);
		if (initializer != null)
			initializer.apply(table);
		return table;
	}

	public TableItem newTableItem(Table parent, final Procedure1<TableItem> initializer) {
		TableItem tableItem = new TableItem(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(tableItem);
		return tableItem;
	}

	public Text newText(final Composite parent, int style, final Procedure1<? super Text> initializer) {
		Text text = new Text(parent, style);
		if (initializer != null)
			initializer.apply(text);
		return text;
	}

	public Text newTextArea(final Composite parent, final Procedure1<? super Text> initializer) {
		Text text = new Text(parent, SWT.MULTI | SWT.BORDER);
		if (initializer != null)
			initializer.apply(text);
		return text;
	}

	public Text newTextField(final Composite parent, int style, final Procedure1<? super Text> initializer) {
		Text text = new Text(parent, style);
		if (initializer != null)
			initializer.apply(text);
		return text;
	}

	public Text newTextField(final Composite parent, final Procedure1<? super Text> initializer) {
		Text text = new Text(parent, SWT.BORDER);
		if (initializer != null)
			initializer.apply(text);
		return text;
	}

	public Thread newThread(final Procedure1<Thread> runnable) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				runnable.apply(Thread.currentThread());
			}
		});
	}

	public ToolBar newToolBar(final Composite parent, int style, final Procedure1<? super ToolBar> initializer) {
		ToolBar toolBar = new ToolBar(parent, style);
		if (initializer != null)
			initializer.apply(toolBar);
		return toolBar;
	}

	public ToolBar newToolBar(final Composite parent, final Procedure1<? super ToolBar> initializer) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT);
		if (initializer != null)
			initializer.apply(toolBar);
		return toolBar;
	}

	public ToolItem newToolItem(final ToolBar parent, int style, final Procedure1<? super ToolItem> initializer) {
		ToolItem item = new ToolItem(parent, style);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public ToolItem newToolItem(final ToolBar parent, final Procedure1<? super ToolItem> initializer) {
		ToolItem item = new ToolItem(parent, SWT.FLAT);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public Transform newTransform(Procedure1<Transform> initializer) {
		Transform transform = new Transform(getDisplay());
		if (initializer != null) {
			initializer.apply(transform);
		}
		return transform;
	}

	public Tree newTree(Composite parent, int style, final Procedure1<Tree> initializer) {
		Tree tree = new Tree(parent, style);
		if (initializer != null)
			initializer.apply(tree);
		return tree;
	}

	public Tree newTree(Composite parent, final Procedure1<Tree> initializer) {
		Tree tree = new Tree(parent, SWT.BORDER);
		if (initializer != null)
			initializer.apply(tree);
		return tree;
	}

	public TreeColumn newTreeColumn(Tree tree, final Procedure1<TreeColumn> initializer) {
		TreeColumn column = new TreeColumn(tree, SWT.DEFAULT);
		if (initializer != null)
			initializer.apply(column);
		return column;
	}

	public TreeItem newTreeItem(Tree tree, final Procedure1<TreeItem> initializer) {
		TreeItem item = new TreeItem(tree, SWT.DEFAULT);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public TreeItem newTreeItem(TreeItem parent, final Procedure1<TreeItem> initializer) {
		TreeItem item = new TreeItem(parent, SWT.DEFAULT);
		if (initializer != null)
			initializer.apply(item);
		return item;
	}

	public UIJob newUIJob(final Procedure0 work) {
		UIJob uiJob = new UIJob("job") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				work.apply();
				return Status.OK_STATUS;
			}
		};
		uiJob.setSystem(true);
		uiJob.setUser(false);
		return uiJob;
	}

	public Label newVerticalSeperator(final Composite parent, final Procedure1<? super Label> initializer) {
		Label _label = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);
		Label label = _label;
		if (initializer != null)
			initializer.apply(label);
		return label;
	}

	public <T extends Widget> void onEvent(final T widget, int eventType, final Procedure1<Event> handler) {
		widget.addListener(eventType, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public int operator_and(int e1, int e2) {
		return e1 & e2;
	}

	public int operator_or(int e1, int e2) {
		return e1 | e2;
	}

	public Path quadTo(Path path, Point c, Point destination) {
		path.quadTo(c.x, c.y, destination.x, destination.y);
		return path;
	}

	public Rectangle relocateBottomLeftWith(Rectangle me, Point bottomLeft) {
		me.x = bottomLeft.x;
		me.y = bottomLeft.y - me.height;
		return me;
	}

	public Rectangle relocateBottomLeftWith(Rectangle me, Rectangle offset) {
		return relocateBottomLeftWith(me, getBottomLeft(offset));
	}

	public Rectangle relocateBottomRightWith(Rectangle me, Point bottomRight) {
		me.x = bottomRight.x - me.width;
		me.y = bottomRight.y - me.height;
		return me;
	}

	public Rectangle relocateBottomRightWith(Rectangle me, Rectangle offset) {
		return relocateBottomRightWith(me, getBottomRight(offset));
	}

	public Rectangle relocateBottomWith(Rectangle me, Point bottom) {
		me.x = bottom.x - me.width / 2;
		me.y = bottom.y - me.height;
		return me;
	}

	public Rectangle relocateBottomWith(Rectangle me, Rectangle offset) {
		return relocateBottomWith(me, getBottom(offset));
	}

	public Rectangle relocateCenterWith(Rectangle me, Point center) {
		me.x = center.x - me.width / 2;
		me.y = center.y - me.height / 2;
		return me;
	}

	public Rectangle relocateCenterWith(Rectangle me, Rectangle other) {
		return relocateCenterWith(me, getCenter(other));
	}

	public Rectangle relocateLeftWith(Rectangle me, Point left) {
		me.x = left.x;
		me.y = left.y - me.height / 2;
		return me;
	}

	public Rectangle relocateLeftWith(Rectangle me, Rectangle offset) {
		return relocateLeftWith(me, getLeft(offset));
	}

	public Rectangle relocateRightWith(Rectangle me, Point right) {
		me.x = right.x - me.width;
		me.y = right.y - me.height / 2;
		return me;
	}

	public Rectangle relocateRightWith(Rectangle me, Rectangle offset) {
		return relocateRightWith(me, getRight(offset));
	}

	public Rectangle relocateTopLeftWith(Rectangle me, Point topLeft) {
		me.x = topLeft.x;
		me.y = topLeft.y;
		return me;
	}

	public Rectangle relocateTopLeftWith(Rectangle me, Rectangle offset) {
		return relocateTopLeftWith(me, getTopLeft(offset));
	}

	public Rectangle relocateTopRightWith(Rectangle me, Point topRight) {
		me.x = topRight.x - me.width;
		me.y = topRight.y;
		return me;
	}

	public Rectangle relocateTopRightWith(Rectangle me, Rectangle offset) {
		return relocateTopRightWith(me, getTopRight(offset));
	}

	public Rectangle relocateTopWith(Rectangle me, Point top) {
		me.x = top.x - me.width / 2;
		me.y = top.y;
		return me;
	}

	public Rectangle relocateTopWith(Rectangle me, Rectangle offset) {
		return relocateTopWith(me, getTop(offset));
	}

	public Rectangle resize(Rectangle rectangle, int width, int height) {
		rectangle.width += width;
		rectangle.height += height;
		return rectangle;
	}

	public Rectangle resize(Rectangle rectangle, Point delta) {
		return resize(rectangle, delta.x, delta.y);
	}

	public void runLoop(final Shell s) {
		if (s.isDisposed()) {
			return;
		}
		while (!s.isDisposed()) {
			if (!getDisplay().readAndDispatch()) {
				getDisplay().sleep();
			}
		}
	}

	public void openAndRunLoop(final Shell s) {
		if (s.isDisposed()) {
			return;
		}
		s.open();
		while (!s.isDisposed()) {
			if (!getDisplay().readAndDispatch()) {
				getDisplay().sleep();
			}
		}
	}

	public void safeDispose(Resource resource) {
		if (resource != null && !resource.isDisposed()) {
			resource.dispose();
		}
	}

	public Point scale(Point p, double scale) {
		p.x *= scale;
		p.y *= scale;
		return p;
	}

	public Rectangle scale(Rectangle p, double scale) {
		p.x *= scale;
		p.y *= scale;
		p.width *= scale;
		p.height *= scale;
		return p;
	}

	public Transform scale(Transform me, float scale) {
		me.scale(scale, scale);
		return me;
	}

	public void schedule(final Procedure1<Display> p) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				p.apply(Display.getDefault());
			}
		});
	}

	private void scheduleAutoRelease(Resource r) {
		getAutoReleaseQueue().add(r);

		if (getDisplay().getData(KEY_AUTO_RELASE_SCHEDULED) == Boolean.TRUE) {
			return;
		}

		getDisplay().setData(KEY_AUTO_RELASE_SCHEDULED, Boolean.TRUE);
		getDisplay().asyncExec(autoRelease);
	}

	public Rectangle setBottom(Rectangle me, int bottom) {
		me.height = bottom - me.y;
		return me;
	}

	public Rectangle setBottomLeft(Rectangle me, int x, int y) {
		setLeft(me, x);
		setBottom(me, y);
		return me;
	}

	public Rectangle setBottomLeft(Rectangle me, Point bottomLeft) {
		return setBottomLeft(me, bottomLeft.x, bottomLeft.y);
	}

	public Rectangle setBottomRight(Rectangle me, int x, int y) {
		setRight(me, x);
		setBottom(me, y);
		return me;
	}

	public Rectangle setBottomRight(Rectangle me, Point bottomRight) {
		return setBottomRight(me, bottomRight.x, bottomRight.y);
	}

	public Rectangle setLeft(Rectangle me, int left) {
		int right = me.x + me.width;
		me.x = left;
		me.width = right - me.x;
		return me;
	}

	public Rectangle setLocation(Rectangle rectangle, int x, int y) {
		rectangle.x = x;
		rectangle.y = y;
		return rectangle;
	}

	public Rectangle setLocation(Rectangle rectangle, Point location) {
		rectangle.x = location.x;
		rectangle.y = location.y;
		return rectangle;
	}

	public <T extends Shell> void setOnActivate(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Activate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends MenuItem> void setOnArm(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Arm, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnClick(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnClose(final Shell control, final Procedure1<Event> function) {
		control.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnCollapse(final Tree control, final Procedure1<Event> function) {
		control.addListener(SWT.Collapse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnDeactivate(final Shell shell, final Procedure1<Event> handler) {
		shell.addListener(SWT.Deactivate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnDefaultSelection(final Control control, final Procedure1<Event> function) {
		control.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnDeiconify(final Shell control, final Procedure1<Event> function) {
		control.addListener(SWT.Deiconify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnDispose(final Control control, final Procedure1<Event> function) {
		control.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public <T extends Control> void setOnDragDetect(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.DragDetect, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnEraseItem(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.EraseItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Widget> void setOnEvent(final T widget, int eventType, final Procedure1<Event> handler) {
		widget.addListener(eventType, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnExpand(final Tree control, final Procedure1<Event> function) {
		control.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnFocus(final Control control, final Procedure1<Event> handler) {
		control.addListener(SWT.FocusIn, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnFocusIn(final Control control, final Procedure1<Event> function) {
		control.addListener(SWT.FocusIn, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnFocusOut(final Control control, final Procedure1<Event> handler) {
		control.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnHelp(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Help, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnHide(final Shell control, final Procedure1<Event> function) {
		control.addListener(SWT.Hide, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnIconify(final Shell control, final Procedure1<Event> function) {
		control.addListener(SWT.Iconify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnKeyDown(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnKeyUp(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.KeyUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public <T extends Control> void setOnMeasureItem(final T control, Listener handler) {
		control.addListener(SWT.MeasureItem, handler);
	}

	public <T extends Control> void setOnModified(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnModify(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnMouseDoubleClick(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnMouseDown(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnMouseEnter(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseEnter, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnMouseExit(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseExit, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public <T extends Control> void setOnMouseHorizontalWheel(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.MouseHorizontalWheel, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnMouseHover(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.MouseHover, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnMouseMove(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnMouseUp(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public <T extends Control> void setOnMouseVerticalWheel(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.MouseVerticalWheel, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnMouseWheel(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.MouseWheel, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnMove(final Control control, final Procedure1<Event> function) {
		control.addListener(SWT.Move, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public void setOnPaint(Control control, Listener renderer) {
		control.addListener(SWT.Paint, renderer);
	}

	public <T extends Control> void setOnPaintItem(final T control, Listener handler) {
		control.addListener(SWT.PaintItem, handler);
	}

	public void setOnResize(final Control control, Listener listener) {
		control.addListener(SWT.Resize, listener);
	}

	public <T extends Widget> void setOnSelection(final T w, final Procedure1<Event> handler) {
		w.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public void setOnShow(final Shell shell, Listener listener) {
		shell.addListener(SWT.Show, listener);
	}

	public <T extends Control> void setOnTraverse(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Control> void setOnVerify(final T control, final Procedure1<Event> handler) {
		control.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public Rectangle setRight(Rectangle me, int right) {
		me.width = right - me.x;
		return me;
	}

	public Rectangle setSize(Rectangle rectangle, int width, int height) {
		rectangle.width = width;
		rectangle.height = height;
		return rectangle;
	}

	public Rectangle setSize(Rectangle rectangle, Point size) {
		rectangle.width = size.x;
		rectangle.height = size.y;
		return rectangle;
	}

	public Rectangle setTop(Rectangle me, int top) {
		int bottom = me.y + me.height;
		me.y = top;
		me.height = bottom - top;
		return me;
	}

	public Rectangle setTopLeft(Rectangle me, int x, int y) {
		setLeft(me, x);
		setTop(me, y);
		return me;
	}

	public Rectangle setTopLeft(Rectangle me, Point topLeft) {
		return setTopLeft(me, topLeft.x, topLeft.y);
	}

	public Rectangle setTopRight(Rectangle me, int x, int y) {
		setRight(me, x);
		setTop(me, y);
		return me;
	}

	public Rectangle setTopRight(Rectangle me, Point topRight) {
		return setTopRight(me, topRight.x, topRight.y);
	}

	public Shell Shell(final Procedure1<? super Shell> initializer) {
		Shell _shell = new Shell();
		Shell s = _shell;
		if (initializer != null)
			initializer.apply(s);
		return s;
	}

	public void shouldDisposeWith(final Resource resource, Widget widget) {
		widget.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				safeDispose(resource);
			}
		});
	}

	public <T extends Control> T showTestGrid(T control) {
		return showTestGrid(control, 10, COLOR_RED(), 100);
	}

	public <T extends Control> T showTestGrid(final T control, final int gridSize, final Color color, final int alpha) {
		control.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle relativeBounds = relocateTopLeftWith(control.getBounds(), new Point(0, 0));
				drawTestGrid(event.gc, relativeBounds, gridSize, color, alpha);
			}
		});

		return control;
	}

	public <T extends Control> T showTestSize(final T control, final int horizontal, final int vertical, final Color color, final int alpha) {
		control.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle relativeBounds = relocateTopLeftWith(control.getBounds(), new Point(0, 0));
				drawTestSize(event.gc, relativeBounds, horizontal, vertical, color, alpha);
			}
		});
		return control;
	}

	public <T extends Control> T showTestSize(final T control, final Color color, final int alpha) {
		return showTestSize(control, SWT.CENTER, SWT.CENTER, color, alpha);
	}

	public <T extends Control> T showTestSize(final T control) {
		return showTestSize(control, SWT.CENTER, SWT.CENTER, COLOR_RED(), 255);
	}

	public Rectangle shrink(Rectangle rectangle, int amount) {
		return expand(rectangle, -amount);
	}

	public Rectangle shrink(Rectangle rectangle, int horizontal, int vertical) {
		return expand(rectangle, -horizontal, -vertical);
	}

	public Rectangle shrink(Rectangle rectangle, int left, int top, int right, int bottom) {
		return expand(rectangle, -left, -top, -right, -bottom);
	}

	public Rectangle shrink(Rectangle rectangle, Point delta) {
		return expand(rectangle, -delta.x, -delta.y);
	}

	public Rectangle shrink(Rectangle rectangle, Rectangle inset) {
		return expand(rectangle, -inset.x, -inset.y, -inset.width, -inset.height);
	}

	public void syncExec(final Procedure1<Void> procedure) {
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				procedure.apply(null);
			}
		});
	}

	/**
	 * Converts {@link Color} object to {@link HSB} Object.
	 * 
	 * @param color
	 * @return A {@link HSB} Object.
	 * @since 1.1.0
	 */
	public HSB toHSB(Color color) {
		return new HSB(color.getRGB());
	}

	/**
	 * Converts {@link RGB} object to {@link HSB} object.
	 * 
	 * @param rgb
	 * @return
	 * 
	 * @since 1.1.0
	 */
	public HSB toHSB(RGB rgb) {
		return new HSB(rgb);
	}

	public String toHTMLCode(RGB rgb) {
		return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
	}

	public Rectangle toRectangle(Point point) {
		return new Rectangle(point.x, point.y, 0, 0);
	}

	public Point translate(Point point, int dx, int dy) {
		point.x += dx;
		point.y += dy;
		return point;
	}

	public Point translate(Point point, Point delta) {
		return translate(point, delta.x, delta.y);
	}

	public Rectangle translate(Rectangle rectangle, int dx, int dy) {
		rectangle.x += dx;
		rectangle.y += dy;
		return rectangle;
	}

	public Rectangle translate(Rectangle rectangle, Point delta) {
		return translate(rectangle, delta.x, delta.y);
	}

	public Transform translate(Transform me, Point location) {
		me.translate(location.x, location.y);
		return me;
	}

	public Point transpose(Point me) {
		Point copy = getCopy(me);
		me.x = copy.y;
		me.y = copy.x;
		return me;
	}

	public Rectangle transpose(Rectangle me) {
		Rectangle copy = getCopy(me);
		me.x = copy.y;
		me.y = copy.x;
		me.width = copy.height;
		me.height = copy.width;
		return me;
	}

	public Rectangle union(Rectangle rectangle, int x, int y) {
		if (x < rectangle.x) {
			rectangle.width += rectangle.x - x;
			rectangle.x = x;
		} else {
			int right = rectangle.x + rectangle.width;
			if (x >= right) {
				right = x + 1;
				rectangle.width = right - rectangle.x;
			}
		}

		if (y < rectangle.y) {
			rectangle.height += rectangle.y - y;
			rectangle.y = y;
		} else {
			int bottom = rectangle.y + rectangle.height;
			if (y <= bottom) {
				bottom = y + 1;
				rectangle.height = bottom - rectangle.y;
			}
		}

		return rectangle;
	}

	public Rectangle union(Rectangle me, int x, int y, int w, int h) {
		int right = Math.max(me.x + me.width, x + w);
		int bottom = Math.max(me.y + me.height, y + h);
		me.x = Math.min(me.x, x);
		me.y = Math.min(me.y, y);
		me.width = right - me.x;
		me.height = bottom - me.y;
		return me;
	}

	public boolean hasFlags(int flags, int... mask) {
		for (int each : mask) {
			if ((flags & each) == 0) {
				return false;
			}
		}
		return true;
	}

	public Rectangle union(Rectangle me, Point point) {
		return union(me, point.x, point.y);
	}

	public Rectangle union(Rectangle me, Rectangle other) {
		return union(me, other.x, other.y, other.width, other.height);
	}

	public String shortenText(GC gc, String text, int width, String ellipses) {
		return shortenText(gc, text, width, ellipses, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);
	}

	public String shortenText(GC gc, String text, int width, String ellipses, int flags) {
		if (gc.textExtent(text, flags).x <= width)
			return text;
		int ellipseWidth = gc.textExtent(ellipses, flags).x;
		int length = text.length();
		TextLayout layout = new TextLayout(getDisplay());
		layout.setText(text);
		int end = layout.getPreviousOffset(length, SWT.MOVEMENT_CLUSTER);
		while (end > 0) {
			text = text.substring(0, end);
			int l = gc.textExtent(text, flags).x;
			if (l + ellipseWidth <= width) {
				break;
			}
			end = layout.getPreviousOffset(end, SWT.MOVEMENT_CLUSTER);
		}
		layout.dispose();
		return end == 0 ? text.substring(0, 1) : text + ellipses;
	}

	public GC withClip(GC gc, Rectangle clip, Procedure1<GC> block) {
		Rectangle oldClip = gc.getClipping();
		if (block != null) {
			gc.setClipping(clip);
			block.apply(gc);
		}

		gc.setClipping(oldClip);
		return gc;
	}
}
