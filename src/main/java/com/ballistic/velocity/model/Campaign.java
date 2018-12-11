package com.ballistic.velocity.model;

import java.util.UUID;

public class Campaign {

    private String id;
    private String campaignId;
    private String adId;

    public Campaign() {}

    public Campaign(String campaignId, String adId) {
        this.campaignId = campaignId;
        this.adId = adId;
    }

    public Campaign(String id, String campaignId, String adId) {
        this.id = id;
        this.campaignId = campaignId;
        this.adId = adId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = UUID.randomUUID().toString(); }

    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }

    public String getAdId() { return adId; }
    public void setAdId(String adId) { this.adId = adId; }

    @Override
    public String toString() {
        return "Campaign{" + "id='" + id + '\'' + ", campaignId='" + campaignId + '\'' + ", adId='" + adId + '\'' + '}';
    }
}
