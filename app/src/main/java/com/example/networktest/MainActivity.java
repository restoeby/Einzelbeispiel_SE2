package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnAbs, btnBer;
    private EditText matrNumber;
    private TextView rueckgabe;
    private TextView ergebnis;
    private EditText auswahl;
    private Spinner beschreibung;
    private TextView kurzText;


    String matrikelnummer;
    String eingabeAufgabe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbs = findViewById(R.id.btn);
        TextView anweisung = findViewById(R.id.anweisung);
        matrNumber = findViewById(R.id.matrNumber);
        rueckgabe = findViewById(R.id.rueckgabe);
        btnBer = findViewById(R.id.berechnen);
        ergebnis = findViewById(R.id.ergebnis);
        auswahl = findViewById(R.id.aufgabe);
        beschreibung = findViewById(R.id.beschreibung);
        kurzText = findViewById(R.id.kurzText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.aufgaben, android.R.layout.simple_spinner_item);
        beschreibung.setAdapter(adapter);
        beschreibung.setOnItemSelectedListener(this);

        btnAbs.setOnClickListener(new OnClickListener() {
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

        btnBer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                matrikelnummer = matrNumber.getText().toString();
                eingabeAufgabe = auswahl.getText().toString();
                if (!matrikelnummer.equals("")) {
                    if (matrikelnummer.length() < 7 || matrikelnummer.length() > 9) {
                        ergebnis.setText("Das ist keine gültige Matrikelnummer");
                    } else {
                            switch (eingabeAufgabe) {
                                case "0":
                                    ergebnis.setText(aufgabe0(matrikelnummer));
                                    break;
                                case "1":
                                    ergebnis.setText(aufgabe1(matrikelnummer));
                                    break;
                                case "2":
                                    ergebnis.setText(aufgabe2(matrikelnummer));
                                    break;
                                case "3":
                                    ergebnis.setText(aufgabe3(matrikelnummer));
                                    break;
                                case "4":
                                    ergebnis.setText(aufgabe4(matrikelnummer));
                                    break;
                                case "5":
                                    ergebnis.setText(aufgabe5(matrikelnummer));
                                    break;
                                case "6":
                                    ergebnis.setText(aufgabe6(matrikelnummer));
                                    break;
                                default:ergebnis.setText("Ungültige Eingabe. Bitte gib eine Zahl zwischen 0 und 6 ein.");
                            }
                        }

                } else {
                    ergebnis.setText("Gib erst eine Matrikelnummer ein");
                }
            }
        });
    }

    public String aufgabe0(String matrikelnummer) {
        String result;
        int num;
        int[] intArray = toArray(matrikelnummer);
        num = alternierendeQuersumme(intArray);
        if (num % 2 == 0) {
            result = "Die Zahl ist gerade! Zahl:  " + num;
        } else {
            result = "Die Zahl ist ungerade! Zahl:   " + num;
        }
        return result;

    }

    public String aufgabe1(String matrikelnummer) {
        String result;
        int num;
        String gerade = "";
        String ungerade = "";
        int[] intArray = toArray(matrikelnummer);
        intArray = sortArray(intArray);
        for (int i = 0; i < intArray.length; i++) {
            num = intArray[i];
            if (intArray[i] % 2 == 0) {
                gerade += num;
            } else {
                ungerade += num;
            }
        }
        result = gerade + ungerade;
        return result;

    }

    public String aufgabe2(String matrikelnummer) {
        String result = "";
        int[] intArray = toArray(matrikelnummer);
        for (int i = 0; i < intArray.length; i++) {
            for (int j = i + 1; j < intArray.length; j++) {
                int ggt = ggt(intArray[i], intArray[j]);
                if (ggt > 1) {
                    result = "Index 1: " + i + "; Index 2: " + j;
                    return result;
                } else {
                    result = "Es gibt keinen gemeinsamen Teiler";
                }

            }

        }

        return result;
    }

    public String aufgabe3(String matrikelnummer) {
        String result = "";
        int[] intArray = toArray(matrikelnummer);
        for (int i = 0; i < intArray.length; i++) {
            if (i % 2 == 1) {
                if (intArray[i] != 0) {
                    char temp = (char) (intArray[i] + 96);
                    result += temp;
                } else {
                    char temp = (char) (intArray[i] + 106);
                    result += temp;
                }
            } else {
                result += intArray[i];
            }
        }

        return result;

    }

    public String aufgabe4(String matrikelnummer) {
        String result;
        int[] intArray = toArray(matrikelnummer);
        int num = quersummeBerechnen(intArray);
        result = Integer.toBinaryString(num);
        return result;

    }

    public String aufgabe5(String matrikelnummer) {
        String result = "";
        int num;
        int[] intArray = toArray(matrikelnummer);
        intArray = sortArray(intArray);

        for (int m = 0; m < intArray.length; m++) {
            num = intArray[m];
            if (!isPrime(num)) {
                result += num;
            }
        }
        return result;

    }

    public int ggt(int a, int b) {
        int result;
        if (a <= 1) {
            return a;
        } else if (b <= 1) {
            return b;
        } else {
            while (b != 0) {
                if (a > b) {
                    a = a - b;
                } else {
                    b = b - a;
                }
            }
            result = a;
        }
        return result;
    }

    public String aufgabe6(String matrikelnummer) {
        String result = "";
        int num;
        int[] intArray = toArray(matrikelnummer);
        for (int m = 0; m < intArray.length; m++) {
            num = intArray[m];
            if (isPrime(num)) {
                result += num;
            }
        }
        return result;
    }

    public int[] sortArray(int[] matrNr) {
        for (int k = 1; k < matrNr.length; k++) {
            for (int l = 0; l < matrNr.length - k; l++) {
                if (matrNr[l] > matrNr[l + 1]) {
                    int temp = matrNr[l];
                    matrNr[l] = matrNr[l + 1];
                    matrNr[l + 1] = temp;
                }
            }
        }
        return matrNr;
    }

    public int alternierendeQuersumme(int[] matrNr) {
        int altQuer = 0;
        int add = 0;
        int sub = 0;
        for (int i = 0; i < matrNr.length; i++) {
            if (i % 2 != 0) {
                add += matrNr[i];
            } else {
                sub += matrNr[i];
            }
        }
        altQuer = add - sub;
        return altQuer;
    }

    public int quersummeBerechnen(int[] matrNr) {
        int result = 0;
        for (int i = 0; i < matrNr.length; i++) {
            result += matrNr[i];
        }
        return result;
    }

    public int[] toArray(String matrNr) {
        int[] matNrInt = new int[matrNr.length()];
        for (int i = 0; i < matrNr.length(); i++) {
            matNrInt[i] = matrNr.charAt(i) - '0';
        }
        return matNrInt;
    }

    public boolean isPrime(int num) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sSelected = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, sSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}