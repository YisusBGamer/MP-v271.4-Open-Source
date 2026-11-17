# Goddess of Maple World, Collapsing Erda 1: [6th Job] Goddess Inside Erda (38603): fill My Arcane Stone with hunting EXP to save her
from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ

STAGE = 38603


def faint_voice():
    # [6th Job] The Faint Voice (38604): her words, on its own record
    if sm.hasQuestCompleted(STAGE) and not sm.hasQuestCompleted(SJ.FAINT_VOICE):
        if sm.questStartDialogue(SJ.FAINT_VOICE, parentID):
            sm.setQRValue(SJ.FAINT_VOICE, "1")
            sm.completeQuest(SJ.FAINT_VOICE)
    elif sm.hasQuestCompleted(SJ.FAINT_VOICE):
        sm.sendSayOkay("Please, save the other goddesses deeper in the torrent.")


sm.setSpeakerID(parentID)
if sm.hasQuest(STAGE):
    if sm.questEndDialogue(STAGE, parentID):
        SJ.goddessSaved(chr)
        faint_voice()
    else:
        if not sm.hasItem(SJ.MY_ARCANE_STONE):
            sm.giveItem(SJ.MY_ARCANE_STONE)
        sm.sendSayOkay("#b(The goddess is still sealed in the crystal. Double-click #i2636387# #t2636387# and hunt to fill it with Arcane Power.)#k")
elif sm.hasQuestCompleted(SJ.NEW_ERDA) and not sm.hasQuestCompleted(STAGE):
    if sm.questStartDialogue(STAGE, parentID) and not sm.hasItem(SJ.MY_ARCANE_STONE):
        sm.giveItem(SJ.MY_ARCANE_STONE)
else:
    faint_voice()
sm.dispose()
