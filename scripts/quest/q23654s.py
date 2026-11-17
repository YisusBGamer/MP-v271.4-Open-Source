# 23654 - An Outside Perspective 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Fenelle (3000001)

sm.setSpeakerID(3000001)  # Fenelle
sm.sendNext("What is it?\r\n#bWhat happened to #m400000000##k?")
sm.sendSay("I'm afraid it is a rather sad tale, one that has touched all of Grandis. This world's Transcendent of Life, Gerand Darmoor, has seized control of the Anima. The entire world is in his dark grasp now.")
sm.sendSay("The Nova have not thrived under these conditions... To make matters worse, one of our own betrayed us long ago. A man named Magnus claimed our capitol of Heliseum. The hero Kaiser attempted to defeat him, but did not survive the battle.")
sm.sendSay("#m400000000# remained safe for a time, protected by four ancient relics, but two of those are now gone. What remains of our military is devoted to defending our barriers from further decay. I fear what would happen, were they to fall...")
if sm.sendAskAccept("If I may, I would like to ask for your help."):
    sm.startQuest(parentID)
    sm.sendNext("Maintaining the safety of #m400000000# is a challenge, even with the new Kaiser and #p3000000# leading our forces. Can you go to the #beastern barrier#k and scout for any suspicious activity?")
else:
    sm.sendNext("Ah, my apologies. I am sure #p3000000# and the new Kaiser will deal with the barrier. You need not worry.")
