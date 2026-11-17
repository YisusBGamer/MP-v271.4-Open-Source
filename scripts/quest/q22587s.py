# 22587 - Map of Turtle Island
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Captain Hwang (2092001)

sm.setSpeakerID(2092001)  # Captain Hwang
sm.sendNext("Are you an explorer? I feel a strangely powerful energy emanating from you. I suppose you've come to see me. What is it that you need?\r\n#b#L0#Do you have the #t4032472#?#l\r\n#k")
sm.sendSay("You must be talking about the island I saw while fishing far out at sea. Yes. I have the map. I can't go out there anymore because of pirates, but I've still got the maps.\r\n#b#L0#Could you give me the #t4032472#?#l\r\n#k")
sm.sendSay("There are lots of reefs near that island. And the winds are so strong, it is nearly impossible to approach. I've never made it myself. The island looks like a turtle covered in snow, hence its name, but very few people have even heard of it. Still want it?\r\n#b#L0#Yes, I'd still like the map.#l\r\n#k")
sm.sendSay("Alright, I'll give you the map if you do me one favor. There are a bunch of Red-Nose Pirate #r#o7130104#s#k and #r#o6130208#s#k constantly threatening #m251000000#. Could you eliminate #r100#k of each?")
sm.startQuest(parentID)
