# Fairy Fountain (38044, Shade) - Astilda, Elder of Life
# The client runs this script when Shade talks to Astilda, Elder of Life during the quest (Check.img npcSpeech 4).
# No official lines exist for it; written from the quest's own summary ("You used the Fairy Fountain Water to awaken
# the elves. Most of them said they were happier in their dreams").
ORDER = ["10332201", "21594763", "21594724", "21594715"]  # npcSpeech done-marks: NPC id + order


def mark_done(mark):
    """Adds this talk to the quest's NpcSpeech record (the same record the other npcSpeech scripts keep)."""
    value = sm.getQRValue(parentID) or ""
    done = set(value[len("NpcSpeech="):].split("/")) if value.startswith("NpcSpeech=") else set()
    done.add(mark)
    sm.setQRValue(parentID, "NpcSpeech=" + "/".join(m for m in ORDER if m in done))


sm.setSpeakerID(2159472)
if not sm.hasItem(4034075):
    sm.sendSayOkay("(Astilda is lost in a dream. You'll need #b#t4034075##k from the Fairy Fountain to wake her.)")
else:
    sm.sendNext("(You sprinkle a few drops of #t4034075# on Astilda.)")
    sm.sendSay("...Ah. So it was only a dream. It was a happier place than this, I'm afraid.")
    mark_done("21594724")
sm.dispose()
