package ru.ixxo.crux.client;

import ru.ixxo.crux.client.tree.UserTreeViewer;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
import ru.ixxo.crux.client.tree.MenuListModel;
import ru.ixxo.crux.client.tree.JCTree;
import ru.ixxo.crux.common.Logger;
//import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.engine.providers.EvaluateSizeProvider;
import ru.ixxo.crux.manager.Manager;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MyFrame extends JFrame {
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -666972553160206290L;

    Dimension minimumSize = new Dimension(400, 400);

    Dimension preferredSize = new Dimension(600, 400);

    JTree tree = null;

    JScrollPane jsp = null;

    JTextField jtf;

    Manager man;

    JMenuBar menubar;

    JMenu menu;

    JPanel mp;

    JProgressBar progressBar;

    JToolBar toolBar;
    
    EvaluateSizeProvider espr;

    private static enum Buttons {
        START("Start scanning"), REFRESH("Refresh tree"), UNCHECK("Uncheck all"), DELETE(
            "Mark for deletion"), CANCEL("Unmark all marked"), COMPLETE(
            "Delete marked"), STOP("Stop scanning");

        private final String pathToImage;

        private final String toolTip;

        Buttons(String tip) {
            pathToImage = "images/" + this.toString().toLowerCase() + ".gif";
            toolTip = tip;
        }

        public String getPathToImage() {
            return pathToImage;
        }

        public String getToolTip() {
            return toolTip;
        }

    }

    JButton[] buttons = new JButton[Buttons.values().length - 1];

    public boolean isStopButton = false;

    ImageIcon[] startStopIcons = {new ImageIcon(Buttons.values()[0].getPathToImage()),
            new ImageIcon(Buttons.values()[buttons.length].getPathToImage())};

    ActionListener toolBarListener = new ActionListener() {
        public void actionPerformed(final ActionEvent e) {
            Logger.info(e.getActionCommand());
            processActions(e);
        }
    };

    BorderLayout layout;

    Container contentPane;

    XMLTreeViewer treeViewer;

    ArrayList<String> paths = new ArrayList<String>();

    public MyFrame(Manager man) {
        this.man = man;
        this.espr = man.getEvaluateSizeProvider();
        layout = new BorderLayout();
        contentPane = this.getContentPane();
        setPreferredSize(preferredSize);
        setMinimumSize(minimumSize);

        mp = new JPanel(layout);
        mp.setMinimumSize(new Dimension(400,40));
        mp.setPreferredSize(new Dimension(400,40));
        contentPane.add(mp,BorderLayout.SOUTH);
        mp.setOpaque(true);
        mp.setVisible(true);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        mp.add(progressBar, BorderLayout.NORTH);
        progressBar.setVisible(false);

        jtf = new JTextField("", 20);
        jtf.setEditable(false);
        mp.add(jtf, BorderLayout.SOUTH);
        jtf.setVisible(true);

        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        Dimension buttonDimension = new Dimension(33,33);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ImageIcon(Buttons.values()[i].getPathToImage()));
            buttons[i].setActionCommand(Buttons.values()[i].toString());
            buttons[i].setToolTipText(Buttons.values()[i].getToolTip());
            buttons[i].addActionListener(toolBarListener);
            buttons[i].setMargin(new Insets(3,0,3,0));
            buttons[i].setMaximumSize(buttonDimension);
            buttons[i].setPreferredSize(buttonDimension);
            toolBar.add(buttons[i]);
        }
        buttons[Buttons.UNCHECK.ordinal()].setEnabled(false);
        buttons[Buttons.CANCEL.ordinal()].setEnabled(false);
        contentPane.add(toolBar, BorderLayout.EAST);

        menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        createJScroll();
        contentPane.add(jsp, BorderLayout.CENTER);

        menu = new JMenu("Menu");
        JMenuItem mi = new JMenuItem("Select Directory");
        mi.setActionCommand("START");
        mi.addActionListener(toolBarListener);
        menu.add(mi);
        mi = new JMenuItem("Exit");
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // MyFrame.this.man.shutDown();
                new ExitAlert();
            }
        });
        menu.add(mi);
        menubar.add(menu);
        initOptionsMenu(menubar);
        menu = new JMenu("Help");
        mi = new JMenuItem("About");
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // MyFrame.this.man.shutDown();
                new AboutWindow();
            }
        });
        menu.add(mi);
        menubar.add(menu);
        
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowListener(){
            public void windowClosing(WindowEvent e)
            {
                new ExitAlert();
            }

            public void windowClosed(WindowEvent e)
            {
            }

            public void windowIconified(WindowEvent e)
            {
            }

            public void windowDeiconified(WindowEvent e)
            {
            }

            public void windowActivated(WindowEvent e)
            {
            }

            public void windowDeactivated(WindowEvent e)
            {
            }

            public void windowOpened(WindowEvent e)
            {
            }
        });

        RefreshGUI();
    }

    protected void switchStartStop(){
        if (isStopButton){
            buttons[0].setIcon(startStopIcons[1]);
            buttons[0].setActionCommand(Buttons.values()[buttons.length].toString());
            buttons[0].setToolTipText(Buttons.values()[buttons.length].getToolTip());
        }
        else {
            buttons[0].setIcon(startStopIcons[0]);
            buttons[0].setActionCommand(Buttons.values()[0].toString());
            buttons[0].setToolTipText(Buttons.values()[0].getToolTip());
        }
    }

    final String DEV_XML_VIEW = "Developer XML View";

    final String USR_VIEW = "User-friendly View";

    public void initOptionsMenu(JMenuBar menubar) {
        JMenu menu = new JMenu("Options");
        JMenu subMenu = new JMenu("Tree View");
        ButtonGroup group = new ButtonGroup();
        ButtonModel selected;

        ActionListener actionPrinter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String strCmd = e.getActionCommand();
                    if (DEV_XML_VIEW.equalsIgnoreCase(strCmd)) {
                        espr.changeTreeView(EvaluateSizeProvider.XML_VIEW);
                    }
                    if (USR_VIEW.equalsIgnoreCase(strCmd)) {
                        espr.changeTreeView(EvaluateSizeProvider.USER_VIEW);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        JRadioButtonMenuItem radioItem = new JRadioButtonMenuItem(DEV_XML_VIEW);
        radioItem.addActionListener(actionPrinter);
        group.add(radioItem);
        subMenu.add(radioItem);

        radioItem = new JRadioButtonMenuItem(USR_VIEW);
        radioItem.addActionListener(actionPrinter);
        selected = radioItem.getModel();
        group.add(radioItem);
        subMenu.add(radioItem);

        menu.add(subMenu);
        group.setSelected(selected, true);

        menubar.add(menu);
    }

    public void centralize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension frameSize = getSize();

        setLocation(screenSize.width / 2 - (frameSize.width / 2),
                screenSize.height / 2 - (frameSize.height / 2));
    }

    protected void createJScroll() {
        if (jsp == null) {
            int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
            int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
            jsp = new JScrollPane(v, h);
            jsp.setMinimumSize(minimumSize);
        }
    }

    public void drawJTree(XMLTreeViewer treeViewer) throws CloneNotSupportedException {

        if (tree == null) {
            Logger.info("Setting new tree");
            this.treeViewer = treeViewer;
            tree = (JTree)((JCTree)treeViewer.getJTree()).clone();
            jsp.setViewportView(tree);
            tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    doMouseClicked(me);
                }
            });
            tree.setVisible(true);
            TreePath path = new TreePath(tree);
            tree.makeVisible(path);
            pack();
        } else {
            jsp.setViewportView(tree);
            tree.setVisible(false);
            tree.setVisible(true);
        }
        switchStartStop();
        if (!isStopButton){
            progressBar.setVisible(false);
        }
    }

    public void RefreshGUI() {

        if (menu != null)
            menu.invalidate();
        if (menubar != null)
            menubar.invalidate();
        if (tree != null)
            tree.invalidate();
        if (toolBar != null)
            toolBar.invalidate();
        if (jsp != null)
            jsp.invalidate();
        if (progressBar != null)
            progressBar.invalidate();
        if (jtf != null)
            jtf.invalidate();
        if (mp != null)
            mp.invalidate();

        invalidate();
        validate();
        pack();
        repaint();
    }

    public void doMouseClicked(MouseEvent me) {
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp != null) {
            jtf.setText(tp.toString());
            if (treeViewer instanceof UserTreeViewer)
                if (((MenuListModel)tree.getModel()).isNothingChecked())
                    buttons[Buttons.UNCHECK.ordinal()].setEnabled(false);
                else
                    buttons[Buttons.UNCHECK.ordinal()].setEnabled(true);

        } else {
            jtf.setText("");
        }
    }

    public void sendDirName(String text) {
        try {
            // File f = new File(text);
            // if ((f.isFile())||(f.isDirectory()))
            // {
            espr.dirname = text;
            espr.setUserFlag(true);
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

    public void processActions(ActionEvent e){
        switch (Buttons.valueOf(e.getActionCommand())) {
            case START:
                Logger.info("Selection of the directory");
                String targetDirectory = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                Logger.info("Show JFileChooser");
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    targetDirectory = fileChooser.getSelectedFile().getAbsolutePath();
                }
                if (targetDirectory == null) break;
                paths.add(targetDirectory);
                Logger.info("Text entered: " + targetDirectory);
                if (treeViewer != null && treeViewer instanceof UserTreeViewer)
                    if (tree.getModel() instanceof MenuListModel)
                    {
                        ((MenuListModel)tree.getModel()).uncheckAll();
                        ((MenuListModel)tree.getModel()).unmark();
                        buttons[Buttons.UNCHECK.ordinal()].setEnabled(false);
                        buttons[Buttons.CANCEL.ordinal()].setEnabled(false);
                    }
                sendDirName(targetDirectory);
                isStopButton = true;
                switchStartStop();
                jsp.add(tree);
                progressBar.setString("Please wait. Process can occupy some minutes");
                progressBar.setStringPainted(true);
                progressBar.setVisible(true);
                pack();
                RefreshGUI();
                break;
            case STOP:
                isStopButton = false;
                //switchStartStop();
                espr.setUserFlag(false);
                man.pauseThreads();
                break;
            case REFRESH:
                espr.reloadTree();
                break;
            case UNCHECK:
                ((MenuListModel)tree.getModel()).uncheckAll();
                tree.repaint();
                buttons[Buttons.UNCHECK.ordinal()].setEnabled(false);
                RefreshGUI();
                break;
            case DELETE:
                ((MenuListModel)tree.getModel()).mark();
                tree.repaint();
                if (treeViewer instanceof UserTreeViewer)
                    if (((MenuListModel)tree.getModel()).isNothingMarked())
                        buttons[Buttons.CANCEL.ordinal()].setEnabled(false);
                    else
                        buttons[Buttons.CANCEL.ordinal()].setEnabled(true);
                RefreshGUI();
                break;
            case CANCEL:
                ((MenuListModel)tree.getModel()).unmark();
                tree.repaint();
                buttons[Buttons.CANCEL.ordinal()].setEnabled(false);
                RefreshGUI();
                break;
            case COMPLETE:

                break;
        }

    }

    private void processDeletion(){
        HashMap<String, DefaultMutableTreeNode> marked = ((MenuListModel)tree.getModel()).getMarkedItems();
        Set keys = marked.keySet();
        //((DefaultMutableTreeNode)marked.values().toArray()[0]).is;
        Comparator<DefaultMutableTreeNode> mycomp = new Comparator<DefaultMutableTreeNode>()
        {
            public int compare(DefaultMutableTreeNode o1, DefaultMutableTreeNode o2)
            {
                return (int)Math.signum(o1.getLevel()-o2.getLevel());
            }
        };
        DefaultMutableTreeNode[] sorted = (DefaultMutableTreeNode[])marked.values().toArray();
        Arrays.sort(sorted, mycomp);
        int firstLevelNode;
        StringBuffer pathToDelete = new StringBuffer();
        for (int i = 0; i < sorted.length; i++)
        {
            firstLevelNode = ((DefaultMutableTreeNode)sorted[i].getRoot()).getIndex(sorted[i].getPath()[1]);
            pathToDelete.setLength(0);
            pathToDelete.append(paths.toArray()[firstLevelNode]);
            


        }
        //sorted[0].get
        

    }


}
