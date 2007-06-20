package ru.ixxo.crux.engine;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Watcher
 * Date: 15.12.2006
 * Time: 20:42:52
 * To change this template use File | Settings | File Templates.
 */
public class Queue
{
    private Vector list;

    public Queue()
    {
        list = new Vector();
    }

    @SuppressWarnings("unchecked")
    public void push(Object obj)
    {
        if (obj!=null)
        {
            list.add(obj);
        }
    }

    public Object pop()
    {
        if (list.size() == 0) return null;

        Object obj = list.get(0);
        list.remove(0);

        return obj;
    }

    public int getSize()
    {
        return list.size();
    }

}
