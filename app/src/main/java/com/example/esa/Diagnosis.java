package com.example.esa;

public class Diagnosis {

    String id, mrn, name, name_diagnosis_desc, procedure_name, procedure_name_2;
    String procedure_name_3, nurse_id;
    String surgeon_id, surgical_team, sub_specialty, last_meal;
    String arrival_time_to_surgeon, high_dependency_area, on_call;
    String inform_anaesthetist, approved_status,time_approval,category_status;

    public Diagnosis(String id, String mrn, String name, String name_diagnosis_desc, String procedure_name, String procedure_name_2, String procedure_name_3, String nurse_id, String surgeon_id, String surgical_team, String sub_specialty, String last_meal, String arrival_time_to_surgeon, String high_dependency_area, String on_call, String inform_anaesthetist, String approved_status, String time_approval, String category_status) {
        this.id = id;
        this.mrn = mrn;
        this.name = name;
        this.name_diagnosis_desc = name_diagnosis_desc;
        this.procedure_name = procedure_name;
        this.procedure_name_2 = procedure_name_2;
        this.procedure_name_3 = procedure_name_3;
        this.nurse_id = nurse_id;
        this.surgeon_id = surgeon_id;
        this.surgical_team = surgical_team;
        this.sub_specialty = sub_specialty;
        this.last_meal = last_meal;
        this.arrival_time_to_surgeon = arrival_time_to_surgeon;
        this.high_dependency_area = high_dependency_area;
        this.on_call = on_call;
        this.inform_anaesthetist = inform_anaesthetist;
        this.approved_status = approved_status;
        this.time_approval = time_approval;
        this.category_status = category_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_diagnosis_desc() {
        return name_diagnosis_desc;
    }

    public void setName_diagnosis_desc(String name_diagnosis_desc) {
        this.name_diagnosis_desc = name_diagnosis_desc;
    }

    public String getProcedure_name() {
        return procedure_name;
    }

    public void setProcedure_name(String procedure_name) {
        this.procedure_name = procedure_name;
    }

    public String getProcedure_name_2() {
        return procedure_name_2;
    }

    public void setProcedure_name_2(String procedure_name_2) {
        this.procedure_name_2 = procedure_name_2;
    }

    public String getProcedure_name_3() {
        return procedure_name_3;
    }

    public void setProcedure_name_3(String procedure_name_3) {
        this.procedure_name_3 = procedure_name_3;
    }


    public String getNurse_id() {
        return nurse_id;
    }

    public void setNurse_id(String nurse_id) {
        this.nurse_id = nurse_id;
    }

    public String getSurgeon_id() {
        return surgeon_id;
    }

    public void setSurgeon_id(String surgeon_id) {
        this.surgeon_id = surgeon_id;
    }


    public String getSurgical_team() {
        return surgical_team;
    }

    public void setSurgical_team(String surgical_team) {
        this.surgical_team = surgical_team;
    }

    public String getSub_specialty() {
        return sub_specialty;
    }

    public void setSub_specialty(String sub_specialty) {
        this.sub_specialty = sub_specialty;
    }

    public String getLast_meal() {
        return last_meal;
    }

    public void setLast_meal(String last_meal) {
        this.last_meal = last_meal;
    }

    public String getArrival_time_to_surgeon() {
        return arrival_time_to_surgeon;
    }

    public void setArrival_time_to_surgeon(String arrival_time_to_surgeon) {
        this.arrival_time_to_surgeon = arrival_time_to_surgeon;
    }

    public String getHigh_dependency_area() {
        return high_dependency_area;
    }

    public void setHigh_dependency_area(String high_dependency_area) {
        this.high_dependency_area = high_dependency_area;
    }

    public String getOn_call() {
        return on_call;
    }

    public void setOn_call(String on_call) {
        this.on_call = on_call;
    }

    public String getInform_anaesthetist() {
        return inform_anaesthetist;
    }

    public void setInform_anaesthetist(String inform_anaesthetist) {
        this.inform_anaesthetist = inform_anaesthetist;
    }

    public String getApproved_status() {
        return approved_status;
    }

    public void setApproved_status(String approved_status) {
        this.approved_status = approved_status;
    }

    public String getTime_approval() {
        return time_approval;
    }

    public void setTime_approval(String time_approval) {
        this.time_approval = time_approval;
    }

    public String getCategory_status() {
        return category_status;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }
}

