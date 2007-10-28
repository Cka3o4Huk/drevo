package ru.ixxo.crux.client.tree;

import ru.ixxo.crux.client.tree.enhance.CheckBoxNode;
import ru.ixxo.crux.common.Logger;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xiva
 * Date: 27.10.2007
 * Time: 1:19:27
 * To change this template use File | Settings | File Templates.
 */
public class MenuListModel extends DefaultTreeModel{

    HashMap<String,CheckBoxNode> selectedItems = new HashMap<String,CheckBoxNode>();

    HashMap<String,CheckBoxNode> markedItems = new HashMap<String,CheckBoxNode>();

    public MenuListModel(TreeNode root)
    {
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
        //к примеру, элементы хранятся в списке selectedItems;
        List res = new ArrayList();
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            Object data = it.next();
            if (data instanceof CheckBoxNode && ((CheckBoxNode)data).isSelected()) {
                res.add(((CheckBoxNode)data).getItem());
                //добавляем к результату нужный объект, а не обертку!!!
            }
        }
        return res;
    }

    public boolean isNothingChecked()
    {
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
            else Logger.info("bug");
            ch.getItem().setSelected(false);
        }
        mark();
        selectedItems.clear();
    }

    public void mark(){
        for (Iterator it = selectedItems.values().iterator(); it.hasNext();) {
            CheckBoxNode ch = (CheckBoxNode)it.next();
            markedItems.put(ch.getText().substring(0, ch.getText().lastIndexOf('[')-1), ch);
        }

        for (Iterator it = markedItems.values().iterator(); it.hasNext();) {
            ((CheckBoxNode)it.next()).setMarked(true);
        }
    }

    public void unmark(){
        for (Iterator it = markedItems.values().iterator(); it.hasNext();) {
            ((CheckBoxNode)it.next()).setMarked(false);
        }
        markedItems.clear();
    }
}
