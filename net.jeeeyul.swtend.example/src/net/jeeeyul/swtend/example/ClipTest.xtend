package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions

class ClipTest {
	def static void main(String[] args) {
		val extension SWTExtensions = SWTExtensions.INSTANCE

		newShell[
			layout=newGridLayout
			newToolBar[
				newToolItem[
					text ="hello"
				]
				
			]
		].openAndRunLoop
	}

}
