package net.swordie.ms.util;

import net.swordie.ms.ext.ServerHooks;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/** The rules for new logins: name, password, email (optional) and PIC. */
public class UserUtil {

    public static final int MIN_NAME_LENGTH = 4;
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 6;
    /** BCrypt reads only this many bytes: a longer password would match any other that starts the same. */
    public static final int MAX_PASSWORD_BYTES = 72;
    /** The PIC (second password), as GMS allows it. */
    public static final int MIN_PIC_LENGTH = 6;
    public static final int MAX_PIC_LENGTH = 16;
    private static final Pattern NAME_CHARS = Pattern.compile("[A-Za-z0-9_]+");
    private static final Pattern emailRegex = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

    /** Why a new login can't have this name, or null when it can. */
    public static String usernameProblem(String username) {
        if (username == null || username.length() < MIN_NAME_LENGTH || username.length() > MAX_NAME_LENGTH) {
            return "Usernames are " + MIN_NAME_LENGTH + " to " + MAX_NAME_LENGTH + " characters.";
        }
        if (!NAME_CHARS.matcher(username).matches()) {
            return "Usernames can only have letters, numbers and _.";
        }
        if (ServerHooks.isReservedLoginName(username)) {
            return "That username isn't available.";
        }
        return null;
    }

    public static boolean isValidUsername(String username) {
        return usernameProblem(username) == null;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH
                && password.getBytes(StandardCharsets.UTF_8).length <= MAX_PASSWORD_BYTES;
    }

    public static boolean isValidEmail(String email) {
        return email != null && emailRegex.matcher(email).matches();
    }

    /** Email is optional: blank means none. */
    public static String optionalEmail(String email) {
        return email == null || email.isBlank() ? null : email.trim();
    }

    public static boolean isValidPic(String newPic) {
        return newPic != null && newPic.length() >= MIN_PIC_LENGTH && newPic.length() <= MAX_PIC_LENGTH;
    }
}
