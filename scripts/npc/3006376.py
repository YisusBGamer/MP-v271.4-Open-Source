# Goddess of Grandis, Dwindling Torrent: [6th Job] Collect the <area> Symbol (38618-38623), one at a time
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

sm.setSpeakerID(parentID)
current = SJ.symbolQuest(chr)
if current != 0:
    n = current - SJ.FIRST_SYMBOL_QUEST + 1
    stone = SJ.stoneOf(current)
    if not sm.hasItem(stone):
        sm.giveItem(stone)
    sm.sendSayOkay("Fill #i%d# #t%d# with hunting EXP (double-click it first), then go through gate %d Deep Within Sol Erda to regain the Symbol." % (stone, stone, n))
elif sm.hasQuest(SJ.INTO_SOL_ERDA):
    for i in range(SJ.SYMBOL_QUESTS):
        q = SJ.FIRST_SYMBOL_QUEST + i
        if not sm.hasQuestCompleted(q):
            if sm.questStartDialogue(q, parentID):
                SJ.setRecordEntries(chr, q, "_f")
                if not sm.hasItem(SJ.stoneOf(q)):
                    sm.giveItem(SJ.stoneOf(q))
            break
    else:
        sm.sendSayOkay("Every Symbol is back. Tell the Goddess of Maple World.")
else:
    sm.sendSayOkay("The torrent is calming down.")
sm.dispose()
