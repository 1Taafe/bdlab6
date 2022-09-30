package by.bstu.bdlab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //ListView personsView = findViewById(R.id.personsView);
        //PersonAdapter stateAdapter = new PersonAdapter(this, R.layout.list_item, persons);
        //personsView.setAdapter(stateAdapter);
    }

    ArrayList<Person> GetList(){
        ArrayList<Person> persons = new ArrayList<Person>();
        try(BufferedReader br = new BufferedReader(new FileReader(MainActivity.PrivateFileLink)))
        {
            String s;
            while((s=br.readLine())!=null){
                String[] parts = s.split("/");
                String surname = parts[0];
                String name = parts[1];
                String phone = parts[2];
                String date = parts[3];
                Person p = new Person(surname, name, phone, date);
                persons.add(p);
            }
        }
        catch(IOException ex){

        }
        return persons;
    }

    public void FindButtonClick(View view) {

        TextView nameText = findViewById(R.id.foundNameText);
        TextView phoneText = findViewById(R.id.foundPhoneText);
        TextView dateText = findViewById(R.id.foundDateText);

        EditText surnameInput = findViewById(R.id.surnameInput2);
        EditText nameInput = findViewById(R.id.nameInput2);
        if(String.valueOf(surnameInput.getText()).isEmpty() || String.valueOf(nameInput.getText()).isEmpty()){
            nameText.setText("Поля не заполнены");
            phoneText.setText("");
            dateText.setText("");
        }
        else{
            try{
                ArrayList<Person> allPersons = GetList();
                Person foundPerson = new Person();
                for (Person p: allPersons
                ) {
                    String surname = String.valueOf(surnameInput.getText());
                    String name = String.valueOf(nameInput.getText());
                    surname = surname.replaceAll("\\s","");
                    name = name.replaceAll("\\s","");
                    if(p.Name.equals(name) && p.Surname.equals(surname)){
                        foundPerson = p;
                    }
                }

                nameText.setText(foundPerson.Surname + " " + foundPerson.Name);
                phoneText.setText(foundPerson.Phone);
                dateText.setText("Дата рождения: " + foundPerson.DateOfBirth);
                if(foundPerson.Name.isEmpty()){
                    nameText.setText("Ничего не найдено");
                    phoneText.setText("");
                    dateText.setText("");
                }
            }
            catch (Exception ex){
                nameText.setText("Ничего не найдено");
                phoneText.setText("");
                dateText.setText("");
            }

        }


    }
}