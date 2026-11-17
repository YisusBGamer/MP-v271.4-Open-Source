isChaos = 8920000 <= mob.getTemplateId() <= 8920003
chestMob = 8920006 if isChaos else 8920106

for chr in field.getChars():
    qm = chr.getQuestManager()
    if chr.hasQuestInProgress(30011):
        qm.completeQuest(30011) # [Root Abyss] Defeat the Third Seal Guardian
sm.spawnMobAppearing(chestMob, 41 if isChaos else 60, 135, 1) # treasure chest, GMS spot and effect