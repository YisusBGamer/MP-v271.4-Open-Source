# Market Hoodlum (2400303), Shaded Black Market (402000210)
# Cadena, "Nabbing the Foodnapper" (34611): "You defeated all the Hoodlums and caught the petty thief" (Quest.wz;
# finished at the thief, 3001214, once its record is clear=1). Defeating as many Market Hoodlums as the market holds
# (22) sets the record, for whoever did the most damage.
FOODNAPPER = 34611
ALL = 22
hunter = mob.getMostDamageChar() if mob is not None else None
if hunter is not None and hunter.getQuestManager().hasQuestInProgress(FOODNAPPER):
    hsm = hunter.getScriptManager()
    if hsm.getQRValue(FOODNAPPER, "clear") != "1":
        try:
            done = int(hsm.getQRValue(FOODNAPPER, "hoodlum") or "0") + 1
        except ValueError:
            done = 1
        if done >= ALL:
            hsm.setQRValue(FOODNAPPER, "clear", "1")
            hunter.chatMessage("You taught all the Hoodlums a lesson. Now catch the thief!")
        else:
            hsm.setQRValue(FOODNAPPER, "hoodlum", str(done))
            hunter.chatMessage("Hoodlums taught a lesson: " + str(done) + "/" + str(ALL))
