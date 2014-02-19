package net.jeeeyul.swtend.example;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Events {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public static void main(final String[] args) {
    Events _events = new Events();
    _events.run();
  }
  
  public void run() {
    final Procedure1<Shell> _function = new Procedure1<Shell>() {
      public void apply(final Shell it) {
        final Procedure1<GridLayout> _function = new Procedure1<GridLayout>() {
          public void apply(final GridLayout it) {
            it.numColumns = 3;
          }
        };
        GridLayout _newGridLayout = Events.this._sWTExtensions.newGridLayout(_function);
        it.setLayout(_newGridLayout);
        final Procedure1<Button> _function_1 = new Procedure1<Button>() {
          public void apply(final Button it) {
            it.setText("Push Me");
            final Procedure1<Event> _function = new Procedure1<Event>() {
              public void apply(final Event it) {
                InputOutput.<String>println("Mouse Down");
              }
            };
            Events.this._sWTExtensions.setOnMouseDown(it, _function);
            final Procedure1<Event> _function_1 = new Procedure1<Event>() {
              public void apply(final Event it) {
                InputOutput.<String>println("Mouse Up");
              }
            };
            Events.this._sWTExtensions.setOnMouseUp(it, _function_1);
          }
        };
        Events.this._sWTExtensions.newPushButton(it, _function_1);
        final Procedure1<Button> _function_2 = new Procedure1<Button>() {
          public void apply(final Button it) {
            it.setText("Button 2");
            final Procedure1<Event> _function = new Procedure1<Event>() {
              public void apply(final Event it) {
                MessageDialog.openInformation(null, "Event", "2");
              }
            };
            Events.this._sWTExtensions.<Button>setOnEvent(it, SWT.Selection, _function);
          }
        };
        Events.this._sWTExtensions.newPushButton(it, _function_2);
        final Procedure1<Button> _function_3 = new Procedure1<Button>() {
          public void apply(final Button it) {
            it.setText("Drag Me");
            final Procedure1<Event> _function = new Procedure1<Event>() {
              public void apply(final Event it) {
                InputOutput.<String>println("drag!");
              }
            };
            Events.this._sWTExtensions.setOnDragDetect(it, _function);
          }
        };
        Events.this._sWTExtensions.newPushButton(it, _function_3);
      }
    };
    Shell shell = this._sWTExtensions.newShell(_function);
    shell.open();
    this._sWTExtensions.runLoop(shell);
  }
}
