package net.jeeeyul.swtend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

public class WidgetIterator implements Iterator<Widget> {
	private List<Widget> contents;
	private int index = 0;

	public WidgetIterator(Widget root, boolean includeRoot) {
		this.contents = new ArrayList<Widget>();
		collect(root, includeRoot);
	}

	private void collect(Widget w, boolean includeRoot) {
		if (includeRoot) {
			contents.add(w);
		}

		if (w instanceof Composite) {
			for (Control each : ((Composite) w).getChildren()) {
				collect(each, true);
			}
		}
	}

	@Override
	public boolean hasNext() {
		return index < contents.size();
	}

	@Override
	public Widget next() {
		Widget result = contents.get(index);
		index++;
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
