package com.example.esa;

public class Patient {

    String id, MRN, Name, Age, Gender, Nationality, Race, Country;
    String Ward, SurgeonName, DiagnosisDescription, ProcedureName1, ProcedureName2, ProcedureName3;
    String SubSpecialty, LastMeal, InformAnesthetist, AnesthetistName, HighDependencyArea;
    String ArrivalTime, CreatedTime, ApprovedTime, Status, category_status;

    public Patient(String id, String MRN, String name, String age, String gender, String nationality, String race, String country, String ward, String surgeonName, String diagnosisDescription, String procedureName1, String procedureName2, String procedureName3, String subSpecialty, String lastMeal, String informAnesthetist, String anesthetistName, String highDependencyArea, String arrivalTime, String createdTime, String approvedTime, String status, String category_status) {
        this.id = id;
        this.MRN = MRN;
        Name = name;
        Age = age;
        Gender = gender;
        Nationality = nationality;
        Race = race;
        Country = country;
        Ward = ward;
        SurgeonName = surgeonName;
        DiagnosisDescription = diagnosisDescription;
        ProcedureName1 = procedureName1;
        ProcedureName2 = procedureName2;
        ProcedureName3 = procedureName3;
        SubSpecialty = subSpecialty;
        LastMeal = lastMeal;
        InformAnesthetist = informAnesthetist;
        AnesthetistName = anesthetistName;
        HighDependencyArea = highDependencyArea;
        ArrivalTime = arrivalTime;
        CreatedTime = createdTime;
        ApprovedTime = approvedTime;
        Status = status;
        this.category_status = category_status;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String race) {
        Race = race;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getSurgeonName() {
        return SurgeonName;
    }

    public void setSurgeonName(String surgeonName) {
        SurgeonName = surgeonName;
    }

    public String getDiagnosisDescription() {
        return DiagnosisDescription;
    }

    public void setDiagnosisDescription(String diagnosisDescription) {
        DiagnosisDescription = diagnosisDescription;
    }

    public String getProcedureName1() {
        return ProcedureName1;
    }

    public void setProcedureName1(String procedureName1) {
        ProcedureName1 = procedureName1;
    }

    public String getProcedureName2() {
        return ProcedureName2;
    }

    public void setProcedureName2(String procedureName2) {
        ProcedureName2 = procedureName2;
    }

    public String getProcedureName3() {
        return ProcedureName3;
    }

    public void setProcedureName3(String procedureName3) {
        ProcedureName3 = procedureName3;
    }

    public String getSubSpecialty() {
        return SubSpecialty;
    }

    public void setSubSpecialty(String subSpecialty) {
        SubSpecialty = subSpecialty;
    }

    public String getLastMeal() {
        return LastMeal;
    }

    public void setLastMeal(String lastMeal) {
        LastMeal = lastMeal;
    }

    public String getInformAnesthetist() {
        return InformAnesthetist;
    }

    public void setInformAnesthetist(String informAnesthetist) {
        InformAnesthetist = informAnesthetist;
    }

    public String getAnesthetistName() {
        return AnesthetistName;
    }

    public void setAnesthetistName(String anesthetistName) {
        AnesthetistName = anesthetistName;
    }

    public String getHighDependencyArea() {
        return HighDependencyArea;
    }

    public void setHighDependencyArea(String highDependencyArea) {
        HighDependencyArea = highDependencyArea;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getApprovedTime() {
        return ApprovedTime;
    }

    public void setApprovedTime(String approvedTime) {
        ApprovedTime = approvedTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCategory_status() {
        return category_status;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }
}