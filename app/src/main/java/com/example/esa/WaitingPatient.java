package com.example.esa;

public class WaitingPatient {

    String id, MRN, PatientName, AnesthetistID, AnesthetistName, ClinicalDescriptor, Category, Needed_by_patient, ArrivalTimeToSurgeon, Status;

    public WaitingPatient(String id, String MRN, String patientName, String anesthetistID, String anesthetistName, String clinicalDescriptor, String category, String needed_by_patient, String arrivalTimeToSurgeon, String status) {
        this.id = id;
        this.MRN = MRN;
        PatientName = patientName;
        AnesthetistID = anesthetistID;
        AnesthetistName = anesthetistName;
        ClinicalDescriptor = clinicalDescriptor;
        Category = category;
        Needed_by_patient = needed_by_patient;
        ArrivalTimeToSurgeon = arrivalTimeToSurgeon;
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMRN() {
        return MRN;
    }

    public void setMRN(String MRN) {
        this.MRN = MRN;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getAnesthetistID() {
        return AnesthetistID;
    }

    public void setAnesthetistID(String anesthetistID) {
        AnesthetistID = anesthetistID;
    }

    public String getAnesthetistName() {
        return AnesthetistName;
    }

    public void setAnesthetistName(String anesthetistName) {
        AnesthetistName = anesthetistName;
    }

    public String getClinicalDescriptor() {
        return ClinicalDescriptor;
    }

    public void setClinicalDescriptor(String clinicalDescriptor) {
        ClinicalDescriptor = clinicalDescriptor;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getNeeded_by_patient() {
        return Needed_by_patient;
    }

    public void setNeeded_by_patient(String needed_by_patient) {
        Needed_by_patient = needed_by_patient;
    }

    public String getArrivalTimeToSurgeon() {
        return ArrivalTimeToSurgeon;
    }

    public void setArrivalTimeToSurgeon(String arrivalTimeToSurgeon) {
        ArrivalTimeToSurgeon = arrivalTimeToSurgeon;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
