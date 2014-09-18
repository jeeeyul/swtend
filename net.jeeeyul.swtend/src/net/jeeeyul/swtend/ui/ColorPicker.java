package net.jeeeyul.swtend.ui;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.swtend.internal.CollectionContentProvider;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.internal.ColorFieldSet;
import net.jeeeyul.swtend.ui.internal.HueCanvas;
import net.jeeeyul.swtend.ui.internal.HueScale;
import net.jeeeyul.swtend.ui.internal.Palette;
import net.jeeeyul.swtend.ui.internal.PipetteTool;
import net.jeeeyul.swtend.ui.internal.SystemColor;
import net.jeeeyul.swtend.ui.internal.SystemColorLabelProvider;
import net.jeeeyul.swtend.ui.internal.UserHomePalette;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * @since 1.2
 */
public class ColorPicker extends Dialog {
	private static final int RECENT_MAX_SIZE = 100;

	public static void main(String[] args) {
		ColorPicker picker = new ColorPicker(null);
		picker.setSelection(new HSB(255, 0, 0));
		picker.open();
	}

	private HueCanvas hueCanvas;
	private HueScale hueScale;
	private HSB selection = new HSB(0f, 0f, 0f);

	private boolean lockHue;
	private ColorWell colorWell;
	private Procedure1<HSB> continuosSelectionHandler;
	private ColorFieldSet fieldSet;
	private ToolItem pipette;
	private TabFolder tabFolder;
	private Palette recentPalette;

	private Image pipetteImage;

	public ColorPicker() {
		this(Display.getDefault().getActiveShell());
	}

