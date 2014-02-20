package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Shell

class BoxAlign {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new BoxAlign().run()
	}

	def run() {
		val shell = newShell[
			size = new Point(400, 300)
			onPaint = [
				var extension Shell = it.widget as Shell
				var box = new Rectangle(0, 0, 20, 20);
				gc.background = COLOR_YELLOW
				
				box.relocateTopLeftWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateTopWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateTopRightWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateLeftWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateCenterWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateRightWith(clientArea)
				gc.fillRectangle(box)
					
				box.relocateBottomLeftWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateBottomWith(clientArea)
				gc.fillRectangle(box)
				
				box.relocateBottomRightWith(clientArea)
				gc.fillRectangle(box)
				
				gc.drawRectangle(box.getRelocatedTopLeftWith(clientArea))
				gc.drawRectangle(box.getRelocatedTopWith(clientArea))
				gc.drawRectangle(box.getRelocatedTopRightWith(clientArea))
				gc.drawRectangle(box.getRelocatedLeftWith(clientArea))
				gc.drawRectangle(box.getRelocatedCenterWith(clientArea))
				gc.drawRectangle(box.getRelocatedRightWith(clientArea))
				gc.drawRectangle(box.getRelocatedBottomLeftWith(clientArea))
				gc.drawRectangle(box.getRelocatedBottomWith(clientArea))
				gc.drawRectangle(box.getRelocatedBottomRightWith(clientArea))
			]
		]
		shell.open()
		shell.runLoop()
	}

}
