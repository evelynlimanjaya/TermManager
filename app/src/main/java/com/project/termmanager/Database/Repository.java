package com.project.termmanager.Database;

import android.app.Application;

import com.project.termmanager.DAO.AssessmentDAO;
import com.project.termmanager.DAO.CourseDAO;
import com.project.termmanager.DAO.TermDAO;
import com.project.termmanager.Entity.Assessment;
import com.project.termmanager.Entity.Course;
import com.project.termmanager.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssesmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssesments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        TermManagerDatabaseBuilder db = TermManagerDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssesmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms(){
        databaseExecutors.execute(()->{
            mAllTerms = mTermDAO.allTermsResult();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insertTerm(Term term){
        databaseExecutors.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateTerm(Term term){
        databaseExecutors.execute(()->{
            mTermDAO.update(term);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteTerm(Term term){
        databaseExecutors.execute(()->{
            mTermDAO.delete(term);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
