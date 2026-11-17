# 61274 - [Sizzling Santa] Naughty or Nice?
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Louie (9201499)

sm.setSpeakerID(9201499)  # Louie
sm.sendNext("Can I ask for just one more favor? I got this dry grass from around here...\r\nI bet it's really pretty speckled with dew. Dew might remind my friends of our innocent past.")
if sm.sendAskAccept("Can I ask for just one more favor? I got this dry grass from around here...\r\nI bet it's really pretty speckled with dew. Dew might remind my friends of our innocent past."):
    sm.startQuest(parentID)
    sm.sendNext("Thanks. Just watch it for a bit!")
