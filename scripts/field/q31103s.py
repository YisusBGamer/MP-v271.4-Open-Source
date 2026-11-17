# Henesys Ruins (271010000)
# [Henesys Ruins] Exploring the Future (31103) can only be started from here (Quest.wz makes it unstartable
# otherwise). Written from the quest's own summary: "Big Headward arrests you and takes you to the chief."
if sm.hasQuestCompleted(31102) and not sm.hasQuest(31103) and not sm.hasQuestCompleted(31103):
    sm.setSpeakerID(2142003)  # Big Headward
    sm.sendNext("You there! I've never seen you in this town before... Are you one of the Black Mage's spies? "
                "You're coming with me to see #b#p2142001##k!")
    sm.startQuestNoCheck(31103)
