import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    
    private static Socket socket;
    
    
    public static void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("Connected!");
    }
    
    public void close() {
        try {
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
        
    public void send(String msg) throws IOException {
    
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush(); // send the message
        dataOutputStream.close(); // close the output stream when we're done.
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    
    
}
