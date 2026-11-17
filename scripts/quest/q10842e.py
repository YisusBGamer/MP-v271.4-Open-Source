# 10842 - Gold Richie's Secret Weapon Test
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("So? How did the test go? My secret weapon was amazing, huh? Hey...what's with the long face?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("What? It failed? Hmm...strange. I thought the secret weapon would easily drive the Black Wings out of Edelstein.")
