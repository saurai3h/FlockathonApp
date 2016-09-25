/*
 * Copyright (C) 2016 Media.net Advertising FZ-LLC All Rights Reserved
 */

package co.flock.flockathon.redis_cluster;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prabhat on 11/2/16.
 */
public class ServerInstance implements Serializable {

    private String host;
    private int port;

    public ServerInstance(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static ServerInstance parse(String instanceString) throws Exception {
        String[] parsedInstance = instanceString.split(":");
        if(parsedInstance.length<2){
            throw new Exception(instanceString +" is not in correct format host:port");
        }
        return new ServerInstance(parsedInstance[0],Integer.parseInt(parsedInstance[1]));
    }

    public static ArrayList<ServerInstance> parseList(String instancesString) throws Exception {
        String[] parsedInstances = instancesString.split(",");
        ArrayList<ServerInstance> instances =  new ArrayList<>();
        for(String parsedInstance: parsedInstances){
            instances.add(parse(parsedInstance));
        }
        return instances;
    }

    @Override
    public String toString() {
        return getHost()+":"+getPort();
    }
}
