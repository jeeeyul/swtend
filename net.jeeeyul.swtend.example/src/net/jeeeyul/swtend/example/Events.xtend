package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.SWT

class Events {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new Events().run()
	}

	def run() {

		var shell = newShell[
			layout = newGridLayout[
				numColumns = 3
			]
			newPushButton[
				text = "Push Me"
				onMouseDown = [
					println("Mouse Down")
				]
				onMouseUp = [
					println("Mouse Up")
				]
			]
			// custom event type
			newPushButton[
				text = "Button 2"
				setOnEvent(SWT.Selection) [
					MessageDialog.openInformation(null, "Event", "2");
				]
			]
			newPushButton[
				text = "Drag Me"
				onDragDetect = [
					println("drag!")
				]
			]
		]

		shell.open()
		shell.runLoop()
	}

}
