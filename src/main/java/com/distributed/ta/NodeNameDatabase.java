package com.distributed.ta;

import java.util.*;

public class NodeNameDatabase {
    private static NodeNameDatabase ourInstance = new NodeNameDatabase();

    public static NodeNameDatabase getInstance() {
        return ourInstance;
    }

    private Map<Integer, String> Map;

    private NodeNameDatabase() {
        Map = new HashMap<>();

        String nodename1 = "node1";
        String nodename2 = "device2";
        String nodename3 = "apparaat3";
        String nodename4 = "toestel4";

        String[] nodes = {nodename1, nodename2, nodename3, nodename4};

        this.Map.put(Math.abs(nodename1.hashCode()) % 32768, "192.168.0.2");
        this.Map.put(Math.abs(nodename2.hashCode()) % 32768, "192.168.0.3");
        this.Map.put(Math.abs(nodename3.hashCode()) % 32768, "192.168.0.4");
        this.Map.put(Math.abs(nodename4.hashCode()) % 32768, "192.168.0.5");

    }

    public void addNode(Node node){
        this.Map.put(Math.abs(node.getName().hashCode()) % 32768, node.getIpAddress());
    }

    public void removeNode(Node node){
        this.Map.remove(node.getHash());
    }


    // expects a node object where ip is not filled in yet and fills it in when available in the hash table
    // When not available the function returns -1
    public int getNodeIp(Node node){
        String ip = this.Map.get(node.getHash());
        if (ip == null){
            return -1;
        }
        node.setIpAddress(this.Map.get(node.getHash()));
        return 0;
    }

    public String getFileLocation(File file){
        Set<Integer> nodes = this.Map.keySet();
        int fileHash = file.getHash();
//        System.out.println("begin");
//        System.out.println(fileHash);
        Object[] hashArray = nodes.toArray();
        Arrays.sort(hashArray);
        int index = hashArray.length -1;
        for (int i = 0; i < hashArray.length; i++) {
            Integer value = (Integer)hashArray[i];
            int val = value.intValue();
//            System.out.printf("%d:%d\n",i,val);
            if(val > fileHash){
                if(i != 0)
                    index = i - 1;
                break;
            }
        }

//        System.out.printf("index:%d\n", index);

        file.setIpAddress(this.Map.get(hashArray[index]));
        return file.getIpAddress();
    }
}
