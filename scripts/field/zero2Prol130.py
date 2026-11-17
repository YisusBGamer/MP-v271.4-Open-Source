# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 321190130 Shadowvale - We Want Answers (40505): Cassius; An Empty Space (40506): his dowsing tool
if sm.hasQuest(40505) and chr.getField().getLifeByTemplateId(2460017) is None:
    sm.spawnNpc(2460017, 300, -6)
elif sm.hasQuest(40506) and not sm.hasItem(4033921):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(Cassius's dowsing tool, left behind where he stood... We'll take it home.)")
    sm.giveItem(4033921)
    sm.warpInstanceOut(320000000, 0)
