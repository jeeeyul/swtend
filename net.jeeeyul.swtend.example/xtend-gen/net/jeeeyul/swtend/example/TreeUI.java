package net.jeeeyul.swtend.example;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import net.jeeeyul.swtend.SWTExtensions;
import net.jeeeyul.swtend.sam.Procedure1;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class TreeUI {
  @Extension
  private SWTExtensions _sWTExtensions = SWTExtensions.INSTANCE;
  
  public static void main(final String[] args) {
    TreeUI _treeUI = new TreeUI();
    _treeUI.run();
  }
  
  private Tree tree;
  
  public void run() {
    final Procedure1<Shell> _function = new Procedure1<Shell>() {
      public void apply(final Shell it) {
        final Procedure1<Tree> _function = new Procedure1<Tree>() {
          public void apply(final Tree it) {
            it.setHeaderVisible(true);
            it.setLinesVisible(true);
            final Procedure1<TreeColumn> _function = new Procedure1<TreeColumn>() {
              public void apply(final TreeColumn it) {
                it.setText("Hello");
                it.setWidth(200);
              }
            };
            TreeUI.this._sWTExtensions.newTreeColumn(it, _function);
            final Procedure1<TreeItem> _function_1 = new Procedure1<TreeItem>() {
              public void apply(final TreeItem it) {
                it.setText("root");
                Color _COLOR_INFO_FOREGROUND = TreeUI.this._sWTExtensions.COLOR_INFO_FOREGROUND();
                it.setForeground(_COLOR_INFO_FOREGROUND);
                Color _COLOR_INFO_BACKGROUND = TreeUI.this._sWTExtensions.COLOR_INFO_BACKGROUND();
                it.setBackground(_COLOR_INFO_BACKGROUND);
                Image _iCON_WORKING = TreeUI.this._sWTExtensions.getICON_WORKING();
                it.setImage(_iCON_WORKING);
                final Procedure1<TreeItem> _function = new Procedure1<TreeItem>() {
                  public void apply(final TreeItem it) {
                    it.setText("sub 1");
                  }
                };
                TreeUI.this._sWTExtensions.newTreeItem(it, _function);
                final Procedure1<TreeItem> _function_1 = new Procedure1<TreeItem>() {
                  public void apply(final TreeItem it) {
                    it.setText("sub 2");
                  }
                };
                TreeUI.this._sWTExtensions.newTreeItem(it, _function_1);
                final Procedure1<TreeItem> _function_2 = new Procedure1<TreeItem>() {
                  public void apply(final TreeItem it) {
                    it.setText("sub 3");
                    Color _COLOR_RED = TreeUI.this._sWTExtensions.COLOR_RED();
                    it.setForeground(_COLOR_RED);
                    Image _iCON_WARNING = TreeUI.this._sWTExtensions.getICON_WARNING();
                    it.setImage(_iCON_WARNING);
                    final Procedure1<TreeItem> _function = new Procedure1<TreeItem>() {
                      public void apply(final TreeItem it) {
                        it.setText("leaf");
                      }
                    };
                    TreeUI.this._sWTExtensions.newTreeItem(it, _function);
                    final Procedure1<TreeItem> _function_1 = new Procedure1<TreeItem>() {
                      public void apply(final TreeItem it) {
                        it.setText("leaf");
                      }
                    };
                    TreeUI.this._sWTExtensions.newTreeItem(it, _function_1);
                    final Procedure1<TreeItem> _function_2 = new Procedure1<TreeItem>() {
                      public void apply(final TreeItem it) {
                        it.setText("leaf");
                      }
                    };
                    TreeUI.this._sWTExtensions.newTreeItem(it, _function_2);
                  }
                };
                TreeUI.this._sWTExtensions.newTreeItem(it, _function_2);
              }
            };
            TreeUI.this._sWTExtensions.newTreeItem(it, _function_1);
          }
        };
        Tree _newTree = TreeUI.this._sWTExtensions.newTree(it, _function);
        TreeUI.this.tree = _newTree;
      }
    };
    Shell shell = this._sWTExtensions.newShell(_function);
    Iterator<? extends Widget> _allContent = this._sWTExtensions.getAllContent(this.tree);
    Iterator<TreeItem> _filter = Iterators.<TreeItem>filter(_allContent, TreeItem.class);
    final Function1<TreeItem,Boolean> _function_1 = new Function1<TreeItem,Boolean>() {
      public Boolean apply(final TreeItem it) {
        int _itemCount = it.getItemCount();
        return Boolean.valueOf((_itemCount > 0));
      }
    };
    Iterator<TreeItem> _filter_1 = IteratorExtensions.<TreeItem>filter(_filter, _function_1);
    final org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TreeItem> _function_2 = new org.eclipse.xtext.xbase.lib.Procedures.Procedure1<TreeItem>() {
      public void apply(final TreeItem it) {
        it.setExpanded(true);
      }
    };
    IteratorExtensions.<TreeItem>forEach(_filter_1, _function_2);
    final Procedure1<Event> _function_3 = new Procedure1<Event>() {
      public void apply(final Event it) {
        it.height = 40;
      }
    };
    this._sWTExtensions.setOnMeasureItem(this.tree, _function_3);
    shell.open();
    this._sWTExtensions.runLoop(shell);
  }
}
