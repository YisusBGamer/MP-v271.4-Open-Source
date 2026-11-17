# 59196 - Fight for the Missing 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Apex (9390379)

sm.setSpeakerID(9390379)  # Apex
sm.sendNext("Good. There's not much time, so I'll cut to the chase.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The mysterious mage was last seen headed towards Magatia.")
    sm.sendSay("I escaped a couple days ago, but I'm sure that's where they were going. You can fly, right? I bet you can still reach them in time.")
    sm.sendSay("I wish I knew more, but we were blindfolded when we were being moved. You're just lucky my hearing is so good...")
    sm.sendSay("That's it. You and I both know there isn't much time. Get to #mMagatia#!")
