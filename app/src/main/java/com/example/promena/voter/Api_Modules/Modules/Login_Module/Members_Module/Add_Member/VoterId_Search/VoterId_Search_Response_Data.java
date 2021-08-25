package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoterId_Search_Response_Data {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("VoterId")
    @Expose
    private String voterId;
    @SerializedName("VoterName")
    @Expose
    private String voterName;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("RelationShipName")
    @Expose
    private String relationShipName;
    @SerializedName("RelationType")
    @Expose
    private String relationType;
    @SerializedName("DoorNo")
    @Expose
    private String doorNo;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PollingStationAddress")
    @Expose
    private String pollingStationAddress;
    @SerializedName("FamilyCode")
    @Expose
    private String familyCode;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("Caste")
    @Expose
    private String caste;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRelationShipName() {
        return relationShipName;
    }

    public void setRelationShipName(String relationShipName) {
        this.relationShipName = relationShipName;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPollingStationAddress() {
        return pollingStationAddress;
    }

    public void setPollingStationAddress(String pollingStationAddress) {
        this.pollingStationAddress = pollingStationAddress;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }
}
