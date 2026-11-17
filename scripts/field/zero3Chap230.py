# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 324090230 Misty Forest 3 - Kidnapped! (40307), Friends and Enemies (40308): Milo waits
if (sm.hasQuest(40307) or (sm.hasQuestCompleted(40307) and not sm.hasQuestCompleted(40308))) \
        and not sm.hasQuest(40308) and chr.getField().getLifeByTemplateId(2440003) is None:
    sm.spawnNpc(2440003, 150, 543)
