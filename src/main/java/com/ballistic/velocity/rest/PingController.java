package com.ballistic.velocity.rest;

import com.ballistic.velocity.bean.email.EmailContent;
import com.ballistic.velocity.bean.email.EmailSubject;
import com.ballistic.velocity.engine.MsgKafka;
import com.ballistic.velocity.model.dto.IABInfoDto;
import com.ballistic.velocity.model.pojo.Document;
import com.ballistic.velocity.model.dto.EmailDto;
import com.ballistic.velocity.bean.view.IVelocityContext;
import com.ballistic.velocity.bean.view.TemplateType;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class PingController extends PingUtil {

    private static final Logger logger = LogManager.getLogger(PingController.class);

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 1) If user send the zip then response will be zip   *
     * 2) if user send the dev it will send the only json  *
     * 3) if both have then send the email send zip        *
     *  * * * * * * * * * * * * * * * * * * * * * * * * *  */
    @RequestMapping(value = "/rtb-bid/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveDocuments(@RequestBody Document document) {
        long startTime = System.currentTimeMillis();
        try {// json send back
            String response = this.templateFactory.getWriterResponse(TemplateType.BID_TEMPLATE_PATH, IVelocityContext.
                getVelocityContextWithMessage(TemplateType.BID_TEMPLATE_PATH, this.templateFactory.getContext(),document)).toString().replaceAll("\\s+","");
            if(response != null) {
                document.setResponse(response);
                String message = new Gson().toJson(this.getDocumentMsgKafka(document,new MsgKafka<Document>()));
                this.producer.sendMessage(message);
            }
            logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return response;
        }catch (NullPointerException ex) {
            logger.error("Error :- " + ex.getLocalizedMessage() + " Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return "Error :- " + ex.getLocalizedMessage();
        }
    }
    /* * * * * * * * * *  *
     * Send Email Process *
     * * * * * * * * * *  */
    @RequestMapping(value = "/email/documents", method = RequestMethod.POST)
    public String emailDocuments(@RequestBody EmailDto emailDto) {
        long startTime = System.currentTimeMillis();
        this.mailMessage = new HashMap<>();
        this.mailMessage.put("BID_RESPONSE", String.format(EmailSubject.BID_RESPONSE, emailDto.getDocument().getCampaignId(), emailDto.getDocument().getAdId()));
        this.emailContent = new EmailContent(emailDto.getSendTo(), emailDto.getCcTo(), emailDto.getSubject(), this.mailMessage);
        Boolean status = this.emailManager.sendEmail(this.emailContent);
        if(!status) {
            logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return "Your Email Not Send";
        }
        logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return "Your Email Send";
    }

    // update service test with consumer and producer
    @RequestMapping(value = "/document/iab", method = RequestMethod.POST)
    public String addIABDocument(@RequestBody IABInfoDto iabInfoDto) {
        String response = null;
        long startTime = System.currentTimeMillis();
        try {
            if(iabInfoDto.getIabs() != null && (iabInfoDto.isValidFieldType(iabInfoDto.getFieldType()) || iabInfoDto.getSourceID() != null)) {
                List<Document> documents = this.documentService.getAllDocumentBySourceId(iabInfoDto.getFieldType(), iabInfoDto.getSourceID());
                if(documents.size() > 0) {
                    StringBuilder updateBid = new StringBuilder();
                    updateBid.append("[");
                    documents.stream().forEach(document -> {
                        // setting the ibas
                        document.setIabs(iabInfoDto.getIabs().replaceAll("\\s+",""));
                        String documentResponse = this.templateFactory.getWriterResponse(TemplateType.BID_TEMPLATE_PATH, IVelocityContext.
                                getVelocityContextWithMessage(TemplateType.BID_TEMPLATE_PATH, this.templateFactory.getContext(),document)).
                                toString().replaceAll("\\s+","");
                        if(documentResponse != null) {
                            // setting the new response
                            updateBid.append(documentResponse).append(",");
                            document.setResponse(documentResponse);
                            String message = new Gson().toJson(this.getDocumentMsgKafka(document, new MsgKafka<Document>()));
                            this.producer.sendMessage(message);
                        }
                    });
                    updateBid.deleteCharAt(updateBid.lastIndexOf(","));
                    updateBid.append("]");
                    return updateBid.toString();
                }else {
                    response = this.templateFactory.getWriterResponse(TemplateType.BAD_REQUEST, null).toString().replaceAll("\\s+","");
                }

             }else {
                response = this.templateFactory.getWriterResponse(TemplateType.BAD_REQUEST, null).toString().replaceAll("\\s+","");
            }
            logger.info("Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return response;
        }catch (NullPointerException ex) {
            logger.error("Error :- " + ex.getLocalizedMessage() + " Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return "Error :- " + ex.getLocalizedMessage();
        }
    }

}