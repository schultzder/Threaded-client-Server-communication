/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author schultzder
 */

import java.io.*;
import java.net.*;

public class Client 
{
    public static void main(String[] args) throws IOException
    {
        String hostName = "127.0.0.1";
        int portNumber = 5764;
        
        try (Socket welcomeSocket = new Socket(hostName, portNumber);
            PrintWriter out = new 
            PrintWriter(welcomeSocket.getOutputStream(), true);
            BufferedReader in = new 
            BufferedReader
            ( new InputStreamReader(welcomeSocket.getInputStream()));)
        {
            BufferedReader inStream = new 
            BufferedReader(new InputStreamReader(System.in));
            String inServer;
            String outClient;
            
            while ((inServer = in.readLine()) != null)
            {
                System.out.println("Server: " + inServer);
                if(inServer.equals("Bye."))
                    break;
                
                outClient = inStream.readLine();
                if(outClient != null)
                {
                    System.out.println("Client: " + outClient);
                    out.println(outClient);
                }
            }         
        }
        
        catch (UnknownHostException e )
        {
          System.err.println("Don;t know about host " + hostName);
          System.exit(1);
        } 
        catch (IOException e )
        {
          System.err.println("Couldn't get I/O for the connection to " 
                  + hostName);
          System.exit(1);
        }
    }
}
