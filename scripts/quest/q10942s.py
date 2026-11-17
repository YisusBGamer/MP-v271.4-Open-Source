# 10942 - Elite Player's Gift
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #h0#! You already reached Lv. 30? Amazing! Now, for that gift that I promised... Are you ready?"):
    sm.startQuest(parentID)
    sm.sendNext("Open your Inventory and double-click on the gift. This will give you a really special look. Oh, and one more thing: When you reach Lv. 50, I'll have some more gifts for you! Keep up the good work!")
else:
    sm.sendNext("Oh, you don't want a gift? Okay. Talk to me if you change your mind.")
