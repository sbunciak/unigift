package cz.muni.fi.pv207.unigift;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

public class EmailWorkItemHandler implements WorkItemHandler {

    // Recipient's email ID needs to be mentioned.
    private String to;

    // Sender's email ID needs to be mentioned
    private String from;

    private String subject;

    private String body;

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        System.err.println("Email Task aborted: " + workItem);
        manager.abortWorkItem(workItem.getId());
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        to = (String) workItem.getParameter("To");
        from = (String) workItem.getParameter("From");
        subject = (String) workItem.getParameter("Subject");
        body = (String) workItem.getParameter("Body");

        System.out.println("====================================");
        System.out.println("Executing Email task: " + workItem);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("====================================");

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        manager.completeWorkItem(workItem.getId(), workItem.getResults());
    }

}
