package ru.ixxo.crux.client.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import ru.ixxo.crux.client.tree.enhance.CheckBoxNode;
import ru.ixxo.crux.client.tree.enhance.CheckBoxNodeEditor;
import ru.ixxo.crux.client.tree.enhance.CheckBoxNodeRenderer;

public class UserTreeViewer extends XMLTreeViewer {

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

	protected void initialize() {
		super.initialize();
		renderer = new CheckBoxNodeRenderer();
		xmlTree.setCellRenderer(renderer);
		xmlTree.setCellEditor(new CheckBoxNodeEditor(xmlTree));
		xmlTree.setEditable(true);
	}

	protected DefaultMutableTreeNode generateNodeByElement(Element element) {

		if (element == null)
			return null;

		String nodeName;
		String fileName = element.getAttributeValue("fileName");

		if (fileName == null) {
			nodeName = "";
		} else {
			int index = fileName.lastIndexOf("/");

			if (index > -1) {
				fileName = fileName.substring(index + 1);
			}

			String size = element.getAttributeValue("size");

			nodeName = fileName + " [size = " + size + "]";
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
		node.setUserObject(new CheckBoxNode(nodeName, false));
		return node;
	}

	protected DefaultMutableTreeNode generateNodeByAttribute(Attribute attr) {
		return null;
	}

}
