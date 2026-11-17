package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.MobConstants;

/**
 * Hundun's map: the Black Flame Amplification timer and damage bar, his template, and his animation.
 */
public class HundunResult {

    /** Rest of the client's animation block (0x1408318b0) after the second action; GMS sends zeros. */
    private static final int ANIMATION_TAIL_SIZE = 20;

    private final HundunResultType type;
    private int arg;
    private boolean on;
    private long value;
    private int templateId;
    private String action, nextAction;

    private HundunResult(HundunResultType type) {
        this.type = type;
    }

    /** Ms until the next Black Flame Amplification. */
    public static HundunResult amplifyTimer(int ms) {
        HundunResult r = new HundunResult(HundunResultType.AmplifyTimer);
        r.arg = ms;
        return r;
    }

    /** Hundun's template, sent on entering his map. */
    public static HundunResult init(int templateId) {
        HundunResult r = new HundunResult(HundunResultType.Init);
        r.arg = templateId;
        return r;
    }

    /** The amplification damage bar above Hundun, on with its size or off. */
    public static HundunResult bar(boolean on, long max) {
        HundunResult r = new HundunResult(HundunResultType.Bar);
        r.on = on;
        r.value = max;
        return r;
    }

    /** Damage dealt to Hundun so far in this amplification. */
    public static HundunResult barValue(long value) {
        HundunResult r = new HundunResult(HundunResultType.BarValue);
        r.value = value;
        return r;
    }

    /** Plays one action, then the next (an amplification running out unbroken: skill1, then sleep). */
    public static HundunResult animation(int mobObjectId, int templateId, String action, String nextAction) {
        HundunResult r = new HundunResult(HundunResultType.Animation);
        r.arg = mobObjectId;
        r.templateId = templateId;
        r.action = action;
        r.nextAction = nextAction;
        return r;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case Bar:
                outPacket.encodeByte(on);
                if (on) {
                    outPacket.encodeLong(value);
                }
                break;
            case Animation:
                outPacket.encodeByte(true);
                outPacket.encodeInt(arg);
                outPacket.encodeString(MobConstants.actionPath(templateId, action));
                outPacket.encodeShort(0);
                outPacket.encodeString(MobConstants.actionPath(templateId, nextAction));
                outPacket.encodeArr(new byte[ANIMATION_TAIL_SIZE]);
                break;
            case AmplifyTimer:
            case Init:
                outPacket.encodeInt(arg);
                break;
            case BarValue:
                outPacket.encodeLong(value);
                break;
        }
    }
}
