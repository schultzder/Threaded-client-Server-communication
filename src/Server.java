/*
 * The purpose of this program is to create a client and server communication
 * that is capable of handling multiple clients. The server class has a main
 * method and a run method. The main creates the server and calls the run 
 * method. The run method creates the print writer for the log file as well as
 * the print writer for the server itself. The method also runs a continous 
 * loop that checks for clients looking to connect. The socket is created, and 
 * and object of the server thread is created in order to handle the clients 
 * request. 
 */

/**
 *
 * @author schultzder
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server 
{
    /*
     * Vairable delecrations
     */
    
    
    private final int portNumber = 5764;
    private PrintWriter printWriter;
    private ServerSocket serverSocket;
    
    public static void main(String[] args) throws IOException
    {
        Server s = new Server();
        s.run(); // method call
    }
        
  
        public void run()
        { 
            /*
             *  Try and catch statement encapulates the various delecratons
             * and creates a socket. Then as server thread is created and 
             * ran using the start() method. 
             */
                try
                {
                    FileOutputStream out = 
                            new FileOutputStream("prog2.log"); // log file) 
                    this.printWriter = new PrintWriter(out, true); // server
                    this.serverSocket = new ServerSocket(portNumber);
                    while (true)
                    {
                        Socket userSocket;
                        userSocket = serverSocket.accept();
                        ServerThread sThread = 
                        new ServerThread(userSocket, printWriter);
                        sThread.start(); // start method for the thread
                    }
                }
                catch (IOException e)
                {
                  System.err.println(e);
                }
                
                try 
                {
                    this.serverSocket.close();
                } catch (IOException ex) 
                
                {
                    Logger.getLogger
                    (Server.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

