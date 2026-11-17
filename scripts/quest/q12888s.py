# 12888 - Gentle Breeze Fan
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mar the Fairy (9000116)

sm.setSpeakerID(9000116)  # Mar the Fairy
if sm.sendAskAccept("Eep! My pets! My poor pets! They're dying of heat stroke! Well, not dying, but they're tired. Real, real tired. If only I had my #e#bGentle Breeze Fans#k#n. I could use them to keep them cool and lovely and adorable. But mean monsters borrowed all my fans, and they won't give them back. Can you retrieve them for me,#h0#? Pretty please?"):
    sm.startQuest(parentID)
    sm.sendNext("The monsters pass the fans amongst each other, so I don't know who has them right now. You'll just have to eliminate them all! Get me #e#b5 of my Gentle Breeze Fans#k#n back! Only monsters between 11 levels below and 21 levels above you will carry them!")
else:
    sm.sendNext("Oh, my poor pets. Look, I think one of them died. Oh, he's just wagging his tail. Still, it's just a matter of time.")
