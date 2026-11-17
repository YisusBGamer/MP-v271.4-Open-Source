# 3150 - Luden's Warning 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Luden (2161002)

sm.setSpeakerID(2161002)  # Luden
sm.sendNext("Excellent work. This #rEquip Enhancement Scroll#k can imbue your equipment with untold power. It is important to note, however, that this can #ronly be used on weapons with no available upgrade count remaining#k.")
sm.sendSay("What you need to remember is that #rthe success rate will decrease the more your enhancement stats increase#k. Also, #ryour equipment can be destroyed if enhancement fails#k. I have heard tale of a powerful scroll that can prevent your weapon from being destroyed, but I have never actually seen one.")
sm.sendSay("Enhanced equipment will have an additional mark in its description. If you see equipment with a star at the market, you can bet it will be a good purchase.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I have told you everything I know about the equipment scroll. I truly hope that you get your hands on powerful equipment to help you stop our fallen king.")
