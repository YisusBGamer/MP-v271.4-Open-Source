package net.swordie.ms.client.character;


import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.converters.FileTimeConverter;
import net.swordie.ms.util.FileTime;

import javax.persistence.*;

/**
 * Created on 2/18/2017.
 */
@Entity
@Table(name = "noncombatstatdaylimit")
public class NonCombatStatDayLimit {

    // "never" as the reference server sends it (its stored 1970-01-01 00:00:01, UTC+7); not the 1900 zero time
    private static final long NEVER_UPDATED_FT = 116444484010000000L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private short charisma;
    private short charm;
    private short insight;
    private short will;
    private short craft;
    private short sense;
    @Convert(converter = FileTimeConverter.class)
    private FileTime lastUpdateCharmByCashPR;
    private byte charmByCashPR;

    public NonCombatStatDayLimit(short charisma, short charm, byte charmByCashPR, short insight, short will, short craft, short sense, FileTime lastUpdateCharmByCashPR) {
        this.charisma = charisma;
        this.charm = charm;
        this.charmByCashPR = charmByCashPR;
        this.insight = insight;
        this.will = will;
        this.craft = craft;
        this.sense = sense;
        this.lastUpdateCharmByCashPR = lastUpdateCharmByCashPR;
    }

    public NonCombatStatDayLimit() {
    }

    public short getCharm() {
        return charm;
    }

    public void setCharm(short charm) {
        this.charm = charm;
    }

    public byte getCharmByCashPR() {
        return charmByCashPR;
    }

    public void setCharmByCashPR(byte charmByCashPR) {
        this.charmByCashPR = charmByCashPR;
    }

    public short getInsight() {
        return insight;
    }

    public void setInsight(short insight) {
        this.insight = insight;
    }

    public short getWill() {
        return will;
    }

    public void setWill(short will) {
        this.will = will;
    }

    public short getCraft() {
        return craft;
    }

    public void setCraft(short craft) {
        this.craft = craft;
    }

    public short getSense() {
        return sense;
    }

    public void setSense(short sense) {
        this.sense = sense;
    }

    public FileTime getLastUpdateCharmByCashPR() {
        return lastUpdateCharmByCashPR;
    }

    public void setLastUpdateCharmByCashPR(FileTime lastUpdateCharmByCashPR) {
        this.lastUpdateCharmByCashPR = lastUpdateCharmByCashPR;
    }

    public void encode(OutPacket outPacket) {
        // 33 bytes
        outPacket.encodeInt(getCharisma());
        outPacket.encodeInt(getInsight());
        outPacket.encodeInt(getWill());
        outPacket.encodeInt(getCraft());
        outPacket.encodeInt(getSense());
        outPacket.encodeInt(getCharm());
        outPacket.encodeByte(getCharmByCashPR());
        FileTime lastUpdate = getLastUpdateCharmByCashPR();
        if (lastUpdate == null || lastUpdate.isMinTime()) {
            outPacket.encodeLong(NEVER_UPDATED_FT);
        } else {
            outPacket.encodeFT(lastUpdate);
        }
        java.time.LocalDate today = net.swordie.ms.util.ResetClock.today();
        outPacket.encodeInt((today.getYear() % 100 * 100 + today.getMonthValue()) * 100 + today.getDayOfMonth()); // v265: today, yyMMdd
    }

    public short getCharisma() {
        return charisma;
    }

    public void setCharisma(short charisma) {
        this.charisma = charisma;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
