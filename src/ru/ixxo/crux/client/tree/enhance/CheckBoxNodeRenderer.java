package ru.ixxo.crux.client.tree.enhance;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer implements TreeCellRenderer {
	private JCheckBox leafRenderer = new JCheckBox();

	Color selectionBorderColor, selectionForeground, selectionBackground,
			textForeground, textBackground;

	protected JCheckBox getLeafRenderer() {
		return leafRenderer;
	}

	public CheckBoxNodeRenderer() {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		Boolean booleanValue = (Boolean) UIManager
				.get("Tree.drawsFocusBorderAroundIcon");
		leafRenderer.setFocusPainted((booleanValue != null)
				&& (booleanValue.booleanValue()));

		selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
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
			Object userObject = ((DefaultMutableTreeNode) value)
					.getUserObject();
			if (userObject instanceof CheckBoxNode) {
				CheckBoxNode node = (CheckBoxNode) userObject;
				leafRenderer.setText(node.getText());
				leafRenderer.setSelected(node.isSelected());
			}
		}

/*
		JPanel panel = new JPanel();
		Border border = BorderFactory.createEmptyBorder();
		panel.setBorder(border);
		panel.add(new JTextField("MIZH"));		
		panel.add(leafRenderer);
		return panel;
*/
        return leafRenderer;
	}
}