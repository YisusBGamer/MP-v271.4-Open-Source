# Monster Park - final stage exit: EXP, the day-of-the-week box, and (first clear of the day) bonus coins.
# The clear counts against the daily limits here (MonsterPark.recordClear); over them, no reward.
from net.swordie.ms.client.character.quest import Quest
from net.swordie.ms.constants import GameConstants
from net.swordie.ms.enums import QuestStatus
from net.swordie.ms.client.character import MonsterPark

COIN = MonsterPark.COIN  # Monster Park Commemorative Coin

qm = sm.getChr().getQuestManager()
quest = qm.getQuestById(GameConstants.MONSTER_PARK_EXP_QUEST)
if quest is None:
    quest = Quest(GameConstants.MONSTER_PARK_EXP_QUEST, QuestStatus.Started)
    quest.setQrValue("0")
    qm.addQuest(quest)
try:
    exp = long(quest.getQRValue())
except:
    exp = 0

# first clear of the day for this character (GMS: 20 coins); Sundays give +50% EXP
firstClear = MonsterPark.isFirstClearToday(chr)
bonus = MonsterPark.FIRST_CLEAR_COINS if firstClear else 0
exp = MonsterPark.getClearExp(exp)

fieldID = sm.getFieldID()
if not sm.hasMobsInField():
    block = MonsterPark.getClearBlock(chr) if hasattr(MonsterPark, "getClearBlock") else None
    if block is not None:
        # over today's clears (another character of the world finished meanwhile, or the ticket is gone)
        sm.setSpeakerID(9071005)
        sm.sendSayOkay(block)
        sm.warpInstanceOut(951000000)
        sm.completeQuest(quest.getQRKey())
        sm.stopEvents()
    elif fieldID / 1000000 == 952 or fieldID / 1000000 == 953 or fieldID / 1000000 == 954:
        reward = sm.getMPReward()
        sm.setSpeakerID(9071005)
        text = "Did you have a blast in Monster Park? Here's your reward\r\n" \
               "\r\n" \
               "#e#bDay-of-the-week reward: #v" + str(reward) + "##t" + str(reward) + "#\r\n" \
               "EXP reward: " + sm.formatNumber(str(exp))
        if bonus > 0:
            text += "\r\nFirst clear of the day: #v" + str(COIN) + "# " + str(bonus) + " #t" + str(COIN) + "#s"
        if hasattr(MonsterPark, "getFreeClearsLeft") and MonsterPark.getFreeClearsLeft(chr) <= 0:
            text += "#n\r\n\r\nThis clear uses one Monster Park entry ticket."
        sm.sendNext(text)
        if sm.canHold(reward) and (bonus == 0 or sm.canHold(COIN, bonus)):
            block = MonsterPark.recordClear(chr)
            sm.warpInstanceOut(951000000) #Monster Park
            if block is None:
                sm.giveExp(exp)
                sm.giveItem(reward)
                if bonus > 0:
                    sm.giveItem(COIN, bonus)
            else:
                chr.chatMessage(MonsterPark.plain(block))
            sm.completeQuest(quest.getQRKey())
            sm.stopEvents()
        else:
            sm.sendSayOkay("Please make some space in your Use and Etc inventories before leaving.")
    else:
        sm.chat("This is uncoded for the current map (" + str(fieldID) + ")")
else:
    sm.chat("Please kill all monsters first.")
