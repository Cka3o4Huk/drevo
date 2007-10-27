package ru.ixxo.crux.client.tree;

import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import org.jdom.output.XMLOutputter;
import ru.ixxo.crux.common.Logger;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jdom.xpath.XPath;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

public class XMLTreeViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -738722273625654365L;

	// The JTree to display the XML
	protected static JTree xmlTree = null;

	private HashMap hm = new HashMap();

	// The XML document to be output to the JTree
	protected Document xmlDoc;

	protected Element xmlDocRootElement;

	static DefaultMutableTreeNode tn;

	public XMLTreeViewer(Document doc) {
		super();
		this.xmlDoc = doc;
		xmlDocRootElement = xmlDoc.getRootElement();

		// setSize(600, 450);
		initialize();
	}

	public XMLTreeViewer(Element root) {
		super();
		this.xmlDocRootElement = root;

		// setSize(600, 450);
		initialize();
	}

	protected void initialize() {
		if (xmlTree == null) {
			xmlTree = new JTree();
			xmlTree.setName("XML Tree");
			tn = new DefaultMutableTreeNode("XML");
		}
		hm.put(new Integer(tn.hashCode()).toString(), tn);
		xmlDocRootElement.setAttribute("id", new Integer(tn.hashCode())
				.toString());
		try {
			XPath xp = XPath.newInstance(".//*[@id='new']");
			List ls = xp.selectNodes(xmlDocRootElement);
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				Element el;
				DefaultMutableTreeNode cn;
				if (obj instanceof Element) {
					el = (Element) obj;
					cn = generateNodeByElement(el);
					obj = el.getParent();
					if (obj instanceof Element) {
						obj = hm.get(((Element) obj).getAttributeValue("id"));
						hm.put(new Integer(cn.hashCode()).toString(), cn);
						el.setAttribute("id", new Integer(cn.hashCode())
								.toString());
						if (obj instanceof DefaultMutableTreeNode) {
							((DefaultMutableTreeNode) obj).add(cn);
						}else if (obj==null) tn.add(cn);
					}
				};
			}
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Logger.info("\n\n\n\nException\n\n\n\n");
		}

		((DefaultTreeModel) xmlTree.getModel()).setRoot(tn);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				// release all the resource
				xmlTree = null;
				tn = null;
			}
		});

		// add Listener to Print to Screen the xml tag selected
		xmlTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				// Get all nodes whose selection status has changed
				// Print the last Path Component selected
				System.out.println(evt.getPath().getLastPathComponent());

				// print the full path from the selected tag
				// System.out.println(evt.getPath().toString());
			}
		});

		/*
		 * getContentPane().add(new JScrollPane(xmlTree), BorderLayout.CENTER);
		 * 
		 * setVisible(true);
		 */
	}

	public JTree getJTree() {
		return xmlTree;
	}

	protected DefaultMutableTreeNode generateNodeByElement(Element element) {
		DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(element
				.getName());
		String text = element.getTextNormalize();

		if ((text != null) && (!text.equals(""))) {
			currentNode.add(new DefaultMutableTreeNode(text));
		}

		return currentNode;
	}

	protected DefaultMutableTreeNode generateNodeByAttribute(Attribute attribute) {
		DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode("@"
				+ attribute.getName());

		currentNode.add(new DefaultMutableTreeNode(attribute.getValue()));

		return currentNode;
	}
}
