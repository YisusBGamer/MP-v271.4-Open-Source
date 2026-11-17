# 34624 - Hazardous Materials
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gen (3001200)

sm.setSpeakerID(3001200)  # Gen
sm.sendNext("As disappointing as it is, what Mr. Hazard says is true. I was informed of it at the meeting.\r\n")
sm.setSpeakerID(3001266)  # Branch Member B
sm.sendSay("What was HQ thinking when they agreed to this? I don't want to share profits with those jerks.\r\n")
if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(3001200)  # Gen
    sm.sendNext("They said this planet's under threat of attack and that Hazard's here to protect us.\r\n")
    sm.sendSay("But it sounds like a load of bull to me. That guy's a magnet for trouble. Whatever brought him here can't be good.\r\n")
    sm.sendSay("Uncomfortable as it is, we're going to be neighbors with Mr. Hazard for a while.\r\n")
    sm.sendSay("Just keep your heads down and mind your own business for now. And no fighting!\r\n")
    sm.sendSay("Whatever you do, don't fight 'em. We just have to wait them out, and eventually, they'll be out of our hair.\r\n")
