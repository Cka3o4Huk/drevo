package ru.ixxo.crux.client;

import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.manager.Manager;
import sun.awt.resources.awt_ko;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutWindow extends JDialog{
	static AboutWindow aw=null;
	public AboutWindow(String str){
		super();
		this.getContentPane().setLayout(null);
		final AboutWindow aw=this;
		JLabel ml=new JLabel();
		this.setTitle("               About program");
		ml.setText("<html><body><h2>    Ixxo File System Monitor</h2>" +
				"<p>This program is made by </p><p> students of MIPT</p>" +
				"<p>and a teacher from <br> Kalashinkov's studing center</p>" +
				"<a href=\"http://ipcccenter.ru/\">http://ipccenter.ru</a></body></html>");
		ml.setBackground(this.getBackground());
		ml.setHorizontalAlignment(ml.CENTER);
		ml.setVerticalAlignment(ml.TOP);
		JButton but=new JButton("Ok");
		but.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(e.getButton()==1) aw.setVisible(false);
			}
			
		});
		Toolkit toolkit = Toolkit.getDefaultToolkit();
        ImageIcon icon= new ImageIcon("./images/icon.gif");
        ml.setIcon(icon);
        ml.setBounds(0, 0, 300, 130);
		this.add(ml, null);
        //cnt.add(but);
		this.resize(300, 200);
		this.setResizable(false);
		this.centralize();
		this.add(but, null);
		ml.setVisible(true);
		but.setBounds(120, 140, 60, 20);
		but.setVisible(true);
		this.setVisible(true);
	}
	public AboutWindow(){
		if (aw==null){
			aw= new AboutWindow("new");
		}
		else{
			aw.setVisible(true);
			return;
		}
		
	}
	 public void centralize() {
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	        Dimension frameSize = getSize();

	        setLocation(screenSize.width / 2 - (frameSize.width / 2),
	                screenSize.height / 2 - (frameSize.height / 2));
	    }
}