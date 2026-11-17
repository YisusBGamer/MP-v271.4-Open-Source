# 23016 - Secret Mech Ability
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Checky (2151004)

sm.setSpeakerID(2151004)  # Checky
sm.sendNext("Hey #h0#, keep this a secret from the Resistance but I found a way to enhance Mechs.")
sm.sendSay("All you have to do is... \r\nHEY! Where did that manual go?!")
if sm.sendAskAccept("I swear, I put it RIGHT HERE!"):
    sm.startQuest(parentID)
    sm.sendNext("Found it! I was so busy tinkering with these Mechs that I lost track of it for a moment, that's all.")
    sm.sendSay("So here's the deal... I've discovered how to change the color of your Mech!")
    sm.sendSay(" Not only that, but your Mech will be more powerful while disguised in its new color! That'll make it even easier to push those Black Wings back where they belong.")
    sm.sendSay("There's just one small issue. I need something called a #bMech Disguise Swap Chip#k to make the whole thing work. They're extremely rare and cost a whooping #e#r1,000,000 mesos#k#n. Also, I haven't quite stabilized the process yet, so your Mech's new color will be...random.")
    sm.sendSay("But I'm ready to begin the process once you bring me 1,000,000 mesos to reimburse me for the chip I bought.")
    sm.sendSay("That's right. I already purchased a chip for you. I'm such a nice guy, aren't I? All you have to do is bring me the mesos.")
    sm.sendSay("Well, that's all I wanted to say. You ready to change your Mech's color now?")
else:
    sm.sendNext("Where is it? WHERE IS IT?! Just give me a minute...")
