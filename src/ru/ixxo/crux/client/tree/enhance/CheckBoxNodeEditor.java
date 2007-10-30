package ru.ixxo.crux.client.tree.enhance;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import ru.ixxo.crux.common.Logger;
 
public class CheckBoxNodeEditor extends AbstractCellEditor implements
		TreeCellEditor {

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
		if (checkbox instanceof ComplexNode)
			return new CheckBoxNode(checkbox.getText(), checkbox.isSelected(),
					renderer.getOwner(), checkbox);
		return null;
	}

	public boolean isCellEditable(EventObject event) {

		boolean returnValue = false;
		if (event instanceof MouseEvent) {

			Logger.info("MouseEvent");
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(),
					mouseEvent.getY());
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Logger.info("Path = " + path);
			if (path != null) {
				Object node = path.getLastPathComponent();
				Logger.info("Node = " + node.getClass());
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					returnValue = processTreeNode((DefaultMutableTreeNode) node) instanceof ComplexNode;
					model.nodeChanged((DefaultMutableTreeNode) node);	
					return returnValue;
				}			
			}
		}
		Logger.info("returnValue = " + returnValue);
		returnValue = false;
		return returnValue;
	}
	
	protected void processChildrenOfComplexNode(ComplexNode item, DefaultMutableTreeNode treeNode){
		if (item.getState() == ComplexNode.SELECTED) {
			Enumeration e = treeNode.depthFirstEnumeration();
			while (e.hasMoreElements()) {
				Object a = e.nextElement();
				if ((DefaultMutableTreeNode) a != treeNode) {
					DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode) a;
					Object checkBox = ((CheckBoxNode) childTreeNode.getUserObject()).getItem();
					if(checkBox instanceof ComplexNode)
						((ComplexNode) checkBox).setState(ComplexNode.SELECTED);
				}
			}
		} else {
			Enumeration e = treeNode.depthFirstEnumeration();
			while (e.hasMoreElements()) {
				Object a = e.nextElement();
				if ((DefaultMutableTreeNode) a != treeNode) {
					DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode) a;
					Object checkBox = ((CheckBoxNode) childTreeNode.getUserObject()).getItem();
					if(checkBox instanceof ComplexNode)
						((ComplexNode) checkBox).setState(ComplexNode.NOT_SELECTED);
				}
			}
		}
	}
	
	protected void processParentOfComplexNode(ComplexNode item, DefaultMutableTreeNode treeNode){
		int flag;
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)(treeNode.getParent());
		Object userObject = parent.getUserObject();
				
		while (userObject instanceof CheckBoxNode) {
			flag = (parent.getChildCount()) * 2;
			for (int r = 0; r <= (parent.getChildCount() - 1); r++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent
						.getChildAt(r);
				Object childUserObject = child.getUserObject();
				if(childUserObject instanceof CheckBoxNode)
				{
					if (((ComplexNode)(((CheckBoxNode) childUserObject).getItem())).getState() == ComplexNode.NOT_SELECTED) {
						flag = flag - 2;
					}
					if (((ComplexNode)(((CheckBoxNode) childUserObject).getItem())).getState() == ComplexNode.DONT_CARE) {
						flag--;
					}
				}
			}
			if (flag == 0) {
				((ComplexNode)((CheckBoxNode) userObject).getItem())
						.setState(ComplexNode.NOT_SELECTED);
			}
			if (flag == (parent.getChildCount()) * 2) {
				((ComplexNode)((CheckBoxNode) userObject).getItem())
						.setState(ComplexNode.SELECTED);
			}
			if ((flag < (parent.getChildCount()) * 2) && (flag > 0)) {
				((ComplexNode)((CheckBoxNode) userObject).getItem())
						.setState(ComplexNode.DONT_CARE);
			}

			parent = (DefaultMutableTreeNode) parent.getParent();
			userObject = parent.getUserObject();
		}
	}
	
	protected Object processCheckBoxNode(CheckBoxNode node){
		JCheckBox item = node.getItem();
		
		if (item instanceof ComplexNode) {
			((ComplexNode)item).nextState();
			
			processChildrenOfComplexNode((ComplexNode)item, node.getOwner());
			processParentOfComplexNode((ComplexNode)item, node.getOwner());
		}	
		
		return item;
	}
	
	protected Object processTreeNode(DefaultMutableTreeNode treeNode){
		Object userObject = treeNode.getUserObject();
					
		if (userObject instanceof CheckBoxNode) 
			return processCheckBoxNode((CheckBoxNode)userObject);		
		else
			return userObject;

		/*
		}*/
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
		if (editor instanceof ComplexNode) {
			((ComplexNode) editor).addItemListener(itemListener);
		}

		return editor;
	}
}
