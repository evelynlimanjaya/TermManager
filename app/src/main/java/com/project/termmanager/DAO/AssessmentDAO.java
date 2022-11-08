package com.project.termmanager.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.Entity.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments_table ORDER BY assessmentID ASC")
    List<Assessment> allAssessmentsResult();

    @Query("SELECT * FROM assessments_table WHERE assessmentID = :id")
    Assessment assessmentById(int id);
}
