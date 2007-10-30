package ru.ixxo.crux.client.tree;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import ru.ixxo.crux.client.tree.enhance.CheckBoxNode;
import ru.ixxo.crux.client.tree.enhance.CheckBoxNodeEditor;
import ru.ixxo.crux.client.tree.enhance.CheckBoxNodeRenderer;

import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URLDecoder;

public class UserTreeViewer extends XMLTreeViewer 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6169896871952578148L;

    public UserTreeViewer(Document doc) {
		super(doc);
	}

	public UserTreeViewer(Element doc) {
		super(doc);
	}

	protected CheckBoxNodeRenderer renderer;

	protected void initialize(){
		super.initialize();
        // add Listener to Print to Screen the xml tag selected
		xmlTree.setCellRenderer(new CheckBoxNodeRenderer());
        xmlTree.setCellEditor(new CheckBoxNodeEditor(xmlTree));
		xmlTree.setEditable(true);
        xmlTree.addMouseListener(new MyMouseAdapter());
        xmlTree.setModel(new MenuListModel(tn));

        TreeModel model = xmlTree.getModel();
		
		if(((DefaultMutableTreeNode)model.getRoot()).getChildCount() == 1){
			tn = ((DefaultMutableTreeNode)model.getRoot()).getNextNode();
			((DefaultTreeModel) xmlTree.getModel()).setRoot(tn);
            
        }
	}

    class MyMouseAdapter extends MouseAdapter  {

    public void mousePressed(MouseEvent e) {
        if (e.getButton()!=MouseEvent.BUTTON1) return;
        TreePath path = xmlTree.getPathForLocation(e.getX(), e.getY());
        //TreePath path = getClosestPathForLocation(e.getX(), e.getY());
        if (path == null) {
            return;
        }
        Object data = path.getLastPathComponent();
        if (data instanceof TreeNode) {
            TreeModel model = xmlTree.getModel();
            Object userObject = ((DefaultMutableTreeNode) data).getUserObject();
            CheckBoxNode selectable = (CheckBoxNode) userObject;
            if (model instanceof MenuListModel) {
                MenuListModel mod = (MenuListModel) model;
                mod.setSelected((DefaultMutableTreeNode) data, !selectable.isSelected());
            }
            repaint();
        }
    }
    }

    protected DefaultMutableTreeNode generateNodeByElement(Element element) {

		if (element == null)
			return null;

		String nodeName;
		String encodedName = element.getAttributeValue("fileName");

		if (encodedName == null) {
//			nodeName = "";
			return null;
		} else {
			String fileName = URLDecoder.decode(encodedName);

			int index = fileName.lastIndexOf(File.separator);

			if (index > -1) {
				fileName = fileName.substring(index + 1);
			}

			String size = element.getAttributeValue("size");

			nodeName = fileName + " [size = " + size + "]";
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
		node.setUserObject(new CheckBoxNode(nodeName, false, node));
		return node;
	}

	protected DefaultMutableTreeNode generateNodeByAttribute(Attribute attr) {
		return null;
	}

}
