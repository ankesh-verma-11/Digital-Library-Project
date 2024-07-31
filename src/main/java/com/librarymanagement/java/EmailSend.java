package com.librarymanagement.java;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.*;

public class EmailSend {
    private static Set<Long> membershipNumbers = new HashSet<>();
    public static String getUniquMemberahipNumber()
    {
        Random random = new Random();
        int min = 100; // Minimum 4-digit number
        int max = 999999; // Maximum 6-digit number
        long randomNumber = random.nextInt(max - min + 1) + min;
        while (membershipNumbers.contains(randomNumber))
        {
            randomNumber = random.nextInt(max - min + 1) + min;
        }
        membershipNumbers.add(randomNumber);
        return  new String(""+randomNumber);
    }
    public static String sendEmailToAdmin(String userName, String userEmail,String membershipId, String userPassword ,String libName)
    {
       String resultMessage ="";
        String host ="smtp.gmail.com";
        String adminEmail = "ankesh.verma.sdbc@gmail.com";
        String password = "zbgc mxky ucsg ffme";
        String subject = "Welcome to the Digital Library, " + userName + "!";
        String message = getEmail(userName,adminEmail,membershipId,userPassword,libName);
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //step 1 : to get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail,password);
            }
        });
        session.setDebug(true);
        // step 2 : compose the message [test, audio, video , image]
        MimeMessage message1 = new MimeMessage(session);
        try{
            //from email id
            message1.setFrom(adminEmail);
            // add recipient to message
            message1.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(userEmail)));
            // adding subject to message
            message1.setSubject(subject);
            //adding text to message
            message1.setText(message);
            //step 3
             Transport.send(message1);
            System.out.println("Message Sent.......");
            resultMessage="sent";
            System.out.println("Message Sent.......");
        } catch (AddressException e) {
            resultMessage = "Email not sent to " + userEmail + " because the email address is invalid.";
            System.out.println(resultMessage);
        } catch (MessagingException e) {
            resultMessage = "Email not sent to " + userEmail + " due to a messaging error: " + e.getMessage();
            System.out.println(resultMessage);
        } catch (Exception e) {
            resultMessage = "Email not sent to " + userEmail + " due to an unexpected error: " + e.getMessage();
            System.out.println(resultMessage);
        }
        return resultMessage;
    }
    public static String sendEmailToStudent(String userName, String userEmail, String membershipId, String userPassword,String role)
    {
        String resultMessage="";
        String host ="smtp.gmail.com";
        String adminEmail = "ankesh.verma.sdbc@gmail.com";
        String password = "zbgc mxky ucsg ffme";
        String subject = "Welcome to the Digital Library, " + userName + "!";
        //changes required
        String message = getEmail(userName,adminEmail,membershipId,userPassword,role);
        // get system property
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //step 1 : to get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail,password);
            }
        });
        session.setDebug(true);
        // step 2 : compose the message [test, audio, video , image]
        MimeMessage message1 = new MimeMessage(session);
        try{
            //from email id
            message1.setFrom(adminEmail);
            // add recipient to message
            message1.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(userEmail)));
            // adding subject to message
            message1.setSubject(subject);
            //addind text to message
            message1.setText(message);
            //step 3
            Transport.send(message1);
            resultMessage="sent";
            System.out.println("Message Sent.......");
        }catch (AddressException e) {
        resultMessage = "Email not sent to " + userEmail + " because the email address is invalid.";
        System.out.println(resultMessage);
        } catch (MessagingException e) {
                resultMessage = "Email not sent to " + userEmail + " due to a messaging error: " + e.getMessage();
                System.out.println(resultMessage);
        } catch (Exception e) {
                resultMessage = "Email not sent to " + userEmail + " due to an unexpected error: " + e.getMessage();
                System.out.println(resultMessage);
        }
        return resultMessage;
    }

    private static String getEmail(String userName, String  adminEmail, String membershipId ,String userPassword,String role)
    {
        String email = ""+adminEmail;
        String message = "Dear "+userName+",\n" +
                "\n" +
                "Thank you for registering with Digital Library !\n" +
                "\n" +
                "We are excited to have you join our community. Your account has been successfully created with the following details:\n" +
                "\n" +
                "MembershipId: "+membershipId +"\n" +
                "Password: "+ userPassword+"\n" +
                "\n" +
                "Please keep this information safe and secure. If you ever need to reset your password, you can do so through our website.\n" +
                "\n" +
                "If you have any questions or need assistance, feel free to contact our support team at "+email+"\n"+
                "\n" +
                "We look forward to seeing you on our platform!\n" +
                "\n" +
                "Best regards,\n" +
                "Digital Library Team\n";
        return message;
    }


    private static void sendAttachment(String message,String subject,String to, String from){
        String host ="smtp.gmail.com";

        // get system property
        Properties properties = System.getProperties();
        System.out.println("Properties : "+properties);

        //setting important information to properties object

        //host set

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //step 1 : to get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ankesh.verma.sdbc@gmail.com","zbgc mxky ucsg ffme");
            }
        });
        session.setDebug(true);

        // step 2 : compose the message [test, audio, video , image]

        MimeMessage message1 = new MimeMessage(session);


        try{
            //from email id
            message1.setFrom(from);

            // add recipient to message
            message1.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));

            // adding subject to message
            message1.setSubject(subject);

            //add attachment file
             String path ="C:/Users/Hp/Desktop/git/Git _commands.txt";
            MimeMultipart mimeMultipart = new MimeMultipart();
            //text
            //file

            MimeBodyPart textMine = new MimeBodyPart();
            MimeBodyPart fileMine = new MimeBodyPart();
            try{
                textMine.setText(message);

                File file = new File(path);
                fileMine.attachFile(file);

                mimeMultipart.addBodyPart(textMine);
                mimeMultipart.addBodyPart(fileMine);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            // attachment set
            message1.setContent(mimeMultipart);
            //step 3
            Transport.send(message1);
            System.out.println("Message Sent.......");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
