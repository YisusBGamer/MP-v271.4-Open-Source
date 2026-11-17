# 2631 - The Kerning City Book Store Owner 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Jeppi (1043005), taken from the other quest phase

sm.setSpeakerID(1043005)  # Jeppi
if sm.sendAskAccept("This is wonderful, simply delightful. If we can convince Jeppi to speak of the Dark Lord's corruption, the citizens of Kerning City will turn against him and the Dual Blades can make our move. Head to the #bGiant Tree#k outside #bElinia#k and talk to Jeppi. Quickly, now.\r\n\r\n#e(You will be moved to the Giant Tree in Ellinia when you accept.)#n"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Have you been a traitor all this time? I don't deal lightly with traitors...")
