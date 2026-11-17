# 57408 - Blind and Battered 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("Did you strike fear into the hearts of the enemy?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Oh, wow! You actually survived! That should keep Momijigaoka safe for now. Oda's Army must not be used to this new world either.")
    sm.sendSay("Good work. Return to Momijigaoka and take a break.")
    sm.sendSay("Those Scouts' reports contain some interesting info. You should take a look.")
