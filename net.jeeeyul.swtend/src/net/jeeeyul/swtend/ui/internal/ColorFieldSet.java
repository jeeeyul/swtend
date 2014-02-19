package net.jeeeyul.swtend.ui.internal;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Function1;
import net.jeeeyul.swtend.sam.Procedure1;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ColorFieldSet {
	private boolean ignoreModify;
	private HSB fSelection = new HSB();
	private Text redField;
	private Text greenField;
	private Text blueField;

	private Text hueField;
	private Text saturationField;
	private Text brightnessField;

	private Text htmlField;

	private Composite control;

	private Procedure1<HSB> selectionHandler;

	public Procedure1<HSB> getSelectionHandler() {
		return selectionHandler;
	}

	public void setSelectionHandler(Procedure1<HSB> selectionHandler) {
		this.selectionHandler = selectionHandler;
	}

	public Control getControl() {
		return control;
	}

	public ColorFieldSet(Composite parent) {
		create(parent);
		updateField(null);
	}

	private void create(Composite parent) {
		control = new Composite(parent, SWT.NORMAL);
		control.setLayout(new GridLayout(2, false));

		new Label(control, SWT.NORMAL).setText("Red");
		redField = new Text(control, SWT.BORDER);
		setValidRange(redField, new IntRange(0, 255));
		redField.setData("kind", "rgb");

		new Label(control, SWT.NORMAL).setText("Green");
		greenField = new Text(control, SWT.BORDER);
		setValidRange(greenField, new IntRange(0, 255));
		greenField.setData("kind", "rgb");

		new Label(control, SWT.NORMAL).setText("Blue");
		blueField = new Text(control, SWT.BORDER);
		setValidRange(blueField, new IntRange(0, 255));
		blueField.setData("kind", "rgb");

		SWTExtensions.INSTANCE.newHorizontalSeparator(control, null);

		new Label(control, SWT.NORMAL).setText("Hue");
		hueField = new Text(control, SWT.BORDER);
		setValidRange(hueField, new FloatRange(0f, 360f));
		hueField.setData("kind", "hsb");

		new Label(control, SWT.NORMAL).setText("Saturation");
		saturationField = new Text(control, SWT.BORDER);
		setValidRange(saturationField, new FloatRange(0f, 1f));
		saturationField.setData("kind", "hsb");

		new Label(control, SWT.NORMAL).setText("Brightness");
		brightnessField = new Text(control, SWT.BORDER);
		setValidRange(brightnessField, new FloatRange(0f, 1f));
		brightnessField.setData("kind", "hsb");

		SWTExtensions.INSTANCE.newHorizontalSeparator(control, null);

		new Label(control, SWT.NORMAL).setText("HTML Code");
		htmlField = new Text(control, SWT.BORDER);
		htmlField.setData("kind", "html");
		setValidExpression(htmlField, "#?[0-9a-fA-F]{0,6}");
		htmlField.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ignoreModify = true;
				updateHTMLField();
				ignoreModify = false;
			}
		});

		for (final Control each : control.getChildren()) {
			if (each instanceof Text) {
				GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
				gridData.widthHint = 100;
				each.setLayoutData(gridData);

				each.addListener(SWT.Modify, new Listener() {
					@Override
					public void handleEvent(Event event) {
						handleModify(event);
					}
				});
			}
		}

	}

	private void updateField(String excludeKind) {
		ignoreModify = true;

		if (excludeKind == null || !excludeKind.equals("hsb")) {
			hueField.setText(Float.toString(fSelection.hue));
			saturationField.setText(Float.toString(fSelection.saturation));
			brightnessField.setText(Float.toString(fSelection.brightness));
		}

		if (excludeKind == null || !excludeKind.equals("rgb")) {
			redField.setText(Integer.toString(fSelection.toRGB().red));
			greenField.setText(Integer.toString(fSelection.toRGB().green));
			blueField.setText(Integer.toString(fSelection.toRGB().blue));
		}

		if (excludeKind == null || !excludeKind.equals("html")) {
			updateHTMLField();
		}

		ignoreModify = false;
	}

	private void updateHTMLField() {
		String code = String.format("#%02x%02x%02x", fSelection.toRGB().red, fSelection.toRGB().green, fSelection.toRGB().blue);
		htmlField.setText(code);
	}

	private void handleModify(Event event) {
		if (ignoreModify) {
			return;
		}

		Text field = (Text) event.widget;
		String kind = (String) field.getData("kind");
		HSB newHSB = null;

		if (kind.equals("hsb")) {
			newHSB = new HSB(floatValue(hueField), floatValue(saturationField), floatValue(brightnessField));
		}

		else if (kind.equals("rgb")) {
			newHSB = new HSB(intValue(redField), intValue(greenField), intValue(blueField));
			if (newHSB.saturation == 0f) {
				newHSB.hue = fSelection.hue;
			}
		}

		else if (kind.equals("html")) {
			String code = htmlField.getText().trim();
			int offset = -1;
			int length = -1;
			Function1<String, Integer> converter = null;

			if (code.matches("#[0-9a-fA-F]{6}")) {
				offset = 1;
				length = 2;
				converter = new Function1<String, Integer>() {
					@Override
					public Integer apply(String t) {
						return Integer.parseInt(t, 16);
					}
				};
			}

			else if (code.matches("[0-9a-fA-F]{6}")) {
				offset = 0;
				length = 2;
				converter = new Function1<String, Integer>() {
					@Override
					public Integer apply(String t) {
						return Integer.parseInt(t, 16);
					}
				};
			}

			else if (code.matches("#[0-9a-fA-F]{3}")) {
				offset = 1;
				length = 1;
				converter = new Function1<String, Integer>() {
					@Override
					public Integer apply(String t) {
						return Integer.parseInt(t + t, 16);
					}
				};
			}

			else if (code.matches("[0-9a-fA-F]{3}")) {
				offset = 0;
				length = 1;
				converter = new Function1<String, Integer>() {
					@Override
					public Integer apply(String t) {
						return Integer.parseInt(t + t, 16);
					}
				};
			}

			else {
				return;
			}

			String redString = code.substring(offset, offset + length);
			String greenString = code.substring(offset + length, offset + length * 2);
			String blueString = code.substring(offset + length * 2, offset + length * 3);

			newHSB = new HSB(converter.apply(redString), converter.apply(greenString), converter.apply(blueString));

			if (newHSB.saturation == 0f) {
				newHSB.hue = fSelection.hue;
			}
		}

		fSelection = newHSB;
		updateField(kind);
		notifySelection();
	}

	private void notifySelection() {
		if (selectionHandler != null) {
			selectionHandler.apply(fSelection);
		}
	}

	public void setSelection(HSB selection) {
		if (this.fSelection == selection || this.fSelection.equals(selection)) {
			return;
		}

		this.fSelection = selection;
		updateField(null);
	}

	private void setValidRange(final Text text, final IntRange range) {
		text.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (ignoreModify) {
					return;
				}

				String before = text.getText();
				String after = before.subSequence(0, text.getSelection().x) + event.text + before.substring(text.getSelection().y);
				String evaluate = after.trim();

				if (evaluate.isEmpty()) {
					evaluate = "";
				}
				if (!evaluate.matches("[0-9]*")) {
					event.doit = false;
					return;
				}

				int value = Integer.parseInt(evaluate);
				event.doit = (range.min <= value && value <= range.max);
			}
		});
	}

	private void setValidRange(final Text text, final FloatRange range) {
		text.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (ignoreModify) {
					return;
				}

				String before = text.getText();
				String after = before.subSequence(0, text.getSelection().x) + event.text + before.substring(text.getSelection().y);
				String evaluate = after.trim();

				if (evaluate.isEmpty()) {
					evaluate = "";
				}
				if (!evaluate.matches("[0-9]*(\\.[0-9]*)?")) {
					event.doit = false;
					return;
				}
				if (evaluate.equals(".")) {
					evaluate = "0";
				}

				float value = Float.parseFloat(evaluate);
				event.doit = (range.min <= value && value <= range.max);
			}
		});
	}

	private void setValidExpression(final Text text, final String regexp) {
		text.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (ignoreModify) {
					return;
				}

				String before = text.getText();
				String after = before.subSequence(0, text.getSelection().x) + event.text + before.substring(text.getSelection().y);
				String evaluate = after.trim();

				event.doit = evaluate.matches(regexp);
			}
		});
	}

	private float floatValue(Text text) {
		String eval = text.getText().trim();
		if (eval.isEmpty()) {
			eval = "0";
		}
		if (eval == ".") {
			return 0f;
		}
		return Float.parseFloat(eval);
	}

	private int intValue(Text text) {
		String eval = text.getText().trim();
		if (eval.isEmpty()) {
			eval = "0";
		}
		return Integer.parseInt(eval);
	}

	public static void main(String[] args) {
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());

		new ColorFieldSet(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!Display.getDefault().readAndDispatch()) {
				Display.getDefault().sleep();
			}
		}

	}
}
