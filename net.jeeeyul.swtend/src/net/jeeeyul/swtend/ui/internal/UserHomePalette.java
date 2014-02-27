package net.jeeeyul.swtend.ui.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.swtend.internal.StringUtil;
import net.jeeeyul.swtend.ui.HSB;

public class UserHomePalette implements Palette {

	private File file;
	private ArrayList<HSB> data = new ArrayList<HSB>();

	public UserHomePalette(String name) {
		String userDir = System.getProperty("user.home");
		file = new File(userDir, name);

		if (file.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				String content = StringUtil.read(fis, "UTF-8");
				String[] segmemts = content.split("\\s*[\r\n]+\\s*");
				for (String each : segmemts) {
					each = each.trim();
					if (each.length() == 0) {
						continue;
					}
					data.add(HSB.deserialize(each));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<HSB> getColors() {
		return data;
	}

	@Override
	public void save() {
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for (HSB each : data) {
				writer.println(each.serialize());
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
