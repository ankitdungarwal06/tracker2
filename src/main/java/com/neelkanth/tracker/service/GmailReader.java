package com.neelkanth.tracker.service;

import com.neelkanth.tracker.constants.GmailConstants;
import com.neelkanth.tracker.model.Note;
import com.neelkanth.tracker.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;

import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

@Service
public class GmailReader {

    @Autowired
    private NoteService noteService;

    @Value("${gmail.username}")
    public String username;

    @Value("${gmail.password}")
    private String password;

    /*
    "db_host": "tracker",
    "db_user": "root",
    "db_password": "Ankit@1992"
     */

    public void fetchEmail() {
        String host = "imap.gmail.com";

        // Set properties for IMAP
        Properties properties = new Properties();
        properties.put("mail.imap.host", GmailConstants.HOST);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true"); // Enable SSL

        try {
            // Create a session with the properties
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true); // Uncomment for debug output

            // Connect to the IMAP store
            Store store = emailSession.getStore("imap");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Define search criteria
            String subjectToSearch = "Your Specific Subject"; // Replace with the subject you want to search for
            SearchTerm searchTerm = new AndTerm(
                    new SubjectTerm(subjectToSearch), // Filter by subject
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false) // Filter by unread emails
            );

            // Fetch the latest 10 messages (or adjust as needed)
            Message[] messages = inbox.getMessages(Math.max(1, inbox.getMessageCount() - 9), inbox.getMessageCount());

            // Limit to the first 5 emails
            int limit = Math.min(5, messages.length);
            System.out.println("Total unread emails with subject '" + subjectToSearch + "': " + messages.length);
            System.out.println("Fetching first " + limit + " emails...");

            // Loop through messages
            for (Message message : messages) {
                System.out.println("---------------------------------");
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Sent Date: " + message.getSentDate());

                // Get the email content
                Object content = message.getContent();
                if (content instanceof String) {
                    System.out.println("Content: " + content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (bodyPart.isMimeType("text/plain")) {
                            System.out.println("Content: " + bodyPart.getContent());
                            break; // Only print plain text for simplicity
                        }
                    }
                }
                // Mark the email as read
                message.setFlag(Flags.Flag.SEEN, true);
            }

            // Close the folder and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Note generateObject(String message){
//        Note note = new Note();
//        note.setEmailContent(message);
//        note.setTags(new Tag().);
//    }
}