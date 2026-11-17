# 23905 - Town Filled With Suspicion
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ferdi (2152000)

sm.setSpeakerID(2152000)  # Ferdi
sm.sendNext("So you met #p2152016#. I told you it wouldn't be fun. Still, #p2152016# is the nicest of all the <Watchmen>.")
sm.sendSay("As you've undoubtedly learned by now, our town is under the control of the Black Wings. And we absolutely detest it! That's why we were suspicious of you, an outsider.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Now that it's been confirmed that you are not one of <Them>, the townspeople won't be suspicious of you anymore. Allow me to welcome you to #m310000000#. As long as you do not become friendly with the Black Wings, the residents of #m310000000# will accept you as their friend.")
