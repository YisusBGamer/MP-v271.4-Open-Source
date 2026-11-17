# 32415 - Start today's Cross World Party Quest! (end) | Spiegelette (9076000)
# QuestInfo 2: "You received bonus points for being diligent!" Act.img gives nothing, so the bonus is paid here in
# Party Points (the amount is not in the game data).
from net.swordie.ms.world.field.instance.instancehelper.pq import PartyQuestRecords as PR

BONUS_PARTY_POINTS = 10

sm.setSpeakerID(9076000)
if sm.questEndDialogue(parentID):
    PR.addPartyPoints(chr, BONUS_PARTY_POINTS)
elif sm.hasQuest(parentID):
    sm.sendSayOkay("Looks like you didn't complete the Cross World Party Quest 5 times! Complete them five times and "
                   "talk to me.")
sm.dispose()
