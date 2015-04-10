package net.jeeeyul.swtend.internal;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

public class AutoDisposeQueue implements Runnable {
	
	private HashSet<Resource> queue = new HashSet<Resource>();
	private Map<RGB, Color> colorMap = new HashMap<RGB, Color>();
	private boolean scheduled = false;
	private Display display = Display.getDefault();
	
	public void add(Resource r) {
		boolean schedule = false;
		synchronized (queue) {
			schedule = queue.add(r) && !scheduled;
			if (schedule) {
				scheduled = true;
			}
		}
		if (schedule) {
			display.asyncExec(this);
		}
	}

	public Color getColor(HSB hsb) {
		return getColor(hsb.toRGB());
	}

	public Color getColor(RGB rgb) {
		Color color = colorMap.get(rgb);
		if (color == null) {
			color = new Color(display, rgb);
			synchronized(queue) {
				colorMap.put(rgb, color);
				add(color);
			}
		}
		return color;
	}

	@Override
	public void run() {
		Resource[] array = null;

		synchronized (queue) {
			array = queue.toArray(new Resource[queue.size()]);
			scheduled = false;
			queue.clear();
			colorMap.clear();
		}

		if (array != null) {
			for (Resource each : array) {
				if (each != null && !each.isDisposed()) {
					each.dispose();
				}
			}
		}

		Debug.getInstance().println(MessageFormat.format("{0} resources are disposed.", array.length));

	}

}
