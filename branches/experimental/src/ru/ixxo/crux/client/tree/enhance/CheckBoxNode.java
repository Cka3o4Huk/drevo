package ru.ixxo.crux.client.tree.enhance;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNode {

    public JCheckBox getItem()
    {
        return item;
    }

    private JCheckBox item = new JCheckBox();

    public CheckBoxNode(String text, boolean selected, DefaultMutableTreeNode owner) {
        item.setText(text);
        item.setSelected(selected);
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
