package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Rectangle

class Test {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		newShell[
			onPaint = [
				var path = newTemporaryPath[
					addRoundRectangle(new Rectangle(10, 10, 100, 100), 20)
				]
				gc.foreground = COLOR_MARGENTA
				gc.drawGradientPath(path, #[COLOR_WHITE, COLOR_RED, COLOR_RED], #[40, 100], true)
			]
		].openAndRunLoop()
	}

}
