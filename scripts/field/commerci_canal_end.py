# 865020500 Canal 6 (see CustomFieldScripts): Riverson waits at the end of the canals for The Raging River (17703) and
# [Mission] Eliminate Riverson (17922) - he isn't in the map's data
if chr.getField().getLifeByTemplateId(9390865) is None:
    for q in (17703, 17922):
        if sm.hasQuest(q) and not sm.isComplete(q):
            sm.chatScript("Riverson rises from the canal!")
            sm.spawnMob(9390865, 200, 288, False)
            break
