# 36310 About the Master of Disguise - end (Athena Pierce): takes Neinheart's Letter.
LETTER = 4033888
sm.setSpeakerID(1012100)
sm.sendNext("A letter from Neinheart? Let me see...")
sm.sendSay("A thief who can take anyone's face... So Olive may be telling the truth after all.")
if sm.completeQuest(parentID) and sm.hasItem(LETTER):
    sm.consumeItem(LETTER, 1)
sm.dispose()
