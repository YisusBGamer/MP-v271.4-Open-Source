package net.swordie.webapi.protocol.request;

import jakarta.ws.rs.BadRequestException;

public class LoginRequest extends ValidatedApiRequest {

    public String username;
    public String password;
    /** Set for a game start: the login is for starting the game (refused while the account is in the game). */
    public boolean game;

    public void validate() {
        if (username == null || password == null) {
            throw new BadRequestException("Missing username/password");
        }
    }

}
