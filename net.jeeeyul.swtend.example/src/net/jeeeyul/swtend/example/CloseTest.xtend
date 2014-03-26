package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.swt.graphics.Rectangle

class CloseTest {
	static extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		newShell[
			onPaint = [
				val gc = it.gc
				val width = 20
				val color = HSB.RED
				val bounds = newRectangle(10, 10, 50, 50)
				val path = createPath(bounds, width)
				val shadowPath = createPath(bounds.getTranslated(0, 1), width)
				shadowPath.addRectangle(bounds.getExpanded(5))

				gc.background = color.toAutoDisposeColor
				gc.fill(path)
				
				gc.background = color.ampBrightness(0.3f).toAutoDisposeColor
				gc.withClip(createPath(bounds, width))[
					gc.fillPath(shadowPath)
				]
			]
		].openAndRunLoop
	}

	def static createPath(Rectangle bounds, int width) {
		newTemporaryPath[
			val center = bounds.center
			var d = Math.sqrt(width * width / 2) as float
			moveTo(bounds.x + d, bounds.y)
			lineTo(center.x, center.y - d)
			lineTo(bounds.x + bounds.width - d, bounds.y)
			lineTo(bounds.x + bounds.width, bounds.y + d)
			lineTo(center.x + d, center.y)
			lineTo(bounds.x + bounds.width, bounds.y + bounds.height - d)
			lineTo(bounds.x + bounds.width - d, bounds.y + bounds.height)
			lineTo(center.x, center.y + d)
			lineTo(bounds.x + d, bounds.y + bounds.height)
			lineTo(bounds.x, bounds.y + bounds.height - d)
			lineTo(center.x - d, center.y)
			lineTo(bounds.x, bounds.y + d)
			lineTo(bounds.x + d, bounds.y)
		]
	}

}
