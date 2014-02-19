package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point

class AutoReleaseAndGraphics {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new AutoReleaseAndGraphics().run()
	}

	def run() {
		val Color green = new Color(display, 0, 255, 0);

		var shell = newShell[
			size = new Point(400, 300)
			onPaint = [ e |
				// red, blue, gradient will be disposed in next run loop
				var red = new Color(display, 255, 0, 0).autoRelease
				var blue = new Color(display, 0, 0, 255).autoRelease
				var gradient = newGradient(clientArea.top, clientArea.bottom, red, blue).autoRelease
				
				
				// draw gradient
				e.gc.backgroundPattern = gradient
				e.gc.fillRoundRectangle(clientArea.getShrinked(10), 20)

				//draw green ball
				var circle = clientArea.center.toRectangle.expand(20)
				e.gc.background = green				
				e.gc.fillOval(circle)
			]
		]
		
		// green will be dispose when shell is disposed.
		green.shouldDisposeWith(shell)
		
		shell.open()
		shell.runLoop()
		
		println(green.disposed)
	}

}
