# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# The Signal Grew Stronger (31374), talk 1 - Lil Lady (2192027): "Ask around at Extreme Station <3> to see if anyone
# has the ribbon brooch." Her lines are her own Npc.wz lines.
ORDER = ["21920271", "21920282", "21920183"]  # npcSpeech done-marks: NPC id + order (Lil Lady, Ladies' Man, Caped Mascot)


def mark_done(mark):
    value = sm.getQRValue(parentID) or ""
    done = set(value[len("NpcSpeech="):].split("/")) if value.startswith("NpcSpeech=") else set()
    done.add(mark)
    sm.setQRValue(parentID, "NpcSpeech=" + "/".join(m for m in ORDER if m in done))

sm.setSpeakerID(2192027)
sm.sendSayOkay("A ribbon brooch? Nope! I want to ride that too! ...Have you seen my mommy?")
mark_done("21920271")
sm.dispose()
