package com.forscher.filehandlingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SplashFile extends AppCompatActivity {


    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_file);

        layout = findViewById(R.id.layoutob);

        Random random = new Random();
        int n = 0;
        try {
            FileInputStream fin = openFileInput("RandomNumber");
            int i = fin.read();
            do {
                n = random.nextInt(3);
            }while(i==n);
            fin.close();
        }catch(IOException e) {
            n = random.nextInt(3);
        }

        switch(n) {
            case 0:
                layout.setBackgroundResource(R.drawable.Image1);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.Image2);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.Image3);
                break;
                default:
        }

        try {
            FileOutputStream fos = openFileOutput("RandomNumber", MODE_PRIVATE);
            fos.write(n);
            fos.close();
        }catch(IOException e) {

        }
    }
}