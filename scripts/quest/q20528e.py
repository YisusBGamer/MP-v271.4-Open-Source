# 20528 - Raising Mimio
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kiridu (1102002)

sm.setSpeakerID(1102002)  # Kiridu
sm.sendNext("Great. Now then, let's make this #t1902005# molt. If you feed it the formula you brought...")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("That's some shedding there. Wait, it's not #t1902005# anymore, but #t1902006#. #t1902006# will be a much more formidable and trustworthy companion for you as you travel your way around the world. Happy Mounting.")
