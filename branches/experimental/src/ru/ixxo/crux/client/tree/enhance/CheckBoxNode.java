package ru.ixxo.crux.client.tree.enhance;

import javax.swing.JCheckBox;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNode {

	private JCheckBox item;
	private DefaultMutableTreeNode owner;

	public JCheckBox getItem() {
		return item;
	}

	public CheckBoxNode(String text, boolean selected,
			DefaultMutableTreeNode owner, JCheckBox item) {
		this.item = item;		
		this.item.setText(text);
		this.item.setSelected(selected);
		this.owner = owner;
	}

	public CheckBoxNode(String text,DefaultMutableTreeNode owner) {
		this(text, false, owner, new ComplexNode(text));		
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
		return getClass().getName() + "[" + item.getText() + "/"
				+ item.isSelected() + "]";
	}

	public DefaultMutableTreeNode getOwner() {
		return owner;
	}
}
