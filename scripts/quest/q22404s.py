# 22404 - Making a Saddle
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Kenta (2060005)

sm.setSpeakerID(2060005)  # Kenta
sm.sendNext("Hmm... That is a really strange-looking lizard. It almost looks like a Dragon. Of course I doubt anyone would ride a Dragon! You have a really peculiar mount.")
sm.sendSay("I've only seen people riding birds, pigs, wolves, etc. so I know the saddle sizes of those animals, but I can't even begin to guess at the size of this strange animal. Let me take some measurements.")
sm.sendSay("#b(#p2060005# takes measurements of #p1013000#'s waist, wing span, head size, tail circumference, and more. But why is he measuring the length of his nails? And why is he making him open his mouth?)#k")
sm.sendSay("Hmm. The size of this animal is so different! You're going to have to #bspecial order a saddle#k. Is that okay with you?")
sm.startQuest(parentID)
