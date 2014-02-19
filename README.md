## SWTend

[xtend](http://xtend-lang.org) libraries for SWT, SWT + Xtend

### Builder Pattern to create SWT UI
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
### Geometry Extension
```xtend
  var rect = new Rectangle(0, 0, 10, 10) // SWT Rectangle
  
  rect.contains(5, 5)     // true
  rect.copy()             // clone
  rect.translate(new Point(10, 10))
```


### Custom Controls
* ColorPicker
* ColorWell
