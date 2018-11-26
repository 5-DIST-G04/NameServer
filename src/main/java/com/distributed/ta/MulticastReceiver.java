package com.distributed.ta;

import com.distributed.common.Node;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class MulticastReceiver extends Thread {
    private volatile boolean running = true;
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    int port;
    String ip;

    public MulticastReceiver(String ip, int port){
        this.port = port;
        this.ip = ip;
    }

    public void stopReceiver(){
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        try{
        socket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(ip);
        socket.joinGroup(group);
        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(
                    packet.getData(), 0, packet.getLength());
            System.out.println("received: " + received);
            String naam = received.substring(0,received.indexOf(","));
            String ip = received.substring(received.indexOf(",")+1);
            System.out.println(naam);
            System.out.println(ip);
            Node node = new Node(naam,ip);
            NodeNameDatabase.getInstance().addNode(node);
            Object amountNodes = new Object(){
                public int value = NodeNameDatabase.getInstance().amountNodes();
            };

            Client c = ClientBuilder.newClient();
            WebTarget target = c.target("http://"+node.getIpAddress()+":8080/");
            Response response = target.path("Nodes").request(MediaType.APPLICATION_JSON).put(Entity.entity(amountNodes,MediaType.APPLICATION_JSON),Response.class);
            System.out.println(response.toString());
        }
        socket.leaveGroup(group);
        socket.close();}catch (Exception e){

        }
    }
}
