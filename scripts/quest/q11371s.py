# 11371 - Find My Monkey Dolls!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

sm.setSpeakerID(9000021)  # Gaga
if sm.sendAskAccept("So, you're going to help me get my Monkey Dolls back? Great! Now, hop to it!"):
    sm.startQuest(parentID)
    sm.sendNext("You can get #t03994360#s from #rmonsters that are between 10 levels below you and 20 levels higher than you#k, #b#h0##k. Please bring back my Monkey Dolls! I'll be waiting with #b#t02430395##k and #b#t02430396##k.")
else:
    sm.sendNext("I really need your help, #b#h0##k. Please come again and help me out!")
