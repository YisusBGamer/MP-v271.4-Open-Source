# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# The Signal Grew Stronger (31374), talk 3 - the Caped Mascot (2192018): "The caped mascot told you to talk to him
# later." (his Npc.wz line: "Those troublesome monsters.")
ORDER = ["21920271", "21920282", "21920183"]  # npcSpeech done-marks: NPC id + order (Lil Lady, Ladies' Man, Caped Mascot)


def mark_done(mark):
    value = sm.getQRValue(parentID) or ""
    done = set(value[len("NpcSpeech="):].split("/")) if value.startswith("NpcSpeech=") else set()
    done.add(mark)
    sm.setQRValue(parentID, "NpcSpeech=" + "/".join(m for m in ORDER if m in done))

sm.setSpeakerID(2192018)
sm.sendSayOkay("A ribbon brooch? Hmm... I'm a little busy with those troublesome monsters right now. Come talk to me later.")
mark_done("21920183")
sm.dispose()
