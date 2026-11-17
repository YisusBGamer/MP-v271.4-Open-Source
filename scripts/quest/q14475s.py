# 14475 - [11th Anniversary] Share That Festive Feeling!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Manager Mush (9000291)

sm.setSpeakerID(9000291)  # Manager Mush
sm.sendNext(" Now I'll teach you how to buff other players. Once I'm done talking, I'll give you a special item. All you have to do is use that item and the person next to you will get a buff! See? It's simple!")
if sm.sendAskAccept("Now go on and share the buff!"):
    sm.startQuest(parentID)
    sm.sendNext("Don't forget, using the 11th Anniversary Sharing Spirit from your Inventory will automatically buff the person nearest to you.")
else:
    sm.sendNext("It'd be even better if you could share the joy with others...")
