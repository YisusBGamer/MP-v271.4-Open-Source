# 2149 - Truth of the Rumor-Manji
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Manji (1022002)

sm.setSpeakerID(1022002)  # Manji
if sm.sendNext("What is it?\r\n#b#L0#Have you heard of a ghost tree?#l") != 0:
    sm.dispose()
if sm.sendSay("You must have heard the story from cowards. There's no such thing as a ghost tree. I've been training myself for a long time around the rocks, trees and mountains in #m102000000#. I have never seen or heard about it for long.\r\n#b#L0#Ah, is that true?#l") != 0:
    sm.dispose()
sm.sendSay("The North Rocky Mountains have recently been under attack, and it's been bothering me...")
sm.startQuest(parentID)
