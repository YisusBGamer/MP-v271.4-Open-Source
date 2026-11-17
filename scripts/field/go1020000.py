# Split Road of Destiny (1020000)
sm.showEffect("Map/Effect.img/maplemap/enter/1020000")
sm.log.info("[{}] Managed to enter map".format(
    chr.getName()))
# Explorer story: the destroyed Maple Island's copy of this map (1120000) - water for the burning Maple Tree
# (32317 The Girl and the Maple Tree, 32318 Wounded Hands)
if sm.getFieldID() == 1120000:
    for quest, bucket in ((32317, 4033894), (32318, 4033896)):
        if sm.hasQuest(quest) and not sm.hasItem(bucket):
            sm.giveItem(bucket)
            sm.chatScript("You fill the bucket with water from the stream.")

