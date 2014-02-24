package net.jeeeyul.swtend.ui;

import java.awt.Point;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Item;

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

		Path path = new Path(getDisplay());

		path.moveTo(bounds.x, bounds.y);
		path.lineTo(bounds.x + bounds.width, bounds.y);
		path.lineTo(bounds.x + bounds.width, bounds.y + bounds.height - 5);
		path.lineTo(bounds.x + bounds.width / 2, bounds.y + bounds.height);
		path.lineTo(bounds.x, bounds.y + bounds.height - 5);
		path.close();

		gc.setBackground(color);
		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER));
		gc.fillPath(path);
		gc.setLineWidth(1);
		if ((state & SWT.SELECTED) != 0) {
			gc.setLineWidth(2);
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
		} else if ((state & SWT.HOT) != 0) {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
		}
		gc.drawPath(path);
		path.dispose();
		color.dispose();

	}

}
