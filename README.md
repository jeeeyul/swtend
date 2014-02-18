swtend
======

xtend libraries for SWT

```xtend
var ui = newComposite[
  layout = newGridLayout[
    numColumns = 2
    
    newLabel[ text = "name" ]
    newTextField[
      layoutData = FILL_HORIZONTAL
    ]
    
    newLabel[
      text = "test"
      layoutData = newGridData[
        horizontalSpan = 2
      ]
    ]
    
    newPushButton[
      text = "push me"
      onClick = [
        println("Hello World!")
      ]
    ]
  ]
]
```
