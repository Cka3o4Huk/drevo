package ru.ixxo.crux.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.tree.TreePath;

import ru.ixxo.crux.client.springlayout.SpringOrder;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.manager.Manager;

public class MyFrame extends JFrame {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -666972553160206290L;

	Dimension minimumSize = new Dimension(300,600);
	Dimension preferredSize = new Dimension(300,600);
	
	JTree tree = null;

	JScrollPane jsp = null;

	JTextField jtf;

	Manager man;

	JMenuBar menubar;

	JMenu menu;

	JPanel mp;
	
	JProgressBar progressBar;

	SpringLayout layout;
	SpringOrder order;
	Container contentPane;
	
	public MyFrame(Manager man) {
		this.man = man;
		layout = new SpringLayout();
		order = new SpringOrder(layout);
		
		contentPane = this.getContentPane();
		contentPane.setLayout(layout);

		setPreferredSize(preferredSize);
		setMinimumSize(minimumSize);
		mp = new JPanel(layout);
		mp.setMinimumSize(minimumSize);
		mp.setBackground(Color.YELLOW);
		contentPane.add(mp,SpringLayout.EAST);
		
		menubar = new JMenuBar();
		menu = new JMenu("Menu");
		JMenuItem mi = new JMenuItem("Select Directory");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.info("Selection of the directory");
				String targetDirectory = null;
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
				Logger.info("Show JFileChooser");				
			    int returnVal = fileChooser.showOpenDialog(MyFrame.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	targetDirectory = fileChooser.getSelectedFile().getAbsolutePath();			    
			    }
/*
			    String text = JOptionPane.showInputDialog(null,
						"Select Directory:", "Select Directory",
						JOptionPane.QUESTION_MESSAGE);
				if (text == null) {
					// User clicked cancel
				}

				else {
*/
			    if(targetDirectory != null){
			    	Logger.info("Text entered: " + targetDirectory);
					MyFrame.this.sendDirName(targetDirectory);
					createJScroll();
					enableProgressBar();
					progressBar.setString("Please wait. Process can occupy some minutes");
					progressBar.setStringPainted(true);					
					mp.add(progressBar);					
					mp.setVisible(true);
					orderJComponents();				
					MyFrame.this.pack();
					MyFrame.this.RefreshGUI();
			    }
			}
		});
		menu.add(mi);

		mi = new JMenuItem("Exit");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// MyFrame.this.man.shutDown();
				System.exit(0);
			}
		});
		menu.add(mi);

		menubar.add(menu);

		menu = new JMenu("Help");
		mi = new JMenuItem("About");

		menu.add(mi);
		menubar.add(menu);

		this.setJMenuBar(menubar);
	}

	public void centralize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension frameSize = getSize();

		setLocation(screenSize.width / 2 - (frameSize.width / 2),
				screenSize.height / 2 - (frameSize.height / 2));
	}

	protected void createProgressBar(){
		if(progressBar == null){
			progressBar = new JProgressBar();		
		}			
	}
	
	protected void enableProgressBar(){
		createProgressBar();
		
		progressBar.setIndeterminate(true);
		//progressBar.setMinimumSize(minimumSize);
		progressBar.setVisible(true);		
	}
	
	protected void disableProgressBar(){
		if(progressBar!=null){
			progressBar.setVisible(false);				
		}
	}

	protected void createJScroll(){
		if(jsp == null){
			int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	
			jsp = new JScrollPane(tree, v, h);
			jsp.setMinimumSize(minimumSize);
	
			mp.add(jsp);
		}
	}
	
	protected void orderJComponents(){
/*		
		//JScrollPane
		layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.NORTH, jtf );
		layout.putConstraint(SpringLayout.NORTH, jsp, 0, SpringLayout.NORTH, mp );
		layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, mp );
		layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, mp );
		
		//JTextField
		layout.putConstraint(SpringLayout.SOUTH, jtf, 0, SpringLayout.SOUTH, mp );
		layout.putConstraint(SpringLayout.EAST, jtf, 0, SpringLayout.EAST, mp);
		layout.putConstraint(SpringLayout.WEST, jtf, 0, SpringLayout.WEST, mp );
		
//			layout.putConstraint(SpringLayout.NORTH, jtf, 5, SpringLayout.SOUTH, tree);
//		layout.putConstraint(SpringLayout.EAST, jtf, 0, SpringLayout.EAST, tree);		
			
//		SpringUtilities.makeCompactGrid(mp,2, 1, 5, 5, 3, 3);
		
		layout.putConstraint(SpringLayout.SOUTH, mp, 0, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, mp, 0, SpringLayout.EAST, contentPane);		
*/
		
		JComponent downComponent = jtf;
		JComponent upComponent = jsp;
		
		if(progressBar!=null && progressBar.isVisible()){
			downComponent = progressBar;
		}
		
		layout.putConstraint(SpringLayout.SOUTH, upComponent, 0, SpringLayout.NORTH, downComponent );
		layout.putConstraint(SpringLayout.NORTH, upComponent, 0, SpringLayout.NORTH, mp );
		layout.putConstraint(SpringLayout.EAST, upComponent, 0, SpringLayout.EAST, mp );
		layout.putConstraint(SpringLayout.WEST, upComponent, 0, SpringLayout.WEST, mp );

		layout.putConstraint(SpringLayout.SOUTH, downComponent, 0, SpringLayout.SOUTH, mp );
				
		order.arrange(downComponent, upComponent, SpringOrder.ORDER_BENEATH, SpringOrder.ARRANGE_BY_BOTH_EDGES);
		
		layout.putConstraint(SpringLayout.SOUTH, mp, 0, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, mp, 0, SpringLayout.EAST, contentPane);				
	}
	
	
	public void drawJTree(JTree jtree) {

//		Container contentPane = this.getContentPane();
		// contentPane.setLayout(new BorderLayout());

		if (tree == null) {
			Logger.info("Setting new tree");
			tree = jtree;

			createJScroll();
			jsp.setViewportView(tree);
			disableProgressBar();
			
			jtf = new JTextField("", 20);
			mp.add(jtf);
/*
			//JScrollPane
			layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.NORTH, jtf );
			layout.putConstraint(SpringLayout.NORTH, jsp, 0, SpringLayout.NORTH, mp );
			layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, mp );
			layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, mp );
			
			//JTextField
			layout.putConstraint(SpringLayout.SOUTH, jtf, 0, SpringLayout.SOUTH, mp );
			layout.putConstraint(SpringLayout.EAST, jtf, 0, SpringLayout.EAST, mp);
			layout.putConstraint(SpringLayout.WEST, jtf, 0, SpringLayout.WEST, mp );
//			
//			layout.putConstraint(SpringLayout.NORTH, jtf, 5, SpringLayout.SOUTH, tree);
//			layout.putConstraint(SpringLayout.EAST, jtf, 0, SpringLayout.EAST, tree);		
//			
//			SpringUtilities.makeCompactGrid(mp,2, 1, 5, 5, 3, 3);
			
			layout.putConstraint(SpringLayout.SOUTH, mp, 0, SpringLayout.SOUTH, contentPane);
			layout.putConstraint(SpringLayout.EAST, mp, 0, SpringLayout.EAST, contentPane);
*/			
			orderJComponents();
			
			tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					doMouseClicked(me);
				}
			});

			jtree.setVisible(true);

			TreePath path = new TreePath(tree);
			tree.makeVisible(path);
			mp.setVisible(true);
			pack();
		} else {
			jsp.setViewportView(jtree);
			tree.setVisible(false);
			jtree.setVisible(true);
			tree = jtree;
		}
	}

	public void RefreshGUI() {

		if(mp!=null)
			mp.invalidate();
		if(menu!=null)
			menu.invalidate();
		if(menubar!=null)
			menubar.invalidate();
		if(tree!=null)
			tree.invalidate();
		if(jsp!=null)
			jsp.invalidate();

		invalidate();

		validate();
		pack();

		repaint();
	}

	public void doMouseClicked(MouseEvent me) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		if (tp != null) {
			jtf.setText(tp.toString());
		} else {
			jtf.setText("");
		}
	}

	public void sendDirName(String text) {
		try {
			// File f = new File(text);
			// if ((f.isFile())||(f.isDirectory()))
			// {
			man.dirname = text;
			man.setUserFlag(true);
			// }
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public void completed() {
		String message = "The calculation has been completed!";
		JOptionPane.showMessageDialog(null, message, "Message",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/*
	 * public static void main(String [] args) { MyFrame mf = new MyFrame();
	 * mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); mf.setTitle("Swing
	 * Interface"); //StatusComponent status = new StatusComponent(mf);
	 * //mf.getContentPane().add(status.getBox(), "South");
	 * 
	 * mf.setSize(800,600); mf.setVisible(true); //mf.repaint(); }
	 */
}