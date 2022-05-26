package ro.pub.cs.systems.eim.lab04.practicaltest;
import ro.pub.cs.systems.eim.lab04.practicaltest.Constants;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private final int port;
    private ServerSocket serverSocket;


    public ServerThread(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
            Log.d(Constants.LOG_COLOCVIU, serverSocket.toString());
        } catch (IOException e) {
            Log.e(Constants.LOG_COLOCVIU, "Could not listen on port: " + port);
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        Log.d(Constants.LOG_COLOCVIU, "Server started on port: " + port);

        while (!Thread.currentThread().isInterrupted()) {

            try {
                Socket socket = serverSocket.accept();
                Log.d(Constants.LOG_COLOCVIU, "Accepted connection from: " + socket.getInetAddress());

                new CommunicationThread(this, socket).start();
            } catch (IOException e) {
                Log.e(Constants.LOG_COLOCVIU, "Accept failed");
                e.printStackTrace();
            }
        }
    }

    public void close() {
        Log.d(Constants.LOG_COLOCVIU, "Closing server socket");
        interrupt();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(Constants.LOG_COLOCVIU, "Could not close the connect socket");
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

}
