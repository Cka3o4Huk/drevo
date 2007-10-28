package ru.ixxo.crux.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

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
