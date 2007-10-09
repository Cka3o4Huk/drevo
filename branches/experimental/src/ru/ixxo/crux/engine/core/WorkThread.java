package ru.ixxo.crux.engine.core;

import org.jdom.Element;
import java.net.URLDecoder;
import java.net.URLEncoder;
import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;
import ru.ixxo.crux.engine.core.tasks.EvaluateFileSize;

/**
 * Created by IntelliJ IDEA. User: Watcher Date: 05.02.2007 Time: 20:59:20 To
 * change this template use File | Settings | File Templates.
 */
public class WorkThread implements Runnable {
	private Thread thread;

	private String dirname;

	private Dispatcher disp;

	public WorkThread(Dispatcher disp) {
		this(disp, "WorkThread");
	}

	public WorkThread(Dispatcher disp, String name) {
		this.disp = disp;
		dirname = null;

		thread = new Thread(this, name);
		thread.start();
	}

	public void run() {
		// Executor executor = new Executor();

		while (true) {
			while (dirname == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Logger.log("Work Thread interrupted!!!");
				}
				dirname = (String) disp.callEngine(null);
			}

			Element taskElement = new Element("Task");
			Element entityElement = new Element("Entity");

			entityElement.setAttribute("fileName", URLEncoder.encode(dirname));
			taskElement.addContent(entityElement);
			EvaluateFileSize newTask = new EvaluateFileSize(taskElement);

			Element resultXML = newTask.execute();
			Logger.log("Sending results");
			dirname = disp.callEngine(resultXML);
		}
	}

	public void interrupt() {
		thread.interrupt();
	}
}
