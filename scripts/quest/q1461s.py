#   [Job Adv] (5th job)   5th Job : Blessings of the Goddess
# Streamlined: instead of travelling to Henesys, Pantheon and the Dark World Tree, the three goddesses send their
# Arcane Stones here (quests 1462-1464), and the 5th job can be awakened right away (1465).
from net.swordie.ms.enums import InvType

GODDESS_QUESTS = [1462, 1463, 1464]  # Maple World, Grandis, Tynerum
RECORD_OF_POWER = 1465

sm.setSpeakerID(2140001)
sm.sendNext("According to legend, after the fall of the 365 gods, the goddesses used the Erdas to sculpt this world into being. A select few have learned the art of #bmanipulating the Erda Flow#k from the goddesses themselves.")
if sm.sendAskYesNo("The goddesses of Maple World, Grandis and Tynerum have heard of your deeds. Rather than making you search the world for them, they have agreed to send their blessings here.\r\n\r\nShall I call upon them?"):
    missing = [q for q in GODDESS_QUESTS if not sm.hasQuestCompleted(q)]
    if chr.getInventoryByType(InvType.CONSUME).getEmptySlots() < len(missing):
        sm.sendSayOkay("Make room for {} item(s) in your Use inventory first, so you can receive the goddesses' Arcane Stones.".format(len(missing)))
    else:
        sm.startQuest(parentID)
        for q in missing:
            sm.completeQuestNoCheck(q)  # gives that goddess's Arcane Stone
        sm.completeQuest(parentID)
        sm.sendNext("The goddesses have answered. #bThe Arcane Stones of Maple World, Grandis and Tynerum#k are yours.\r\n\r\n#v2435734# #v2435735# #v2435736#")
        if sm.sendAskYesNo("With the stones, I can awaken the power of the Erda Flow within you now.\r\n\r\n#b(Select Yes to complete the #e5th Job Advancement#n.)#k"):
            if not sm.hasQuestCompleted(RECORD_OF_POWER):
                sm.completeQuestNoCheck(RECORD_OF_POWER)
                chr.getJobHandler().GiveVSkills(chr)
            sm.sendSayOkay("The Erda Flow is yours to command. Use it well.\r\n\r\n#b(You have unlocked the #eV Matrix#n.)#k")
        else:
            sm.sendSayOkay("Talk to me again when you are ready.")
sm.dispose()
