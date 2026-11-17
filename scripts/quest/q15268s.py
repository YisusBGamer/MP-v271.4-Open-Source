# 15268 - [[2016 KMS NY Event]][Event] Happy New Every Day! Dokidoki Gift Day!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: [[2016 KMS NY Event]]Pink Mong (9001130)

sm.setSpeakerID(9001130)  # [[2016 KMS NY Event]]Pink Mong
sm.sendNext("[[2016 KMS NY Event]]Ook! It's the #bHappy New Every Day!#k Event!\r\nA dokidoki gift is prepared today! I, Pink Mong, prepared it!")
if sm.sendAskAccept("[[2016 KMS NY Event]]I gathered pretty chairs and an awesome damage skin. I'll give you one of them as a gift if you help me out.Ook! How about it? Will you listen to my request? Ook-ook!"):
    sm.startQuest(parentID)
    sm.sendNext("[[2016 KMS NY Event]]Actually... I wanted to send my friends a card as a New Year's greeting.But I was jumping around the trees and dropped the letters Happy New Year I prepared as decorations. The monsters took them all.\r\n")
    sm.sendSay("[[2016 KMS NY Event]]You don't need to find all of them but just 30 each! I will give you a gift if you bring me #b30 each of Happy, New, and Year#k! Oh, oh! You can get Happy, New, and Year decorations from #bmonsters around your level#k. Ook!")
else:
    sm.sendNext("[[2016 KMS NY Event]]I'm getting sullen... You'll be blessed in the new year if you help a friend.Don't be ashamed and talk to me if you're going to help! Ook.")
