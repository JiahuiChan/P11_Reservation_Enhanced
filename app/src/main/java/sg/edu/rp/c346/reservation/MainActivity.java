package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    EditText etDate;
    EditText etTime;
    Button btReserve;
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Reservation");

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "Smoking";
                } else {
                    isSmoke = "Non-smoking";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Your Reservation Details:");
                myBuilder.setMessage("Name: " + etName.getText().toString() + "\nContact Number: " + etTelephone.getText().toString() +  "\nSmoking:" + isSmoke + "\nSize: " + etSize.getText().toString() + "\nDate: " + etDate.getText().toString() + "\nTime: " + etTime.getText().toString());

                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM", null);
                myBuilder.setNeutralButton("CANCEL" , null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.putString("name", etName.getText().toString());
                editPrefs.putString("phone", etTelephone.getText().toString());
                editPrefs.putString("size", etSize.getText().toString());

                if(checkBox.isChecked()){
                    editPrefs.putBoolean("cb", true);
                }else{
                    editPrefs.putBoolean("cb",false);
                }

                editPrefs.putString("date", etDate.getText().toString());
                editPrefs.putString("time", etTime.getText().toString());
                editPrefs.commit();

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etTelephone.setText(null);
                etSize.setText(null);
                checkBox.setChecked(false);
                etDate.setText(null);
                etTime.setText(null);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.putString("name", etName.getText().toString());
                editPrefs.putString("phone", etTelephone.getText().toString());
                editPrefs.putString("size", etSize.getText().toString());

                if(checkBox.isChecked()){
                    editPrefs.putBoolean("cb", true);
                }else{
                    editPrefs.putBoolean("cb",false);
                }
                editPrefs.putString("date", etDate.getText().toString());
                editPrefs.putString("time", etTime.getText().toString());
                editPrefs.commit();

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the Listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };

                //Create the Date Picker Dialog
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener, year, month, day);

                myDateDialog.show();

            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Create the Listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                //Create the Date Picker Dialog
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener, hour, minute, true);
                myTimeDialog.show();
            }
        });
    }
        @Override
        protected void onResume() { //To retrieve the saved data when the Activity is resumed (fully visible)
            super.onResume();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

            etName.setText(prefs.getString("name",""));
            etTelephone.setText(prefs.getString("phone",""));
            etSize.setText(prefs.getString("size", ""));

            boolean isSmoking = prefs.getBoolean("cb" , false);
            checkBox.setChecked(isSmoking);

            etDate.setText(prefs.getString("date",""));
            etTime.setText(prefs.getString("time",""));
        }

    @Override
    protected void onPause() { //To save the persistent data when the app is no longer (fully) visible
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editPrefs = prefs.edit();

        editPrefs.putString("name",etName.getText().toString());
        editPrefs.putString("phone",etTelephone.getText().toString());
        editPrefs.putString("size", etSize.getText().toString());

        if(checkBox.isChecked()){
            editPrefs.putBoolean("cb", true);
        }else{
            editPrefs.putBoolean("cb",false);
        }
        editPrefs.putString("date", etDate.getText().toString());
        editPrefs.putString("time" , etTime.getText().toString());
        editPrefs.commit();
    }
    }
