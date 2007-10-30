package ru.ixxo.crux.engine.core;

import java.util.Vector;

import ru.ixxo.crux.common.Logger;
import ru.ixxo.crux.engine.Dispatcher;

/**
 * Modified by: Cka3o4Huk
 */

public class EngineManager implements Runnable
{
    private Thread thread;
    private Dispatcher disp;
//    private String dirname;

    final int threadcount = 20;

    boolean isStarted = false;
    
    private Vector wthreads;

    public EngineManager(Dispatcher disp)
    {
        this.disp = disp;

        thread = new Thread(this, "EngineManager");
        wthreads = new Vector();

        thread.start();
    }

    private void workHandle()
    {
        String completed = disp.callEngine(null);
        if ("Completed".equals(completed) && isStarted){
        	Logger.info("Stop all threads");        	
        	interruptThreads();
        	isStarted = false;
        }
    }

    private void manHandle()
    {    	
        boolean f = disp.callInterface(null);
        if (f)
        {
        	Logger.info("Reload all threads");
            interruptThreads();
            Logger.info("Start all threads");            
            createThreads();
        }
    }

    public void run()
    {
        //createThreads();
        try
        {
            while (true)
            {
                for (int i=0; i<50; i++)
                {
                    Thread.sleep(100);
                    manHandle();
                }

                workHandle();

            }
        }
        catch (InterruptedException e)
        {
            System.out.println("EngineManager interrupted!!!");
        }

    }
    
    @SuppressWarnings("unchecked")
    private void createThreads()
    {    	
        WorkThread wthread;
        for (int i=0; i< threadcount; i++)
        {
            wthread = new WorkThread(disp, "WorkThread #"+i);
            wthreads.add(wthread);
        }
        isStarted = true;
    }

    public void interruptThreads()
    {
        WorkThread wthread;

        for (int i=0; i< wthreads.size(); i++)
        {
           	wthread = (WorkThread) wthreads.elementAt(i);
          	if(wthread !=null) 	wthread.interrupt();
        }

        wthreads.removeAllElements();
    }

    public void interrupt()
    {
        thread.interrupt();
    }

}
