package com.xkpt;

public class Material {
    private String subjectCategory;
    private String firstSubject;


    public Material() {
    }

    public Material(String subjectCategory, String firstSubject) {
        this.subjectCategory = subjectCategory;
        this.firstSubject = firstSubject;
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
}
