package com.distributed.ta;

public class File {
    private String ipAddress;

    private String fileName;

    public File(){ }

    public File(String name, String ip){
        this.ipAddress = ip;
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getHash(){
        return Math.abs(this.fileName.hashCode()) % 32768;
    }
}
