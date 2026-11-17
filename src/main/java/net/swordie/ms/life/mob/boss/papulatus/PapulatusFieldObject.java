package net.swordie.ms.life.mob.boss.papulatus;

import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;

import java.util.Collections;
import java.util.List;

public class PapulatusFieldObject implements Encodable {

    private PapulatusFieldObjectType type;

    private List<PapulatusLaserInfo> papulatusLaserInfos;
    private List<PapulatusTweezerInfo> papulatusTweezerInfos;

    private boolean bool1;
    private boolean bool2;

    private int int1;

    public PapulatusFieldObject(PapulatusFieldObjectType type) {
        this.type = type;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.ordinal());
        switch (type) {
            case Laser:
                outPacket.encodeInt(papulatusLaserInfos.size());
                for (var pli : papulatusLaserInfos) {
                    outPacket.encode(pli);
                }
                break;
            case LaserEnd:
                outPacket.encodeInt(int1); // fade (ms); GMS sends 2000, then an empty laser list
                break;
            case Tweezer:
                outPacket.encodeInt(papulatusTweezerInfos.size());
                for (var pti : papulatusTweezerInfos) {
                    outPacket.encode(pti);
                }
                break;
            case AlarmClockTimer:
                outPacket.encodeByte(bool1); // bEnabled
                outPacket.encodeByte(bool2); // bAlarmClockPhase
                outPacket.encodeInt(int1); // nRemainingMillis
                break;
            case AlarmState:
                outPacket.encodeInt(int1); // Papulatus' template id
                outPacket.encodeByte(bool1); // alarm ringing (GMS: 1 when it starts, 0 when it ends)
                break;
        }
    }

    public static PapulatusFieldObject lasers(List<PapulatusLaserInfo> laserInfos) {
        var pfo = new PapulatusFieldObject(PapulatusFieldObjectType.Laser);
        pfo.papulatusLaserInfos = laserInfos;
        return pfo;
    }

    public static PapulatusFieldObject laserEnd(int fadeMillis) {
        var pfo = new PapulatusFieldObject(PapulatusFieldObjectType.LaserEnd);
        pfo.int1 = fadeMillis;
        return pfo;
    }

    public static PapulatusFieldObject tweezer(PapulatusTweezerInfo papulatusTweezerInfo) {
        return tweezers(Collections.singletonList(papulatusTweezerInfo));
    }

    public static PapulatusFieldObject tweezers(List<PapulatusTweezerInfo> tweezerInfos) {
        var pfo = new PapulatusFieldObject(PapulatusFieldObjectType.Tweezer);
        pfo.papulatusTweezerInfos = tweezerInfos;
        return pfo;
    }

    public static PapulatusFieldObject unk3() {
        return new PapulatusFieldObject(PapulatusFieldObjectType.Unk3);
    }

    public static PapulatusFieldObject alarmClockTimer(boolean enabled, boolean alarmClockPhase, int remainingMillis) {
        var pfo = new PapulatusFieldObject(PapulatusFieldObjectType.AlarmClockTimer);
        pfo.bool1 = enabled;
        pfo.bool2 = alarmClockPhase;
        pfo.int1 = remainingMillis;
        return pfo;
    }

    public static PapulatusFieldObject alarmState(int templateId, boolean ringing) {
        var pfo = new PapulatusFieldObject(PapulatusFieldObjectType.AlarmState);
        pfo.int1 = templateId;
        pfo.bool1 = ringing;
        return pfo;
    }

    private enum PapulatusFieldObjectType {
        Laser,
        LaserEnd,
        Tweezer,
        Unk3,
        AlarmClockTimer,
        AlarmState
    }

}
