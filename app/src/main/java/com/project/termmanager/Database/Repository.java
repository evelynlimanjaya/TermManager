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
    private Term filteredTerm;
    private List<Course> mAllCourses;
    private Course filteredCourse;
    private List<Course> mAllCoursesById;
    private List<Assessment> mAllAssesments;
    private  Assessment filteredAssessment;

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

    public Term getTermById(int id){
        databaseExecutors.execute(()->{
            filteredTerm = mTermDAO.termById(id);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return filteredTerm;
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

    public List<Course> getAllCourses(){
        databaseExecutors.execute(()->{
            mAllCourses = mCourseDAO.allCoursesResult();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public Course getCourseById(int id){
        databaseExecutors.execute(()->{
            filteredCourse = mCourseDAO.courseById(id);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return filteredCourse;
    }

    public void insertCourse(Course course){
        databaseExecutors.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course){
        databaseExecutors.execute(()->{
            mCourseDAO.update(course);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteCourse(Course course){
        databaseExecutors.execute(()->{
            mCourseDAO.delete(course);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutors.execute(()->{
            mAllAssesments = mAssesmentDAO.allAssessmentsResult();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssesments;
    }

    public Assessment getAssessmentById(int id){
        databaseExecutors.execute(()->{
            filteredAssessment = mAssesmentDAO.assessmentById(id);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return filteredAssessment;
    }

    public void insertAssessment(Assessment assessment){
        databaseExecutors.execute(() -> {
            mAssesmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateAssessment(Assessment assessment){
        databaseExecutors.execute(()->{
            mAssesmentDAO.update(assessment);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAssessment(Assessment assessment){
        databaseExecutors.execute(()->{
            mAssesmentDAO.delete(assessment);
        });

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
