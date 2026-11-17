# 6009 - Dragon Rider
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gritto (2081200)

sm.setSpeakerID(2081200)  # Gritto
sm.sendNext("Once you are ready to accept your new companion, step up. You will now be encountering a mysterious being totally unlike anything you've ever faced.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You are the one who has already exceeded the boundaries and the limits of a human being... the only time the proud #t1902002# bows its head is to you. Hopefully you'll experience new, amazing adventures that are surely to await you with #t1902002#...")
