package net.swordie.ms.client;

import net.swordie.ms.util.FileTime;

public class AuthInfo {

    private FileTime expiryTime;
    private final int userId;
    private boolean wzVerified;
    private byte[] key;
    private boolean needsWzVerified;
    /** A launch token: short-lived, only for starting the game. */
    private boolean launchToken;
    /** The address that first started the game with this token; later uses must come from it. */
    private String gameAddress;
    // Contributed by Arixal (from GitLab).
    /** Started as a launch token: it keeps working for the game, but never as a web API login. */
    private boolean gameOnly;

    public AuthInfo(int userId, FileTime expiryTime, boolean wzVerified, boolean needsWzVerified) {
        this.expiryTime = expiryTime;
        this.userId = userId;
        this.wzVerified = wzVerified;
        this.needsWzVerified = needsWzVerified;
    }

    public FileTime getExpiryTime() {
        return expiryTime;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isWzVerified() {
        return wzVerified;
    }

    public boolean isExpired() {
        return expiryTime == null || expiryTime.isExpired();
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return key;
    }

    public void setWzVerified(boolean verified) {
        wzVerified = verified;
    }

    public boolean isNeedsWzVerified() {
        return needsWzVerified;
    }

    public boolean isLaunchToken() {
        return launchToken;
    }

    public void setLaunchToken(boolean launchToken) {
        this.launchToken = launchToken;
    }

    public String getGameAddress() {
        return gameAddress;
    }

    public void setGameAddress(String gameAddress) {
        this.gameAddress = gameAddress;
    }

    public boolean isGameOnly() {
        return gameOnly;
    }

    public void setGameOnly(boolean gameOnly) {
        this.gameOnly = gameOnly;
    }

    public void setExpiryTime(FileTime expiryTime) {
        this.expiryTime = expiryTime;
    }

}
