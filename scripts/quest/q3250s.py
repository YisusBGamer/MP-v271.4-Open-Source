# 3250 - Operation Strategy 3: A Cute Baby Bird
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Sodane (9073007)

sm.setSpeakerID(9073007)  # Sodane
sm.sendNext("Wow! I fed this one, this one... did I feed this one? No! I haven't fed the 3rd one yet! Here, have some food. Hehehe, it fills my appetite to just see them eat... Oh, are you here? ")
sm.sendSay("Pheeeew... I've been running around all day. Some researchers have said that #o5220003#s, though they've undergone changes under the management of Papulatus, can become cute, obedient birds when you tame them. Since all the monsters have been wiped out, I've decided to raise birds with all the time in my hands.")
if sm.sendAskAccept("But it's so much work to raise not just one, but ten birds at once... not to mention how hard it is to find food to feed all of them. Well, they're much too cute, though. Would you like to raise one?"):
    sm.startQuest(parentID)
    sm.sendNext("Here, I'll give you one. Please take good care of it. You can feed them #b#t4031992##k that feed off of monsters at the Clocktower.")
    sm.sendSay("#o5220003#s must be returned to their original habitat when full-fledged, so bring the #o5220003# back to me #bwhen it's fully grown#k. I'll be counting on you.")
else:
    sm.sendNext("Hmm... Guess you're not a huge fan of animals. But how? They're so cute... I'd even give them my hamburger.")
