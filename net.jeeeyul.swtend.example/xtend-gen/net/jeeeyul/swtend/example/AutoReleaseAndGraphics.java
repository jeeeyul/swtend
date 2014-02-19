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
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class AutoReleaseAndGraphics {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public static void main(final String[] args) {
    AutoReleaseAndGraphics _autoRelease = new AutoReleaseAndGraphics();
    _autoRelease.run();
  }
  
  public Boolean run() {
    Boolean _xblockexpression = null;
    {
      Display _display = this._sWTExtensions.getDisplay();
      final Color green = new Color(_display, 0, 255, 0);
      final Procedure1<Shell> _function = new Procedure1<Shell>() {
        public void apply(final Shell it) {
          Point _point = new Point(400, 300);
          it.setSize(_point);
          final Procedure1<Event> _function = new Procedure1<Event>() {
            public void apply(final Event e) {
              Display _display = it.getDisplay();
              Color _color = new Color(_display, 255, 0, 0);
              Color red = AutoReleaseAndGraphics.this._sWTExtensions.<Color>autoRelease(_color);
              Display _display_1 = it.getDisplay();
              Color _color_1 = new Color(_display_1, 0, 0, 255);
              Color blue = AutoReleaseAndGraphics.this._sWTExtensions.<Color>autoRelease(_color_1);
              Rectangle _clientArea = it.getClientArea();
              Point _top = AutoReleaseAndGraphics.this._sWTExtensions.getTop(_clientArea);
              Rectangle _clientArea_1 = it.getClientArea();
              Point _bottom = AutoReleaseAndGraphics.this._sWTExtensions.getBottom(_clientArea_1);
              Pattern _newGradient = AutoReleaseAndGraphics.this._sWTExtensions.newGradient(_top, _bottom, red, blue);
              Pattern gradient = AutoReleaseAndGraphics.this._sWTExtensions.<Pattern>autoRelease(_newGradient);
              e.gc.setBackgroundPattern(gradient);
              Rectangle _clientArea_2 = it.getClientArea();
              Rectangle _shrinked = AutoReleaseAndGraphics.this._sWTExtensions.getShrinked(_clientArea_2, 10);
              AutoReleaseAndGraphics.this._sWTExtensions.fillRoundRectangle(e.gc, _shrinked, 20);
              Rectangle _clientArea_3 = it.getClientArea();
              Point _center = AutoReleaseAndGraphics.this._sWTExtensions.getCenter(_clientArea_3);
              Rectangle _rectangle = AutoReleaseAndGraphics.this._sWTExtensions.toRectangle(_center);
              Rectangle circle = AutoReleaseAndGraphics.this._sWTExtensions.expand(_rectangle, 20);
              e.gc.setBackground(green);
              AutoReleaseAndGraphics.this._sWTExtensions.fillOval(e.gc, circle);
            }
          };
          AutoReleaseAndGraphics.this._sWTExtensions.setOnPaint(it, _function);
        }
      };
      Shell shell = this._sWTExtensions.newShell(_function);
      this._sWTExtensions.shouldDisposeWith(green, shell);
      shell.open();
      this._sWTExtensions.runLoop(shell);
      boolean _isDisposed = green.isDisposed();
      _xblockexpression = (InputOutput.<Boolean>println(Boolean.valueOf(_isDisposed)));
    }
    return _xblockexpression;
  }
}
