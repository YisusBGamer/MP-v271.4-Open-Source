package net.swordie.webapi.protocol.request;

import jakarta.ws.rs.BadRequestException;
import net.swordie.ms.util.UserUtil;

public class ChangePasswordRequest extends ValidatedApiRequest {

    public String currentPassword;

    public String newPassword;

    @Override
    public void validate() {
        if (currentPassword == null || currentPassword.equals("")) {
            throw new BadRequestException("Enter your current password.");
        }
        if (newPassword == null || newPassword.equals("")) {
            throw new BadRequestException("Enter a new password.");
        }

        if (!UserUtil.isValidPassword(newPassword)) {
            throw new BadRequestException("The new password must be at least 6 characters.");
        }
    }
}
