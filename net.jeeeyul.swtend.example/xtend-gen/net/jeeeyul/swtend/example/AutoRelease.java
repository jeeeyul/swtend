package net.jeeeyul.swtend.example;

import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class AutoRelease {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public static void main(final String[] args) {
    AutoRelease _autoRelease = new AutoRelease();
    _autoRelease.run();
  }
  
  public void run() {
    final Procedure1<Shell> _function = new Procedure1<Shell>() {
      public void apply(final Shell it) {
        Point _point = new Point(400, 300);
        it.setSize(_point);
        final Procedure1<Event> _function = new Procedure1<Event>() {
          public void apply(final Event e) {
            Display _display = it.getDisplay();
            Color _color = new Color(_display, 255, 0, 0);
            Color red = AutoRelease.this._sWTExtensions.<Color>autoRelease(_color);
            Display _display_1 = it.getDisplay();
            Color _color_1 = new Color(_display_1, 0, 0, 255);
            Color blue = AutoRelease.this._sWTExtensions.<Color>autoRelease(_color_1);
            Rectangle _clientArea = it.getClientArea();
            Point _top = AutoRelease.this._sWTExtensions.getTop(_clientArea);
            Rectangle _clientArea_1 = it.getClientArea();
            Point _bottom = AutoRelease.this._sWTExtensions.getBottom(_clientArea_1);
            Pattern _newGradient = AutoRelease.this._sWTExtensions.newGradient(_top, _bottom, red, blue);
            Pattern gradient = AutoRelease.this._sWTExtensions.<Pattern>autoRelease(_newGradient);
            e.gc.setBackgroundPattern(gradient);
            Rectangle _clientArea_2 = it.getClientArea();
            Rectangle _shrinked = AutoRelease.this._sWTExtensions.getShrinked(_clientArea_2, 10);
            AutoRelease.this._sWTExtensions.fillRoundRectangle(e.gc, _shrinked, 20);
          }
        };
        AutoRelease.this._sWTExtensions.setOnPaint(it, _function);
      }
    };
    Shell shell = this._sWTExtensions.newShell(_function);
    shell.open();
    this._sWTExtensions.runLoop(shell);
  }
}
