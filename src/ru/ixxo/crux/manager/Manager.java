package ru.ixxo.crux.manager;

import ru.ixxo.crux.client.MyFrame;
import ru.ixxo.crux.client.SplashWindow;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.engine.providers.EvaluateSizeProvider;
import ru.ixxo.crux.engine.core.EngineManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 
 */
public class Manager
{

    //protected DefaultMutableTreeNode tree;
    private Dispatcher disp;
    private EvaluateSizeProvider espr;
    protected EngineManager em;

  
    private Dispatcher getDispatcher(){
    	if (disp==null)
    		disp=new Dispatcher(null);
    	return disp;
    }
    public EvaluateSizeProvider getEvaluateSizeProvider(){
    	if (espr==null) {
    		espr=new EvaluateSizeProvider(getDispatcher());
    		disp.setEvaluateSizeProvider(espr);
    	}

    	return espr;
    }
    
    public Manager()
    {

    }


    
    public static void main(String args[])
    {
        Manager man = new Manager();

        Logger.debug = false;
        Logger.info = true;
        
        MyFrame mf = new MyFrame(man);
        JFrame splashFrame = new JFrame();
        SplashWindow splash = new SplashWindow("images/splash.jpg",splashFrame,mf,3000);
        
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setTitle("Ixxo File System Monitor");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon= toolkit.getImage("./images/icon.gif");
        mf.setIconImage(icon);
		//StatusComponent status = new StatusComponent(mf);
        //mf.getContentPane().add(status.getBox(), "South");

		//mf.setSize(800,600);

        mf.centralize();
        
        if(splash == null)
            mf.setVisible(true);
        
        String javaPath = System.getProperty("java.class.path", "");
        System.out.println("Java Path = " + javaPath);

        try
        {
            man.em = new EngineManager(man.getEvaluateSizeProvider());

        	while (true)
            {
                Thread.sleep(100);
                man.getEvaluateSizeProvider().handle(mf);
            }
        }
        catch (RuntimeException e)
        {
            System.out.println("Runtime: Manager interrupted!!!");
            e.printStackTrace();
	    Logger.log(e.toString());
        }
        catch (Exception e) {
            System.out.println("Exception: Manager interrupted!!!");
            e.printStackTrace();
	    Logger.log(e.toString());
        }
    }


    protected void shutDown()
    {
        em.interruptThreads();
        em.interrupt();
        System.exit(0);
    }

    public void pauseThreads(){
        em.interruptThreads();
        em.interrupt();
        espr.reloadTree();
    }

    public void resumeThreads(){
        em.run();
        em.createThreads();
    }

}
