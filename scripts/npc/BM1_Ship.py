# Small Airship (3003640 / 3003643 / 3003646), White Spear Decks 1-3.
# besides the Outpost (Deck 1) and the Crash Site (Deck 3), the airship flies to the ships of the
# Moonbridge story while one of their quests is under way (see zgen/gen_bm.py).

def active(*quests):
    for q in quests:
        if sm.hasQuest(q):
            return True
    return False


def available(q, prev):
    return sm.hasQuestCompleted(prev) and not sm.hasQuest(q) and not sm.hasQuestCompleted(q)


STORY_FIGHTS = (993060049, 993060067, 993060081)  # your own copy, the fight's monsters spawn on entry

field = sm.getFieldID()
dests = []  # (map id, label)
if field == 450009100:
    if active(35615):
        dests.append((993060049, "Beyond the barrier (the operation)"))
    elif active(35613) or available(35615, 35613):
        dests.append((993060038, "Resistance Flagship (Claudine)"))
    dests.append((450009050, "Outpost"))
elif field == 450009300:
    if active(35628):
        dests.append((993060067, "Detachment Airship (the Giant Monster)"))
    elif available(35628, 35620):
        dests.append((993060066, "Detachment Airship"))
    if active(35631):
        dests.append((993060081, "Eye of the Void Interior (the Core)"))
    labyrinth_story = active(36760, 36761) or available(36761, 36760)
    if labyrinth_story and chr.getLevel() >= 250:
        dests.append((993063000, "White Spear (Neinheart)"))
    dests.append((450011120, "Crash Site (Labyrinth of Suffering)"))

sm.flipDialoguePlayerAsSpeaker()
if not dests:
    sm.sendSayOkay("The fog still rages in this area. Boarding the airship here would be very risky.")
else:
    text = "Where do you want to go?#b\r\n"
    for i, (m, label) in enumerate(dests):
        text += "\r\n#L%d#%s#l" % (i, label)
    sel = sm.sendNext(text)
    if 0 <= sel < len(dests):
        m = dests[sel][0]
        if m == 450011120 and chr.getLevel() < 250:
            sm.sendSayOkay("You must be Level 250 or higher to enter the Labyrinth of Suffering.")
        elif m in STORY_FIGHTS:
            sm.warpInstanceIn(m, 0)
        else:
            sm.warp(m, 0)
sm.dispose()
