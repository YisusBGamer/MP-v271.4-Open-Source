# 3151 - Luden's Warning 3
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Luden (2161002)

sm.setSpeakerID(2161002)  # Luden
sm.sendNext("Ah, so you've found a #rPotential Scroll#k already! How fortunate! It has a rather interesting power that lesser scrolls lack.")
sm.sendSay("First, you can only use Potential Scrolls on #ritems of normal rank#k. When used, #ra randomly selected rank and hidden potential will be added to your equipment#k, giving you a significant boost in performance.")
sm.sendSay("Once an equipment has been blessed by a Potential Scroll, you will not be able to use another. If your luck is good, you will get the configuration you desire. There is a box of some sort that can reset these potentials, but I have no knowledge of it.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I have shared all the knowledge I can of the Potential Scroll. I hope its power will be enough to aid you in stopping our fallen king.")
