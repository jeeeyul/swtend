package net.jeeeyul.swtend.ui;

import java.util.Locale;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Represents a color in hue, saturation, brightness.
 * 
 * @since 1.2
 */
public class HSB extends LightWeightResource {
	public static final HSB BLACK = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_BLACK).getRGB());
	public static final HSB BLUE = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_BLUE).getRGB());
	public static final HSB CYAN = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_CYAN).getRGB());
	public static final HSB DARK_BLUE = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE).getRGB());
	public static final HSB DARK_GRAY = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY).getRGB());
	public static final HSB DARK_GREEN = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN).getRGB());
	public static final HSB DARK_MAGENTA = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_MAGENTA).getRGB());
	public static final HSB DARK_RED = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED).getRGB());
	public static final HSB DARK_YELLOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW).getRGB());
	public static final HSB GRAY = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_GRAY).getRGB());
	public static final HSB GREEN = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_GREEN).getRGB());
	public static final HSB MAGENTA = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_MAGENTA).getRGB());
	public static final HSB RED = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_RED).getRGB());
	public static final HSB WHITE = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WHITE).getRGB());
	public static final HSB YELLOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_YELLOW).getRGB());

	public static final HSB WIDGET_DARK_SHADOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW).getRGB());
	public static final HSB WIDGET_NORMAL_SHADOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW).getRGB());
	public static final HSB WIDGET_LIGHT_SHADOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW).getRGB());
	public static final HSB WIDGET_HIGHLIGHT_SHADOW = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW).getRGB());
	public static final HSB WIDGET_FOREGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND).getRGB());
	public static final HSB WIDGET_BACKGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB());
	public static final HSB WIDGET_BORDER = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BORDER).getRGB());
	public static final HSB LIST_FOREGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_LIST_FOREGROUND).getRGB());
	public static final HSB LIST_BACKGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB());
	public static final HSB LIST_SELECTION = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB());
	public static final HSB LIST_SELECTION_TEXT = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT).getRGB());
	public static final HSB INFO_FOREGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_INFO_FOREGROUND).getRGB());
	public static final HSB INFO_BACKGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND).getRGB());
	public static final HSB TITLE_FOREGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_FOREGROUND).getRGB());
	public static final HSB TITLE_BACKGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND).getRGB());
	public static final HSB TITLE_BACKGROUND_GRADIENT = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT).getRGB());
	public static final HSB TITLE_INACTIVE_FOREGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND).getRGB());
	public static final HSB TITLE_INACTIVE_BACKGROUND = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND).getRGB());
	public static final HSB TITLE_INACTIVE_BACKGROUND_GRADIENT = new HSB(Display.getDefault().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT)
			.getRGB());

	public static HSB deserialize(String literal) {
		HSB result = new HSB();

		Scanner scanner = null;
		try {
			scanner = new Scanner(literal);
			scanner.useDelimiter("\\s*,\\s*");
			scanner.useLocale(Locale.US);

			result.hue = scanner.nextFloat();
			result.saturation = scanner.nextFloat();
			result.brightness = scanner.nextFloat();
		} catch (Exception e) {
			e.printStackTrace();
			result = new HSB(0f, 1f, 1f);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		HSB hsb = new HSB(0, 1f, 1f);
		System.out.println(hsb.shiftHue(380f));
	}

	public float hue;
	public float saturation;
	public float brightness;

	public HSB() {

	}

	public HSB(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}

	public HSB(float[] hsb) {
		if (hsb == null || hsb.length < 3) {
			throw new IllegalArgumentException();
		}
		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];
	}

	public HSB(int red, int green, int blue) {
		this(new RGB(red, green, blue).getHSB());
	}

	public HSB(RGB rgb) {
		this(rgb.getHSB());
	}

	public HSB(String htmlCode) {
		if (htmlCode.startsWith("#")) {
			htmlCode = htmlCode.substring(1);
		}

		RGB rgb = new RGB(0, 0, 0);

		if (htmlCode.matches("[0-9a-fA-F]{6}")) {
			rgb.red = Integer.parseInt(htmlCode.substring(0, 2), 16);
			rgb.green = Integer.parseInt(htmlCode.substring(2, 4), 16);
			rgb.blue = Integer.parseInt(htmlCode.substring(4, 6), 16);
		} else if (htmlCode.matches("[0-9a-fA-F]{3}")) {
			rgb.red = Integer.parseInt(htmlCode.substring(0, 1) + htmlCode.substring(0, 1), 16);
			rgb.green = Integer.parseInt(htmlCode.substring(1, 2) + htmlCode.substring(1, 2), 16);
			rgb.blue = Integer.parseInt(htmlCode.substring(2, 3) + htmlCode.substring(2, 3), 16);
		}

		else {
			throw new UnsupportedOperationException(htmlCode + " is not supported color code.");
		}

		float[] hsb = rgb.getHSB();
		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];
	}

	/**
	 * @since 2.2
	 * @param systemColorKey
	 */
	public HSB(int systemColorKey) {
		this(Display.getDefault().getSystemColor(systemColorKey).getRGB());
	}

	/**
	 * @param amp
	 * @return
	 * @deprecated use {@link #getBrightnessScaled(float)} instead.
	 */
	public HSB ampBrightness(float amp) {
		return new HSB(hue, saturation, limit(this.brightness * amp, 0f, 1f));
	}

	/**
	 * 
	 * @param amp
	 * @return
	 * 
	 * @deprecated use {@link #getSaturationScaled(float)} instead.
	 */
	public HSB ampSaturation(float amp) {
		return new HSB(hue, limit(this.saturation * amp, 0f, 1f), brightness);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HSB) {
			HSB other = (HSB) obj;
			return this.hue == other.hue && this.saturation == other.saturation && this.brightness == other.brightness;
		}
		return super.equals(obj);
	}

	public HSB getBrightnessScaled(float amp) {
		return getCopy().scaleBrightness(amp);
	}

	public HSB getCopy() {
		return new HSB(hue, saturation, brightness);
	}

	/**
	 * Creates a new {@link HSB} by mixing this {@link HSB} and given
	 * {@link HSB}.
	 * 
	 * @param color
	 *            color to mix.
	 * @param strength
	 *            0f to 1f. 0.5f means 1:1 blending.
	 * @return blended {@link HSB}.
	 * 
	 * @since 2.1
	 */
	public HSB getMixedWith(HSB color, float strength) {
		return getCopy().mixWith(color, strength);
	}

	public HSB getSaturationScaled(float amp) {
		return getCopy().scaleSaturation(amp);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	private float limit(float original, float min, float max) {
		return Math.min(Math.max(original, min), max);
	}

	public HSB mixWith(HSB color, float strength) {
		if (color == null || strength < 0 || strength > 1f) {
			throw new IllegalArgumentException();
		}

		RGB thisRGB = toRGB();
		RGB otherRGB = color.toRGB();
		RGB newRGB = new RGB(0, 0, 0);

		newRGB.red = (int) (thisRGB.red * (1f - strength) + otherRGB.red * strength);
		newRGB.green = (int) (thisRGB.green * (1f - strength) + otherRGB.green * strength);
		newRGB.blue = (int) (thisRGB.blue * (1f - strength) + otherRGB.blue * strength);

		float[] hsb = newRGB.getHSB();

		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];

		return this;
	}

	public HSB shiftHue(float amount) {
		this.hue = (this.hue + amount) % 360f;
		while (this.hue < 0) {
			this.hue += 360f;
		}
		return this;
	}

	public HSB getHueShifted(float amount) {
		return getCopy().shiftHue(amount);
	}

	/**
	 * @param newHue
	 * @return
	 * 
	 * @deprecated just access {@link #hue} instead.
	 */
	public HSB rewriteHue(float newHue) {
		return new HSB(newHue, saturation, brightness);
	}

	public HSB scaleBrightness(float amp) {
		this.brightness = limit(this.brightness * amp, 0f, 1f);
		return this;
	}

	public HSB scaleSaturation(float amp) {
		this.saturation = limit(this.saturation * amp, 0f, 1f);
		return this;
	}

	public String serialize() {
		return String.format(Locale.ENGLISH, "%f, %f, %f", hue, saturation, brightness);
	}

	public float[] toArray() {
		return new float[] { hue, saturation, brightness };
	}

	public String toHTMLCode() {
		RGB rgb = toRGB();
		return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
	}

	public RGB toRGB() {
		return new RGB(hue, saturation, brightness);
	}

	@Override
	public String toString() {
		return "HSB [hue=" + hue + ", saturation=" + saturation + ", brightness=" + brightness + "]";
	}
}
