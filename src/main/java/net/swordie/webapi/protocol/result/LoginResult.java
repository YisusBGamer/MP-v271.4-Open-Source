package net.swordie.webapi.protocol.result;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.User;
import net.swordie.webapi.dto.UserDto;

public class LoginResult {

    public String token;
    public UserDto user;
    public int version = ServerConstants.VERSION;
    public String minorVersion = ServerConstants.MINOR_VERSION;
    /** the setup's admin account still has its setup password (admin / admin): the launcher asks to change it */
    public boolean defaultPassword;

    public LoginResult() {
    }

    public LoginResult(byte[] token, User user) {
        this.token = new String(token);
        this.user = UserDto.fromUser(user);
    }
}
