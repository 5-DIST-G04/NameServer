package com.distributed.ta;

import java.io.*;
import java.util.*;

public class NodeNameDatabase {
    private static NodeNameDatabase ourInstance = new NodeNameDatabase();

    public static NodeNameDatabase getInstance() {
        return ourInstance;
    }

    private Map<Integer, String> Map;

    private static final String storageLocation = "name-database";

    private NodeNameDatabase() {
        loadMapFromDisk();
    }

    public void addNode(Node node){
        this.Map.put(Math.abs(node.getName().hashCode()) % 32768, node.getIpAddress());
        writeMapToDisk();
    }

    public String removeNode(Node node){
        String output = this.Map.remove(node.getHash());
        writeMapToDisk();
        return output;
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

    public boolean NodeExists(Node node){
        return this.Map.containsKey(node.getHash());
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

    private void writeMapToDisk(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(storageLocation);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Map);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in storagelocation\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void loadMapFromDisk(){
        try {
            FileInputStream fileIn = new FileInputStream(storageLocation);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Map = (HashMap<Integer,String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e){
            Map = new HashMap<>();
            //System.out.println("the file did not exist and was created");
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }
}
