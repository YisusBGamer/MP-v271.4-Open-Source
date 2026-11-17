# 21733 - Gathering Some Strange Information
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tru (1204007)

sm.setSpeakerID(1204007)  # Tru
if sm.sendNext("Yo, where are you? We have a situation here! \r\n#b#L0# (Yo...? #p1002104# always called me 'hero,' not 'yo'...)#l") != 0:
    sm.dispose()
if sm.sendAskAccept("I have some very important information! Quick, come to #b#m104000004##k!"):
    sm.startQuest(parentID)
    sm.sendNext("It's seriously urgent! Come as quickly as possible!")
else:
    sm.sendNext("What are you saying? I'm in a rush right now! Stop with the nonsense and just come over here!")
