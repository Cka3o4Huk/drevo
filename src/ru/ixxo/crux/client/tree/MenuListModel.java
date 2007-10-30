package ru.ixxo.crux.client.tree;

import ru.ixxo.crux.client.tree.enhance.CheckBoxNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MenuListModel extends DefaultTreeModel{

    public HashMap<String, DefaultMutableTreeNode> getSelectedItems() {
        return selectedItems;
    }

    HashMap<String,DefaultMutableTreeNode> selectedItems = new HashMap<String,DefaultMutableTreeNode>();

    public HashMap<String, DefaultMutableTreeNode> getMarkedItems() {
        return markedItems;
    }

    HashMap<String, DefaultMutableTreeNode> markedItems = new HashMap<String,DefaultMutableTreeNode>();

    public MenuListModel(TreeNode root) {
        super(root);
    }

    public void setSelected(DefaultMutableTreeNode data, boolean selected) {
        //data.setSelected(selected);
        String str = ((CheckBoxNode)data.getUserObject()).getText();
        str = str.substring(0, str.lastIndexOf('[')-1);
        if (selected) {
            selectedItems.put(str,data);
            ((CheckBoxNode)data.getUserObject()).setSelected(true);
        }
        else {
            //data.owner = selectedItems.get(str).owner;
            selectedItems.remove(str);
            ((CheckBoxNode)data.getUserObject()).setSelected(false);
        }
    }

//    public List getSelected() {
//        List res = new ArrayList();
//        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
//            Object data = it.next();
//            if (data instanceof CheckBoxNode && ((CheckBoxNode)data).isSelected()) {
//                res.add(((CheckBoxNode)data).getItem());
//            }
//        }
//        return res;
//    }

    public boolean isNothingChecked() {
//        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
//            if (((CheckBoxNode)it.next()).isSelected()) return false;
//        }
//        return true;
        return (selectedItems.size()==0);
    }

    public void uncheckAll(){
//        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
//            CheckBoxNode ch = (CheckBoxNode)it.next();
//            if (ch.owner!=null)
//                ch.owner.setUserObject(new CheckBoxNode(ch.getText().
//                        substring(0, ch.getText().lastIndexOf(']')+1), false, ch.owner));
//        }

        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            CheckBoxNode ch = (CheckBoxNode)((DefaultMutableTreeNode)it.next()).getUserObject();
            ch.setSelected(false);
        }
        selectedItems.clear();
    }

    public void mark(){
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) it.next();
            CheckBoxNode ch = (CheckBoxNode)node.getUserObject();
            markedItems.put(ch.getText().substring(0, ch.getText().lastIndexOf('[')-1), node);
        }
    }

    public void unmark(){
        markedItems.clear();
    }

    public boolean isNothingMarked() {
        return (markedItems.size()==0);
    }
}
