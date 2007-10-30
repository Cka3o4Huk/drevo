package ru.ixxo.crux.client.tree.enhance;

import ru.ixxo.crux.common.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.* ;
import javax.swing.event.*;
import javax.swing.tree.*;

import ru.ixxo.crux.common.Logger;

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
		
		
		
		ComplexNode checkbox = renderer.getLeafRenderer();
		ComplexNode checkBoxNode = new ComplexNode(checkbox.getText(),
				checkbox.getState());
		return checkBoxNode;
	}

	public boolean isCellEditable(EventObject event) {
		
		boolean returnValue = false;
		if (event instanceof MouseEvent) {

            Logger.info("MouseEvent");
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(),
					mouseEvent.getY());
			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			Logger.info("Path = "+path);
			if (path != null) {
				Object node = path.getLastPathComponent();
				Logger.info("Node = "+node);
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					((ComplexNode)userObject).nextState();
                    ///////////////////////////
					if(((ComplexNode)userObject).getState() == ComplexNode.SELECTED)
					{
						
					Enumeration e = treeNode.depthFirstEnumeration();
					while(e.hasMoreElements())
					{
						Object a = e.nextElement();
						if((DefaultMutableTreeNode)a != treeNode)
						{
						Object userObject1 = ((DefaultMutableTreeNode)a).getUserObject();
						((ComplexNode)userObject1).setState(ComplexNode.SELECTED);
						}
					}
					
					}
					else
					{
						
						Enumeration e = treeNode.depthFirstEnumeration();
						while(e.hasMoreElements())
						{
							Object a = e.nextElement();
							if((DefaultMutableTreeNode)a != treeNode)
							{
							Object userObject1 = ((DefaultMutableTreeNode)a).getUserObject();
							((ComplexNode)userObject1).setState(ComplexNode.NOT_SELECTED);
							}
						}
						
					}
					
					///////////////////////////

					///////////////////////////
					
					int flag;
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode.getParent();
					Object userObject1 = parent.getUserObject();
					while(userObject1 instanceof ComplexNode)
					{
						flag = (parent.getChildCount())*2;
						for(int r=0;r<=(parent.getChildCount()-1);r++)
						{
							DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt (r);
							Object userObject2 = child.getUserObject();
							   if(((ComplexNode)userObject2).getState() == ComplexNode.NOT_SELECTED)
							   {
								   flag = flag - 2;
							   }
							   if(((ComplexNode)userObject2).getState() == ComplexNode.DONT_CARE)
							   {
								   flag--;
							   }
						}
						if(flag == 0)
						{
							((ComplexNode)userObject1).setState(ComplexNode.NOT_SELECTED);
						}	
						if(flag == (parent.getChildCount())*2)
						{
							((ComplexNode)userObject1).setState(ComplexNode.SELECTED);
						}	
						if( (flag < (parent.getChildCount())*2) && (flag > 0) )
						{
							((ComplexNode)userObject1).setState(ComplexNode.DONT_CARE);
						}
						
							
							parent = (DefaultMutableTreeNode) parent.getParent();
							userObject1 = parent.getUserObject();
					}
					
					model.nodeChanged(treeNode);
					////////////////////////
					Logger.info("treeNode.isLeaf() = "+treeNode.isLeaf());				
					Logger.info("userObject instanceof ComplexNode = "+ (userObject instanceof ComplexNode));	
					returnValue = (userObject instanceof ComplexNode);
				}
			}
		}
		Logger.info("returnValue = "+returnValue);
		returnValue = false;
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
		if (editor instanceof ComplexNode) {
			((ComplexNode) editor).addItemListener(itemListener);
		}

		return editor;
	}
}
