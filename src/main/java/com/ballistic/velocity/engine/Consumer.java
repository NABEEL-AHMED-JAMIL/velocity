package com.ballistic.velocity.engine;

import com.ballistic.velocity.model.pojo.Document;
import com.ballistic.velocity.service.DocumentService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class Consumer {

    private static final Logger logger = LogManager.getLogger(Consumer.class);

    @Autowired
    private DocumentService documentService;
    private static Integer blukNumber = 0;


    @KafkaListener(topics = "campaign-topic", groupId = "group_id")
    public void consume(String message) throws IOException {
        long startTime = System.currentTimeMillis();
        logger.info(String.format("#### -> Consumed message -> %s", message));
        JsonObject mainObject = new JsonParser().parse(message).getAsJsonObject();
        if(mainObject.has("field")) {
            JsonObject campaignObject = mainObject.get("field").getAsJsonObject();
            Document document = new Gson().fromJson(campaignObject, Document.class);
            this.documentService.saveDocument(document);
            blukNumber = blukNumber + 1;
        }
        logger.info(String.format("#### -> Bulk #%d Operation Perform -> %s in %d.ms", blukNumber ,"Successfully", (System.currentTimeMillis()-startTime)));
    }
}