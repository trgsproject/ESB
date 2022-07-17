package com.example.esa;

public class Schedule  {

    String id, MRN, PatientName, Category, SurgeryDate, OT, CallTime, ArrivalTimeToOT, SurgeryStartTime, SurgeryEndTime, PHUStartTime, PHUidBed, PACUStartTime, PACUidBed;

    public Schedule(String id, String MRN, String patientName, String category, String surgeryDate, String OT, String callTime, String arrivalTimeToOT, String surgeryStartTime, String surgeryEndTime, String PHUStartTime, String PHUidBed, String PACUStartTime, String PACUidBed) {
        this.id = id;
        this.MRN = MRN;
        PatientName = patientName;
        Category = category;
        SurgeryDate = surgeryDate;
        this.OT = OT;
        CallTime = callTime;
        ArrivalTimeToOT = arrivalTimeToOT;
        SurgeryStartTime = surgeryStartTime;
        SurgeryEndTime = surgeryEndTime;
        this.PHUStartTime = PHUStartTime;
        this.PHUidBed = PHUidBed;
        this.PACUStartTime = PACUStartTime;
        this.PACUidBed = PACUidBed;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSurgeryDate() {
        return SurgeryDate;
    }

    public void setSurgeryDate(String surgeryDate) {
        SurgeryDate = surgeryDate;
    }

    public String getOT() {
        return OT;
    }

    public void setOT(String OT) {
        this.OT = OT;
    }

    public String getCallTime() {
        return CallTime;
    }

    public void setCallTime(String callTime) {
        CallTime = callTime;
    }

    public String getArrivalTimeToOT() {
        return ArrivalTimeToOT;
    }

    public void setArrivalTimeToOT(String arrivalTimeToOT) {
        ArrivalTimeToOT = arrivalTimeToOT;
    }

    public String getSurgeryStartTime() {
        return SurgeryStartTime;
    }

    public void setSurgeryStartTime(String surgeryStartTime) {
        SurgeryStartTime = surgeryStartTime;
    }

    public String getSurgeryEndTime() {
        return SurgeryEndTime;
    }

    public void setSurgeryEndTime(String surgeryEndTime) {
        SurgeryEndTime = surgeryEndTime;
    }

    public String getPHUStartTime() {
        return PHUStartTime;
    }

    public void setPHUStartTime(String PHUStartTime) {
        this.PHUStartTime = PHUStartTime;
    }

    public String getPHUidBed() {
        return PHUidBed;
    }

    public void setPHUidBed(String PHUidBed) {
        this.PHUidBed = PHUidBed;
    }

    public String getPACUStartTime() {
        return PACUStartTime;
    }

    public void setPACUStartTime(String PACUStartTime) {
        this.PACUStartTime = PACUStartTime;
    }

    public String getPACUidBed() {
        return PACUidBed;
    }

    public void setPACUidBed(String PACUidBed) {
        this.PACUidBed = PACUidBed;
    }
}