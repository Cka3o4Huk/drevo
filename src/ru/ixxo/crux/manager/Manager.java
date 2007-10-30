package ru.ixxo.crux.manager;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTree;

import ru.ixxo.crux.client.MyFrame;
import ru.ixxo.crux.client.SplashWindow;
import ru.ixxo.crux.common.Logger;

import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.engine.core.EngineManager;

/**
 * 
 */
public class Manager
{
    private boolean engflag;
    public final String userFlagMutex = "userFlagMutex"; 
    private boolean userflag;
    public  String dirname;
    //protected DefaultMutableTreeNode tree;
    protected Dispatcher disp;
    protected EngineManager em;

    public Manager()
    {
        this.engflag=false;
        this.userflag=false;
    }

    public void setUserFlag(boolean flagValue){
    	synchronized (userFlagMutex) {
			userflag = flagValue;
		}
    }
    
    public static void main(String args[])
    {
        Manager man = new Manager();

        Logger.debug = false;
        Logger.info = true;
        
        MyFrame mf = new MyFrame(man);
        JFrame splashFrame = new JFrame();
        SplashWindow splash = new SplashWindow("splash.jpg",splashFrame,mf,5000);

        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setTitle("Swing Interface");
		//StatusComponent status = new StatusComponent(mf);
        //mf.getContentPane().add(status.getBox(), "South");

		mf.setSize(800,600);

        mf.centralize();

        if(splash == null)
            mf.setVisible(true);
        
        String javaPath = System.getProperty("java.class.path", "");
        System.out.println("Java Path = " + javaPath);

        try
        {
            man.disp = new Dispatcher(null);
            man.em = new EngineManager(man.disp);

        	while (true)
            {
                Thread.sleep(100);
                man.handle(mf);
            }
        }
        catch (RuntimeException e)
        {
            System.out.println("Manager interrupted!!!");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Manager interrupted!!!");
            e.printStackTrace();
        }
    }

    
    /**
     * 
     * @param mf
     */
    @SuppressWarnings("unchecked")
	private void handle(MyFrame mf)
    {
        synchronized (userFlagMutex) {
		
	    	Object obj = new Boolean(userflag);
	
	        ArrayList sendParameters = new ArrayList();
	        
	        if(userflag){
	        	sendParameters.add(dirname);
	        }else{
	        	/**
	        	 * Tree request
	        	 */	        	
	        	sendParameters.add(null);
	        }
	        
	        ArrayList params = (ArrayList) sendParameters.clone();
	        boolean newEngFlag = disp.callInterface(params);
	        
	        /**
	         * If task was completed then manager should make request tree
	         */
	        if (newEngFlag != engflag && newEngFlag){
	        	/**
	        	 * Tree Request
	        	 */        		        
	        	
	        	sendParameters.clear();
	        	sendParameters.add(Boolean.TRUE);
		        params = (ArrayList) sendParameters.clone();	        	
	        	disp.callInterface(params);
	        }
	        
	        ArrayList receivedParameters = params;
	        
	        obj = (receivedParameters!=null && receivedParameters.size() > 0) ? 
	        		 receivedParameters.toArray()[0] : null;	
	        		 
	        if (newEngFlag != engflag)
	        {
	        	Logger.info("Status changed to "+newEngFlag);
	        	if (obj != null && newEngFlag)
	            {
	        		Logger.info("Drawing new tree");
	        		mf.drawJTree((JTree) obj);
	                if(!mf.isDisplayable()){
	                	mf.setVisible(true);
	                }
	        		mf.RefreshGUI();
	            }
	            
	            engflag = newEngFlag;
	        	//mf.completed();
	        }
	        
	        userflag = false;
        }
    }

    protected void shutDown()
    {
        em.interruptThreads();
        em.interrupt();
        System.exit(0);
    }

}
