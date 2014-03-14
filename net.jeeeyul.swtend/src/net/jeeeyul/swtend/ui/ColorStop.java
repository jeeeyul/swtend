package net.jeeeyul.swtend.ui;

/**
 * @since 1.2
 */
public class ColorStop {
	public int percent;
	public HSB color;

	public ColorStop(HSB color, int percent) {
		this.percent = percent;
		this.color = color;
	}

	public ColorStop getCopy() {
		return new ColorStop(this.color.getCopy(), this.percent);
	}
}