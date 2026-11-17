# 3455 - The Endangered Lives of Grays
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Alien Gray (2050002)

sm.setSpeakerID(2050002)  # Alien Gray
if sm.sendNext("You... you're back. My human friend, it's nice to see you again but I really don't have time to chat. Perhaps next time? \r\n#L0# #b(Activate the Wave Translator.)#k") != 0:
    sm.dispose()
if sm.sendSay("You can ask about Zeno all you want but I don't have anything to say. The seniors are in charge of Zeno, so I don't know anything.(Oh no, my palate is getting accustomed to human food. Everything about me is becoming more and more human.) \r\n#L0# #b(Wave Translator activated)#k   ") != 0:
    sm.dispose()
if sm.sendSay("More importantly, what happened to you, my human friend? You're not cooperating with the hypocrites in the Omega Sector, are you?(Even my standards for attractiveness are becoming human too. Agent Marco is probably the most handsome human ever.) \r\n#L0# #b(Wave Translator activated)#k ") != 0:
    sm.dispose()
if sm.sendSay("The Grays do not want to hurt the humans. We want to get closer. Don't you know? (No, forget it! If Prince finds out, he'll be furious!) \r\n#L0# #b(Wave Translator activated)#k ") != 0:
    sm.dispose()
if sm.sendSay("Don't be afraid of our guidance. Grays are on humans' side. We will lead the humans to their glory.(Let's think of more practical things... like washing my chute and drying food... I'm going to get alien's eczema soon.) \r\n#L0# #b(Wave Translator activated )#k") != 0:
    sm.dispose()
if sm.sendAskAccept("So... would you like to cooperate with us now?"):
    sm.startQuest(parentID)
    sm.sendNext("You must have decided to side with the Grays! Here, sign this contract to pledge your loyalty!")
else:
    sm.sendNext("Too bad. Well, I guess I'll see you around.")
