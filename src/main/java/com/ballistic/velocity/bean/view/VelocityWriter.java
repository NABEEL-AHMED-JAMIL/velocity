package com.ballistic.velocity.bean.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

/* * * * * * * * * * * * * * * * * * * *
 * Note :- VelocityWriter Section Done *
 * * * * * * * * * * * * * * * * * * * */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Note :- For New Template Integration
 * 1) add the view file into the view folder
 * 2) path of view file into the VelocityWriter
 * 3) Init the view file path into th e TemplateFactory
 * by create the new view instance
 * 4) Add the Enum Type for Template related in TemplateType
 * 5) update the method getTemplate under TemplateFactory
 * 6) IVelocityContext update the context related object in method getVelocityContextWithMessage
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public abstract class VelocityWriter {

    private static final Logger logger = LogManager.getLogger(VelocityWriter.class);

    public static final String BID_TEMPLATE_PATH = "templates/bid.vm";
    public static final String EMAIL_TEMPLATE_PATH = "templates/email.vm";
    public static final String BAD_REQUEST_TEMPLATE_PATH = "templates/bad-reqeust.vm";
    /*  create a context and add data */
    public VelocityEngine engine = new VelocityEngine();
    /*  create a context and add data */
    private VelocityContext context = null;
    /* now render the template into a StringWriter */
    protected StringWriter writer = null;

    /**
     * Note :- Init each time new object
     */
    public VelocityWriter() {
        logger.debug("Velocity Context INIT");
        this.setContext(new VelocityContext());
        logger.debug("Velocity Context END-INIT");
    }

    public VelocityEngine getEngine() { return engine; }

    public VelocityContext getContext() { return context; }
    private void setContext(VelocityContext context) { this.context = context; }

    protected StringWriter getWriter() { return writer; }
    protected void setWriter(StringWriter writer) { this.writer = writer; }

}