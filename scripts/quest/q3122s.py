# 3122 - Confirming the Seal
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Robeira (2020009)

sm.setSpeakerID(2020009)  # Robeira
if sm.sendNext("Are you crazy!? You're just going to take Shammos at his word? \r\n#b#L0#Well, it's not that I trust him, I just feel sorry for him. If we don't calm him down, I'm worried he might give himself a heart attack.#l\r\n#k") != 0:
    sm.dispose()
if sm.sendAskAccept("You pity him?! You don't even know anything about him! \r\nYou know what? Forget it. I don't want to waste any more time on that miserable Hoblin's delusions. If you want to go, then go. Just do NOT put your hands on the seal. Got it?"):
    sm.startQuest(parentID)
    sm.sendNext("Leave El Nath and go east. You'll reach #bIce Valley 2#k. From there, head to the #bIce Ravine Entrance#k and go through the cave to enter the Ice Gorge. You won't find anything there, since it's so cold. You better prepare yourself. It's much colder than even El Nath.  ")
else:
    sm.sendNext("What? Are you just wasting my time?")
