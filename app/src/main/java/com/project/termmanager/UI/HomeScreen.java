package com.project.termmanager.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.termmanager.Database.Repository;
import com.project.termmanager.Entity.Term;
import com.project.termmanager.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void allTerms(View view) {
        Intent intent = new Intent(HomeScreen.this, com.project.termmanager.UI.Terms.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
//        Term term = new Term(1, "coba", "coba", "coba");
//        repo.insertTerm(term);
    }
}