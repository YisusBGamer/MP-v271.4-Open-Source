# 10842 - Gold Richie's Secret Weapon Test
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
if sm.sendAskAccept("Finally, Gold Richie's secret weapon is complete! Wanna see?"):
    sm.startQuest(parentID)
    sm.sendNext("Gaga has been developing a secret weapon for me. After all sorts of struggles, it's finally finished. With the new weapon, we should be able to drive the Black Wings out of Edelstein in no time! Yes! Hahahahaha!")
    sm.sendSay("I'll let you test this secret weapon. Normally, I would do it myself, but I have a lot of business to take care of. I swear! It's not because I think it'll be dangerous or anything... Honest.")
    sm.sendSay("There is a problem, though. I lost the Ignition Key while I was out walking. And, you can't activate the secret weapon without the Ignition Key...")
    sm.sendSay("I hadn't gone too far from here when I lost the Ignition Key, so you should be able to find it somewhere nearby. After you find the Ignition Key, execute the Secret Weapon Test immediately. I left the secret weapon next to Gaga.")
else:
    sm.sendNext("You're not interested? But, this is Gold Richie's secret weapon!")
