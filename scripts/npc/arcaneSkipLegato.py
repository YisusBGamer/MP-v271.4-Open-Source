# Chief Legato's one-time offer on arriving in Nameless Town (ArcaneRiverSkipModule.offerOnArrival): skip the
# Vanishing Journey story (and any later area the level already allows). Opened by the server, not by clicking him;
# talking to him offers the same any time (npc/3003111.py).
sm.setSpeakerID(3003111)  # Chief Legato
REMINDER = "\r\n\r\n#b(You can talk to me any time to skip the Arcane River story.)#k"

areas = list(sm.getArcaneRiverSkipAreas())
if len(areas) > 0:
    allAreas = list(sm.getArcaneRiverAreas())
    text = ("Welcome to Nameless Town, traveler. The Arcane River's story starts here.\r\n\r\n"
            "If you'd rather not follow it, I can see to it that the story quests are finished for you. You won't get "
            "their EXP, mesos or items, but you'll receive each skipped area's #bArcane Symbol#k if you don't have it, "
            "and your #bArcane Symbol slots#k will open as if you had done them.\r\n\r\n#b")
    for a in areas:
        text += "#L{}#Skip the story up to and including {} (level {}+)#l\r\n".format(
            allAreas.index(a), a.getName(), a.getLevel())
    text += "#L99#No, I'll follow the story.#l"
    sel = sm.sendNext(text)
    valid = [allAreas.index(a) for a in areas]
    if sel in valid:
        chosen = allAreas[sel]
        if sm.sendAskYesNo("Skip the story of every area up to #b{}#k?\r\n\r\n#r[Warning]#k You won't experience the "
                           "story as it was meant to be played, and this can't be undone.".format(chosen.getName())):
            sm.sendSayOkay(sm.skipArcaneRiver(sel) + REMINDER)
        else:
            sm.sendSayOkay("Then may your journey go well." + REMINDER)
    else:
        sm.sendSayOkay("Then may your journey go well." + REMINDER)
sm.dispose()
