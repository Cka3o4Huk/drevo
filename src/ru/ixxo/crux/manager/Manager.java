package ru.ixxo.crux.manager;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTree;

import ru.ixxo.crux.client.MyFrame;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.engine.core.EngineManager;

/**
 * Created by IntelliJ IDEA.
 * User: Watcher
 * Date: 10.11.2006
 * Time: 21:43:33
 * To change this template use File | Settings | File Templates.
 * $LastChangedDate: 2007-06-05 19:51:29 +0400 (Вт, 05 июн 2007) $
 * $Id: Manager.java 72 2007-06-05 15:51:29Z zhilin $
 * $HeadURL: svn://ipccenter.ru/ncmipt/FSM/branches/experimental/src/ru/mipt/fsm/ixxo/manager/Manager.java $
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
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setTitle("Swing Interface");
		//StatusComponent status = new StatusComponent(mf);
        //mf.getContentPane().add(status.getBox(), "South");

		mf.setSize(800,600);
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
