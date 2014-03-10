package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.NinePatch
import org.eclipse.swt.graphics.ImageData
import org.eclipse.swt.graphics.Rectangle

class NinePatchExample {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE
		
		var data = new ImageData(typeof(NinePatchExample).getResourceAsStream("patch-test.png"))
		val patch = new NinePatch(data, new Rectangle(10, 10, 10, 10))
		
		newShell[
			val me = it
			chainDispose(patch)
			onPaint = [
				patch.fill(it.gc, me.clientArea)
			]
		].openAndRunLoop
		
		println(patch.disposed)
	}
	
}