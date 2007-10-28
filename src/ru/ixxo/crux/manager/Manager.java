package ru.ixxo.crux.manager;

import java.awt.Image;
import java.util.ArrayList;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTree;

import ru.ixxo.crux.client.MyFrame;
import ru.ixxo.crux.client.SplashWindow;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
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
    private Dispatcher disp;
    protected EngineManager em;

    protected boolean enforceReloadTree = false;
    
    private Dispatcher getDispatcher(){
    	if (disp==null)
    		disp=new Dispatcher(null);
    	return disp;
    }
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
            man.em = new EngineManager(man.getDispatcher());

        	while (true)
            {
                Thread.sleep(100);
                man.handle(mf);
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


    /**
     * 
     * @param mf
     */
    @SuppressWarnings("unchecked")
	private void handle(MyFrame mf)
    {
        synchronized (userFlagMutex) {
//		
//	    	Object obj = new Boolean(userflag);
	
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
	        if (newEngFlag != engflag) {

				if (newEngFlag) {
					/**
					 * Tree Request
					 */
						XMLTreeViewer tree = requestTree();

					if (tree != null)
						repaintTree(mf, tree);
				}

				engflag = newEngFlag;
			}
	        else if(enforceReloadTree)
			{
				XMLTreeViewer tree = requestTree();

				if (tree != null)
					repaintTree(mf, tree);
				
				enforceReloadTree = false;
			}
	        
	        userflag = false;
        }
    }
    
    public XMLTreeViewer requestTree()
    {
        ArrayList sendParameters = new ArrayList();
        ArrayList params;
        ArrayList receivedParameters;
        
    	sendParameters.clear();
    	sendParameters.add(Boolean.TRUE);
    	params = (ArrayList) sendParameters.clone();	       	
    	disp.callInterface(params);
    	
    	receivedParameters = params;
        Object obj = (receivedParameters!=null && receivedParameters.size() > 0) ? 
       		 receivedParameters.toArray()[0] : null;	
       		 
        return (obj != null && obj instanceof XMLTreeViewer)? (XMLTreeViewer) obj: null;
    }
    
    protected void repaintTree(MyFrame mf, XMLTreeViewer tree){
		mf.drawJTree(tree);
        if(!mf.isDisplayable()){
        	mf.setVisible(true);
        }
		mf.RefreshGUI();
    }
    
    public void changeTreeView(int viewMode)
    {
    	if(disp.viewMode != viewMode){
    		disp.viewMode = viewMode;
    		reloadTree();
    	}
    }
    
    public void reloadTree()
    {
    	enforceReloadTree = true;
    }
    	
    protected void shutDown()
    {
        em.interruptThreads();
        em.interrupt();
        System.exit(0);
    }

}
