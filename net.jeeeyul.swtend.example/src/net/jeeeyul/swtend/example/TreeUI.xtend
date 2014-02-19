package net.jeeeyul.swtend.example

import net.jeeeyul.swtend.SWTExtensions
import org.eclipse.swt.widgets.TreeItem
import org.eclipse.swt.widgets.Tree

class TreeUI {
	extension SWTExtensions = SWTExtensions.INSTANCE

	def static void main(String[] args) {
		new TreeUI().run()
	}

	private var Tree tree;

	def run() {

		var shell = newShell[
			tree = newTree[
				headerVisible = true
				linesVisible = true
				newTreeColumn[
					text = "Hello"
					width = 200
				]
				newTreeItem[
					text = "root"
					foreground = COLOR_INFO_FOREGROUND
					background = COLOR_INFO_BACKGROUND
					image = ICON_WORKING
					
					newTreeItem[text = "sub 1"]
					newTreeItem[text = "sub 2"]
					newTreeItem[
						text = "sub 3"
						foreground = COLOR_RED
						image = ICON_WARNING
						newTreeItem[text = "leaf"]
						newTreeItem[text = "leaf"]
						newTreeItem[text = "leaf"]
					]
				]
			]
		]

		tree.allContent.filter(typeof(TreeItem)).filter[it.itemCount > 0].forEach [
			it.expanded = true
		]
		
		tree.onMeasureItem = [
			it.height = 40
		]
		
		shell.open()
		shell.runLoop()
	}

}
