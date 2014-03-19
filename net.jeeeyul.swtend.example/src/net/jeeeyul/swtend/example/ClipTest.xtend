package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT

class ClipTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		newShell[
			onPaint = [
				var gc = it.gc
				gc.antialias = SWT.ON
				val offset = newRectangle(10, 10, 100, 22)
				gc.fillGradientRoundRectangle(offset, 10, CORNER_TOP, #[COLOR_RED, COLOR_WHITE, COLOR_MAGENTA], #[50, 100], true)
				gc.fillGradientRoundRectangle(offset.getTranslated(110, 0), 20, CORNER_TOP_RIGHT, #[COLOR_RED, COLOR_WHITE, COLOR_MAGENTA], #[50, 100], false)
				
				gc.fillRoundRectangle(offset.getTranslated(0, 30), 10, CORNER_ALL)
			]
		].openAndRunLoop
	}

}
