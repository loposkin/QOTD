package com.company;
import java.io.*;
import java.net.*;
public class Client {
    private Socket socket;
    public Client(InetAddress host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }
    public void run() throws  IOException{
        DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
        System.out.println(inputStream.readUTF());
        socket.close();
    }
    public static void main(String[] args) throws IOException{
        if(args.length < 2) {
            System.out.println("Usage: hostIP hostPort");
            System.exit(1);
        }
        Client client = null;
        InetAddress address = InetAddress.getByName(args[0]);
        int port = Integer.valueOf(args[1]);
        try {
            client = new Client(address, port);

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        client.run();


    }
}
