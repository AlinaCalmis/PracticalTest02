package ro.pub.cs.systems.eim.lab04.practicaltest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public EditText serverPortEditText;
    public EditText addressEditText;
    public EditText clientPortEditText;
    public EditText operation;
    public EditText putValueEditText;
    public EditText getResultText;

    public Button startServerButton;
    public Button startClientButton;

    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverPortEditText = findViewById(R.id.serverPortEditText);
        addressEditText = findViewById(R.id.addressEditText);
        clientPortEditText = findViewById(R.id.clientPortEditText);
        operation = findViewById(R.id.Operation);
        getResultText = findViewById(R.id.getResultText);

        startServerButton = findViewById(R.id.startServerButton);
        startClientButton = findViewById(R.id.startClientButton);


        startServerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                serverThread = new ServerThread(Integer.parseInt(serverPortEditText.getText().toString()));
                serverThread.start();
            }
        });

        startClientButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                String address = addressEditText.getText().toString();
                int clientPort = Integer.parseInt(clientPortEditText.getText().toString());
                String Operation = operation.getText().toString();
                new ClientThread(address, clientPort, Operation, getResultText).start();
            }

        });
    }
}