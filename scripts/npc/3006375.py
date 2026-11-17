# Goddess of Grandis, Collapsing Erda 2: [6th Job] Goddess Inside Erda (2) (38605): fill My Arcane Stone to save her
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

STAGE = SJ.GODDESS_2

sm.setSpeakerID(parentID)
if sm.hasQuest(STAGE):
    if sm.questEndDialogue(STAGE, parentID):
        SJ.goddessSaved(chr)
    else:
        if not sm.hasItem(SJ.MY_ARCANE_STONE):
            sm.giveItem(SJ.MY_ARCANE_STONE)
        sm.sendSayOkay("#b(The goddess is still sealed in the crystal. Double-click #i2636387# #t2636387# and hunt to fill it with Arcane Power.)#k")
elif sm.hasQuestCompleted(SJ.FAINT_VOICE) and not sm.hasQuestCompleted(STAGE):
    if sm.questStartDialogue(STAGE, parentID) and not sm.hasItem(SJ.MY_ARCANE_STONE):
        sm.giveItem(SJ.MY_ARCANE_STONE)
else:
    sm.sendSayOkay("The voice in the Erda... It comes from deeper inside the torrent.")
sm.dispose()
