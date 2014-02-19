package net.jeeeyul.swtend;

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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.progress.UIJob;

public class SWTExtensions {
	private static Integer MENU_BAR_HEIGHT = null;

	public static final SWTExtensions INSTANCE = new SWTExtensions();

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

	public boolean contains(Point size, Point targetSize) {
		return size.x >= targetSize.x && size.y >= targetSize.y;
	}

	public boolean contains(Rectangle rect, Point point) {
		return rect.x <= point.x && rect.y <= point.y && point.x <= rect.x + rect.width && point.y <= rect.y + rect.height;
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

	public void drawImage(GC gc, Image image, Rectangle sourceArea, Rectangle targetArea) {
		gc.drawImage(image, sourceArea.x, sourceArea.y, sourceArea.width, sourceArea.height, targetArea.x, targetArea.y, targetArea.width, targetArea.height);
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

	public GC fillRoundRectangle(GC gc, Rectangle rectangle, int radius) {
		gc.fillRoundRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, radius, radius);
		return gc;
	}

	public Iterator<? extends Widget> getAllContent(Composite root) {
		return new WidgetIterator(root, true);
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

	public <T extends Object> T init(T widget, Procedure1<T> initializer) {
		if (initializer != null)
			initializer.apply(widget);
		return widget;
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

	public Composite newComposite(final Composite parent, int style, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(parent, style);
		if (initializer != null)
			initializer.apply(composite);
		return composite;
	}

	public Composite newComposite(final Composite parent, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(parent, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(composite);
		return composite;
	}

	public Composite newComposite(final CTabItem ctabItem, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(ctabItem.getParent(), SWT.NORMAL);
		if (initializer != null)
			initializer.apply(composite);
		ctabItem.setControl(composite);
		return composite;
	}

	public Composite newComposite(final TabItem tabItem, final Procedure1<? super Composite> initializer) {
		Composite composite = new Composite(tabItem.getParent(), SWT.NORMAL);
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

	public TreeItem newRootItem(Tree tree, final Procedure1<TreeItem> initializer) {
		TreeItem item = new TreeItem(tree, SWT.NORMAL);
		if (initializer != null)
			initializer.apply(item);
		return item;
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

	public Shell newShell(Procedure1<? super Shell> initializer) {
		Shell shell = new Shell(getDisplay());
		if (initializer != null)
			initializer.apply(shell);
		return shell;
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

	public int operator_and(int e1, int e2) {
		return e1 & e2;
	}

	public int operator_or(int e1, int e2) {
		return e1 | e2;
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

	public void schedule(final Procedure1<Display> p) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				p.apply(Display.getDefault());
			}
		});
	}

	public Rectangle setLocation(Rectangle rectangle, Point location) {
		rectangle.x = location.x;
		rectangle.y = location.y;
		return rectangle;
	}

	public void setOnClick(final Control button, final Procedure1<Event> function) {
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				function.apply(event);
			}
		});
	}

	public <T extends Widget> void setOnEvent(final T w, int eventType, final Procedure1<Event> handler) {
		w.addListener(eventType, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
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

	public void setOnFocusOut(final Control control, final Procedure1<Event> handler) {
		control.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public <T extends Widget> void setOnModified(final T w, final Procedure1<T> handler) {
		w.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(w);
			}
		});
	}

	public void setOnPaint(Control control, final Procedure1<Event> renderer) {
		control.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				renderer.apply(event);
			}
		});
	}

	public void setOnResize(Control control, final Procedure1<Event> resizer) {
		control.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				resizer.apply(event);
			}
		});
	}

	public <T extends Widget> void setOnSelection(final T w, final Procedure1<Event> handler) {
		w.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handler.apply(event);
			}
		});
	}

	public Rectangle setSize(Rectangle rectangle, Point size) {
		rectangle.width = size.x;
		rectangle.height = size.y;
		return rectangle;
	}

	public Shell Shell(final Procedure1<? super Shell> initializer) {
		Shell _shell = new Shell();
		Shell s = _shell;
		if (initializer != null)
			initializer.apply(s);
		return s;
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
}
