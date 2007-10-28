package ru.ixxo.crux.client.tree.enhance;

import ru.ixxo.crux.common.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CheckBoxNodeRenderer renderer;

	ChangeEvent changeEvent = null;

	JTree tree;

	public CheckBoxNodeEditor(JTree tree) {
		this.tree = tree;
        renderer = new CheckBoxNodeRenderer();
    }

	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer.getLeafRenderer();
		CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(),checkbox.isSelected(), renderer.getOwner());
		return checkBoxNode;
	}

	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {

            Logger.info("MouseEvent");
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(),
					mouseEvent.getY());
			Logger.info("Path = "+path);
			if (path != null) {
				Object node = path.getLastPathComponent();
				Logger.info("Node = "+node);				
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					Logger.info("treeNode.isLeaf() = "+treeNode.isLeaf());				
					Logger.info("userObject instanceof CheckBoxNode = "+ (userObject instanceof CheckBoxNode));
                    Logger.info("CheckBoxNode.isSelected = " + ((CheckBoxNode)userObject).isSelected());
                    returnValue = ((userObject instanceof CheckBoxNode) && ((CheckBoxNode)userObject).isEditable());
				}
			}
		}
		Logger.info("returnValue = "+returnValue);
		return returnValue;
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row) {

        Component editor = renderer.getTreeCellRendererComponent(tree, value,
				true, expanded, leaf, row, true);

		// editor always selected / focused
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
                if (stopCellEditing()) {
                    fireEditingStopped();
				}
			}
		};
		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(itemListener);
		}

		return editor;
	}
}