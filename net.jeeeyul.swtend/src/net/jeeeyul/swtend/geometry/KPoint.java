package net.jeeeyul.swtend.geometry;

import org.eclipse.swt.graphics.Point;

/**
 * @since 1.2
 * @deprecated
 */
public class KPoint {
	public int x = 0;
	public int y = 0;

	public KPoint() {

	}

	public KPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public KPoint(KPoint original) {
		this.x = original.x;
		this.y = original.y;
	}

	public KPoint(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public KPoint getCopy() {
		return new KPoint(this);
	}

	public KPoint getTranslated(int dx, int dy) {
		return getCopy().translate(dx, dy);
	}

	public Point toPoint() {
		return new Point(x, y);
	}

	public KPoint translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		return this;
	}
}
