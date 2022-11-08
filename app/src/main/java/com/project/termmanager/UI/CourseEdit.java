package com.project.termmanager.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseEdit extends AppCompatActivity {
    final Calendar calendarStart = Calendar.getInstance();
    final Calendar calendarEnd = Calendar.getInstance();
    EditText courseTitle;
    String name;
    EditText courseStartDate;
    String start;
    EditText courseEndDate;
    String end;
    EditText courseStatus;
    String status;
    EditText courseInstName;
    String instName;
    EditText courseInstPhone;
    String instPhone;
    EditText courseInstEmail;
    String instEmail;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;

    int courseID;
    int termID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("id", -1);
        courseTitle = findViewById(R.id.editTextCourseTitle);
        courseStartDate = findViewById(R.id.editTextCourseStartDate);
        courseEndDate = findViewById(R.id.editTextCourseEndDate);
        courseStatus = findViewById(R.id.editTextCourseStatus);
        courseInstName = findViewById(R.id.editTextCourseInstName);
        courseInstPhone = findViewById(R.id.editTextCourseInstPhone);
        courseInstEmail = findViewById(R.id.editTextCourseInstEmail);
        termID = getIntent().getIntExtra("termID", -1);
        Course selectedCourse = repo.getCourseById(courseID);

        if(courseID != -1){
            name = selectedCourse.getCourseName();
            courseTitle.setText(name);

            start = selectedCourse.getStartDate();
            courseStartDate.setText(start);

            end = selectedCourse.getEndDate();
            courseEndDate.setText(end);

            status = selectedCourse.getStatus();
            courseStatus.setText(status);

            instName = selectedCourse.getInstructorName();
            courseInstName.setText(instName);

            instPhone = selectedCourse.getInstructorPhone();
            courseInstPhone.setText(instPhone);

            instEmail = selectedCourse.getInstructorEmail();
            courseInstEmail.setText(instEmail);
        }

//        if(courseID != -1){
//            name = getIntent().getStringExtra("name");
//            courseTitle.setText(name);
//
//            start = getIntent().getStringExtra("startDate");
//            courseStartDate.setText(start);
//
//            end = getIntent().getStringExtra("endDate");
//            courseEndDate.setText(end);
//
//            status = getIntent().getStringExtra("status");
//            courseStatus.setText(status);
//
//            instName = getIntent().getStringExtra("instName");
//            courseInstName.setText(instName);
//
//            instPhone = getIntent().getStringExtra("instPhone");
//            courseInstPhone.setText(instPhone);
//
//            instEmail = getIntent().getStringExtra("instEmail");
//            courseInstEmail.setText(instEmail);
//        }

        startDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                updateLabelStart();
            }
        };

        endDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                updateLabelEnd();
            }
        };

        courseStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(CourseEdit.this, startDP, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(CourseEdit.this, endDP, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        courseStartDate.setText(sdf.format(calendarStart.getTime()));

    }

    private void updateLabelEnd(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        courseEndDate.setText(sdf.format(calendarEnd.getTime()));

    }

    public void saveButton(View view) {
        Course course;
        if(courseID == -1 && repo.getAllCourses().size() == 0){
            course = new Course(1, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), courseStatus.getText().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), termID);
            repo.insertCourse(course);
        } else if(courseID == -1){
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
            course = new Course(newID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), courseStatus.getText().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), termID);
            repo.insertCourse(course);
        } else if(courseID != -1){
            course = new Course(courseID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), courseStatus.getText().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), termID);
            repo.updateCourse(course);
        }

    }

    public void deleteButton(View view) {
        Course course = new Course(courseID, name, start,end, status, instName, instPhone, instEmail, termID);
        repo.deleteCourse(course);

    }

    public void addButton(View view) {
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//            case R.id.refreshCourse:jgh


        }
        return super.onOptionsItemSelected(item);
    }
}