package ru.ixxo.crux.engine.core.tasks;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.jdom.Element;

import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Task;

public class EvaluateFileSize implements Task {

	private Element args;
	private Element out;
	
	public EvaluateFileSize(Element args){
		init(args);
	}
	
	public Element execute(Element arguments) {
		init(arguments);
		return execute();
	}

	public Element execute() {
		Logger.log("Task Execution is started");
		if(args == null) return null;		
		out = (Element) args.clone();
		List entities = out.getChildren("Entity");
		if(entities == null || entities.size() == 0)
			return null;
		
		Iterator itEntity = entities.iterator();
		while(itEntity.hasNext()){
			Element file = (Element)itEntity.next();
			if(file!=null){
				Element result = evaluateSize(file);
				file.addContent(result);
			}
		}

		return out;
	}
	
    private Element evaluateSize(Element xmlElement)
    {
    	Logger.log("Evaluate Size start");
    	
    	if(xmlElement==null) return null;
    	
    	String fileName = xmlElement.getAttributeValue("fileName");

    	Logger.log("Evaluate Size for "+fileName);
    	
    	Element xmlResult = new Element("Result");       
    	File currentFile = new File(fileName);
    	    	
    	long totalSize = 0;
    	
        String subFiles[] = currentFile.list();
        String subDirectory;

        if(subFiles == null || subFiles.length == 0){
        	
        }else{
	        for (int index = 0; index < subFiles.length; index++)
	        {
	            File sub = new File(fileName + File.separatorChar + subFiles[index]);
	            
	            if (sub.isDirectory())
	            {
	                subDirectory = sub.getAbsolutePath();
	                Element subDirElem = new Element("Directory");
	                subDirElem.setAttribute("fileName", subDirectory);
	                xmlResult.addContent(subDirElem);
	            }
	            else
	            {
	                subDirectory = sub.getAbsolutePath();
	                Element subDirElem = new Element("File");
	                subDirElem.setAttribute("fileName", subDirectory);
	                subDirElem.setAttribute("size", ""+sub.length());
	                xmlResult.addContent(subDirElem);
	
	                totalSize = totalSize + sub.length();
	            }
	        }
    	}
        xmlResult.setAttribute("size", ""+totalSize);
        
        Logger.log("Evaluate Size finished");
        return xmlResult;
    }
    
	public void init(Element arguments) {
		args = arguments;		
	}

}
