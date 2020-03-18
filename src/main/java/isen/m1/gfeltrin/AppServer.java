package isen.m1.gfeltrin;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer
{
    private static String HTML_BODY = "<html><body>"
            + "<h1>Hello World!</h1>"
            + "</br><img src='https://media.giphy.com/media/JIX9t2j0ZTN9S/giphy.gif'>"
            + "</body></html>";
    public static void main( String[] args ) throws IOException {
        ServerSocket server = null;
        Socket client = null;
        try{
            server = new ServerSocket(8888);    //TCP server

            while(true) {
                System.out.println("Server is waiting");
                client = server.accept();

                try {
                    System.out.println("A client is connected");

                    InputStream is = client.getInputStream();
                    //tuyaux entrees/sorties
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = br.readLine();
                    System.out.println(line);

                    if ((line != null) && line.contains("stop")){
                        throw new ServerShutdownException();
                    }
                    OutputStream os = client.getOutputStream();
                    PrintWriter pr = new PrintWriter(os);

                    pr.print("HTTP/1.1 200 \r\n");
                    pr.print("Content-Type: text/html\r\n");
                    pr.print("Connection: close\r\n");
                    pr.print("\r\n");
                    pr.print(HTML_BODY + "\n");
                    pr.flush();
                } finally {
                    if (client != null) {
                        client.close();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        catch (ServerShutdownException e){
            System.out.println("Server closing");
        }
        finally {
            server.close();
        }
    }
}
