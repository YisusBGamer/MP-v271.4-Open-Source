# 51216 - Clearing Party Quests
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Principal Researcher Bass (9250134)

sm.setSpeakerID(9250134)  # Principal Researcher Bass
sm.sendNext("I've been thinking... If the aliens realized how united Maplers are, they might think twice about invading.")
sm.sendSay("So the OSSS will issue a #b#t04310004##k to anyone who clears one of various party quests.")
sm.sendSay("It doesn't matter which of the following party quests you clear. Just remember that you can only receive a medal for this once every day. \r\n\r\n#bParty Quest Type:\r\n#b<Moon Bunny>, <First Time Together>, <Ariant Coliseum>, <The Crack of Dimensions>, <Forest of Poison Fog>, <Monster Carnival>, <Monster Carnival 2>, <Remnant of the Goddess>, <Lord Pirate>, <Romeo and Juliet>, <Dragon Rider>.\r\n")
if sm.sendAskAccept("We ask for your assistance.\r\nYour help is greatly needed if we are to scare off those aliens."):
    sm.startQuest(parentID)
    sm.sendNext("Good luck.\r\nRemember that the future of Maple World is in your hands.")
else:
    sm.sendNext("Who then will stand up for Maple World...?")
