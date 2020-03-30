package com.forscher.filehandlingdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    FileOutputStream fos;
    boolean canSave = false;
    TextView txt;
    File file, file1;
    ListView fileList;
    EditText txt_Data;

    /*
    public void readInternalData(View view) {
        try {
            FileInputStream fin = openFileInput("MyAccountDetails");
            fin.close();
        }catch(IOException e){

        }
    }
*/
    public void readData(View view) {
        try {
            FileInputStream fin = new FileInputStream(file.getAbsolutePath() + "/SD/fastnote/AccDetails.txt");
            int c=fin.read();
            String data="";
            while(c!=-1) {
                data += (char)c;
                c = fin.read();
            }
            fin.close();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            txt_Data.setText(data);
        }catch(IOException e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData(View view) {
        if(canSave) {
        try {
            fos = new FileOutputStream(file.getAbsolutePath()+"/Android/FileDemo/FileHandling.txt");
            String data = txt_Data.getText().toString().trim();

            for(int i=0;i<data.length();i++) {
                fos.write(data.charAt(i));
            }
            fos.close();
            Toast.makeText(this, "Data Written in File", Toast.LENGTH_SHORT).show();
        }catch(IOException e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        }
        else
        {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Data = findViewById(R.id.txt);
        fileList = findViewById(R.id.filesList);
        file = Environment.getExternalStorageDirectory();
        txt = findViewById(R.id.txt_files);
        txt.setText(file.getAbsolutePath());


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        else {
            canSave = true;
            makeRequiredDirectory();
            showContents();
        }
    }

    private void showContents() {
        String[] files = file.list();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,files);
        fileList.setAdapter(adapter);
    }

    private void makeRequiredDirectory() {
        file1 = new File(file.getAbsolutePath()+"/Android/FileDemo");
        if(!file1.exists())
            Toast.makeText(this, ""+file1.mkdirs(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Directory already exists !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                canSave = true;
                makeRequiredDirectory();
                showContents();
            }
            else
            {
                canSave = false;
                Toast.makeText(this, "Could not create directory, Permission Denied !!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
