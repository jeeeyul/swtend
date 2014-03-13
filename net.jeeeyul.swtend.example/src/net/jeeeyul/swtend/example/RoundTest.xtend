package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions

class RoundTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		newShell[
			onPaint = [
				var gc = it.gc
				
				var path = newTemporaryPath[
					moveTo(200, 20)
					lineTo(40, 20)
					addArc(newRectangleWithSize(40).translate(20, 20), 90, 90);
				]
				gc.draw(path)
				
			]
		].openAndRunLoop()
		
	}
	
}