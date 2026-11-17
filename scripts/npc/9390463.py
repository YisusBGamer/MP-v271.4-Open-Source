# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Arby (9390463), Wolf Woods - his Npc.wz script, "al_tuto01"
# Becoming a Hero... (59006) waits for its record "ap": set here once every Ability Point is assigned.
ARBY = 9390463
sm.setSpeakerID(ARBY)
if sm.hasQuest(59006):
    if sm.getChr().getAvatarData().getCharacterStat().getAp() == 0:
        sm.setQRValue(59006, "ap", "1")
        sm.sendNext("See? You're already stronger!")
        sm.setPlayerAsSpeaker()
        sm.sendNext("Whoa, I totally feel stronger now. HYAH! Hero-ville, here I come!")
        sm.completeQuest(59006)
    else:
        sm.sendSayOkay("Press the S key to open the stat window. I have no idea how I know that!")  # official (Say.img)
elif sm.hasQuest(59005) and "mob=5" not in (sm.getQRValue(59005) or ""):
    sm.sendSayOkay("The wolves! Watch out!")
else:
    sm.sendSayOkay("Meow! You're really something, you know that?")
sm.dispose()
