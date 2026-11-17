# 2905 - Feats of Dairy-do
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tangyoon (1092000)

sm.setSpeakerID(1092000)  # Tangyoon
sm.sendNext("Hey, if you ain't too busy, mind giving me a hand? I was so busy cooking, I forgot to feed the cows. The PIRATE cows, that is\r\n")
if sm.sendAskAccept("The Baby Cows get hungry real fast, too. If I don't get some food in their bellies soon, they'll probably stampede.\r\nSo, what're you waiting for? Get feeding!"):
    sm.startQuest(parentID)
    sm.sendNext("All right, the ranch is just this way. All you need to do is collect Hay by hitting Haystacks with a #rRegular Attack#k and bring the Hay to the cows. You can handle this, right?")
    sm.sendSay("While you're at it, get #ba cup of Milk from the Mama Cow#k #rafter you feed the Baby Dairy Cows#k. Do a good job, and Ma will be happy to give you some milk.")
else:
    sm.sendNext("Fine, be that way. It won't be my fault when you get trampled in a Baby Cow stampede.")
