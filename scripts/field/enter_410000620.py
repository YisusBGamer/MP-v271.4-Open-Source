# 410000620 (Cernium), on entering.
# [Cernium (Before)] See You Around, Seren (39817) is handed in only once its record says Roland was stopped here
# (Check.img infoex: quest 39817, key "mp" = 1, "Stop Roland"); the live server's scene sets it. Here the scene is
# told and the record set, and the player is sent on to Seren.

QUEST = 39817
SEREN_MAP = 410000500 # Cernium Square, where Seren (3004416) waits

if sm.hasQuest(QUEST) and not sm.hasQuestCompleted(QUEST) and sm.getQRValue(QUEST).find("mp=1") < 0:
    sm.setSpeakerID(3004409) # Carlisle
    sm.sendNext("#b(You find Roland here. He has lost all faith in the gods of Cernium.)#k")
    sm.sendNext("#b(You try to stop him, but he slips away in the confusion, and you lose sight of him, possibly "
                "forever.)#k")
    sm.setQRValue(QUEST, "mp", "1")
    if sm.sendAskYesNo("Seren will want to know what happened.\r\n\r\nShall I take you to #b#m" + str(SEREN_MAP)
                       + "##k?"):
        sm.warp(SEREN_MAP)
sm.dispose()
