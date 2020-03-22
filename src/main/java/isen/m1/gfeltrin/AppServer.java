package isen.m1.gfeltrin;

import javax.management.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer
{
    private static ExecutorService executor = Executors.newFixedThreadPool(20);
    public static void main( String[] args ) throws IOException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, InterruptedException {
        ServerSocket server = null;
        Socket client = null;

        try {
            server = new ServerSocket(8888);    //TCP server

            //Petit soucis dans la gestion des Beans que je n'arrive pas à résoudre
            /*ServerAdmin serverAdmin = new ServerAdmin();
            serverAdmin.setRunning(true);
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.gfeltrin:type=ServerAdmin");
            mbs.registerMBean(serverAdmin, name);*/

            while (true ) { //serverAdmin.isRunnig()
                System.out.println("Server is waiting for a response");
                client = server.accept();

                System.out.println("A client is connected");

                Client clientExecutor = new Client(client);
                executor.execute(clientExecutor);
                new Thread(clientExecutor).start();
                clientExecutor.run();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                server.close();
                executor.shutdown();
                //serverAdmin.shutdown();
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
}

class Client implements Runnable{

    private static String HTML_BODY = "<html><body>"
            + "<h1>Hello World!</h1>"
            + "</br><img src='https://media.giphy.com/media/JIX9t2j0ZTN9S/giphy.gif'>"
            + "</body></html>";
    Socket socket;

    public Client(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() throws RuntimeException{
        try {
            Book b1 = new Book("");
            Book b2 = new Book( "");
            Book b3 = new Book("");

            b1.setTitle("Lord of the Rings 1");
            b2.setTitle("Lord of the Rings 2");
            b3.setTitle("Lord of the Rings 3");

            //b1.readBookServer();
            System.out.println("A client is connected");

            InputStream is = socket.getInputStream();
            //tuyaux entrees/sorties
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            System.out.println(line);

            if ((line != null) && line.contains("stop")){
                throw new ServerShutdownException();
            }
            OutputStream os = socket.getOutputStream();
            PrintWriter pr = new PrintWriter(os);

            pr.print("HTTP/1.1 200 \r\n");
            pr.print("Content-Type: text/html\r\n");
            pr.print("Connection: close\r\n");
            pr.print("\r\n");
            //System.out.println(b.getTitle());
            //pr.print(b.getTitle());
            Thread.sleep(1000);
            pr.print("<html><body>"
                    + "<h1>Hello World!</h1>"
                    + "</br><img src='https://media.giphy.com/media/JIX9t2j0ZTN9S/giphy.gif'>"
                    + b1.getTitle() + "\n"
                    + b2.getTitle() + "\n"
                    + b3.getTitle() + "\n"
                    + "</body></html>" + "\n");
            pr.flush();
        } catch (ServerShutdownException | InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
