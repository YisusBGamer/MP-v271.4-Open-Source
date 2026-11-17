# 31677 - Plan B (Angelic Buster), from Edea (3001100). Battlefield quest: accepting moves you to the battlefield;
# winning it (portal/finish_warZone1.py) sets record 31678.
sm.setSpeakerID(3001100)  # Edea
if sm.sendAskAccept(u"The #benemies will launch an all-out attack tonight#k. You are the hidden variable, the #rWILD CARD#k: hide and #battack the enemies from behind while they're distracted by the attack#k.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401072800)  # Occupied Heliseum Downtown, then the Foggy Valley
else:
    sm.sendNext("Talk to me again when your battle preparations are complete.")
sm.dispose()
