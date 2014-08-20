package net.jeeeyul.swtend.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @since 1.2
 */
public class Gradient extends LightWeightResource implements List<ColorStop> {
	ArrayList<ColorStop> data = new ArrayList<ColorStop>();

	public Gradient(HSB start, HSB end) {
		data.add(new ColorStop(start, 0));
		data.add(new ColorStop(end, 100));
	}

	public Gradient() {
	}

	public boolean add(ColorStop arg0) {
		return data.add(arg0);
	}

	public void add(int arg0, ColorStop arg1) {
		data.add(arg0, arg1);
	}

	public boolean addAll(Collection<? extends ColorStop> arg0) {
		return data.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends ColorStop> arg1) {
		return data.addAll(arg0, arg1);
	}

	public void clear() {
		data.clear();
	}

	public boolean contains(Object arg0) {
		return data.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return data.containsAll(arg0);
	}

	public void ensureCapacity(int arg0) {
		data.ensureCapacity(arg0);
	}

	public boolean equals(Object arg0) {
		return data.equals(arg0);
	}

	public ColorStop get(int arg0) {
		return data.get(arg0);
	}

	public int indexOf(Object arg0) {
		return data.indexOf(arg0);
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public Iterator<ColorStop> iterator() {
		return data.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return data.lastIndexOf(arg0);
	}

	public ListIterator<ColorStop> listIterator() {
		return data.listIterator();
	}

	public ListIterator<ColorStop> listIterator(int arg0) {
		return data.listIterator(arg0);
	}

	public ColorStop remove(int arg0) {
		return data.remove(arg0);
	}

	public boolean remove(Object arg0) {
		return data.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return data.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return data.retainAll(arg0);
	}

	public ColorStop set(int arg0, ColorStop arg1) {
		return data.set(arg0, arg1);
	}

	public int size() {
		return data.size();
	}

	public List<ColorStop> subList(int arg0, int arg1) {
		return data.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return data.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return data.toArray(arg0);
	}

	public void trimToSize() {
		data.trimToSize();
	}

	public String toSWTCSSString() {
		StringBuffer colorNameBuffer = new StringBuffer();
		StringBuffer percentBuffer = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			ColorStop each = get(i);
			if (i == 0) {
				if (each.percent != 0) {
					colorNameBuffer.append(each.color.toHTMLCode() + " " + each.color.toHTMLCode());
					percentBuffer.append(Integer.toString(each.percent) + "%");
				} else {
					colorNameBuffer.append(each.color.toHTMLCode());
				}
			}

			else {
				colorNameBuffer.append(each.color.toHTMLCode());
				percentBuffer.append(Integer.toString(each.percent) + "%");
			}

			if (i == size() - 1) {
				if (each.percent != 100) {
					colorNameBuffer.append(" ");
					percentBuffer.append(" ");
					colorNameBuffer.append(each.color.toHTMLCode());
					percentBuffer.append("100%");
				}
			}

			if (i < size() - 1) {
				colorNameBuffer.append(" ");
			}
			if (i > 0 && i < size() - 1) {
				percentBuffer.append(" ");
			}
		}

		return colorNameBuffer.toString() + " " + percentBuffer.toString();
	}

	public Gradient getCopy() {
		Gradient result = new Gradient();
		for (ColorStop each : this) {
			result.add(each.getCopy());
		}
		return result;
	}

	public HSB getColorInPosition(int percent) {
		percent = Math.min(Math.max(percent, 0), 100);
		if (size() == 0) {
			throw new IllegalStateException();
		}

		if (size() == 1) {
			return get(0).color;
		}

		ColorStop from = null;
		ColorStop to = null;
		for (int i = 0; i < this.size(); i++) {
			to = get(i);
			if (to.percent >= percent) {
				break;
			}
		}
		if (to == null) {
			to = get(size() - 1);
		}
		int toIndex = indexOf(to);
		from = toIndex > 0 ? get(toIndex - 1) : to;
		if (to.percent < percent) {
			return to.color;
		}

		int delta = to.percent - from.percent;
		if (delta == 0) {
			return to.color;
		}

		float strength = (percent - from.percent) / (float) delta;
		return from.color.getMixedWith(to.color, strength);
	}

	/**
	 * @deprecated use {@link #getMiddlePointColor()}
	 */
	public HSB getAverageColor() {
		return getColorInPosition(50);
	}

	public HSB getMiddlePointColor() {
		return getColorInPosition(50);
	}
}
