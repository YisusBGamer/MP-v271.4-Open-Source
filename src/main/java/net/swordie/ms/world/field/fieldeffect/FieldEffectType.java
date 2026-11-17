package net.swordie.ms.world.field.fieldeffect;

import net.swordie.ms.util.Util;

/**
 * Created on 3/26/2018.
 */
// v265.3 values (client switch at 0x142e9d380); the UnkN entries are unused v232 leftovers
public enum FieldEffectType {
    Summon(0),
    Tremble(1),
    Object(2),
    ObjectDisable(3),
    Screen(4),
    PlaySound(7),
    MobHPTag(9),
    ChangeBGM(10),
    BGMVolumeOnly(11),
    BGMVolume(12),
    Unk10(5),
    Unk11(6),
    Unk12(8),
    Unk13(13),
    Unk14(14),
    RewardRoulette(18),
    TopScreen(19),
    ScreenDelayed(20),
    TopScreenDelayed(21),
    ScreenAutoLetterBox(23),
    FloatingUI(24),
    Blind(25),
    GreyScale(33),
    OnOffLayer(34),
    Overlap(35),
    OverlapDetail(36),
    RemoveOverlapDetail(37),
    ColorChange(38),
    StageClear(40),
    TopScreenWithOrigin(41),
    SpineScreen(42),
    OffSpineScreen(43),
    Unk33(39),
    Unk34(44),
    ;

    private byte val;

    FieldEffectType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static FieldEffectType getByVal(int val) {
        return Util.findWithPred(values(), v -> v.getVal() == val);
    }
}
