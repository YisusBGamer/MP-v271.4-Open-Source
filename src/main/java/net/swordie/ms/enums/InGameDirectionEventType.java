package net.swordie.ms.enums;

import net.swordie.ms.util.Util;

/**
 * @author Sjonnie
 * Created on 8/31/2018.
 */
// v265.3 values (client switch at 0x143255eb0)
public enum InGameDirectionEventType {
    ForcedAction(0),
    Delay(2),
    EffectPlay(3),
    ForcedInput(4),
    PatternInputRequest(5),
    CameraMove(7),// automated send delay
    CameraOnCharacter(8),
    CameraZoom(9),// automated send delay
    CameraReleaseFromUserPoint(11),
    Unk9(10),
    VansheeMode(13),
    FaceOff(14),
    Monologue(15),
    MonologueScroll(16),
    AvatarLookSet(17),
    RemoveAdditionalEffect(20),
    RemoveAdditionalEffect_2(21),
    ForcedMove(23), // v271: 23 reads 4 dir 4 speed 1 (22 is a new 1 4 4)
    ForcedFlip(24), // v271: 24 reads 4
    Unk18(19),
    InputUI(25),
    CloseUI(27), // v271: 27 closes UI 0xf3, reads nothing (26 is a new 1 [4])
    Monologue_2(41), // v271: 41 reads str 1 1
    Unk22(28), // v271: 28 reads 4
    BossGuide(40), // v271 0x14385dd30 case 0x28: str key; a boss guide page the character hasn't seen is shown (the client keeps what was seen) and answered when closed (60 s at most), a seen one at once (answer type 4)
    ;

    private int val;

    InGameDirectionEventType(int val) {
        this.val = val;
    }

    public static InGameDirectionEventType getByVal(byte val) {
        return Util.findWithPred(values(), v -> v.getVal() == val);
    }

    public int getVal() {
        return val;
    }
}
