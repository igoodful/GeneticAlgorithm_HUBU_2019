package com.xkpt;
import javax.xml.ws.Endpoint;
public class Publish {
    public static void main(String[] args){
        Object o = new Main();
        String address = "http://localhost:8989/geneticAlgorithm";
        Endpoint.publish(address, o);
    }
}