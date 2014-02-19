package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point

class AutoRelease {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new AutoRelease().run()
	}

	def run() {

		var shell = newShell[
			size = new Point(400, 300)
			
			onPaint = [e|
				// will be disposed in next run loop
				var red = new Color(display, 255, 0, 0).autoRelease
				var blue = new Color(display, 0, 0, 255).autoRelease
				var gradient = newGradient(clientArea.top, clientArea.bottom, red, blue).autoRelease
				
				e.gc.backgroundPattern = gradient
				e.gc.fillRoundRectangle(clientArea.getShrinked(10), 20)
			]
		]

		shell.open()
		shell.runLoop()
	}

}
