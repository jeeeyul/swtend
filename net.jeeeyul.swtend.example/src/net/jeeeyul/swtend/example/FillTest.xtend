package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions

class FillTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		newShell[
			val me = it
			onPaint = [
				gc.fillGradientRectangle(me.clientArea, #[COLOR_RED, COLOR_WHITE], #[30], false)
			]
		].openAndRunLoop
	}
	
}