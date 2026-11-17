# 21734 - Traces of the Puppeteer
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tru (1002104)

sm.setSpeakerID(1002104)  # Tru
sm.sendNext("Are you busy? I have been looking all over Victoria Island in search of valuable information and found something that might intrigue you. It's about the #o9300346#...")
sm.sendSay("I don't know if you know this, but ever since you taught #o9300346# a lesson, the #m101030500# is sealed up. It looks like #o9300346# has moved to a new hideout.")
if sm.sendAskAccept("I heard that #b#o9300346#'s hideout#k is in #b#m100040000##k near #m100000000#. Use this opportunity to eliminate the #r#o9300346##k."):
    sm.startQuest(parentID)
    sm.sendNext("Come back after defeating the #o9300346#. I have some good news I want to share when you come back. Haha, you won't be disappointed.")
else:
    sm.sendNext("Hm? Hey, what are you saying? It's certified information provided to you by the finest source on this continent! You can trust me on this.")
