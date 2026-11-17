# Jett tutorial | 552000010 Space Station Hectare 1: the station's guards took out the crew; Burke has Jett clear
# them (53245, turned in to Burke), then sends her after the master key (53247)
BURKE = 9270083
GUARD = 9420564
GUARD_HP = 60 # the data's 5,000,000 is beyond a level 1 character

if not any(n.getTemplateId() == BURKE for n in sm.getField().getNpcs()):
    sm.spawnNpc(BURKE, 553, -120)
if not sm.hasQuest(53245) and not sm.hasQuestCompleted(53245):
    sm.lockInGameUI(True, False)
    sm.setSpeakerID(BURKE)
    sm.removeEscapeButton()
    sm.sendNext("Jett! The guards got the rest of the crew. They're coming for us next!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("Then I'll deal with them. Stay behind me, Burke.")
    sm.lockInGameUI(False)
    sm.startQuestNoCheck(53245)
if sm.hasQuest(53245) and not sm.hasMobsInField():
    for x in (-300, 0, 300):
        sm.spawnMob(GUARD, x, -120, False, GUARD_HP)
