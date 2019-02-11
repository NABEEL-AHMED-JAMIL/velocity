package com.ballistic.velocity.model.dto;

public class IABInfoDto {

    private String sourceID;
    private String fieldType;
    private String iabs;

    public IABInfoDto() { }

    public String getSourceID() { return sourceID; }
    public void setSourceID(String sourceID) { this.sourceID = sourceID; }

    public String getFieldType() { return fieldType; }
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    public String getIabs() { return iabs; }
    public void setIabs(String iabs) { this.iabs = iabs; }

    public Boolean isValidFieldType(String fieldType) {
        Boolean isValidOpertion = false;
        switch (fieldType) {
            case "documentId":
                isValidOpertion = true;
                break;
            case "adId":
                isValidOpertion = true;
                break;
            case "campaignId":
                isValidOpertion = true;
                break;
            case "flightId":
                isValidOpertion = true;
                break;
        }
        return isValidOpertion;
    }

}
