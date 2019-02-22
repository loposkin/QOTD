package com.company;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;


public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(10000);
    }

    /**
     * accepts connection on listening port
     * @return socket of connected client
     */
    private Socket acceptConnection() {
        Socket clientSocket = null;
        try {
            if(this.serverSocket != null) {
                clientSocket = this.serverSocket.accept();
            }
        } catch (IOException e) {
            System.out.print(e.toString());
        }
        return clientSocket;
    }


    /**
     * reads file from current directory
     * @return
     */
    private static List<String> readFile() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Path path = Paths.get("qotd.txt");
        Charset charset = Charset.forName("UTF-8");
        try(BufferedReader reader  = Files.newBufferedReader(path, charset)) {
            String line = null;
            while((line = reader.readLine()) != null) {
                arrayList.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return  arrayList;
    }

    /**
     * main-loop function
     * @throws IOException
     */
    private void run() throws IOException{
        List<String> quotes = readFile();
        Random random = new Random();

        while (true) {
            Socket clientSocket = this.acceptConnection();

            DataOutputStream outputStream = null;
            if(clientSocket != null) {
                outputStream = new DataOutputStream(clientSocket.getOutputStream());
            }
            outputStream.writeUTF(quotes.get(random.nextInt(quotes.size())));
            if(clientSocket != null) {
                clientSocket.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("Usage: port");
            System.exit(1);
        }
        int port = Integer.valueOf(args[0]);
        Server server = new Server(port);
        server.run();
    }


}
