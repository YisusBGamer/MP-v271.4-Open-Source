# 11468 - [Legends] The Monster Park Five!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9071000)

sm.setSpeakerID(9071000)  # Spiegelmann
sm.sendNext("Did you clear my Monster Park dungeon five times? Did you REALLY?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Well then, good for you. You must have wanted these #r#t4310027##k a whole lot. They're yours now, so it paid off, didn't it? Ha! Keep at it until 1/31/2012 to get even more!")
