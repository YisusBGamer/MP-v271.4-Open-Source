# Fairy Fountain (38044, Shade) - Danika, Elder of War
# The client runs this script when Shade talks to Danika, Elder of War during the quest (Check.img npcSpeech 5). No
# official lines exist for it; written from the quest's own summary ("You used the Fairy Fountain Water to awaken the
# elves. Most of them said they were happier in their dreams").
ORDER = ["10332201", "21594763", "21594724", "21594715"]  # npcSpeech done-marks: NPC id + order


def mark_done(mark):
    """Adds this talk to the quest's NpcSpeech record (the same record the other npcSpeech scripts keep)."""
    value = sm.getQRValue(parentID) or ""
    done = set(value[len("NpcSpeech="):].split("/")) if value.startswith("NpcSpeech=") else set()
    done.add(mark)
    sm.setQRValue(parentID, "NpcSpeech=" + "/".join(m for m in ORDER if m in done))


sm.setSpeakerID(2159471)
if not sm.hasItem(4034075):
    sm.sendSayOkay("(Danika is lost in a dream. You'll need #b#t4034075##k from the Fairy Fountain to wake her.)")
else:
    sm.sendNext("(You sprinkle a few drops of #t4034075# on Danika.)")
    sm.sendSay("Hm? What... where am I? I was dreaming of better days. I'd almost have preferred to stay there.")
    mark_done("21594715")
sm.dispose()
