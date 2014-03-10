package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Shell

class AutoReleaseAndGraphics {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new AutoReleaseAndGraphics().run()
	}

	def run() {
		val Color green = new Color(display, 0, 255, 0);
		
		val shell = newShell[
			size = new Point(400, 300)
			onPaint = [ 
				var extension Shell = it.widget as Shell
				
				// gradient and font be disposed next run loop
				var gradient = newGradient(clientArea.top, clientArea.bottom, COLOR_RED, COLOR_YELLOW).autoRelease
				val font = newFont("Georgia", 80).autoRelease
				
				// creating shape
				val box = clientArea.shrink(50)
				var path = newPath[
					// path will be disposed next run loop
					autoRelease()
					
					// round shapes
					addRoundRectangle(box, 10)
					addRoundRectangle(box.shrink(10), 5)
					
					// text
					var text = "Eclipse"
					var textSize = text.computeTextExtent(font)
					var textRect = new Rectangle(0, 0, textSize.x, textSize.y)
					textRect.relocateCenterWith(box)
					addString("Eclipse", textRect.topLeft, font)
				]
				
				// rotate context
				gc.transform = newTransform[
					autoRelease() // transform will be disposed next loop
					translate(box.center);
					rotate(-8)
					translate(box.center.negated)
				]
				
				gc.lineWidth = 1
				gc.foreground = COLOR_MAGENTA
				gc.background = COLOR_YELLOW
				gc.backgroundPattern = gradient
				gc.fillPath(path)
				gc.drawPath(path)
			]
		]

		// green will be dispose when shell is disposed.
		green.shouldDisposeWith(shell)
		
		shell.open()
		shell.runLoop()

		println(green.disposed)
	}

}
