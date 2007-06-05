package ru.ixxo.crux.engine;

/**
 * Created by IntelliJ IDEA.
 * User: Zhilin
 * Date: 12.04.2007
 * Time: 21:29:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class Provider {

    protected Queue ticketQueue;

    public Provider(){
        ticketQueue = new Queue();        
    }

    public abstract ProviderResponse processRequest(ProviderRequest request);

    public abstract ProviderTicket submitRequest(ProviderRequest request);

    public abstract ProviderResponse getResponse(ProviderTicket ticket);

    public abstract boolean cancelRequest(ProviderTicket ticket);

    public abstract boolean isResponseReady(ProviderTicket ticket);
}
