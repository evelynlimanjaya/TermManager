package com.project.termmanager.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.termmanager.DAO.AssessmentDAO;
import com.project.termmanager.DAO.CourseDAO;
import com.project.termmanager.DAO.TermDAO;
import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 7, exportSchema = false)
public abstract class TermManagerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static  volatile TermManagerDatabaseBuilder INSTANCE;

    static TermManagerDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TermManagerDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermManagerDatabaseBuilder.class, "termManagerDatabase").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;

    }
}
