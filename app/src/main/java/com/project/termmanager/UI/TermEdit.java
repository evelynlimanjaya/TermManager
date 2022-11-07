package com.project.termmanager.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Term;
import com.project.termmanager.R;

public class TermEdit extends AppCompatActivity {
    EditText termTitle;
    String name;
    EditText termStartDate;
    String start;
    EditText termEndDate;
    String end;

    int termID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        repo = new Repository(getApplication());
        termID = getIntent().getIntExtra("id", -1);
        termTitle = findViewById(R.id.editTextTermTitle);
        termStartDate = findViewById(R.id.editTextTermStartDate);
        termEndDate = findViewById(R.id.editTextTermEndDate);
        if(termID != -1){
            name = getIntent().getStringExtra("name");
            termTitle.setText(name);

            start = getIntent().getStringExtra("startDate");
            termStartDate.setText(start);

            end = getIntent().getStringExtra("endDate");
            termEndDate.setText(end);

            System.out.println(termID);
        }
    }

    public void saveButton(View view) {
        Term term;
        System.out.println(termTitle.getText().toString());
        if(termID == -1 && repo.getAllTerms().size() == 0){
            term = new Term(1, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.insertTerm(term);
        } else if(termID == -1){
            int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.insertTerm(term);
        } else if (termID != -1){
            term = new Term(termID, termTitle.getText().toString(), termStartDate.getText().toString(), termEndDate.getText().toString());
            repo.updateTerm(term);
        }

        Intent intent = new Intent(TermEdit.this, Terms.class);
        startActivity(intent);
    }

    public void deleteButton(View view) {
        Term term = new Term(termID, name, start, end);
        repo.deleteTerm(term);
    }
}