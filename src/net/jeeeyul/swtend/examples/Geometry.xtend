package net.jeeeyul.swtend.examples

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.SWT

class Geometry {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new Geometry().run()
	}

	def run() {
		var shell = newShell[
			layout = newFillLayout
			background = colors.white
			backgroundMode = SWT::INHERIT_FORCE
			
			newTabFolder[
				newTabItem[
					text = "Hello"
					newComposite[
						layout = newGridLayout
						newPushButton[text = "Hello"]
					]
				]
				newTabItem[
					text = "tree"
					newComposite[
						layout = newFillLayout
						newTree(SWT.NONE)[
							newTreeItem[
								text = "Root"
								newTreeItem[text = "Sub"]
								newTreeItem[text = "Sub"]
								newTreeItem[text = "Sub"]
							]
						]
					]
				]
			]
		]
		shell.open;
		shell.runLoop()
	}

}
