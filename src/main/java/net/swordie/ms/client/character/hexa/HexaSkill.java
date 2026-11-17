package net.swordie.ms.client.character.hexa;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.TrackedObject;

import java.util.Objects;

/** An activated HEXA Matrix core (skill, mastery, boost or common node) and its level. */
public class HexaSkill extends TrackedObject {

    private long id;
    private int coreId;
    private int level;
    private boolean enabled = true;
    private boolean disabled;

    public HexaSkill() {
    }

    public HexaSkill(int coreId, int level) {
        this.coreId = coreId;
        this.level = level;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(coreId);
        outPacket.encodeInt(level);
        outPacket.encodeByte(enabled);
        outPacket.encodeByte(disabled);
    }

    @Override
    public int getTransactionalHash() {
        return Objects.hash(coreId, level, enabled, disabled);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
