package net.swordie.ms.client.character.chair;

import java.util.Arrays;

/**
 * Created on 12/07/2020.
 *
 */
public enum CustomChairType {
    NullCustomChair(0),
    PortableChair(1),
    SaveTimeChair(2),
    PopularChair(3),
    StarForceChair(4),
    TrickOrTreatChair(5),
    CelebChair(6),
    RandomChair(7),
    TraitsChair(8),
    IdentityChair(9),
    MirrorChair(10),
    PopButtonChair(11),
    RollingHouseChair(12),
    AndroidChair(13),
    MannequinChair(14),
    RotatedSleepingBagChair(15),
    EventPointChair(16),
    HashTagChair(17),
    PetChair(18),
    CharLvChair(19),
    ScoreChair(20),
    ArcaneForceChair(21),
    ScaleAvatarChair(22),
    TowerChair(23),
    TextChair(24),
    MesoChair(25),
    TotalChair(26),
    WasteChair(27),
    RollingHouseChair2019(28),
    YetiChair(29),
    MapleGlobeChair(30),
    IdolChair(31),
    // v265 client types (0x142d259a0); the number is ours, the client picks the class by name
    DummyAvatarChair(33),
    OutfitVoteChair(35),
    ShowcaseChair(38),
    FieldObjectChair(39),
    WsrChair(42),
    RandomTamingMobChair(45),
    RandomEffectChair(46),
    RandomBGMChair(47),
    RandomEffectBGMChair(48),
    EventPointTamingMobChair(49),
    NoDataChair(50), // types whose client reader takes nothing
    CollectionCardChair(51), // v271 only (client type 43; every later type is one higher than v265)
    ;
    private int val;

    CustomChairType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static CustomChairType getByVal(int val) {
        return Arrays.stream(values()).filter(cct -> cct.getVal() == val).findFirst().orElse(null);
    }

    public static CustomChairType getByString(String name) {
        switch (name.toLowerCase()) {
            case "timechair":
            case "savetimechair":
                return SaveTimeChair;
            case "popchair":
            case "popularchair":
                return PopularChair;
            case "androidchair":
                return AndroidChair;
            case "starforcechair":
                return StarForceChair;
            case "trickortreatchair":
                return TrickOrTreatChair;
            case "celebchair":
                return CelebChair;
            case "randomchair":
                return RandomChair;
            case "identitychair":
                return IdentityChair;
            case "mirrorchair":
                return MirrorChair;
            case "popbuttonchair":
                return PopButtonChair;
            case "traitschair":
                return TraitsChair;
            case "rollinghousechair":
                return RollingHouseChair;
            case "rotatedsleepingbagchair":
                return RotatedSleepingBagChair;
            case "hashtagchair":
                return HashTagChair;
            case "eventpointchair":
            case "eventpointgenderlychair":
            case "eventpointclonechair":
                return EventPointChair;
            case "mannequinchair":
                return MannequinChair;
            case "petchair":
                return PetChair;
            case "charlvchair":
                return CharLvChair;
            case "scorechair":
                return ScoreChair;
            case "scaleavatarchair":
                return ScaleAvatarChair;
            case "wastechair":
                return WasteChair;
            case "2019rollinghousechair":
                return RollingHouseChair2019;
            case "yetichair":
                return YetiChair;
            case "mapleglobechair":
                return MapleGlobeChair;
            case "idolchair":
                return IdolChair;
            case "arcaneforcechair":
                return ArcaneForceChair;
            case "dummyavatarchair":
                return DummyAvatarChair;
            case "outfitvotechair":
                return OutfitVoteChair;
            case "showcasechair":
                return ShowcaseChair;
            case "fieldobjectchair":
                return FieldObjectChair;
            case "wsrchair":
                return WsrChair;
            case "randomtamingmobchair":
                return RandomTamingMobChair;
            case "randomeffectchair":
                return RandomEffectChair;
            case "randombgmchair":
                return RandomBGMChair;
            case "randomeffectbgmchair":
                return RandomEffectBGMChair;
            case "eventpointtamingmobchair":
                return EventPointTamingMobChair;
            case "collectioncardchair":
                return CollectionCardChair;
            case "timeexpgifteventchair":
            case "bgmselectionchair":
            case "groupcustomchair":
            case "flipxychair":
            case "clipchair":
                return NoDataChair;
        }

        return null;
    }
}
