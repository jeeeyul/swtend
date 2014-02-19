swtend
======

xtend libraries for SWT

```xtend
var ui = newComposite[
  layout = newGridLayout[
    numColumns = 2
  ]
  
  newTree[
    layoutData = FILL_HORIZONTAL[
      horizontalSpan = 2
    ]
    
    newTreeItem[
      text = "Root"
      
      newTreeItem[text = "Sub"]
      newTreeItem[text = "Sub"]
    ]
    
    newPushButton[
      text = "Push Me!"
      onSelection = [
        println("Hello World!")
      ]
    ]
  ]
]
```
