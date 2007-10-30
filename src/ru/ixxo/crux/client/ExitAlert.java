package ru.ixxo.crux.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;

public class ExitAlert extends JFrame
{
    public ExitAlert(){
        super();
        this.setLayout(new BorderLayout());
        JLabel ml = new JLabel("Are you sure you want to exit FSM?");
        ml.setHorizontalAlignment(SwingConstants.CENTER);
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                ExitAlert.this.dispose();
            }
        });
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        this.setAlwaysOnTop(true);
        this.setTitle("Confirm Exit");
        this.add(ml, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.SOUTH);
        panel.add(yes, BorderLayout.WEST);
        panel.add(no, BorderLayout.EAST);
        this.setResizable(false);
        this.setSize(250, 80);
        this.centralize();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        panel.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);

        Set s = new HashSet();
        s.add (AWTKeyStroke.getAWTKeyStroke (KeyEvent.VK_RIGHT, 0));
        s.add (AWTKeyStroke.getAWTKeyStroke (KeyEvent.VK_TAB, 0));
        yes.setFocusTraversalKeys (KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, s);
        s.clear();
        s.add (AWTKeyStroke.getAWTKeyStroke (KeyEvent.VK_LEFT, 0));
        s.add (AWTKeyStroke.getAWTKeyStroke (KeyEvent.VK_TAB, 0));
        no.setFocusTraversalKeys (KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, s);

        ml.setVisible(true);
        this.setVisible(true);
        this.getOwner().setFocusable(false);
    }

    public void centralize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(screenSize.width / 2 - (frameSize.width / 2),
                screenSize.height / 2 - (frameSize.height / 2));
    }
}
