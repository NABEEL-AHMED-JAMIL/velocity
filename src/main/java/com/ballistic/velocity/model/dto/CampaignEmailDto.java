package com.ballistic.velocity.model.dto;

import com.ballistic.velocity.model.Campaign;

import java.util.Set;

public class CampaignEmailDto {

    private Set<String> sendTo;
    private Set<String> ccTo;
    private String subject;
    private Campaign campaign;

    public CampaignEmailDto() {}

    public CampaignEmailDto(Set<String> sendTo, String subject, Campaign campaign) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.campaign = campaign;
    }

    public CampaignEmailDto(Set<String> sendTo, Set<String> ccTo, String subject, Campaign campaign) {
        this.sendTo = sendTo;
        this.ccTo = ccTo;
        this.subject = subject;
        this.campaign = campaign;
    }

    public Set<String> getSendTo() { return sendTo; }
    public void setSendTo(Set<String> sendTo) { this.sendTo = sendTo; }

    public Set<String> getCcTo() { return ccTo; }
    public void setCcTo(Set<String> ccTo) { this.ccTo = ccTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Campaign getCampaign() { return campaign; }
    public void setCampaign(Campaign campaign) { this.campaign = campaign; }

    @Override
    public String toString() {
        return "CampaignEmailDto{" + "sendTo=" + sendTo + ", ccTo=" + ccTo + ", subject='" + subject + '\'' + ", campaign=" + campaign + '}';
    }
}
