# Somewhere in Esfera (450007430): Will's ambush after the Light Executors (pt_450007420). Ends [Esfera] A Sunken Thing
# (37866: its record clear=c1) and leaves the masked warrior (3003554, record 34560 54=h1) in the Slumbering Deep,
# where The Trap (37867) starts.
OLLIE = 3003652
WILL = 3003503
MASKED = 3003772
SLUMBERING_DEEP = 450007170


def scene():
    sm.removeNpc(3003551)
    sm.removeNpc(3003519)
    sm.removeNpc(3003554)
    sm.spawnNpc(3003551, -1334, 215, True)
    sm.spawnNpc(3003519, -1582, 215, False)
    sm.lockUI()
    sm.removeEscapeButton()
    sm.zoomCamera(0, 1600, -1500, 250)
    sm.forcedInput(1)
    sm.sendDelay(500)
    sm.forcedInput(0)
    sm.sendDelay(2000)
    sm.forcedInput(0)
    say(OLLIE, "#face2#Shubert, cough... Transfer Tana... Argh! Run, #h0#...")
    me("No! Shubert, transfer Ollie. I'll take care of Tana.")
    say(OLLIE, "#face2#Y-you can't! My mission was... to protect you...")
    me("What?!")
    say(OLLIE, "#face3#Cough... Don't... fight...")
    me("Shubert! Now!")
    say(WILL, "#face0#You fool. If only you had listened to your spirited friend.")
    sm.forcedInput(1)
    sm.sendDelay(500)
    sm.forcedInput(4)
    sm.removeNpc(3003551)
    me("Argh! Your power... I thought you couldn't...")
    say(WILL, "#face2#Ha ha ha! Do you understand anything that's happening?")
    say(WILL, "#face2#You defeated the Executors and weakened Tana's power.")
    me("That's why you didn't fight me earlier! Argh...")
    say(WILL, "#face0#That's got to sting, yes? You've put the whole world at risk for the sake of that girl.")
    say(WILL, "#face0#Oh, that poor girl. I suppose she was just a #e#rpawn#k#n to sacrifice in your place.")
    me("What?")
    say(WILL, "#face0#You, the great Adversary, are the only one who can face him. The obvious choice is to "
              "#rsacrifice others to save you#k if necessary.")
    me("No... You can't...")
    say(WILL, "#face0#Now, I believe the time has come to reveal yourself.")
    sm.zoomCamera(0, 1600, -1200, 250)
    sm.setQRValue(34560, "54", "h1")
    sm.spawnNpc(3003554, -1100, 215, True)
    say(WILL, "#face0#What a pity. You thought I would use the power of Mirror World if I fought with the Adversary...")
    say(WILL, "#face0#But there's no need for me to do that.")
    say(MASKED, "#face0#...")
    say(WILL, "#face0#I have no intention of fighting with you.")
    say(WILL, "#face2#Now, it's time for you to choose. Will you save the Adversary, or stop the ritual?")
    say(WILL, "#face0#You're free to do as you like, of course. Just keep this in mind...")
    sm.blind(1, 255, 0, 0)
    say(WILL, "#face2#You don't have time to hesitate.")
    sm.OnOffLayer_On(0, "0", 0, 0, 0, "Map/Effect3.img/esfera/confront/0", 4, 1, -1, 0)
    sm.sendDelay(1000)
    me("?!...")
    sm.OnOffLayer_Off(1000, "0", 0)
    me("I can't...breathe...")
    me("...")
    sm.sayMonologue("To destroy him... I need to create an #radversary#k.", True)
    me("Him... You mean the Black Mage?")


def say(npc, text):
    sm.setSpeakerID(npc)
    sm.setBoxChat()
    sm.sendNext(text)


def me(text):
    sm.setPlayerBoxChat()
    sm.sendNext(text)


if sm.hasQuest(37866):
    try:
        scene()
    finally:
        # also when the dialog is closed: the ambush happened, the quest is done
        sm.blind(0, 0, 0, 0)
        sm.unlockUI()
        if sm.hasQuest(37866):
            sm.createQuestWithQRValue(37866, "clear=c1")
            sm.completeQuestNoCheck(37866)
            sm.createQuestWithQRValue(34560, "30=h0;31=h1;32=h1;40=h0;41=h0;42=h0;44=h0;45=h0;46=h0;47=h0;48=h0;49=h0;"
                                             "50=h0;51=h0;52=h0;53=h0;54=h1;77=h0;78=h0;79=h0;80=h0")
        sm.warpInstanceOut(SLUMBERING_DEEP)
else:
    sm.warpInstanceOut(SLUMBERING_DEEP)
