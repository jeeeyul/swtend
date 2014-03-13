package net.jeeeyul.swtend.internal;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.UIJob;

public class AutoDisposeQueue extends UIJob {
	private HashSet<Resource> queue = new HashSet<Resource>();
	private Map<RGB, Color> colorMap = new HashMap<RGB, Color>();

	public AutoDisposeQueue() {
		super(Display.getDefault(), "Auto Dispose Queue");
		setSystem(true);
		setUser(false);
	}

	public void add(Resource r) {
		boolean added = false;
		synchronized (queue) {
			added = queue.add(r);
		}
		if (added) {
			schedule(10);
		}
	}

	public Color getColor(HSB hsb) {
		return getColor(hsb.toRGB());
	}

	public Color getColor(RGB rgb) {
		Color color = colorMap.get(rgb);
		if (color == null) {
			color = new Color(getDisplay(), rgb);
			colorMap.put(rgb, color);
			add(color);
		}
		return color;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		Resource[] array = null;

		synchronized (queue) {
			array = queue.toArray(new Resource[queue.size()]);
			queue.clear();
		}

		colorMap.clear();

		if (array != null) {
			for (Resource each : array) {
				if (each != null && !each.isDisposed()) {
					each.dispose();
				}
			}
		}

		Debug.getInstance().println(MessageFormat.format("{0} resources are disposed.", array.length));

		return Status.OK_STATUS;
	}

}
