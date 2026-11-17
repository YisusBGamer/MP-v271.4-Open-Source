# 32913 - [Orange Mushroom] Yearning to Jump
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Scholarly 'Shroom (9000292)

sm.setSpeakerID(9000292)  # Scholarly 'Shroom
if sm.sendAskAccept(" Heya, human! Kind of weird for you to be studying Orange Mushrooms, huh? Still, we like the attention."):
    sm.startQuest(parentID)
    sm.sendNext("First, I'll tell you a little bit about us. Listen up.")
    sm.sendSay("Did you know that all Orange Mushrooms love to jump? I bet you didn't! Explains a lot about mushrooms bouncing around the forest, right?")
    sm.sendSay("I once bounced along as they did. Never questioning, never wondering... why?")
    sm.sendSay("The whole jumping business never sat well with me, you see. I'm a little too... refined for that. Yet I'd be reading a very smart-looking book, no pictures of course, and I'd suddenly jump and hit the roof. It would happen everywhere! At home, at work, on the toilet... Terrible thing.")
    sm.sendSay("I looked into this strange instinct, and found that it's one of Maple World's 11 great monster mysteries! I devoted my research to the matter, and I've already got TWO hypotheses. Yeah, I know... pretty impressive.")
    sm.sendSay("Let's look into the first hypothesis. Talk to me when you're ready.")
else:
    sm.sendNext("Oh, come on! Let's explore! Research! Investigate!")
