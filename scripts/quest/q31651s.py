# 31651 - Dream Team (Angelic Buster), from Edea (3001000). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31652 and Edea hands it in there (Eskalade, the
# quest's end NPC, isn't on any map).
sm.setSpeakerID(3001000)  # Edea
if sm.sendAskAccept("Kaiser is luring enemies into a corner so Edea can take them all out with a huge burst of magic, but there are a few stragglers. #bIt's up to you to herd the stragglers over to Kaiser.#k\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070600)  # Occupied Heliseum Downtown, then the Foggy Valley
else:
    sm.sendNext(u"We\u2019re not ready yet!")
sm.dispose()
