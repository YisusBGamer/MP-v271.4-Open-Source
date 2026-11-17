# Chief Legato (3003111), Nameless Town (Vanishing Journey): offers to skip the Arcane River storylines, first thing in
# his chat (always, also once Spiegelmann (3003146) shows up in the Arcane River towns, who offers the same). The work
# is done by ArcaneRiverSkipModule.
from net.swordie.ms.client.character import ArcaneRiverSkipModule

sm.setSpeakerID(3003111)


def skip_level(a):
    # Classic characters skip an area once they've outleveled it (the next area's level)
    if hasattr(ArcaneRiverSkipModule, "getSkipLevel"):
        return ArcaneRiverSkipModule.getSkipLevel(chr, a)
    return a.getLevel()

line = sm.getRandomNpcDefaultLine(parentID)
if line is None:
    line = "I hope for peace among the inhabitants of Nameless Town."

areas = list(sm.getArcaneRiverSkipAreas())
if len(areas) == 0:
    if sm.hasFifthJob():
        sm.sendSayOkay(line + "\r\n\r\n#b(There is no Arcane River story you can skip right now. New areas open up to "
                       "skipping as your level rises.)#k")
    elif chr.getLevel() >= 200:
        sm.sendSayOkay(line + "\r\n\r\n#b(Arcane Symbols only work after the 5th job advancement, and the story can "
                       "only be skipped after it. It starts with the Memory Keeper in the Temple of Time.)#k")
    else:
        sm.sendSayOkay(line)
else:
    allAreas = list(sm.getArcaneRiverAreas())
    text = line + "\r\n\r\n#b"
    text += "#L1#I'd rather skip the Arcane River story.#l\r\n"
    text += "#L0#Continue my journey through the Arcane River.#l"
    if sm.sendNext(text) == 1:
        text = ("If you'd rather not follow the story, I can see to it that the story quests of an area are finished "
                "for you. You won't get their EXP, mesos or items, but you'll receive the area's #bArcane Symbol#k if "
                "you don't have one, and your #bArcane Symbol slots#k will open as if you had done them.\r\n\r\n"
                "#r[Warning]#k You won't experience the Arcane River story as it was meant to be played. "
                "This can't be undone.\r\n\r\n"
                "How far should I take you? (Every area before it is included.)\r\n#b")
        for a in areas:
            text += "#L{}#Up to and including {} (level {}+)#l\r\n".format(allAreas.index(a), a.getName(), skip_level(a))
        sel = sm.sendNext(text)
        valid = [allAreas.index(a) for a in areas]
        if sel in valid:
            chosen = allAreas[sel]
            if sm.sendAskYesNo("Skip the story of every area up to #b{}#k?\r\n\r\n#rThis can't be undone.#k".format(
                    chosen.getName())):
                sm.sendSayOkay(sm.skipArcaneRiver(sel))
sm.dispose()
