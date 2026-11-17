# NPC 3003146's Npc.wz script name is "npc_3003146", so this file runs when it is clicked instead of 3003146.py.
# Keep the two the same.
# Spiegelmann (3003146, Arcane River Express Pass), in every Arcane River town. Maple Points aren't used on this
# server, so instead he offers to skip the Arcane River storylines: the client opens the Arcane Symbol slots by story
# progress, so players who don't want the story can have it completed and progress by level and Arcane Power instead.
# The work is done by ArcaneRiverSkipModule. (Chief Legato offers the same while the client still hides Spiegelmann.)
# The skip offer comes first, and he always lists every area with what's possible there (skip now, story done, or the
# level it needs), so he never just says his line.
sm.setSpeakerID(3003146)

LINE = "Sometimes speed is more important than quality! I believe I may be of some assistance."


def status_text(allAreas, skippable):
    text = ""
    for a in allAreas:
        if a in skippable:
            state = "#bcan be skipped now#k"
        elif chr.getLevel() < a.getLevel():
            state = "#rneeds level {}#k".format(a.getLevel())
        else:
            state = "#gstory done#k"
        text += "\r\n- {} (Lv. {}+): {}".format(a.getName(), a.getLevel(), state)
    return text


allAreas = list(sm.getArcaneRiverAreas())
areas = list(sm.getArcaneRiverSkipAreas())
if not sm.hasFifthJob():
    sm.sendSayOkay(LINE + "\r\n\r\n#b(The Arcane River story can only be skipped after the 5th job advancement. It "
                   "starts with the Memory Keeper in the Temple of Time.)#k")
elif len(areas) == 0:
    sm.sendSayOkay(LINE + "\r\n\r\nThere's no Arcane River story I can skip for you right now:" +
                   status_text(allAreas, areas) +
                   "\r\n\r\n#b(Come back when you reach the next area's level.)#k")
else:
    text = ("If you'd rather not follow the story, I can see to it that the story quests of an area are finished "
            "for you. You won't get their EXP, mesos or items, but you'll receive the area's #bArcane Symbol#k if "
            "you don't have one, and your #bArcane Symbol slots#k will open as if you had done them." +
            status_text(allAreas, areas) +
            "\r\n\r\n#r[Warning]#k This can't be undone. How far should I take you? (Every area before it is "
            "included.)\r\n#b")
    for a in areas:
        text += "#L{}#Skip up to and including {} (level {}+)#l\r\n".format(allAreas.index(a), a.getName(),
                                                                          a.getLevel())
    text += "#L99#Nothing for now, thanks.#l"
    sel = sm.sendNext(LINE + "\r\n\r\n" + text)
    valid = [allAreas.index(a) for a in areas]
    if sel in valid:
        chosen = allAreas[sel]
        if sm.sendAskYesNo("Skip the story of every area up to #b{}#k?\r\n\r\n#rThis can't be undone.#k".format(
                chosen.getName())):
            sm.sendSayOkay(sm.skipArcaneRiver(sel))
sm.dispose()
