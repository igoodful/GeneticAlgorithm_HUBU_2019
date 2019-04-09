package com.xkpt;

public class Expert {
    public int expertNumber;
    public String subjectCategory;
    public String firstSubject;
    public String grade;
    public int compactness;





    public Expert() {
    }

    public Expert(int expertNumber, String subjectCategory, String firstSubject, String grade) {
        this.expertNumber = expertNumber;
        this.subjectCategory = subjectCategory;
        this.firstSubject = firstSubject;
        this.grade = grade;
    }

    public int getExpertNumber() {
        return expertNumber;
    }

    public void setExpertNumber(int expertNumber) {
        this.expertNumber = expertNumber;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public String getFirstSubject() {
        return firstSubject;
    }

    public void setFirstSubject(String firstSubject) {
        this.firstSubject = firstSubject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
