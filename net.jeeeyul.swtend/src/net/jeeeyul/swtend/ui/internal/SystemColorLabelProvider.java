package net.jeeeyul.swtend.ui.internal;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Function1;
import net.jeeeyul.swtend.ui.HSB;
import net.jeeeyul.swtend.ui.ResourceRegistry;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class SystemColorLabelProvider extends LabelProvider {
	private static final SWTExtensions swtExt = SWTExtensions.INSTANCE;

	private ResourceRegistry<SystemColor, Image> imageReg;

	public SystemColorLabelProvider() {
		imageReg = new ResourceRegistry<SystemColor, Image>();
		imageReg.setResourceFactory(new Function1<SystemColor, Image>() {
			@Override
			public Image apply(SystemColor t) {
				RGB fillRGB = Display.getDefault().getSystemColor(t.key).getRGB();
				RGB borderRGB = new HSB(fillRGB).getBrightnessScaled(0.7f).toRGB();
				PaletteData palette = new PaletteData(new RGB[] { fillRGB, borderRGB, new RGB(0, 0, 0) });
				ImageData imageData = new ImageData(16, 16, 2, palette);
				imageData.transparentPixel = 2;

				Rectangle fillArea = swtExt.getShrinked(new Rectangle(0, 0, 16, 16), 2, 2, 3, 3);
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < 16; y++) {
						if (swtExt.intersects(fillArea, x, y)) {
							imageData.setPixel(x, y, 1);
						} else if (fillArea.contains(x, y)) {
							imageData.setPixel(x, y, 0);
						} else {
							imageData.setPixel(x, y, 2);
						}
					}
				}

				return new Image(Display.getDefault(), imageData);
			}
		});
	}

	@Override
	public Image getImage(Object element) {
		return imageReg.get((SystemColor) element);
	}

	@Override
	public String getText(Object element) {
		return ((SystemColor) element).name;
	}

	@Override
	public void dispose() {
		super.dispose();
		imageReg.dispose();
	}

}
