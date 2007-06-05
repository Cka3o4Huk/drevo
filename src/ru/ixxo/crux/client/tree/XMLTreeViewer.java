package ru.ixxo.crux.client.tree;

import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

public class XMLTreeViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -738722273625654365L;

	// The JTree to display the XML
	protected JTree xmlTree;

	// The XML document to be output to the JTree
	protected Document xmlDoc;

	protected Element xmlDocRootElement;

	DefaultMutableTreeNode tn;

	public XMLTreeViewer(Document doc) {
		super();
		this.xmlDoc = doc;
		xmlDocRootElement = xmlDoc.getRootElement();

		setSize(600, 450);
		tn = new DefaultMutableTreeNode("XML");
		initialize();
	}

	public XMLTreeViewer(Element root) {
		super();
		this.xmlDocRootElement = root;

		setSize(600, 450);
		tn = new DefaultMutableTreeNode("XML");
		initialize();
	}

	protected void initialize() {
		xmlTree = new JTree();
		xmlTree.setName("XML Tree");

		processElement(xmlDocRootElement, tn);

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

	private void processElement(Element el, DefaultMutableTreeNode dmtn) {

		DefaultMutableTreeNode currentNode = generateNodeByElement(el);
		if (currentNode != null) {
			processAttributes(el, currentNode);

			Iterator children = el.getChildren().iterator();

			while (children.hasNext()) {
				processElement((Element) children.next(), currentNode);
			}

			dmtn.add(currentNode);
		}
	}

	protected void processAttributes(Element el, DefaultMutableTreeNode dmtn) {
		Iterator atts = el.getAttributes().iterator();

		while (atts.hasNext()) {
			Attribute att = (Attribute) atts.next();
			DefaultMutableTreeNode attNode = generateNodeByAttribute(att);
			if (attNode != null)
				dmtn.add(attNode);
		}
	}
}
