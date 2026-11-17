# Finishes a job story SKIP that ended without its levels, job and quests (!script Npc storySkipRedo).
# The job path record is set aside while it runs, so it also works for characters that used @job first.
from net.swordie.ms.client.character import StorySkipModule
from net.swordie.ms.constants import GameConstants, JobConstants

STORY_QR = 89999943

qm = chr.getQuestManager()
sm.setSpeakerID(2007)
if JobConstants.isZero(chr.getJob()) or chr.getLevel() >= 30 \
        or JobConstants.getAdvancementTier(chr.getJob()) >= 2:
    sm.sendSayOkay("Your job story skip has nothing left to finish.")
else:
    path = qm.getHiddenRecord(GameConstants.JOB_PATH_QR)
    if path:
        qm.setHiddenRecord(GameConstants.JOB_PATH_QR, "")
    qm.setHiddenRecord(STORY_QR, "skip;0")
    try:
        StorySkipModule.run(sm, chr)
    finally:
        if path:
            qm.setHiddenRecord(GameConstants.JOB_PATH_QR, path)
sm.dispose()
