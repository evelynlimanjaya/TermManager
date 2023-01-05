package com.project.termmanager.UI;

import static com.project.termmanager.UI.HomeScreen.numCourseAlert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    Spinner statusSpinner;
    String status;
    EditText courseInstName;
    String instName;
    EditText courseInstPhone;
    String instPhone;
    EditText courseInstEmail;
    String instEmail;
    EditText courseNote;
    String addNote;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;

    int courseID;
    int termID;
    Repository repo;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("id", -1);
        courseTitle = findViewById(R.id.editTextCourseTitle);
        courseStartDate = findViewById(R.id.editTextCourseStartDate);
        courseEndDate = findViewById(R.id.editTextCourseEndDate);
        statusSpinner = findViewById(R.id.spinnerCourseStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(0);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] statusArray = getResources().getStringArray(R.array.course_status_array);
                Toast.makeText(CourseEdit.this, statusArray[i], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        courseInstName = findViewById(R.id.editTextCourseInstName);
        courseInstPhone = findViewById(R.id.editTextCourseInstPhone);
        courseInstEmail = findViewById(R.id.editTextCourseInstEmail);
        courseNote = findViewById(R.id.editTextTextMultiLineCourseNote);

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
            String[] statusArray = getResources().getStringArray(R.array.course_status_array);
            int index = java.util.Arrays.asList(statusArray).indexOf(status);
            statusSpinner.setSelection(index);

            instName = selectedCourse.getInstructorName();
            courseInstName.setText(instName);

            instPhone = selectedCourse.getInstructorPhone();
            courseInstPhone.setText(instPhone);

            instEmail = selectedCourse.getInstructorEmail();
            courseInstEmail.setText(instEmail);

            addNote = selectedCourse.getNote();
            courseNote.setText(addNote);
        }

        System.out.println("Course ID " + courseID + name);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAssessments);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        int newCourseID = 0;
        if(courseID == -1 && repo.getAllCourses().size() == 0){
            newCourseID = 1;
        } else if(courseID == -1){
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
            newCourseID = newID;
        } else if (courseID != -1) {
            newCourseID = courseID;
        }
        for(Assessment a:repo.getAllAssessments()){
            if(a.getCourseID() == newCourseID){
                filteredAssessments.add(a);
            }
        }
        adapter.setAssessments(filteredAssessments, repo.getAllCourses());

        System.out.println("Term id " + termID);
    }

    private void updateLabelStart(){
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        courseStartDate.setText(sdf.format(calendarStart.getTime()));

    }

    private void updateLabelEnd(){
//        String myFormat = "MM/dd/yy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        courseEndDate.setText(sdf.format(calendarEnd.getTime()));

    }

    public void saveButton(View view) {
        Course course;
        if(courseID == -1 && repo.getAllCourses().size() == 0){
            course = new Course(1, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
            repo.insertCourse(course);
        } else if(courseID == -1){
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
            course = new Course(newID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
            repo.insertCourse(course);
        } else if(courseID != -1){
            course = new Course(courseID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
            repo.updateCourse(course);
        }

    }

    public void deleteButton(View view) {
        Course course = new Course(courseID, name, start,end, status, instName, instPhone, instEmail, addNote, termID);
        repo.deleteCourse(course);

    }

    public void addButton(View view) {
        Intent intent = new Intent(CourseEdit.this, AssessmentEdit.class);
        if(courseID == -1 && repo.getAllCourses().size() == 0){
            intent.putExtra("courseID", 1);
        } else if(courseID == -1){
            int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
            intent.putExtra("courseID", newID);

        } else if(courseID != -1){
            intent.putExtra("courseID", courseID);
        }
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_courseedit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveCourseMenu:
                Course course;
                if(courseID == -1 && repo.getAllCourses().size() == 0){
                    course = new Course(1, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
                    repo.insertCourse(course);
                } else if(courseID == -1){
                    int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
                    course = new Course(newID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
                    repo.insertCourse(course);
                } else if(courseID != -1){
                    course = new Course(courseID, courseTitle.getText().toString(), courseStartDate.getText().toString(), courseEndDate.getText().toString(), statusSpinner.getSelectedItem().toString(), courseInstName.getText().toString(), courseInstPhone.getText().toString(), courseInstEmail.getText().toString(), courseNote.getText().toString(), termID);
                    repo.updateCourse(course);
                }
                return true;
            case R.id.deleteCourseMenu:
                course = new Course(courseID, name, start,end, status, instName, instPhone, instEmail, addNote, termID);
                repo.deleteCourse(course);
                return true;
            case R.id.refreshCourseEdit:
                RecyclerView recyclerView = findViewById(R.id.recyclerViewAssessments);
                final AssessmentAdapter adapter = new AssessmentAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Assessment> filteredAssessments = new ArrayList<>();
                int newCourseID = 0;
                if(courseID == -1 && repo.getAllCourses().size() == 0){
                    newCourseID = 1;
                } else if(courseID == -1){
                    int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
                    newCourseID = newID;
                } else if (courseID != -1) {
                    newCourseID = courseID;
                }
                for(Assessment a:repo.getAllAssessments()){
                    if(a.getCourseID() == newCourseID){
                        filteredAssessments.add(a);
                    }
                }
                adapter.setAssessments(filteredAssessments, repo.getAllCourses());
                return true;
            case R.id.shareNoteCourseEdit:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Note from " + courseTitle.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.alertStartCourseEdit:
                String startAlertDateStr = courseStartDate.getText().toString();
                Date startAlertDate = null;
                try {
                    startAlertDate = sdf.parse(startAlertDateStr);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long startDateTrigger = startAlertDate.getTime();
                Intent startDateAlertIntent = new Intent(CourseEdit.this, CourseAlertReceiver.class);
                startDateAlertIntent.putExtra("courseKey", "Course title: " + courseTitle.getText().toString() + " starts today!!!");
                PendingIntent startDateSender = PendingIntent.getBroadcast(CourseEdit.this, numCourseAlert++, startDateAlertIntent, 0);

                AlarmManager startAm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAm.set(AlarmManager.RTC_WAKEUP, startDateTrigger, startDateSender);
                return true;
            case R.id.alertEndCourseEdit:
                String endAlertDateStr = courseEndDate.getText().toString();
                Date endAlertDate = null;
                try {
                    endAlertDate = sdf.parse(endAlertDateStr);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long endDateTrigger = endAlertDate.getTime();
                Intent endDateAlertIntent = new Intent(CourseEdit.this, CourseAlertReceiver.class);
                endDateAlertIntent.putExtra("courseKey", "Course title: " + courseTitle.getText().toString() + " ends today!!!");
                PendingIntent endDateSender = PendingIntent.getBroadcast(CourseEdit.this, numCourseAlert++, endDateAlertIntent, 0);

                AlarmManager endAm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAm.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endDateSender);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}