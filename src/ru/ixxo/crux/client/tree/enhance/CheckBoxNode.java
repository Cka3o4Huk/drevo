package ru.ixxo.crux.client.tree.enhance;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNode {
//    String text;
//    boolean selected;

    public boolean isMarked()
    {
        return marked;
    }

    public void setMarked(boolean marked)
    {
        this.marked = marked;
    }

    private boolean marked = false;

    public JCheckBox getItem()
    {
        return item;
    }

    private JCheckBox item = new JCheckBox();

    public DefaultMutableTreeNode owner  = null;

    public CheckBoxNode(String text, boolean selected, DefaultMutableTreeNode owner) {
        item.setText(text);
        item.setSelected(selected);
        this.owner = owner;
    }

    public boolean isSelected() {
        return item.isSelected();
    }

    public boolean isEditable() {
        return true;
    }

    public void setSelected(boolean newValue) {
        item.setSelected(newValue);
    }

    public String getText() {
        return item.getText();
    }

    public void setText(String newValue) {
        item.setText(newValue);
    }

    public String toString() {
        return getClass().getName() + "[" + item.getText() + "/" + item.isSelected() + "]";
    }
}
