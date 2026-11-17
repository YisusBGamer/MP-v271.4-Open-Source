# Goddess of Tynerum, Collapsing Erda 3: hands in [6th Job] Whose Voice Is It? (38606); Goddess Inside Erda (3) (38607)
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

STAGE = SJ.GODDESS_3

sm.setSpeakerID(parentID)
if sm.hasQuest(SJ.WHOSE_VOICE):
    sm.questEndDialogue(SJ.WHOSE_VOICE, parentID)
elif sm.hasQuest(STAGE):
    if sm.questEndDialogue(STAGE, parentID):
        SJ.goddessSaved(chr)
        sm.warp(SJ.DWINDLING, 0)  # the Symbol vanished beyond the torrent: the goddesses gather in the Dwindling Torrent
    else:
        if not sm.hasItem(SJ.MY_ARCANE_STONE):
            sm.giveItem(SJ.MY_ARCANE_STONE)
        sm.sendSayOkay("#b(The goddess is still sealed in the crystal. Double-click #i2636387# #t2636387# and hunt to fill it with Arcane Power.)#k")
elif sm.hasQuestCompleted(SJ.WHOSE_VOICE) and not sm.hasQuestCompleted(STAGE):
    if sm.questStartDialogue(STAGE, parentID) and not sm.hasItem(SJ.MY_ARCANE_STONE):
        sm.giveItem(SJ.MY_ARCANE_STONE)
elif sm.hasQuestCompleted(STAGE):
    sm.warp(SJ.DWINDLING, 0)
sm.dispose()
