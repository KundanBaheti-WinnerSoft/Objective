package com.winnersoft.objective;


public class ProfileDetails {

    Boolean activeDeactive;
    String id,userId,motherName,role,firstname,middlename,lastname,birthdate,admissionDate,gender,country,state,city,mobileno,emailid,address,loginName,bloodGroup,emergencyMobile,qualification;

    public ProfileDetails() {
    }

    public ProfileDetails(Boolean activeDeactive, String id, String userId, String motherName, String role, String firstname, String middlename, String lastname, String birthdate, String admissionDate, String gender, String country, String state, String city, String mobileno, String emailid, String address, String loginName, String bloodGroup, String emergencyMobile, String qualification) {
        this.activeDeactive = activeDeactive;
        this.id = id;
        this.userId = userId;
        this.motherName = motherName;
        this.role = role;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.admissionDate = admissionDate;
        this.gender = gender;
        this.country = country;
        this.state = state;
        this.city = city;
        this.mobileno = mobileno;
        this.emailid = emailid;
        this.address = address;
        this.loginName = loginName;
        this.bloodGroup = bloodGroup;
        this.emergencyMobile = emergencyMobile;
        this.qualification = qualification;
    }

    public Boolean getActiveDeactive() {
        return activeDeactive;
    }

    public void setActiveDeactive(Boolean activeDeactive) {
        this.activeDeactive = activeDeactive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
