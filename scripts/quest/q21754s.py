# Info on Another Seal Stone (21754, Aran) - Athena Pierce
# Written from the quest's own summary: "Athena Pierce flashed a big smile, saying that she knew she'd someday meet
# you again. She then handed you the letter."
ATHENA = 1012100
LETTER = 4032328  # Old Letter

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(ATHENA)
    if not sm.canHold(LETTER):
        sm.sendSayOkay("Please make some room in your Etc inventory first.")
    elif sm.sendAskAccept("I knew I'd meet you again someday, Aran. I've kept this letter for you all this time. "
                          "Please show it to #b#p1002104##k."):
        sm.startQuest(parentID)
        if sm.hasQuest(parentID) and not sm.hasItem(LETTER):
            sm.giveItem(LETTER)
sm.dispose()
