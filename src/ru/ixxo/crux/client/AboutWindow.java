package ru.ixxo.crux.client;

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

public class AboutWindow extends JFrame{
	public AboutWindow(){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea ml=new JTextArea();
		this.setTitle("About program");
		ml.setText("    IXXO drevo\n" +
				"This program is made by students of MIPT\n" +
				"and a teacher from Kalashinkov's studing center\n" +
				"http://ipccenter.ru");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon= toolkit.getImage("./images/icon.gif");
        this.setIconImage(icon);
		this.add(ml);
		this.resize(300, 300);
		this.setResizable(false);
		this.centralize();
		ml.setVisible(true);
		this.setVisible(true);
	}
	 public void centralize() {
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	        Dimension frameSize = getSize();

	        setLocation(screenSize.width / 2 - (frameSize.width / 2),
	                screenSize.height / 2 - (frameSize.height / 2));
	    }
}