package net.jeeeyul.swtend.ui;

import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

/**
 * @since 1.2
 */
public class NinePatch extends Resource {
	private Rectangle centerArea;
	private Rectangle imageBounds;
	private Image[] patches;
	private boolean isDisposed = false;

	/**
	 * Creates a {@link NinePatch} {@link Resource} object. It must be disposed
	 * after use.
	 * 
	 * @param source
	 * @param centerArea
	 */
	public NinePatch(ImageData source, Rectangle centerArea) {
		this.centerArea = centerArea;
		imageBounds = new Rectangle(0, 0, source.width, source.height);
		buildPatches(source);
	}

	private void buildPatches(ImageData source) {
		ImageData[] patcheDatas = new ImageData[9];

		patcheDatas[0] = new ImageData(centerArea.x, centerArea.y, source.depth, source.palette);
		copy(source, patcheDatas[0], 0, 0);

		patcheDatas[1] = new ImageData(centerArea.width, centerArea.y, source.depth, source.palette);
		copy(source, patcheDatas[1], centerArea.x, 0);

		patcheDatas[2] = new ImageData(source.width - centerArea.x - centerArea.width, centerArea.y, source.depth, source.palette);
		copy(source, patcheDatas[2], centerArea.x + centerArea.width, 0);

		patcheDatas[3] = new ImageData(centerArea.x, centerArea.height, source.depth, source.palette);
		copy(source, patcheDatas[3], 0, centerArea.y);

		patcheDatas[4] = new ImageData(centerArea.width, centerArea.height, source.depth, source.palette);
		copy(source, patcheDatas[4], centerArea.x, centerArea.y);

		patcheDatas[5] = new ImageData(source.width - centerArea.x - centerArea.width, centerArea.height, source.depth, source.palette);
		copy(source, patcheDatas[5], centerArea.x + centerArea.width, centerArea.y);

		patcheDatas[6] = new ImageData(centerArea.x, source.height - centerArea.height - centerArea.y, source.depth, source.palette);
		copy(source, patcheDatas[6], 0, centerArea.y + centerArea.height);

		patcheDatas[7] = new ImageData(centerArea.width, source.height - centerArea.height - centerArea.y, source.depth, source.palette);
		copy(source, patcheDatas[7], centerArea.x, centerArea.y + centerArea.height);

		patcheDatas[8] = new ImageData(source.width - centerArea.x - centerArea.width, source.height - centerArea.y - centerArea.height, source.depth,
				source.palette);
		copy(source, patcheDatas[8], centerArea.x + centerArea.width, centerArea.y + centerArea.height);

		patches = new Image[9];
		for (int i = 0; i < patches.length; i++) {
			patches[i] = new Image(Display.getDefault(), patcheDatas[i]);
		}

	}

	private void copy(ImageData from, ImageData to, int fromX, int fromY) {
		for (int x = 0; x < to.width; x++) {
			for (int y = 0; y < to.height; y++) {
				int sx = x + fromX;
				int sy = y + fromY;
				int pixel = from.getPixel(sx, sy);
				int alpha = from.getAlpha(sx, sy);

				to.setPixel(x, y, pixel);
				to.setAlpha(x, y, alpha);
			}
		}
	}

	@Override
	public void dispose() {
		if (isDisposed) {
			return;
		}
		SWTExtensions.INSTANCE.safeDispose(patches);
		super.dispose();
		isDisposed = true;
	}

	private int getPatchIndex(int vertical, int horizontal) {
		int index = 0;

		switch (vertical) {
		case SWT.CENTER:
			index += 3;
			break;
		case SWT.BOTTOM:
			index += 6;
			break;
		}

		switch (horizontal) {
		case SWT.CENTER:
			index += 1;
			break;
		case SWT.RIGHT:
			index += 2;
			break;
		}

		return index;
	}

	private void drawPart(GC gc, int patchIndex, Rectangle dest) {
		if (isPositiveArea(dest)) {
			Rectangle src = patches[patchIndex].getBounds();
			gc.drawImage(patches[patchIndex], src.x, src.y, src.width, src.height, dest.x, dest.y, dest.width, dest.height);
		}
	}

	public void fill(GC gc, Rectangle dest) {
		drawPart(gc, getPatchIndex(SWT.TOP, SWT.LEFT), getCorner(dest, SWT.TOP, SWT.LEFT));
		drawPart(gc, getPatchIndex(SWT.TOP, SWT.CENTER), getCorner(dest, SWT.TOP, SWT.CENTER));
		drawPart(gc, getPatchIndex(SWT.TOP, SWT.RIGHT), getCorner(dest, SWT.TOP, SWT.RIGHT));

		drawPart(gc, getPatchIndex(SWT.CENTER, SWT.LEFT), getCorner(dest, SWT.CENTER, SWT.LEFT));
		drawPart(gc, getPatchIndex(SWT.CENTER, SWT.CENTER), getCorner(dest, SWT.CENTER, SWT.CENTER));
		drawPart(gc, getPatchIndex(SWT.CENTER, SWT.RIGHT), getCorner(dest, SWT.CENTER, SWT.RIGHT));

		drawPart(gc, getPatchIndex(SWT.BOTTOM, SWT.LEFT), getCorner(dest, SWT.BOTTOM, SWT.LEFT));
		drawPart(gc, getPatchIndex(SWT.BOTTOM, SWT.CENTER), getCorner(dest, SWT.BOTTOM, SWT.CENTER));
		drawPart(gc, getPatchIndex(SWT.BOTTOM, SWT.RIGHT), getCorner(dest, SWT.BOTTOM, SWT.RIGHT));
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

	@Override
	public boolean isDisposed() {
		return isDisposed;
	}

	private boolean isPositiveArea(Rectangle rect) {
		return rect.width > 0 && rect.height > 0;
	}
}
