package net.jeeeyul.swtend.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class WidgetIterator implements Iterator<Widget> {
	private List<Widget> contents;
	private int index = 0;

	public WidgetIterator(Widget root, boolean includeRoot) {
		this.contents = new ArrayList<Widget>();
		collect(root, includeRoot);
	}

	private void collect(Widget w, boolean includeRoot) {
		if (w == null || w.isDisposed() || contents.contains(w)) {
			return;
		}

		if (includeRoot) {
			contents.add(w);
		}

		if (w instanceof TabFolder) {
			TabItem[] items = ((TabFolder) w).getItems();
			for (TabItem each : items) {
				collect(each, true);
			}
		}

		else if (w instanceof CTabFolder) {
			CTabItem[] items = ((CTabFolder) w).getItems();
			for (CTabItem each : items) {
				collect(each, true);
			}
		}

		else if (w instanceof TabItem) {
			collect(((TabItem) w).getControl(), true);
		}

		else if (w instanceof CTabItem) {
			collect(((CTabItem) w).getControl(), true);
		}

		else if (w instanceof Tree) {
			for (TreeItem each : ((Tree) w).getItems()) {
				collect(each, true);
			}
		}

		else if (w instanceof TreeItem) {
			for (TreeItem each : ((TreeItem) w).getItems()) {
				collect(each, true);
			}
		}

		else if (w instanceof Table) {
			for (TableItem each : ((Table) w).getItems()) {
				collect(each, true);
			}
		}

		else if (w instanceof Composite) {
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
