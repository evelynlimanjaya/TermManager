package com.project.termmanager.UI;

import static com.project.termmanager.UI.HomeScreen.numCourseAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentEdit extends AppCompatActivity {
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    EditText assessmentTitle;
    String name;
    EditText assessmentStartDate;
    String start;
    EditText assessmentEndDate;
    String end;
    RadioGroup assessmentRadioGroup;
    RadioButton assessmentTypePerformance;
    RadioButton assessmentTypeObjective;
    String type;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;

    int assessmentID;
    int courseID;
    Repository repo;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_edit);
        repo = new Repository(getApplication());
        assessmentID = getIntent().getIntExtra("id", -1);
        assessmentTitle = findViewById(R.id.editTextAssessmentTitle);
        assessmentStartDate = findViewById(R.id.editTextAssessmentStartDate);
        assessmentEndDate = findViewById(R.id.editTextAssessmentEndDate);
        assessmentRadioGroup = findViewById(R.id.radioButtonGroup);
        assessmentTypePerformance = findViewById(R.id.radioButtonPerformance);
        assessmentTypeObjective = findViewById(R.id.radioButtonObjective);
        courseID = getIntent().getIntExtra("courseID", -1);
        Assessment selectedAssessment = repo.getAssessmentById(assessmentID);

        if(assessmentID != -1){
            name = selectedAssessment.getAssesmentName();
            assessmentTitle.setText(name);

            start = selectedAssessment.getStartDate();
            assessmentStartDate.setText(start);

            end = selectedAssessment.getEndDate();
            assessmentEndDate.setText(end);

            type = selectedAssessment.getType();
            if(type.equals("performance")){
                assessmentTypePerformance.setChecked(true);
            } else if(type.equals("objective")){
                assessmentTypeObjective.setChecked(true);
            }
        }

        startDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        assessmentStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(AssessmentEdit.this, startDP, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(AssessmentEdit.this, endDP, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart(){
        assessmentStartDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd(){
        assessmentEndDate.setText(sdf.format(calendarEnd.getTime()));
    }

    public void saveButton(View view) {
        Assessment assessment;
        String newType = "N/A";
        if(assessmentTypePerformance.isChecked()){
            newType = "performance";
        } else if(assessmentTypeObjective.isChecked()){
            newType = "objective";
        }

        if(assessmentID == -1 && repo.getAllAssessments().size() == 0){
            assessment = new Assessment(1, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
            repo.insertAssessment(assessment);
        } else if(assessmentID == -1){
            int newID = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssessmentID() + 1;
            assessment = new Assessment(newID, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
            repo.insertAssessment(assessment);
        } else if(assessmentID != -1){
            assessment = new Assessment(assessmentID, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
            repo.updateAssessment(assessment);
        }

    }

    public void deleteButton(View view) {
        Assessment assessment = new Assessment(assessmentID, name, start,end, type, courseID);
        repo.deleteAssessment(assessment);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessmentedit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveAssessmentMenu:
                Assessment assessment;
                String newType = "N/A";
                if(assessmentTypePerformance.isChecked()){
                    newType = "performance";
                } else if(assessmentTypeObjective.isChecked()){
                    newType = "objective";
                }

                if(assessmentID == -1 && repo.getAllAssessments().size() == 0){
                    assessment = new Assessment(1, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
                    repo.insertAssessment(assessment);
                } else if(assessmentID == -1){
                    int newID = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssessmentID() + 1;
                    assessment = new Assessment(newID, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
                    repo.insertAssessment(assessment);
                } else if(assessmentID != -1){
                    assessment = new Assessment(assessmentID, assessmentTitle.getText().toString(), assessmentStartDate.getText().toString(), assessmentEndDate.getText().toString(), newType, courseID);
                    repo.updateAssessment(assessment);
                }
                return true;
            case R.id.deleteAssessmentMenu:
                assessment = new Assessment(assessmentID, name, start,end, type, courseID);
                repo.deleteAssessment(assessment);
            case R.id.refreshAssessmentMenu:
                Assessment selectedAssessment = repo.getAssessmentById(assessmentID);

                if(assessmentID != -1){
                    name = selectedAssessment.getAssesmentName();
                    assessmentTitle.setText(name);

                    start = selectedAssessment.getStartDate();
                    assessmentStartDate.setText(start);

                    end = selectedAssessment.getEndDate();
                    assessmentEndDate.setText(end);

                    type = selectedAssessment.getType();
                    if(type.equals("performance")){
                        assessmentTypePerformance.setSelected(true);
                    } else if(type.equals("objective")){
                        assessmentTypeObjective.setSelected(true);
                    }
                }
                return true;
            case R.id.alertStartAssessmentEdit:
                String startAlertDateStr = assessmentStartDate.getText().toString();
                Date startAlertDate = null;
                try {
                    startAlertDate = sdf.parse(startAlertDateStr);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long startDateTrigger = startAlertDate.getTime();
                Intent startDateAlertIntent = new Intent(AssessmentEdit.this, AssessmentAlertReceiver.class);
                startDateAlertIntent.putExtra("assessmentKey", "Assessment title: " + assessmentTitle.getText().toString() + " starts today!!!");
                PendingIntent startDateSender = PendingIntent.getBroadcast(AssessmentEdit.this, numCourseAlert++, startDateAlertIntent, 0);

                AlarmManager startAm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAm.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);
                return true;
            case R.id.alertEndAssessmentEdit:
                String endAlertDateStr = assessmentEndDate.getText().toString();
                Date endAlertDate = null;
                try {
                    endAlertDate = sdf.parse(endAlertDateStr);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long endDateTrigger = endAlertDate.getTime();
                Intent endDateAlertIntent = new Intent(AssessmentEdit.this, AssessmentAlertReceiver.class);
                endDateAlertIntent.putExtra("assessmentKey", "Assessment title: " + assessmentTitle.getText().toString() + " ends today!!!");
                PendingIntent endDateSender = PendingIntent.getBroadcast(AssessmentEdit.this, numCourseAlert++, endDateAlertIntent, 0);

                AlarmManager endAm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAm.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}