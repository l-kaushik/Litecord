import java.net.*;
import java.io.*;


public class Server{

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // constructor
    public Server(){
        try{
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
            out = new PrintWriter(socket.getOutputStream());

            StartReading();
            StartWriting();
        
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void StartReading(){
        // thread - this will read data

        Runnable r1 = ()->{
            System.out.println("Reader started...");

            while(true){
                try{
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("client terminated the chat");
                        break;
                    }
    
                    System.out.println("Client: " + msg);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start();
    }

    public void StartWriting(){
        // thread - this will send data to client
        Runnable r1 = ()->{
            System.out.println("Writer started...");

            while(true){
                try{
                  
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args){
        new Server();
    }
}