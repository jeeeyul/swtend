package net.jeeeyul.swtend.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

public class SystemColor {
	public final int key;
	public final String name;

	public SystemColor(int key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	private static List<SystemColor> SYSTEM_COLORS;

	public static List<SystemColor> getSystemColors() {
		if (SYSTEM_COLORS == null) {
			SYSTEM_COLORS = new ArrayList<SystemColor>();
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_BACKGROUND, "Widget Background"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_FOREGROUND, "Widget Foreground"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_BORDER, "Widget Border"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_DARK_SHADOW, "Widget Dark Shadow"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW, "Widget Normal Shadow"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW, "Widget Light Shadow"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW, "Widget Highlight Shadow"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_LIST_BACKGROUND, "List Background"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_LIST_FOREGROUND, "List Foreground"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_LIST_SELECTION, "List Selection"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_LIST_SELECTION_TEXT, "List Selection Text"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_INFO_BACKGROUND, "Info Background"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_INFO_FOREGROUND, "Info Foreground"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_BACKGROUND, "Title Background"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT, "Title Gradient"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_FOREGROUND, "Title Foreground"));

			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND, "Inactive Title Background"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT, "Inactive Title Gradient"));
			SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND, "Inactive Title Foreground"));

			try {
				if (SWT.class.getDeclaredField("COLOR_LINK_FOREGROUND") != null) {
					SYSTEM_COLORS.add(new SystemColor(SWT.COLOR_LINK_FOREGROUND, "Link"));
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// Ignore
			}

		}
		return SYSTEM_COLORS;
	}
}
