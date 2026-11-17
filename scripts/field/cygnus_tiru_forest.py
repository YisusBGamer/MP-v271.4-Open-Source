# 130010120 Tiru's Forest (see CustomFieldScripts) - The Lost Treasure (20850): the Master of Disguise was hiding here
if sm.hasQuest(20850) and not sm.isComplete(20850) and chr.getField().getLifeByTemplateId(9300743) is None:
    sm.chatScript("Someone among the Tirus isn't what they seem... It's the Master of Disguise!")
    sm.spawnMob(9300743, -2000, 88, False)
