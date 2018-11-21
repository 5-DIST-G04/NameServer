package com.distributed.ta;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.StringTokenizer;

import static com.distributed.ta.Main.nodes;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    int port;
    String ip;

    public MulticastReceiver(String ip, int port){
        this.port = port;
        this.ip = ip;
    }
    public void run() {
        try{
        socket = new MulticastSocket(port);
        InetAddress group = InetAddress.getByName(ip);
        socket.joinGroup(group);
        while (true) {
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
            nodes.addNode(node);


            //functie
            if ("end".equals(received)) {
                break;
            }
        }
        socket.leaveGroup(group);
        socket.close();}catch (Exception e){

        }
    }
}
