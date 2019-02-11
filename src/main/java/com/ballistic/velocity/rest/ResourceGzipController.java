package com.ballistic.velocity.rest;

import com.ballistic.velocity.model.pojo.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ResourceGzipController extends PingUtil {

    private static final Logger logger = LogManager.getLogger(ResourceGzipController.class);

    // update service test with consumer and producer
    @RequestMapping(value = "/fetch/document/get", params = { "page", "size" },method = RequestMethod.GET)
    public Page<Document> fetchAllDocumet(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Document> resultPage = documentService.findPaginated(page, size);
        if( page > resultPage.getTotalPages() ) {
            throw new ResourceNotFoundException("Page Not Found");
        }
        return resultPage;
    }

    // ?sort=+salary,+joiningDate
    // ?pageSize=10&pageNumber=2  '+' (ASC Order) or '-' (DESC Order)
    public Page<Document> fetchAllDocument() {
        return null;
    }

    // gzip test
    @RequestMapping(value = "/rtb-bid/request", method = RequestMethod.POST)
    public String showDocuments(HttpServletRequest request) throws IOException {
        logger.info("Request :- " + requestBodyParams(request));
        return "{\"response\": \"pakistan zindabad\"}";
    }

}
