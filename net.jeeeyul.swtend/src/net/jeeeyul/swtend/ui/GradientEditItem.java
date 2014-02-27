package net.jeeeyul.swtend.ui;

import java.awt.Point;

import net.jeeeyul.swtend.SWTExtensions;

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

		Path path = createPath(0);
		Path fillPath = createPath(2);

		if ((state & SWT.SELECTED) != 0) {
			if (parent.isFocusControl()) {
				gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			} else {
				gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			}
		} else if ((state & SWT.HOT) != 0) {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
		gc.fillPath(path);

		gc.setBackground(color);
		gc.fillPath(fillPath);

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		gc.drawPath(fillPath);

		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		gc.drawPath(path);

		path.dispose();
		fillPath.dispose();
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

}
