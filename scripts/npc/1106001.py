# Cygnus (1106001), General Store Yard (913070050): Mihile's "Preparation for Knighthood" (20036) after the test.
# Its v265 start NPC stands in the new Mihile intro (36601); played through the old store, it is given here, as
# q20036s does.
CYGNUS = 1106001
NEINHEART = 1106003

sm.setSpeakerID(CYGNUS)
if sm.getChr().getJob() == 5000 and sm.hasQuestCompleted(20035) and not sm.hasQuestCompleted(20036):
    sm.sendNext("Do you see, Neinheart? This child must be the descendant of the Knight of Light.")
    sm.setSpeakerID(NEINHEART)
    sm.sendSay("I suppose the Empress is correct. You will have to learn the ways of a true knight, shopkeep.")
    sm.setPlayerAsSpeaker()
    sm.sendSay("My father was the Knight of Light? What does that even mean? I'm just an ordinary kid...")
    sm.setSpeakerID(CYGNUS)
    if sm.sendAskAccept("The choice is yours. Listen to your heart. For the good of your own soul, and the good of the world...\r\nWill you come with me?"):
        sm.giveItem(1142399)  # Newborn Light (Medal)
        sm.giveItem(1052444)  # Apprentice Knight of Light Robe
        sm.giveItem(1302077)  # Beginner Warrior's Sword
        sm.giveNewSecondary(1098000)  # Soul Shield of Protection
        sm.removeEscapeButton()
        sm.sendNext("You need a name. How about... #b#e'Mihile'#k#n? It means 'Born of Light'. Let us go to Ereve.")
        sm.addLevel(2)
        sm.setJob(5100)
        sm.setSTR(4)
        sm.setINT(4)
        sm.setDEX(4)
        sm.setLUK(4)
        sm.setAP(4 + chr.getLevel() * 5)
        sm.startQuestNoCheck(29976)
        sm.completeQuestNoRewards(29976)
        sm.completeQuestNoRewards(20036)
        # the v265 Mihile story (36601-36630) plays in story instances this server has no scripts for;
        # Light Reborn (20810, 2nd job) needs 36630
        for q in [36601] + [q for q in range(36603, 36631) if q not in (36622, 36626)]:
            sm.completeQuestNoRewards(q)
        sm.warp(913070071, 0)
    else:
        sm.sendNext("Why are you refusing destiny?")
elif sm.getChr().getJob() == 5000 and sm.hasQuestCompleted(20034) and not sm.hasQuestCompleted(20035):
    sm.sendSayOkay("Neinheart wants to see what you can do. Please, humor him.")
else:
    sm.sendSayOkay("Light will guide you.")
sm.dispose()
