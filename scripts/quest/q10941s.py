# 10941 - A Helping Hand
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Hiya, #h0#! Have you reached Lv. 50 already? It's wonderful to see you developing so quickly. Well, here's the gift I promised you. "):
    sm.startQuest(parentID)
    sm.sendNext("Open your Inventory and check out your gifts! These items will make you look awesome! I can't wait to see what you do next!")
else:
    sm.sendNext("Oh, you don't want a gift? Okay. Talk to me if you change your mind.")
