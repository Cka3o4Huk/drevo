package ru.ixxo.crux.engine;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Zhilin
 * Date: 12.04.2007
 * Time: 21:33:37
 * To change this template use File | Settings | File Templates.
 */
public class ProviderTicket {

    private BigInteger ticketID;

    public ProviderTicket(BigInteger ticketID){
        this.ticketID = ticketID;
    }

    public BigInteger getTicketID(){
        return ticketID;
    }

}
