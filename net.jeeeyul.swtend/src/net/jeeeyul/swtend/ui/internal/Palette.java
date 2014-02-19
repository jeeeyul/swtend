package net.jeeeyul.swtend.ui.internal;

import java.util.List;

import net.jeeeyul.swtend.ui.HSB;

public interface Palette {
	public List<HSB> getColors();

	public void save();
}
