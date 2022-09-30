package by.bstu.bdlab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String fileName = "Contacts.txt";
    public File PrivateFile;
    public File PublicFile;
    public static File PrivateFileLink;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public boolean isPublicFileExists(){
        File publicFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        boolean re = false;
        if(publicFile.exists()){
            //Log.d("Log4", "Файл " + fileName + " существует");
            //Toast toast = Toast.makeText(this, "Файл " + fileName + " существует", Toast.LENGTH_LONG);
            //toast.show();
            re = true;
            PublicFile = publicFile;
        }
        else{
            //Log.d("Log4", "Файл " + fileName + " не найден");
            //Toast toast = Toast.makeText(this, "Файл " + fileName + " не найден и будет создан", Toast.LENGTH_LONG);
            //toast.show();
            re = false;
            try{
                publicFile.createNewFile();
                //Log.d("Log4", "Файл " + fileName + " создан");
                PublicFile = publicFile;
            }
            catch (Exception ex){
                Log.d("Log6", "Файл " + fileName + " не создан");
            }
        }
        return re;
    }

    public boolean isPrivateFileExists(){
        File privateFile = new File(getFilesDir(), fileName);
        boolean re = false;
        if(privateFile.exists()){
            //Log.d("Log4", "Файл " + fileName + " существует");
            //Toast toast = Toast.makeText(this, "Файл " + fileName + " существует", Toast.LENGTH_LONG);
            //toast.show();
            re = true;
            PrivateFile = privateFile;
            PrivateFileLink = privateFile;
        }
        else{
            //Log.d("Log4", "Файл " + fileName + " не найден");
            //Toast toast = Toast.makeText(this, "Файл " + fileName + " не найден и будет создан", Toast.LENGTH_LONG);
            //toast.show();
            re = false;
            try{
                privateFile.createNewFile();
                //Log.d("Log4", "Файл " + fileName + " создан");
                PrivateFile = privateFile;
                PrivateFileLink = privateFile;
            }
            catch (Exception ex){
                Log.d("Log6", "Файл " + fileName + " не создан");
            }
        }
        return re;
    }

    public void WriteFile(String message){
        isPublicFileExists();
        isPrivateFileExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PublicFile, true))) {
            writer.write(message);
        }
        catch (IOException e) {
            //Hello world :D
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PrivateFile, true))) {
            writer.write(message);
        }
        catch (IOException e) {
            //Hello world :D
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
    }

    public void AddButtonClick(View view) {

        EditText surnameInput = findViewById(R.id.surnameInput);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText phoneInput = findViewById(R.id.phoneInput);
        DatePicker datePicker = findViewById(R.id.datePicker);

        String surname = String.valueOf(surnameInput.getText());
        String name = String.valueOf(nameInput.getText());
        String phone = String.valueOf(phoneInput.getText());
        String date = datePicker.getDayOfMonth() + "." + (datePicker.getMonth()+1) + "." + datePicker.getYear();
        String message = surname + "/" + name + "/" + phone + "/" + date + "\n";
        if(surname.isEmpty() || name.isEmpty() || phone.isEmpty()){
            Toast toast = Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            WriteFile(message);
            Toast toast = Toast.makeText(this, "Контакт добавлен!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void SearchButtonClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}