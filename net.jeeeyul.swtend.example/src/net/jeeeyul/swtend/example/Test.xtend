package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Rectangle

class Test {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		newShell[
			backgroundMode = SWT.INHERIT_FORCE
			backgroundImage = ICON_QUESTION
			newCTabFolder[
				minimizeVisible = true
				topRight = newToolBar(SWT.FLAT) [
					background = null
					newToolItem[
						text = "aaa"
					]
				]
				newToolBar(SWT.FLAT) [
					background = null
					newToolItem[
						text = "bbb"
					]
					bounds = new Rectangle(100, 0, 100, 20)
				]
				newCTabItem[
					text = "A Tab Item"
					newComposite[
						newPushButton[
							text = "A Tab Item"
						]
					]
				]
			]
		].openAndRunLoop()
	}

}
