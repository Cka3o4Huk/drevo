package ru.ixxo.crux.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class SplashWindow extends JWindow {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Frame mainFrame = null;

    public void setMainFrameVisible(boolean flag){
        if(mainFrame !=null)
            mainFrame.setVisible(flag);
    }

    public void organizeFrame(String fileName){
        JLabel l = new JLabel(new ImageIcon(fileName));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width / 2 - (labelSize.width / 2),
                screenSize.height / 2 - (labelSize.height / 2));
    }

    public SplashWindow(String filename, Frame splashFrame,Frame mainFrame, int waitTime) {
        super(splashFrame);

        this.mainFrame = mainFrame;

        organizeFrame(filename);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                setMainFrameVisible(true);
                dispose();
            }
        });

        final int pause = waitTime;

        final Runnable closerRunner = new Runnable() {
            public void run() {
                if(isVisible()){
                	setVisible(false);
                	setMainFrameVisible(true);
                	dispose();
                }
            }
        };
        Runnable waitRunner = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(pause);
                    SwingUtilities.invokeAndWait(closerRunner);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    // can catch InvocationTargetException
                    // can catch InterruptedException
                }
            }
        };

        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
    }
}
