package ru.ixxo.crux.client.tree;

import ru.ixxo.crux.client.tree.enhance.CheckBoxNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xiva
 * Date: 27.10.2007
 * Time: 1:19:27
 * To change this template use File | Settings | File Templates.
 */
public class MenuListModel extends DefaultTreeModel{

    HashMap<String,CheckBoxNode> selectedItems = new HashMap<String,CheckBoxNode>();

    public HashMap<String, DefaultMutableTreeNode> getMarkedItems() {
        return markedItems;
    }

    HashMap<String, DefaultMutableTreeNode> markedItems = new HashMap<String,DefaultMutableTreeNode>();

    public MenuListModel(TreeNode root) {
        super(root);
    }

    public void setSelected(CheckBoxNode data, boolean selected) {
        data.setSelected(selected);
        String str = data.getText();
        str = str.substring(0, str.lastIndexOf('[')-1);
        if (selected) selectedItems.put(str,data);
        else {
            data.owner = selectedItems.get(str).owner;
            selectedItems.remove(str);
        }
    }

    public List getSelected() {
        List res = new ArrayList();
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            Object data = it.next();
            if (data instanceof CheckBoxNode && ((CheckBoxNode)data).isSelected()) {
                res.add(((CheckBoxNode)data).getItem());
            }
        }
        return res;
    }

    public boolean isNothingChecked() {
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            if (((CheckBoxNode)it.next()).isSelected()) return false;
        }
        return true;
    }

    public void uncheckAll(){
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            CheckBoxNode ch = (CheckBoxNode)it.next();
            if (ch.owner!=null)
                ch.owner.setUserObject(new CheckBoxNode(ch.getText().
                        substring(0, ch.getText().lastIndexOf(']')+1), false, ch.owner));
        }
        selectedItems.clear();
    }

    public void mark(){
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            CheckBoxNode ch = (CheckBoxNode)it.next();
            markedItems.put(ch.getText().substring(0, ch.getText().lastIndexOf('[')-1), ch.owner);
        }
    }

    public void unmark(){
        markedItems.clear();
    }

    public boolean isNothingMarked() {
        return (markedItems.size()==0);
    }
}
