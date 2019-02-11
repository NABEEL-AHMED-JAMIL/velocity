package com.ballistic.velocity.model;

import com.ballistic.velocity.model.pojo.Document;
import java.util.Random;

public interface CampaignMockup {

    public static Integer getRandomNumber() {
        return new Random().nextInt(10);
    }

    public static Document getCampaignMockup() {
        return new Document(String.valueOf(getRandomNumber()), String.valueOf(getRandomNumber()));
    }
}
