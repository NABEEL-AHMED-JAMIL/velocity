package com.ballistic.velocity.rest;

import com.ballistic.velocity.bean.email.EmailContent;
import com.ballistic.velocity.bean.email.EmailManager;
import com.ballistic.velocity.bean.view.TemplateFactory;
import com.ballistic.velocity.engine.MsgKafka;
import com.ballistic.velocity.engine.Producer;
import com.ballistic.velocity.model.pojo.Document;
import com.ballistic.velocity.service.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class PingUtil {

    private static final Logger logger = LogManager.getLogger(PingUtil.class);

    @Autowired
    protected TemplateFactory templateFactory;
    @Autowired
    protected EmailManager emailManager;
    @Autowired
    protected Producer producer;

    protected EmailContent emailContent;
    protected Map<String, Object> mailMessage;

    @Autowired
    protected DocumentService documentService;

    protected MsgKafka<Document> getDocumentMsgKafka(Document document, MsgKafka msgKafka) {
        msgKafka.setId(String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", ""));
        msgKafka.setTimestamp(String.valueOf(new Timestamp(System.currentTimeMillis())));
        msgKafka.setField(document);
        return msgKafka;
    }

    protected String requestBodyParams(HttpServletRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        StringBuilder bodyParams = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String line = "";

        try {
            inputStreamReader = new InputStreamReader(request.getInputStream());
            br = new BufferedReader(inputStreamReader);
            line = br.readLine();

            while (line != null) {
                bodyParams.append(line);
                line = br.readLine();
            }
        } catch (Exception e) {
        } finally {
            if (inputStreamReader != null)
                inputStreamReader.close();
            if (br != null)
                br.close();
        }
        logger.debug("Parsed requestBodyParams of [ " + request.getContentLength() + " ] in " + (System.currentTimeMillis() - startTime) + " ms.");
        return bodyParams.toString();
    }

}