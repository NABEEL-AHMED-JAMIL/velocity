package com.ballistic.velocity.model;


import java.util.Random;

public interface CampaignMockup {

    public static Integer getRandomNumber() {
        return new Random().nextInt(10);
    }

    public static Campaign getCampaignMockup() {
        return new Campaign(String.valueOf(getRandomNumber()), String.valueOf(getRandomNumber()));
    }
}
