package com.asiaInfo.caozg.ch_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerThread {
    private int port = 8000;
    private ServerSocket serverSocket;

    public EchoServerThread() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("server started.;");
    }

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();  //Waiting for client to connect
                // Create a thread for each client
                Thread workThread = new Thread(new Handles(socket));
                //Start thread
                workThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        new EchoServerThread().service();
    }

    private class Handles implements Runnable {
        private Socket socket;

        public Handles(final Socket socket) {
            this.socket = socket;
        }

        public String echo(String charFromClient) {
            return "echo:" + charFromClient;
        }
				
				//Obtain the output stream of the socket and open PrintWriter on it
        private PrintWriter getWriter(Socket socket) throws IOException {
            OutputStream socketOut = socket.getOutputStream();
            return new PrintWriter(socketOut, true);
        }
				
				//Get the input stream of the socket and open a BufferedReader on it
        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn));
        }

		//remove which is not letter
		public String letteronly(String charFC)
		{
			String str=charFC.trim();
			String str2="";
			if(str != null && !"".equals(str))
			{
				for(int index=0;index<str.length();index++)
				{
					if(str.charAt(index)>=97 && str.charAt(index)<=122)
					{
						str2+=str.charAt(index);
					}
				}
			}
			return str2;
		}		
		
        @Override public void run() {
            try {
                System.out.println("New connection accepted "
                        + socket.getInetAddress() + ":" + socket.getPort());
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                
                String charFromClient = null;
                
                while ((charFromClient = br.readLine()) != null) {
                		String toClient=letteronly(charFromClient);
                    pw.println(echo(toClient));
                    if (toClient.equals("quit")) //If the message sent by the client is "quit", end the communication
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
