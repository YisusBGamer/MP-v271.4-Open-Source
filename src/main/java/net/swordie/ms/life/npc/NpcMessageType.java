package net.swordie.ms.life.npc;

import net.swordie.ms.util.Util;

/**
 * Dialog types; values from 27 up are v265.3 (the reference server's table).
 */
public enum NpcMessageType {
    SayOk(0, false, false, ResponseType.Response),
    SayNext(0, false, true, ResponseType.Response),
    SayPrev(0, true, false, ResponseType.Response),
    Say(0, true, true, ResponseType.Response),
    SayUnk(1, ResponseType.Response),
    SayImage(2, ResponseType.Response),
    AskYesNo(3, ResponseType.Response),
    AskText(4, ResponseType.Text),
    AskNumber(5, ResponseType.Answer),
    AskMenu(6, ResponseType.Answer),
    InitialQuiz(7, ResponseType.Answer),
    InitialSpeedQuiz(8, ResponseType.Answer),
    ICQuiz(9, ResponseType.Answer),
    AskAvatar(10, ResponseType.Answer),
    AskAndroid(11, ResponseType.Answer),
    AskAndroid2(12, ResponseType.Answer), // AskMannequin in v265
    AskPet(13, ResponseType.Answer),
    AskPetAll(14, ResponseType.Answer),
    AskActionPetEvolution(15, ResponseType.Answer),
    // 16
    AskAccept2(17, ResponseType.Response),
    AskAccept(18, ResponseType.Response),
    AskBoxtext(19, ResponseType.Answer),
    AskSlideMenu(20, ResponseType.Answer),
    AskIngameDirection(21, ResponseType.Response),
    PlayMovieClip(22, ResponseType.Response),
    AskCenter(23, ResponseType.Answer),
    AskAvatar2(25, ResponseType.Answer),
    AskSelectMenu(26, ResponseType.Answer),
    AskStoryUI(27, ResponseType.Answer),
    AskAngelicBuster(29, ResponseType.Answer),
    SayIllustration(30, ResponseType.Answer),
    SayDualIllustration(31, ResponseType.Answer),
    AskYesNoIllustration(32, ResponseType.Answer),
    AskAcceptIllustration(33, ResponseType.Answer), // bQuest=True
    AskMenuIllustration(34, ResponseType.Answer),
    AskYesNoDualIllustration(35, ResponseType.Answer), // bDual=True
    AskAcceptDualIllustration(36, ResponseType.Answer), // bQuest=True;bDual=True
    AskMenuDualIllustration(37, ResponseType.Answer), // bDual=True
    AskAvatarZero(38, ResponseType.Answer),
    Unk37(39, ResponseType.Answer), // Avatar stuff
    Unk38(40, ResponseType.Answer), // Avatar stuff

    AskWeaponBox(42, ResponseType.Answer),
    AskBoxTextBgImg(44, ResponseType.Answer),
    AskUserSurvey(45, ResponseType.Answer),
    Monologue(46, ResponseType.Response),
    AskMixHair(47, ResponseType.Answer),
    AskMixHairExZero(48, ResponseType.Answer),
    OnAskCustomMixHair(49, ResponseType.Answer),
    OnAskCustomMixHairAndProb(50, ResponseType.Answer),
    OnAskMixHairNew(51, ResponseType.Answer),
    OnAskMixHairNewExZero(52, ResponseType.Answer),
    OnAskNumberUseKeyPad(53, ResponseType.Answer),
    OnSpinOffGuitarRhythmGame(54, ResponseType.Answer),
    OnGhostParkEnter(55, ResponseType.Answer),
    OnGhostParkEnter2(56, ResponseType.Answer),
    // 59
    // 60
    // 61
    // 62
    // 63
    // 64
    Unk64(65, ResponseType.Answer),
    Unk65(66, ResponseType.Answer),
    // 67
    Unk67(68, ResponseType.Answer),
    Unk68(69, ResponseType.Answer),
    Unk69(70, ResponseType.Answer),
    None(-1, ResponseType.Answer),
    ;

    private byte val;
    private boolean prevPossible, nextPossible;
    private int delay;
    private ResponseType responseType;

    NpcMessageType(int val, ResponseType responseType) {
        this.val = (byte) val;
        prevPossible = false;
        nextPossible = false;
        this.responseType = responseType;
    }

    NpcMessageType(int val, boolean prev, boolean next, ResponseType responseType) {
        this.val = (byte) val;
        prevPossible = prev;
        nextPossible = next;
        this.responseType = responseType;
    }

    public static NpcMessageType getByVal(byte val) {
        return Util.findWithPred(values(), v -> v.getVal() == val);
    }

    public byte getVal() {
        return val;
    }

    /**
     * The v271 client's number for this type (SCRIPT_MESSAGE switch 0x143f3acc0 aligned with v265 0x143926400 by each
     * case's reader): 0-39 the same, then one type inserted after 39, one after 56 and one after 67.
     */
    public byte getV271Val() {
        return (byte) toV271(val);
    }

    public static int toV271(int v) {
        return v <= 39 ? v : v <= 56 ? v + 1 : v <= 67 ? v + 2 : v + 3;
    }

    /** The server number of a v271 client type, -1 for the inserted v271 types (40, 58, 70). */
    public static int fromV271(int v) {
        return v <= 39 ? v : v == 40 || v == 58 || v == 70 ? -1 : v <= 57 ? v - 1 : v <= 69 ? v - 2 : v - 3;
    }

    public boolean isPrevPossible() {
        return prevPossible;
    }

    public boolean isNextPossible() {
        return nextPossible;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


    public ResponseType getResponseType() {
        return responseType;
    }

    public enum ResponseType {
        Response, Answer, Text
    }
}
