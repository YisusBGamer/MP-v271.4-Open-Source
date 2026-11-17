package net.swordie.ms.client.character.union;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.util.FileTime;

/** A Union Artifact crystal of an account: index 0-8, level 1-5, three stat skills and an expiry. */
public class UnionArtifact {

    private int index;
    private int level = 1;
    private final int[] skills = new int[3];
    private FileTime expiration = FileTime.fromType(FileTime.Type.ZERO_TIME);

    public UnionArtifact() {
    }

    public UnionArtifact(int index, int skill1, int skill2, int skill3, FileTime expiration) {
        this.index = index;
        skills[0] = skill1;
        skills[1] = skill2;
        skills[2] = skill3;
        this.expiration = expiration;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(level);
        for (int skill : skills) {
            outPacket.encodeInt(skill);
        }
        outPacket.encodeFT(expiration);
    }

    public boolean isExpired() {
        return expiration == null || expiration.toMillis() < System.currentTimeMillis();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSkill(int i) {
        return skills[i];
    }

    public void setSkill(int i, int skill) {
        skills[i] = skill;
    }

    public FileTime getExpiration() {
        return expiration;
    }

    public void setExpiration(FileTime expiration) {
        this.expiration = expiration;
    }
}
