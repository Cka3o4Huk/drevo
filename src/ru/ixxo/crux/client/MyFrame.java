package ru.ixxo.crux.client;

import ru.ixxo.crux.client.tree.UserTreeViewer;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
import ru.ixxo.crux.client.tree.MenuListModel;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.manager.Manager;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private static enum Buttons {
        START("Start scanning"), REFRESH("Refresh tree"), UNMARK("Uncheck all"), DELETE(
            "Mark for deleting"), CANCEL("Unmark all marked"), COMPLETE(
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

    ActionListener toolBarListener = new ActionListener() {
        public void actionPerformed(final ActionEvent e) {
            Logger.info(e.getActionCommand());
            processActions(e);
        }
    };

    BorderLayout layout;

    Container contentPane;

    XMLTreeViewer treeViewer;
    //TreeSet<Integer> selection = new TreeSet<Integer>();

    public MyFrame(Manager man) {
        this.man = man;
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
            buttons[i] = new JButton(new ImageIcon(Buttons.values()[i]
                    .getPathToImage()));
            buttons[i].setActionCommand(Buttons.values()[i].toString());
            buttons[i].setToolTipText(Buttons.values()[i].getToolTip());
            buttons[i].addActionListener(toolBarListener);
            buttons[i].setMargin(new Insets(3,0,3,0));
            buttons[i].setMaximumSize(buttonDimension);
            buttons[i].setPreferredSize(buttonDimension);
            toolBar.add(buttons[i]);
        }
        buttons[Buttons.UNMARK.ordinal()].setEnabled(false);
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
                System.exit(0);
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

        RefreshGUI();
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
                        man.changeTreeView(Dispatcher.XML_VIEW);
                    }
                    if (USR_VIEW.equalsIgnoreCase(strCmd)) {
                        man.changeTreeView(Dispatcher.USER_VIEW);
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

    public void drawJTree(XMLTreeViewer treeViewer) {

        //		Container contentPane = this.getContentPane();
        // contentPane.setLayout(new BorderLayout());

        if (tree == null) {
            Logger.info("Setting new tree");
            this.treeViewer = treeViewer;
            tree = treeViewer.getJTree();
            jsp.setViewportView(tree);
            progressBar.setVisible(false);
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
                    buttons[Buttons.UNMARK.ordinal()].setEnabled(false);
                else
                    buttons[Buttons.UNMARK.ordinal()].setEnabled(true);

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
                    targetDirectory = fileChooser.getSelectedFile()
                            .getAbsolutePath();
                }
                if (targetDirectory == null) break;
                Logger.info("Text entered: " + targetDirectory);
                sendDirName(targetDirectory);
                jsp.add(tree);
                progressBar.setString("Please wait. Process can occupy some minutes");
                progressBar.setStringPainted(true);
                progressBar.setVisible(true);
                pack();
                RefreshGUI();
                break;
            case STOP:
            case REFRESH:
            case UNMARK:
                ((MenuListModel)tree.getModel()).uncheckAll();
                tree.repaint();
                buttons[Buttons.UNMARK.ordinal()].setEnabled(false);
                RefreshGUI();
                break;
            case DELETE:
                ((MenuListModel)tree.getModel()).mark();
                tree.repaint();
                RefreshGUI();
                break;
            case CANCEL:
                ((MenuListModel)tree.getModel()).unmark();
                tree.repaint();
                RefreshGUI();
                break;
            case COMPLETE:
        }

    }


}
