/*
 * This class extends the java class "Thread" which enables multiple clinets to
 * connect to the server. The majority of the funcionality of this class 
 * consits of the run method. This run method checks for the client's 
 * responses and then sends the message to the encryption class. The servers 
 * response will be the encrypted message using a Ceasar Chipher. This class
 * also contains a method to write to the log file with the proper formatting. 
 */

/**
 *
 * @author schultzder
 */
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerThread extends Thread  
{
    /*
     * Delecration of global variables. Used later for printing and reading. 
     */
    private final Socket socket;
    private final PrintWriter forClient;
    private final PrintWriter logFile;
    private final BufferedReader outClient;
    private boolean isConnected = false;
    
    /*
     * This is the constuctor for the class. The constuctor simply intializes 
     * the various print writers, buffered readers, and sockets. 
     */
    
    public ServerThread(Socket clientSocket, PrintWriter printWriter) 
            throws IOException
    {
        this.socket = clientSocket;
        this.logFile = printWriter;
        this.forClient = new PrintWriter
        (clientSocket.getOutputStream(), true);
        this.outClient = new BufferedReader
        (new InputStreamReader(clientSocket.getInputStream()));
        this.isConnected = true;
        outLogFile(); // method call
    }
    
    /*
     * This is the main method for this class. It handles the logic for 
     * reading the messages from the client and then sending the message to the
     * encryption class. The method also handles sending the message from the 
     * server back to the client. This method also contains code for setting up 
     * the chipher "object". 
     */
    
    @Override
    public void run()
    {
        String dataIn, dataOut;
        ChipherAlphabet ca = new ChipherAlphabet(5, 19);
        try {
            while((dataIn = outClient.readLine()) != null)
            {
                if(dataIn.equals("quit"))
                    break;
                dataOut = ca.encrypt(dataIn); //call chipher class
                forClient.println(dataOut); // sends the server message back
            }
        
            isConnected = false;
            outLogFile(); // method call
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger
            (ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            this.socket.close();
            this.forClient.close();
            this.outClient.close();
            this.logFile.close();
        }
        catch(IOException i )
        {
            this.logFile.println("Trouble closing");
        }
        
    }
    
    /*
     * This method writes the appropriate messages to the log file, depending 
     * on weather the client is connecting or disconnecting to/from the server
     */
    
    private void outLogFile()
    {
        Date date = new Date();
        InetAddress address = socket.getInetAddress();
        
        if(isConnected)
            logFile.println("Connected: " + date.toString() + 
                    " Address: " + address.toString() + " port number: " 
                    + socket.getPort()); // connection
        else
            logFile.println("Connection with on " + socket.getPort() 
                    + " was closed."); // disconnection
    }
}
