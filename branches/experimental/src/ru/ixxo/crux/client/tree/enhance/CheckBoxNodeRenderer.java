package ru.ixxo.crux.client.tree.enhance;

import ru.ixxo.crux.client.tree.MenuListModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class CheckBoxNodeRenderer implements TreeCellRenderer {

    private JCheckBox leafRenderer = new JCheckBox();

    public DefaultMutableTreeNode getOwner()
    {
        return owner;
    }

    private DefaultMutableTreeNode owner;

    Color selectionBorderColor, selectionForeground, selectionBackground,
			textForeground, textBackground, marked;

	protected JCheckBox getLeafRenderer() {
        return leafRenderer;
	}

	public CheckBoxNodeRenderer() {
        leafRenderer.setOpaque(false);
        Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		Boolean booleanValue = (Boolean) UIManager
				.get("Tree.drawsFocusBorderAroundIcon");
		leafRenderer.setFocusPainted((booleanValue != null)
				&& (booleanValue));

        selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
        marked = new Color(255, 0, 0);
    }

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, selected, expanded,
				leaf, row, false);
		leafRenderer.setText(stringValue);
		leafRenderer.setSelected(false);

		leafRenderer.setEnabled(tree.isEnabled());

		if (selected) {
			leafRenderer.setForeground(selectionForeground);
			leafRenderer.setBackground(selectionBackground);
		} else {
			leafRenderer.setForeground(textForeground);
			leafRenderer.setBackground(textBackground);
		}

        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            owner = (DefaultMutableTreeNode) value;
            Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

            if (userObject instanceof CheckBoxNode) {
                CheckBoxNode node = (CheckBoxNode) userObject;
                leafRenderer.setText(node.getText());
                leafRenderer.setSelected(node.isSelected());
                if (tree.getModel() instanceof MenuListModel){
                    if (((MenuListModel)tree.getModel()).getSelectedItems().containsValue(owner))
                        leafRenderer.setSelected(true);
                    if (((MenuListModel)tree.getModel()).getMarkedItems().containsValue(owner))
                        leafRenderer.setForeground(marked);
                }


            }
        }

        return leafRenderer;
	}
}