	public ColorPicker(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.TITLE | SWT.CLOSE | SWT.APPLICATION_MODAL);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		return super.createButtonBar(parent);
	}

	private void createChooseTab() {
		Composite container = new Composite(tabFolder, SWT.NORMAL);
		GridLayout mainLayout = new GridLayout();
		mainLayout.numColumns = 2;
		mainLayout.marginWidth = mainLayout.marginHeight = 0;
		mainLayout.horizontalSpacing = 0;
		container.setLayout(mainLayout);

		TabItem item = new TabItem(tabFolder, SWT.NORMAL);
		item.setText("Choose");
		item.setControl(container);

		Composite left = new Composite(container, SWT.NORMAL);
		left.setLayout(new GridLayout());

		hueCanvas = new HueCanvas(left, SWT.NORMAL);
		hueCanvas.setPadding(6);
		hueCanvas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		hueCanvas.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleNewSelection();
			}
		});

		fieldSet = new ColorFieldSet(container);
		fieldSet.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fieldSet.setSelectionHandler(new Procedure1<HSB>() {
			@Override
			public void apply(HSB hsb) {
				setSelection(hsb);
			}
		});

		new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		Composite bottom = new Composite(container, SWT.NORMAL);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		GridLayout bottomLayout = new GridLayout(3, false);
		bottomLayout.marginHeight = 0;
		bottomLayout.marginWidth = 15;
		bottom.setLayout(bottomLayout);

		colorWell = new ColorWell(bottom, SWT.BORDER);

		ToolBar toolBar = new ToolBar(bottom, SWT.FLAT);

		pipette = new ToolItem(toolBar, SWT.PUSH);
		pipette.setImage(getPipetteImage());
		pipette.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!isLockHue()) {
					openPipetteMode();
				}
			}
		});

		hueScale = new HueScale(bottom, SWT.NORMAL);
		hueScale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hueScale.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				hueCanvas.setHue(hueScale.getSelection());
			}
		});
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		tabFolder = new TabFolder(container, SWT.NORMAL);

		createChooseTab();
		createRecentTab();
		createSystemColorsTab();

		getShell().setText("Color Picker");
		getShell().setImage(getPipetteImage());
		update();

		hueCanvas.setFocus();

		return container;
	}

	private void createSystemColorsTab() {
		Composite composite = new Composite(tabFolder, SWT.NORMAL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.marginWidth = layout.marginHeight = 0;

		TableViewer viewer = new TableViewer(composite, SWT.BORDER);

		TabItem tabItem = new TabItem(tabFolder, SWT.NORMAL | SWT.V_SCROLL);
		tabItem.setText("System");
		tabItem.setControl(composite);

		viewer.setContentProvider(new CollectionContentProvider());
		viewer.setLabelProvider(new SystemColorLabelProvider());
		viewer.setInput(SystemColor.getSystemColors());

		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.heightHint = 100;
		viewer.getControl().setLayoutData(layoutData);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				SystemColor selection = (SystemColor) ((IStructuredSelection) event.getSelection()).getFirstElement();
				setSelection(new HSB(selection.key));
			}
		});

		viewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				okPressed();
			}
		});
	}

	private void createRecentTab() {
		Composite container = new Composite(tabFolder, SWT.NORMAL);
		container.setLayout(new GridLayout(10, true));
		TabItem tabItem = new TabItem(tabFolder, SWT.NORMAL);
		tabItem.setText("Recent");
		tabItem.setControl(container);

		Listener wellListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				HSB hsb = (HSB) event.widget.getData();
				setSelection(hsb);
				okPressed();
			}
		};

		for (HSB each : getRecentPalette().getColors()) {
			ColorWell well = new ColorWell(container, SWT.NORMAL);
			well.setSelection(each);
			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			well.setLayoutData(gridData);
			well.setData(each);
			well.addListener(SWT.Selection, wellListener);
		}

	}

	public Procedure1<HSB> getContinuosSelectionHandler() {
		return continuosSelectionHandler;
	}

	private Image getPipetteImage() {
		if (pipetteImage == null || pipetteImage.isDisposed()) {
			pipetteImage = new Image(Display.getDefault(), new ImageData(getClass().getResourceAsStream("internal/pipette.png")));
		}
		return pipetteImage;
	}

	@Override
	public boolean close() {
		if (pipetteImage != null && !pipetteImage.isDisposed()) {
			pipetteImage.dispose();
		}
		return super.close();
	}

	private Palette getRecentPalette() {
		if (recentPalette == null) {
			recentPalette = new UserHomePalette(".swtend-colorpicker-recent");
		}
		return recentPalette;
	}

	public HSB getSelection() {
		return selection;
	}

	private void handleNewSelection() {
		selection = hueCanvas.getSelection();
		colorWell.setSelection(selection);
		fieldSet.setSelection(selection);
		if (continuosSelectionHandler != null) {
			continuosSelectionHandler.apply(selection);
		}
	}

	public boolean isLockHue() {
		return lockHue;
	}

	@Override
	protected void okPressed() {
		saveRecentColors();
		super.okPressed();
	}

	private void openPipetteMode() {
		HSB result = new PipetteTool(getShell()).open();
		if (result != null) {
			hueScale.setSelection(result.hue);
		}
		hueCanvas.setSelection(result.toArray());
	}

	private void saveRecentColors() {
		List<HSB> recentColors = getRecentPalette().getColors();
		if (recentColors.contains(selection)) {
			recentColors.remove(selection);
		}

		recentColors.add(0, selection);

		if (recentColors.size() > RECENT_MAX_SIZE) {
			List<HSB> aliveList = new ArrayList<HSB>(recentColors.subList(0, RECENT_MAX_SIZE));
			recentColors.clear();
			recentColors.addAll(aliveList);
		}

		getRecentPalette().save();
	}

	/**
	 * Sets a continuous selection handler.
	 * 
	 * @param continuosSelectionHandler
	 *            a handler that handles continuous selection.
	 */
	public void setContinuosSelectionHandler(Procedure1<HSB> continuosSelectionHandler) {
		this.continuosSelectionHandler = continuosSelectionHandler;
	}

	public void setLockHue(boolean lockHue) {
		this.lockHue = lockHue;
		update();
	}

	public void setSelection(HSB selection) {
		this.selection = selection;
		update();
	}

	private void update() {
		if (getShell() == null) {
			return;
		}

		hueCanvas.setSelection(selection.toArray());
		hueScale.setSelection(selection.hue);
		hueScale.setEnabled(!lockHue);
		pipette.setEnabled(!lockHue);
		fieldSet.setSelection(selection);
	}

}
