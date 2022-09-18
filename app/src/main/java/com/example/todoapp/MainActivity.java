package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> tasksList = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private EditText input;
    private Button addButton;
    SharedPreferences sharedPreferences;

    public void addItem(View view) {

        EditText input= findViewById(R.id.inputEditText);
        String itemText= input.getText().toString();

        if(!(itemText.equals(""))){
           tasksList.add(itemText);
            input.setText("");

            SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(MainActivity.tasksList);

            sharedPreferences.edit().putString("taskLists", String.valueOf(set)).apply();

            arrayAdapter.notifyDataSetChanged();



        }else{
            Toast.makeText(this, "Hey, type some text in it....", Toast.LENGTH_LONG).show();
        }

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

       listView = (ListView)findViewById(R.id.taskListView);
        addButton = findViewById(R.id.addItem);


       sharedPreferences= getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
       HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("tasksList", null);

       if(set == null){
           tasksList.add("NO Prior Task!");
       }else{
           tasksList = new ArrayList(set);
       }



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addItem(view);
            }
        });


        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, tasksList);

        listView.setAdapter(arrayAdapter);

        setUpListView();


    }

    private void setUpListView() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // view input aayega, usko convert krna parega CHECKED TEXT VIEW

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if(checkedTextView.isChecked()){

                    tasksList.remove(i);


                    SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<>(MainActivity.tasksList);

                    sharedPreferences.edit().putString("tasksList", String.valueOf(set)).apply();

                    arrayAdapter.notifyDataSetChanged();

                }



            }
        });
    }
}