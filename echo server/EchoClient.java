package com.asiaInfo.caozg.ch_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Authgor: Yunsong Wang
 * @Description:
 * @Date Created in 21:31 2021/1/20
 * @Modified By:Client code
 */
public class EchoClientThread {
    private String host = "localhost";
    private int port = 8000;
    private Socket socket;
    
    public EchoClientThread() throws IOException {
        socket = new Socket(host, port);
    }

    public static void main(String args[]) throws IOException {
        new EchoClientThread().talk();
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

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
	
    public void talk() throws IOException {
        try {
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String fromClient = null;
            while ((fromClient = localReader.readLine()) != null) {
								String prcessedstr=letteronly(fromClient);
                pw.println(fromClient);
                System.out.println(br.readLine());
                if (prcessedstr.equals("quit"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
