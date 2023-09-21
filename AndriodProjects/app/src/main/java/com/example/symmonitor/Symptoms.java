package com.example.symmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Symptoms extends AppCompatActivity {
    Spinner symptoms_dropdown;
    RatingBar ratingBar;
    Button upload_symptoms;
    String selSymptom = "";
    float selRating =0;
    String user_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            user_name = "";
        }else{
            user_name = extras.getString("name");
        }
        setContentView(R.layout.activity_symptoms);
        symptoms_dropdown = findViewById(R.id.symtpoms_dropdown);
        ratingBar = findViewById(R.id.symptom_level_rating);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.symptoms));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        symptoms_dropdown.setAdapter(spinnerAdapter);
        uploadSymptoms();
        onChangeSymptom();
    }
    public void onChangeSymptom(){
        symptoms_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ratingBar.setRating(0);
                selSymptom = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void uploadSymptoms(){
        upload_symptoms = findViewById(R.id.upload_symptoms);
        upload_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selRating = ratingBar.getRating();
                //Toast toast =Toast.makeText(getApplicationContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT);
                //toast.show();
                //Toast.makeText(getApplicationContext(),"buttonCLicked",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),selSymptom+'-'+selRating, Toast.LENGTH_LONG).show();
              Readings currentReading = getReading();
                DataBaseHelper databaseAction = new DataBaseHelper(getApplicationContext());
                //Toast.makeText(getApplicationContext(), currentReading.HEAD_ACHE.toString(), Toast.LENGTH_SHORT).show();
               if(databaseAction.onInsert(currentReading)== true){
                    Toast.makeText(getApplicationContext(), "Data updated successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public Readings getReading(){
        switch(selSymptom){
            case "Nausea":
                Readings readings=new Readings("Nikhil",0.0,0.0, (double) selRating,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
            return readings;
            case "RESP_RATE":
                Readings readings2=new Readings("Nikhil",0.0,0.0, (double) selRating,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
                return readings2;
            case "HEART_RATE":
                Readings readings3=new Readings("Nikhil",0.0,(double) selRating,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
                return readings3;
            case "Headache":
                Readings readings4=new Readings("Nikhil",0.0,0.0,0.0,(double) selRating,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
                return readings4;
                case "diarrhea":
                    Readings readings5=new Readings("Nikhil",0.0,0.0, 0.0,0.0,(double) selRating,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
                return readings5;
            case "Soar Throat":
                Readings readings51=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,(double) selRating,0.0,0.0,0.0,0.0,0.0,0.0);
                return readings51;
            case "Fever":
                Readings readings6=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,(double) selRating,0.0,0.0,0.0,0.0,0.0);
                return readings6;
            case "Muscle Ache":
                Readings readings7=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,0.0,(double) selRating,0.0,0.0,0.0,0.0);
                return readings7;
                case "Loss of Smell":
                    Readings readings8=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,0.0,0.0,(double) selRating,0.0,0.0,0.0);
                return readings8;
            case "Cough":
                Readings readings9=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,(double) selRating,0.0,0.0);
                return readings9;
            case "Feeling Tried":
                Readings readings91=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,(double) selRating);
                return readings91;
                case "Shortness of Breath":
                Readings readings10=new Readings("Nikhil",0.0,0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,(double) selRating,0.0);
                return readings10;
            default:
                Readings reading1 = new Readings("Nikhil",0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
            return reading1;
        }
    }

}