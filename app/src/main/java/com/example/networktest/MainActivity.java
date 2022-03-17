package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnAbs;
    private Button btnBer;
    private TextView anweisung;
    private EditText matrNumber;
    private TextView rueckgabe;
    private TextView ergebnis;


    String matrikelnummer;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbs = findViewById(R.id.btn);
        anweisung = findViewById(R.id.anweisung);
        matrNumber = findViewById(R.id.matrNumber);
        rueckgabe = findViewById(R.id.rueckgabe);
        btnBer = findViewById(R.id.berechnen);
        ergebnis = findViewById(R.id.ergebnis);

        btnAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = matrNumber.getText().toString();
                TCPClient client = new TCPClient(matrikelnummer);
                client.start();
                try {
                    client.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rueckgabe.setText(client.getOutput());

            }
        });

        btnBer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = matrNumber.getText().toString();

                if (!matrikelnummer.equals("")) {
                    if (matrikelnummer.length() < 7 || matrikelnummer.length() > 9) {
                        ergebnis.setText("Das ist keine gültige Matrikelnummer");
                    } else {

                        int[] matNrInt = new int[matrikelnummer.length()];
                        for (int i = 0; i < matrikelnummer.length(); i++) {
                            matNrInt[i] = matrikelnummer.charAt(i) - '0';
                        }

                        for (int k = 1; k < matNrInt.length; k++) {
                            for (int l = 0; l < matNrInt.length - k; l++) {
                                if (matNrInt[l] > matNrInt[l + 1]) {
                                    int temp = matNrInt[l];
                                    matNrInt[l] = matNrInt[l + 1];
                                    matNrInt[l + 1] = temp;
                                }
                            }
                        }
                        String result = "";
                        boolean isPrime;
                        int num = 0;

                        for (int m = 0; m < matNrInt.length; m++) {
                            num = matNrInt[m];
                            if (!isPrime(num)) {
                                result += num;
                            }
                            ergebnis.setText(result);

                        }
                    }
                }
                    else{
                        ergebnis.setText("Gib erst eine Matrikelnummer ein");
                    }
                }
                public boolean isPrime(int num){
                    if (num <= 1) {
                        return false;
                    }
                    for (int i = 2; i <= Math.sqrt(num); i++) {
                        if (num % i == 0) {
                            return false;
                        }
                    }
                    return true;
                }
            });
        }

    }