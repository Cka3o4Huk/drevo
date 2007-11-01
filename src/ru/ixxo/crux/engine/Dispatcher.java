package ru.ixxo.crux.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.jdom.output.XMLOutputter;

import ru.ixxo.crux.client.tree.UserTreeViewer;
import ru.ixxo.crux.client.tree.XMLTreeViewer;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.providers.EvaluateSizeProvider;

/**
 * Created by IntelliJ IDEA. User: Watcher Date: 02.12.2006 Time: 0:29:52 To
 * change this template use File | Settings | File Templates.
 */
public class Dispatcher 
{

	private Queue queue;

	private int dutycount;
    
	private EvaluateSizeProvider espr;
    
	public Dispatcher(String dirname) {
		queue = new Queue();
		queue.push(dirname);
		dutycount = 0;
	}

	public void setEvaluateSizeProvider(EvaluateSizeProvider espr) {
		this.espr = espr;
	}
	/**
	 * Modified by Cka3o4Huk
	 * 
	 * @param collParams
	 *            is collection of parameter, it can be modified and uses as
	 *            transmit input and output data
	 * @return
	 */
    @SuppressWarnings("unchecked")

    public void pushDir(String dirname) {
		queue.push(dirname);
		dutycount = 0;    	
    }
    public void pushFile(String filename) {
		queue.push(filename);
    }
    
	// public synchronized boolean callInterface(Object obj)
	//public synchronized boolean callInterface(Collection collParams) {

	//}

	public synchronized String callEngine(Object obj) {

		//if (Thread.currentThread().getName().equals("EngineManager")) {

		//} 
		//else {
			/**
			 * Working Thread request
			 */

			if (obj != null) {
				/**
				 * Result was received
				 */

				Logger.log("Result received");

				if (obj instanceof Element)
					try {
						espr.processXMLResult((Element) obj);
					} catch (JDOMException e) {
						throw new RuntimeException(e);
					}

				// this.pushVector((Vector) obj);
				dutycount--;
			}

			if (dutycount + queue.getSize() == 0) {
				espr.setCompleted();

				return null;
			}

			Object retobj = queue.pop();
			if (retobj != null)
				dutycount++;
				Logger.info("Que-fileName: "+(String) retobj+"\n");
			return (String) retobj;
		//}
	}

}
