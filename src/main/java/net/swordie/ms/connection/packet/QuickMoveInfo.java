package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.QuickMoveType;
import net.swordie.ms.util.FileTime;

/**
 * @author Sjonnie
 * Created on 7/12/2018.
 */
public class QuickMoveInfo {
    private int qmiID;
    private int templateID;
    private QuickMoveType code;
    private int levelMin;
    private String msg;
    private FileTime start;
    private FileTime end;
    private boolean noInstances;

    public QuickMoveInfo(int qmiID, int templateID, QuickMoveType code) {
        this.qmiID = qmiID;
        this.templateID = templateID;
        this.code = code;
        this.start = FileTime.fromType(FileTime.Type.ZERO_TIME);
        this.end = FileTime.fromType(FileTime.Type.MAX_TIME);
    }

    public QuickMoveInfo(int qmiID, int templateID, QuickMoveType code, int levelMin, String msg, boolean noInstances, FileTime start, FileTime end) {
        this.qmiID = qmiID;
        this.templateID = templateID;
        this.code = code;
        this.levelMin = levelMin;
        this.msg = msg;
        this.noInstances = noInstances;
        this.start = start;
        this.end = end;
    }

    public QuickMoveInfo withLevelMin(int level) {
        setLevelMin(level);
        return this;
    }

    public QuickMoveInfo withMsg(String msg) {
        setMsg(msg);
        return this;
    }

    public QuickMoveInfo withStartAndEnd(FileTime start, FileTime end) {
        setStart(start);
        setEnd(end);
        return this;
    }

    // v265 entry fields (GMS): the entry's code (100+, the client's icon and place), quest needed, the
    // script the NPC runs here, the title and the line under it
    private int v265Code = -1;
    private int questReq;
    private String script = "";
    private String title;
    private String requirement = "";
    private String runScript = "";

    /**
     * A GMS v265 Quick Move entry. gmsScript is the name GMS sends (the client only keeps it); runScript is the
     * script of ours that a click runs.
     */
    public static QuickMoveInfo gms(int v265Code, int npc, int levelMin, int questReq, String title, String msg,
                                    String requirement, String gmsScript, String runScript) {
        var qmi = new QuickMoveInfo(0, npc, QuickMoveType.Gladiator, levelMin, msg, false,
                FileTime.fromType(FileTime.Type.ZERO_TIME), FileTime.fromType(FileTime.Type.MAX_TIME));
        qmi.v265Code = v265Code;
        qmi.questReq = questReq;
        qmi.title = title;
        qmi.requirement = requirement;
        qmi.script = gmsScript;
        qmi.runScript = runScript;
        return qmi;
    }

    /**
     * v265 entry, client 0x1406e82d0: code, level, tooltip, required quest, script, title, NPC, an int (-1), three
     * strings (the middle one the requirement line), the shown period.
     */
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getV265Code());
        outPacket.encodeInt(getLevelMin());
        outPacket.encodeString(getMsg());
        outPacket.encodeInt(questReq);
        outPacket.encodeString(script);
        outPacket.encodeString(title == null ? getMsg() : title);
        outPacket.encodeInt(getTemplateID());
        outPacket.encodeInt(-1);
        outPacket.encodeString("");
        outPacket.encodeString(requirement);
        outPacket.encodeString("");
        outPacket.encodeFT(getStart());
        outPacket.encodeFT(getEnd());
    }

    /**
     * v265 numbers its entries from 100: Monster Park 100, Ardentmill 101, Legion Coin Shop 102, Crystals & Elixir
     * 103, Auction House 104, Meso Market 105, Dimensional Mirror 106, Symbol Express Pass 107, Maple Administrator
     * 109, Storage Room 110.
     */
    public int getV265Code() {
        if (v265Code >= 0) {
            return v265Code;
        }
        return switch (getCode()) {
            case MonsterPark -> 100;
            case Ardentmill -> 101;
            case FreeMarket -> 105;
            case DimensionalPortal -> 106;
            default -> 109;
        };
    }

    public int getQuestReq() {
        return questReq;
    }

    /** The NPC script of ours this entry runs (empty: the NPC's own). */
    public String getRunScript() {
        return runScript;
    }

    public int getQmiID() {
        return qmiID;
    }

    public void setQmiID(int qmiID) {
        this.qmiID = qmiID;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public QuickMoveType getCode() {
        return code;
    }

    public void setCode(QuickMoveType code) {
        this.code = code;
    }

    public int getLevelMin() {
        return levelMin;
    }

    public void setLevelMin(int levelMin) {
        this.levelMin = levelMin;
    }

    public boolean isNoInstances() {
        return noInstances;
    }

    public void setNoInstances(boolean noInstances) {
        this.noInstances = noInstances;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FileTime getStart() {
        return start;
    }

    public void setStart(FileTime start) {
        this.start = start;
    }

    public FileTime getEnd() {
        return end;
    }

    public void setEnd(FileTime end) {
        this.end = end;
    }
}
