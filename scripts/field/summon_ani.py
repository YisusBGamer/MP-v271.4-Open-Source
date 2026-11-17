# 211061100-211061109 Ani's Jail (first enter): Prison Guard Ani (8210013) for The Lion King's Gatekeeper (3142), and
# for Looking Into the Lion King: Final (3147: "defeat the Prison Guard Ani, and bring back the Congealed Evil"; Ani
# drops it for characters on that quest, sql V118).
if ((sm.hasQuest(3142) and not sm.isComplete(3142)) or (sm.hasQuest(3147) and not sm.hasItem(4033114))) \
        and not sm.hasMobsInField(sm.getFieldID(), 8210013):
    pos = chr.getPosition()
    sm.spawnMob(8210013, pos.getX() + 300, pos.getY(), False)
