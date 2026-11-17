# 10940 - Newbie's Gift
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hello, #h0#! Welcome to Maple World! We're in the midst of a fabulous event to welcome all our new Maplers! Would you like to receive your gift now?"):
    sm.startQuest(parentID)
    sm.sendNext("Open your Inventory and double-click on your gift! It's full of wonderful items to make you look your best! Oh, and there's more! When you reach Lv. 30, there are more gifts waiting for you! See you then!")
else:
    sm.sendNext("Oh, you don't want a gift? Okay. Talk to me if you change your mind.")
