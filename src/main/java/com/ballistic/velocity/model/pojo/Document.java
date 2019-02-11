package com.ballistic.velocity.model.pojo;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document(collection = "document")
public class Document {

    @Id
    private String id;
    private String campaignId;
    private String adId;
    private String iabs;
    private String response;


    public Document() {}

    public Document(String campaignId, String adId) {
        this.campaignId = campaignId;
        this.adId = adId;
    }

    public Document(String id, String campaignId, String adId) {
        this.id = id;
        this.campaignId = campaignId;
        this.adId = adId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }

    public String getAdId() { return adId; }
    public void setAdId(String adId) { this.adId = adId; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getIabs() { return iabs; }
    public void setIabs(String iabs) { this.iabs = iabs; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
