package ru.ixxo.crux.engine.providers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.jdom.output.XMLOutputter;


import ru.ixxo.crux.client.MyFrame;
import ru.ixxo.crux.client.tree.UserTreeViewer;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Queue;
import ru.ixxo.crux.engine.Dispatcher;

public class EvaluateSizeProvider {

//from Manager	
    private boolean engflag;
    public final String userFlagMutex = "userFlagMutex"; 
    private boolean userflag;
    public  String dirname;	
    protected boolean enforceReloadTree = false;    

//from Dispatcher
    private boolean flag;
    private boolean completed;
    private String dirnameD;

    private Element resultTree;

    private final static String treeMutex = "TreeMutex";

    public static final int XML_VIEW = 1;
    public static final int USER_VIEW = 2;
    public static final int UNDEF_VIEW = 0;

    public int viewMode = UNDEF_VIEW;

    

    private Dispatcher disp;
    
    public EvaluateSizeProvider(Dispatcher disp) {
    	this.disp = disp;
    	
        this.engflag=false;
        this.userflag=false;
    	
    	if(resultTree==null){
    		resultTree = new Element("Root");
    		resultTree.setAttribute("id","new");
    	}
    	this.flag = false;

    	completed = false;
    	
    }
    public Dispatcher getDispatcher() {
    	return disp;
    }
    
	
//from Manager
    public void setUserFlag(boolean flagValue){
    	synchronized (userFlagMutex) {
			userflag = flagValue;
		}
    }
    /**
     * 
     * @param mf
     */
    @SuppressWarnings("unchecked")

    public void handle(MyFrame mf)
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
	        boolean newEngFlag = callInterface(params);
	        
	        /**
	         * If task was completed then manager should make request tree
	         */
	        if (newEngFlag != engflag) {

                if (newEngFlag) {
                    /**
                     * Tree Request
                     */
                    XMLTreeViewer tree = requestTree();
                    mf.isStopButton = false;

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
    	callInterface(params);
    	
    	receivedParameters = params;
        Object obj = (receivedParameters!=null && receivedParameters.size() > 0) ? 
       		 receivedParameters.toArray()[0] : null;	
       		 
        return (obj != null && obj instanceof XMLTreeViewer)? (XMLTreeViewer) obj: null;
    }
    
    protected void repaintTree(MyFrame mf, XMLTreeViewer tree){
        try
        {
            mf.drawJTree(tree);
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(!mf.isDisplayable()){
        	mf.setVisible(true);
        }
		mf.RefreshGUI();
    }
    
    public void changeTreeView(int viewMode)
    {
    	if(this.viewMode != viewMode){
    		this.viewMode = viewMode;
    		reloadTree();
    	}
    }
    
    public void reloadTree()
    {
    	enforceReloadTree = true;
    }
    	
	
//
    
    

public void setCompleted() {
	if (!completed) {
		Logger.info("Complete!");
		completed = true;
	}
}
    
//from Dispatcher	

	public synchronized boolean callInterface(Collection collParams) {

		/**
		 * First element if exist
		 */

		Object obj = (collParams != null && collParams.size() > 0) ? collParams
				.toArray()[0] : null;

		if (collParams == null) {
			collParams = new ArrayList();
		}

		collParams.clear();

		if (Thread.currentThread().getName().equals("EngineManager")) {
			if (flag) {
				Logger.info("Engine Call for DirName");
				obj = dirnameD;
				flag = false;
				return true;
			}
			return false;
		} else {
			/**
			 * Request From Manager
			 */

			if ((obj != null) && (obj instanceof String)) {
				dirnameD = (String) obj;
				/**
				 * XML implementation
				 */
				Element directory = new Element("Directory");

				directory.setAttribute("fileName", URLEncoder.encode(dirnameD));
				directory.setAttribute("id", "new");
				resultTree.addContent(directory);

				disp.pushDir(dirnameD);
				flag = true;
				completed = false;
				Logger.log("Add to process following folder:" + dirnameD);
				return false;
			} else if((obj != null) && (obj instanceof Boolean) && (Boolean.TRUE.equals(obj))){
				// Logger.log("Tree request");
				synchronized (treeMutex) {
					/**
					 * Return current tree
					 */
					// JTree result = new JTree(tree);
					
					XMLTreeViewer viewer;
				//	Logger.info(new XMLOutputter().outputString(resultTree));
					if (((viewMode == UNDEF_VIEW) && (Logger.debug)) 
							|| (viewMode == XML_VIEW))
					{
							Logger.info("XML Interface");
							viewer = new XMLTreeViewer(resultTree);
					} 
					else
					{
							Logger.info("User Interface");
							viewer = new UserTreeViewer(resultTree);
					}
						
					collParams.add(viewer);
				}
				//Logger.info(new XMLOutputter().outputString(resultTree));
				return completed;
			}else{
				return completed;
			}
		}
	}    
    

	public synchronized String callEngine(Object obj) {

		//if (Thread.currentThread().getName().equals("EngineManager")) {
			synchronized (treeMutex) {
				//TODO:
			}
			if (completed)
				return "Completed";
			else
				return null;
		//} else {
			/**
			 * Working Thread request
			 */
		//}
	}

	public void processXMLResult(Element result) throws JDOMException {

		Logger.log("Processing XML Result");
		XPath entityPath = XPath.newInstance("Entity");
		XPath dirsOfResultPath = XPath.newInstance("Result/Directory");
		XPath filesOfResultPath = XPath.newInstance("Result/File");

		List entities = entityPath.selectNodes(result);
		if (entities == null || entities.size() == 0) {
			Logger.info("No entities");
			return;
		}

		Iterator it = entities.iterator();
		while (it.hasNext()) {
			Element entity = (Element) it.next();
			entity.setAttribute("id","new");
			String sourceFileName = entity.getAttributeValue("fileName");
			
			Logger.log("Processing Source Directory = " + sourceFileName);

			XPath findDirectory = null;
			try{
			findDirectory = XPath.newInstance(".//Directory[@fileName='"
					+sourceFileName+ "']");
			}catch(Exception e){ 
				Logger.info("Error opening directory: "+sourceFileName);
			}
			Element sourceElement = null;
			if (findDirectory!=null){
				sourceElement = (Element) findDirectory
					.selectSingleNode(resultTree);
			}
			if (sourceElement == null) {
				Logger.info("Directory don't found in main tree"+resultTree.toString());
	
				continue;
			}

			List directories = dirsOfResultPath.selectNodes((Element) entity);

			if (directories == null || directories.size() == 0) {
			} else {
				Iterator directoryIt = directories.iterator();
				while (directoryIt.hasNext()) {
					Element directory = (Element) directoryIt.next();
					String fileName = URLDecoder.decode(directory.getAttributeValue("fileName"));
					
					disp.pushFile(fileName);
					directory.setAttribute("id","new");
					directory.detach();
				}

				sourceElement.addContent(directories);
			}

			List files = filesOfResultPath.selectNodes((Element) entity);
			if (files == null || files.size() == 0) {
				Logger.log("No files");
			} else {
				Iterator fileIt = files.iterator();
				while (fileIt.hasNext()) {
					Element file = (Element) fileIt.next();
					file.setAttribute("id","new");
					file.detach();
				}

				sourceElement.addContent(files);
			}

			// TODO: add new directories to queue
		}
	}
	
	
}
