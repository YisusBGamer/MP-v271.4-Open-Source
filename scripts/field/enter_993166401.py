# A Path Out of the Forgotten Forest (993166401): Kimu starts Knight's Path (36451) and The Empress's Spear and Shield
# (36452); the way out (out_993166401) opens once 36452 has been taken up.
if not sm.hasQuestCompleted(36451) and not sm.hasQuest(36451):
    sm.chatScript("Talk to Kimu.")
