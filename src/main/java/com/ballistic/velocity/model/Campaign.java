package com.ballistic.velocity.model;

public class Campaign {

    private String campaignId;
    private String adId;

    public Campaign() {}

    public Campaign(String campaignId, String adId) {
        this.campaignId = campaignId;
        this.adId = adId;
    }

    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }

    public String getAdId() { return adId; }
    public void setAdId(String adId) { this.adId = adId; }

    @Override
    public String toString() {
        return "Campaign{" + "campaignId='" + campaignId + '\'' + ", adId='" + adId + '\'' + '}';
    }
}
