# Fairy Fountain (38044, Shade) - the Fountainhead Pillar at the Fairy Fountain
# The client runs this script when Shade talks to the Fountainhead Pillar at the Fairy Fountain during the quest
# (Check.img npcSpeech 1). No official lines exist for it; written from the quest's own summary ("You used the Fairy
# Fountain Water to awaken the elves. Most of them said they were happier in their dreams").
ORDER = ["10332201", "21594763", "21594724", "21594715"]  # npcSpeech done-marks: NPC id + order


def mark_done(mark):
    """Adds this talk to the quest's NpcSpeech record (the same record the other npcSpeech scripts keep)."""
    value = sm.getQRValue(parentID) or ""
    done = set(value[len("NpcSpeech="):].split("/")) if value.startswith("NpcSpeech=") else set()
    done.add(mark)
    sm.setQRValue(parentID, "NpcSpeech=" + "/".join(m for m in ORDER if m in done))


WATER = 4034075  # Fairy Fountain Water

sm.setSpeakerID(1033220)
if sm.hasItem(WATER):
    sm.sendSayOkay("(The flask is already full of #t4034075#.)")
elif not sm.canHold(WATER):
    sm.sendSayOkay("(Make some room in your Etc inventory first.)")
else:
    sm.sendNext("(Clear water wells up from the white marble pillar. You fill a flask with #b#t4034075##k. "
                "It should be enough to wake the elves of Elluel from their dream.)")
    sm.giveItem(WATER)
    mark_done("10332201")
sm.dispose()
