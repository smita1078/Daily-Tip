package com.example.dailyjavatip;
import java.util.*;
import java.util.concurrent.*;
import javax.mail.*;
import javax.mail.internet.*;

public class DailyJavaTip {
    private static final String EMAIL = "prajapatisheshnath8@gmail.com";
    private static final String PASSWORD = "ydbbsletefbtxfii";
     private static final String RECIPIENT_EMAIL = "prajapatisheshnath8@gmail.com";

    private static final List<String> JAVA_TIPS = Arrays.asList(
            "Tip 1: Use meaningful variable names.",
            "Tip 2: Follow Java naming conventions.",
            "Tip 3: Use comments and Javadoc to document your code.",
            "Tip 4: Handle exceptions appropriately.",
            "Tip 5: Use Java's standard libraries whenever possible."
    );

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(DailyJavaTip::sendDailyTip, 0, 1, TimeUnit.DAYS);
    }

    private static void sendDailyTip() {
        String tip = getRandomTip();
        sendEmail("Daily Java Tip", tip);
    }

    private static String getRandomTip() {
        Random random = new Random();
        int index = random.nextInt(JAVA_TIPS.size());
        return JAVA_TIPS.get(index);
    }

    private static void sendEmail(String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            //@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to " + RECIPIENT_EMAIL);
        } catch (MessagingException e) {
            e.printStackTrace(); // Print stack trace for debugging
            throw new RuntimeException(e);
        }
    }
}

