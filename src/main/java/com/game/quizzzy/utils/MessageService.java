package com.game.quizzzy.utils;

public class MessageService {

    private static final String SYSTEM_MESSAGE = "This is a system message. Please don't answer it! \n" +
            "\n" +
            "Best regards! \n" +
            "Quizzzy team \n";

    public static String getChangedPasswordTextMessage(String email, String password) {
        return "Hello " + email + ", \n" +
                "\n" +
                "Your password was changed! \n" +
                "The new password is: " + password + "\n" +
                "\n" + SYSTEM_MESSAGE;
    }

    public static String getNewPasswordLinkTextMessage(String email) {
        return "Hello " + email + ", \n" +
                "\n" +
                "You are receiving this message because you have requested to change your password. \n" +
                "Follow the instructions to change your password. \n" +
                "\n" +
                "Click on this link to add your new password: \n" +
                "http://localhost:3000/forgot-password \n" +
                "\n" +
                "\n" +
                "\n" + SYSTEM_MESSAGE;
    }

    public static String getMessageForRegisterUser(String email) {
        return "Hello, " + email + "\n" +
                "\n" +
                "You have successfully registered in Quizzzy - challenge your self.\n" +
                "\n" +
                "We expect you to show knowledge and answer more questions truthfully. \n" +
                "Also, don't forget to join the game with your own questions, which other users will be able to answer.\n" +
                "Good luck! \n" +
                "\n" +
                "\n" +
                "\n" + SYSTEM_MESSAGE;
    }

    public static String getMessageForDeleteUser(String email) {
        return "Hello, " + email + "\n" +
                "\n" +
                "Your account was deleted!\n" +
                "\n" +
                "\n" + SYSTEM_MESSAGE;
    }
}
