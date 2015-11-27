package com.tabinsight.util;

/**
 * Created by bijilap on 11/26/15.
 */
public class TabInsightApplicationContext {

    public enum BroadcastRecieverClients{
        UI_ACTIVITY,
        SERVICE
    }

    private static  BroadcastRecieverClients broadcastRecieverClient;

    public static BroadcastRecieverClients getBroadcastRecieverClient(){
        return broadcastRecieverClient;
    }

    public static void setBroadcastRecieverClient(BroadcastRecieverClients client){
        broadcastRecieverClient = client;
    }

}
