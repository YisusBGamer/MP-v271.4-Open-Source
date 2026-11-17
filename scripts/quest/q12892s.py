# 12892 - Catch the Cat Burglars
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Nolbu (9000120)

sm.setSpeakerID(9000120)  # Nolbu
if sm.sendAskAccept("I've been robbed! After ten long years, I finally saved up enough to take my family to the budget diner down the street for their summer vacation, and when we get back, all the gold coins I hid around the house were GONE! From the footprints the thieves left behind, I'm sure it's the work of the Street Cats. You have to teach them that no one steals from ME and gets away with it!"):
    sm.startQuest(parentID)
    sm.sendNext("The Street Cats are masters of disguise. They're hiding amongst monsters, disguised as balls of yarn. When you encounter a Ball of Yarn while you're fighting monsters, #rdouble-click#k it and ELIMINATE IT! I won't be avenged until you've eliminated #b#e5 Street Cats#k#n!")
else:
    sm.sendNext("But those are MY gold coins, and I want them back!")
