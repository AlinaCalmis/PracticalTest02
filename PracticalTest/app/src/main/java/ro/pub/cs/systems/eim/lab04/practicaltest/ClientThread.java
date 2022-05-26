package ro.pub.cs.systems.eim.lab04.practicaltest;

import ro.pub.cs.systems.eim.lab04.practicaltest.Constants;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientThread extends Thread {
    private String address;
    private int port;
    private String value;

    private Socket socket;
    TextView responseTextView;

    public ClientThread(String address, int port, String value, TextView responseTextView) {
        this.address = address;
        this.port = port;
        this.value = value;
        this.responseTextView = responseTextView;
    }

    public void run() {
        try {
            socket = new Socket(address, port);
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            Log.d(Constants.LOG_COLOCVIU, "Sending value: " + value);

            printWriter.println(value);
            printWriter.flush();


            String response = bufferedReader.readLine();
            responseTextView.post(new Runnable() {
                @Override
                public void run() {
                    responseTextView.setText(response);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}