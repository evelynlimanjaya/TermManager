package com.project.termmanager.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "assessments_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;

    private String assesmentName;
    private String startDate;
    private String endDate;
    private String type;
    private int courseID;

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssesmentName() {
        return assesmentName;
    }

    public void setAssesmentName(String assesmentName) {
        this.assesmentName = assesmentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", assesmentName='" + assesmentName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                ", courseID=" + courseID +
                '}';
    }

    public Assessment(int assessmentID, String assesmentName, String startDate, String endDate, String type, int courseID) {
        this.assessmentID = assessmentID;
        this.assesmentName = assesmentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.courseID = courseID;
    }



}
