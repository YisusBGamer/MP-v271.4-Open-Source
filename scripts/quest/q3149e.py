# 3149 - Luden's Warning 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Luden (2161002)

sm.setSpeakerID(2161002)  # Luden
sm.sendNext("Well done! That scroll was given out to honored knights before our king turned into... It is a shame to see them in those monsters' hands.")
sm.sendSay("I apologize. An old man gets caught up thinking of the past sometimes. You may take that Lion King's Weapon Scroll you got and #rapply it to your equipment for a 70% chance of upgrade#k. Give it a shot if you're curious! ")
sm.sendSay("Many weapon scrolls exist throughout Maple World. Just remember, #rusing these scrolls comes with a risk of destroying the weapon you are attempting to enhance#k. It is important to consider the possible consequences. But do not worry for now. The Lion King's Weapon Scroll will not destroy your weapon, even should it fail.")
sm.sendSay("Finally, #ravailable equipment upgrade counts are displayed in an item's details#k. Items can only be upgraded a limited number of times, so pay close attention to the scrolls you receive before you go willy-nilly with the upgrading.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I've told you everything you need to know about the weapon scroll. I truly hope that you get your hands on a powerful weapon to help you stop the fallen king.")
