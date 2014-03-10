package net.jeeeyul.swtend.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class NinePatch extends Resource {
	private Image image;
	private Rectangle centerArea;
	private Rectangle imageBounds;

	public NinePatch(Image image, Rectangle centerArea) {
		this.image = image;
		this.centerArea = centerArea;
		imageBounds = image.getBounds();
	}

	@Override
	public void dispose() {
		image.dispose();
		super.dispose();
	}

	private void drawPart(GC gc, Rectangle src, Rectangle dest) {
		if (isPositiveArea(src) && isPositiveArea(dest)) {
			gc.drawImage(image, src.x, src.y, src.width, src.height, dest.x, dest.y, dest.width, dest.height);
		}
	}

	public void fill(GC gc, Rectangle dest) {
		drawPart(gc, getCorner(SWT.TOP, SWT.LEFT), getCorner(dest, SWT.TOP, SWT.LEFT));
		drawPart(gc, getCorner(SWT.TOP, SWT.CENTER), getCorner(dest, SWT.TOP, SWT.CENTER));
		drawPart(gc, getCorner(SWT.TOP, SWT.RIGHT), getCorner(dest, SWT.TOP, SWT.RIGHT));

		drawPart(gc, getCorner(SWT.CENTER, SWT.LEFT), getCorner(dest, SWT.CENTER, SWT.LEFT));
		drawPart(gc, getCorner(SWT.CENTER, SWT.CENTER), getCorner(dest, SWT.CENTER, SWT.CENTER));
		drawPart(gc, getCorner(SWT.CENTER, SWT.RIGHT), getCorner(dest, SWT.CENTER, SWT.RIGHT));

		drawPart(gc, getCorner(SWT.BOTTOM, SWT.LEFT), getCorner(dest, SWT.BOTTOM, SWT.LEFT));
		drawPart(gc, getCorner(SWT.BOTTOM, SWT.CENTER), getCorner(dest, SWT.BOTTOM, SWT.CENTER));
		drawPart(gc, getCorner(SWT.BOTTOM, SWT.RIGHT), getCorner(dest, SWT.BOTTOM, SWT.RIGHT));
	}

	private Rectangle getCorner(int vFlag, int hFlag) {
		Rectangle r = new Rectangle(0, 0, 0, 0);
		if ((hFlag & SWT.LEFT) != 0) {
			r.x = 0;
			r.width = centerArea.x;
		} else if ((hFlag & SWT.CENTER) != 0) {
			r.x = centerArea.x;
			r.width = centerArea.width;
		} else if ((hFlag & SWT.RIGHT) != 0) {
			r.x = centerArea.x + centerArea.width;
			r.width = imageBounds.width - centerArea.x - centerArea.width;
		}

		if ((vFlag & SWT.TOP) != 0) {
			r.y = 0;
			r.height = centerArea.y;
		} else if ((vFlag & SWT.CENTER) != 0) {
			r.y = centerArea.y;
			r.height = centerArea.height;
		} else if ((vFlag & SWT.BOTTOM) != 0) {
			r.y = centerArea.y + centerArea.height;
			r.height = imageBounds.height - centerArea.y - centerArea.height;
		}

		return r;
	}

	private Rectangle getCorner(Rectangle offset, int vFlag, int hFlag) {
		Rectangle r = new Rectangle(0, 0, 0, 0);
		if ((hFlag & SWT.LEFT) != 0) {
			r.x = offset.x;
			r.width = centerArea.x;
		}

		else if ((hFlag & SWT.CENTER) != 0) {
			r.x = offset.x + centerArea.x;
			r.width = offset.width - (imageBounds.width - centerArea.width);
		}

		else if ((hFlag & SWT.RIGHT) != 0) {
			r.x = offset.x + offset.width - (imageBounds.width - centerArea.x - centerArea.width);
			r.width = imageBounds.width - centerArea.x - centerArea.width;
		}

		if ((vFlag & SWT.TOP) != 0) {
			r.y = offset.y;
			r.height = centerArea.y;
		}

		else if ((vFlag & SWT.CENTER) != 0) {
			r.y = offset.y + centerArea.y;
			r.height = offset.height - centerArea.y - (imageBounds.height - centerArea.y - centerArea.height);
		}

		else if ((vFlag & SWT.BOTTOM) != 0) {
			r.y = offset.y + offset.height - (imageBounds.height - centerArea.y - centerArea.height);
			r.height = (imageBounds.height - centerArea.y - centerArea.height);
		}

		return r;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public boolean isDisposed() {
		return image.isDisposed();
	}

	private boolean isPositiveArea(Rectangle rect) {
		return rect.width > 0 && rect.height > 0;
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Image testImage = new Image(display, new ImageData(NinePatch.class.getResourceAsStream("patch-test.png")));

		final NinePatch patch = new NinePatch(testImage, new Rectangle(10, 10, 10, 10));

		Shell shell = new Shell(display);
		shell.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.gc.drawImage(patch.getImage(), 0, 0);
				patch.fill(event.gc, new Rectangle(200, 0, 100, 100));
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
