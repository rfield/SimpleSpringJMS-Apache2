package com.cvc.techarch.simplejms.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.io.FileInputStream;


public class SimpleFileMessageProducer implements MessageProducer {

    @Autowired
    public SimpleFileMessageProducer(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public void sendMessage() {

        Logger logger = LoggerFactory.getLogger(SimpleFileMessageProducer.class);

        logger.info("Filename=" + fileName);
        try {
            String message = FileUtil.stringFromInputStream( new FileInputStream(fileName) );
            jmsTemplate.convertAndSend(destination, message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done");
    }

    private JmsTemplate jmsTemplate;
    private Destination destination;
    private String fileName;
}
