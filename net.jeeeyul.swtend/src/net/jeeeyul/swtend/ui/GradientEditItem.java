package net.jeeeyul.swtend.ui;

import java.awt.Point;

import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Item;

/**
 * @since 1.2
 */
public class GradientEditItem extends Item {
	final static Point SIZE = new Point(10, 20);
	private GradientEdit parent;
	int state = SWT.NONE;
	Rectangle bounds = new Rectangle(0, 0, 20, 20);

	public GradientEditItem(GradientEdit parent) {
		super(parent, SWT.NORMAL);
		this.parent = parent;
		parent.items.add(this);
	}

	@Override
	public void dispose() {
		parent.items.remove(this);
		super.dispose();
	}

	void draw(GC gc) {
		ColorStop colorStop = (ColorStop) getData();
		Color color = new Color(getDisplay(), colorStop.color.toRGB());

		Path innerOutline = createPath(2);
		Path innerFill = createFillPath(2);
		Path path = createPath(0);

		switch (state) {
		case SWT.HOT:
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			break;

		case SWT.SELECTED:
		case SWT.HOT | SWT.SELECTED:
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			break;

		default:
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		}

		gc.setAlpha(255);
		gc.fillPath(path);

		gc.setAlpha(255);
		gc.setBackground(color);
		gc.fillPath(innerFill);

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));
		gc.setAlpha(255);
		gc.drawPath(path);

		gc.setAlpha(100);
		gc.drawPath(innerOutline);

		path.dispose();
		innerOutline.dispose();
		innerFill.dispose();
		color.dispose();
	}

	protected Path createPath(int inset) {
		Path path = new Path(getDisplay());
		Rectangle box = SWTExtensions.INSTANCE.getShrinked(bounds, inset);
		path.moveTo(box.x, box.y);
		path.lineTo(box.x + box.width, box.y);
		path.lineTo(box.x + box.width, box.y + box.height - 5);
		path.lineTo(box.x + box.width / 2, box.y + box.height - inset);
		path.lineTo(box.x, box.y + box.height - 5);
		path.close();
		return path;
	}

	protected Path createFillPath(int inset) {
		Path path = new Path(getDisplay());
		Rectangle box = SWTExtensions.INSTANCE.getShrinked(bounds, inset, inset, inset - 1, inset - 1);
		path.moveTo(box.x, box.y);
		path.lineTo(box.x + box.width, box.y);
		path.lineTo(box.x + box.width, box.y + box.height - 5);
		path.lineTo(box.x + box.width / 2, box.y + box.height - (inset + 1));
		path.lineTo(box.x, box.y + box.height - 5);
		path.close();
		return path;
	}

}
