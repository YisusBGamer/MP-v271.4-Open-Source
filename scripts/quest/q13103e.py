# 13103 - [Maple Castle] A Tall Order
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cygnus (9000136)

sm.setSpeakerID(9000136)  # Cygnus
sm.sendNext("Did you find anything useful?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Oh, are those test sheets? Hm...these are from some #bMagic School#k. Wait, are these from the #btop magic school of Maple World#k? ")
    sm.sendSay("I've heard legends of this school, but seeing these test results really makes it real. Er... I will take care of these...embarrassing test sheets. Hey, don't look at the name! Stop stop stop! STOP!\r\n\r\nWait! I thought of something more #binteresting than checking names on the test sheets#k!")
    sm.sendSay("#bNeinheart#k apparently found #bMaple Castle artifacts#k while preparing for the party. I bet #bNeinheart#k knows a lot about this place. Why don't you talk to him?\r\n\r\nAnd get away from the test sheets!")
