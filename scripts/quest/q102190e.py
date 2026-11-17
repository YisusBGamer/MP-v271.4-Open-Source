# [Legion Champion] Rank SSS Champion Trial (end): the boss was beaten solo in time (record 102198 clear=1)
from net.swordie.ms.client.character.union import ChampionModule

sm.setSpeakerID(9010112)
if sm.getQRValue(ChampionModule.TRIAL_RECORD, "clear") != "1":
    sm.sendSayOkay("It doesn't look like you completed the champion trial yet. Come back after you're qualified.\r\n\r\n#r* You can complete it by defeating Kalos the Guardian (Normal) Champion Mode.")
elif sm.completeQuest(parentID):
    sm.sendSayOkay("You proved you're qualified to be a Rank SSS Champion! Raise your rank in the #bLegion window#k.")
sm.dispose()
