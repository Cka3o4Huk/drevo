package ru.ixxo.crux.client;

import javax.swing.*;
import javax.swing.tree.TreePath;

import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.manager.Manager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame
 {
    /**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -666972553160206290L;
	
	JTree tree;
    JTextField jtf;
    Manager man;
    
    JMenuBar menubar;
    JMenu menu;
    JPanel mp;
    JScrollPane jsp;

	public MyFrame(Manager man)
    {
		this.man=man;

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        mp= new MyPanel();
		contentPane.add(mp);

		menubar = new JMenuBar();
		menu = new JMenu("Menu");
		JMenuItem mi = new JMenuItem("Select Directory");
		mi.addActionListener(
			new ActionListener()
            {
				public void actionPerformed(ActionEvent arg0)
                {
                    String text = JOptionPane.showInputDialog(null, "Select Directory:", "Select Directory", JOptionPane.QUESTION_MESSAGE);
                        if (text == null)
                        {
                            // User clicked cancel
                        }

                        else
                        {
                        	Logger.info("Text entered: "+ text);
                            MyFrame.this.sendDirName(text);
                        }
				}
		    });
        menu.add(mi);

        mi = new JMenuItem("Exit");
		mi.addActionListener(
			new ActionListener()
            {
				public void actionPerformed(ActionEvent arg0)
                {
                    //MyFrame.this.man.shutDown();
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

        /*DefaultMutableTreeNode top = new DefaultMutableTreeNode("Directory");

        DefaultMutableTreeNode a = new DefaultMutableTreeNode("A");
        top.add(a);
        DefaultMutableTreeNode a1 = new DefaultMutableTreeNode("A1");
        a.add(a1);
        a1 = new DefaultMutableTreeNode("A2");
        a.add(a1);

        a = new DefaultMutableTreeNode("B");
        top.add(a);
        a1 = new DefaultMutableTreeNode("A1");
        a.add(a1);
        a1 = new DefaultMutableTreeNode("A2");
        a.add(a1);


        tree = new JTree(top);

        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane jsp = new JScrollPane(tree, v, h);
        contentPane.add(jsp, BorderLayout.CENTER);

        jtf = new JTextField("", 20);
        contentPane.add(jtf, BorderLayout.SOUTH);

        tree.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                doMouseClicked(me);
            }
        });    */
	}

    public void drawJTree(JTree jtree)
    {

    	Container contentPane = this.getContentPane();
        //contentPane.setLayout(new BorderLayout());

        if(jsp == null){
            tree = jtree;
	        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	        jsp = new JScrollPane(tree, v, h);
	        contentPane.add(jsp, BorderLayout.NORTH);
	
	        jtf = new JTextField("", 20);
	        contentPane.add(jtf, BorderLayout.SOUTH);
	
	        tree.addMouseListener(new MouseAdapter()
	        {
	            public void mouseClicked(MouseEvent me)
	            {
	                doMouseClicked(me);
	            }
	        });
	
	        jtree.setVisible(true);
	        
	        TreePath path = new TreePath(tree);
	        tree.makeVisible(path);
        }else{
        	jsp.setViewportView(jtree);        	
        	tree.setVisible(false);
        	jtree.setVisible(true);
        	tree = jtree;
        }
    }

    public void RefreshGUI()
	{

        mp.invalidate();
		menu.invalidate();
		menubar.invalidate();
		tree.invalidate();
		jsp.invalidate();
		
		invalidate();

		validate();
		pack();
		repaint();
	}
    
    public void doMouseClicked(MouseEvent me)
    {
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp!=null)
        {
            jtf.setText(tp.toString());
        }
        else
        {
            jtf.setText("");
        }
    }

    public void sendDirName(String text)
    {
        try
        {
            //File f = new File(text);
            //if ((f.isFile())||(f.isDirectory()))
            //{
                man.dirname = text;
                man.setUserFlag(true);
            //}
        }
        catch(SecurityException e)
        {
        	throw new RuntimeException(e);
        }
    }

    public void completed()
    {
        String message = "The calculation has been completed!";
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

   /* public static void main(String [] args)
    {
		MyFrame mf = new MyFrame();
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setTitle("Swing Interface");
		//StatusComponent status = new StatusComponent(mf);
        //mf.getContentPane().add(status.getBox(), "South");

		mf.setSize(800,600);
        mf.setVisible(true);
		//mf.repaint();

   	} */
}

