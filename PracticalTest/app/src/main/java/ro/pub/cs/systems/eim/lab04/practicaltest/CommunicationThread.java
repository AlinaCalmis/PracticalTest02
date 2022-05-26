package ro.pub.cs.systems.eim.lab04.practicaltest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        Log.d(Constants.LOG_COLOCVIU, "CommunicationThread started");

        if (socket == null) {
            Log.e(Constants.LOG_COLOCVIU, "Error: socket is null");
            return;
        }

        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            String request = bufferedReader.readLine();
            Log.d(Constants.LOG_COLOCVIU, "Received message is here: " + request);

            String[] requestOperation = request.split(",");

            String operation = requestOperation[0];
            Integer val1 = Integer.parseInt(requestOperation[1]);
            Integer val2 = Integer.parseInt(requestOperation[2]);

            if (operation.equals("add")) {

                Integer result = val1 + val2;
                printWriter.println(result.toString());

            } else if (operation.equals("mul")) {
                try {
                    Thread.sleep(4000);

                    Integer result = val1 * val2;
                    printWriter.println(result.toString());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                     socket.close();
                     Log.d(Constants.LOG_COLOCVIU, "Connection Closed");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}