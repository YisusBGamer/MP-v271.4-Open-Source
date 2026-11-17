# 2559 - Sky High
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000)

sm.setSpeakerID(9010000)  # Maple Administrator
sm.sendNext("Hello Explorer. I have something to tell you.")
sm.sendSay("The sky is so clear today! Ahh, I feel like I could just fly away. Wouldn't it be nice to soar through the sky?")
if sm.sendAskAccept("I think you know how I feel. Heh. Now, what would you say if I told you that there IS a way to fly? Interested?"):
    sm.startQuest(parentID)
    sm.sendNext("It's nothing complicated. See, everyone can fly if they have an #bairplane#k, of course. Ah, but where do you get an airplane? Well, there's a retired pilot, #bInstructor Irvin#k, who is currently renting out airplanes.")
    sm.sendSay("Instructor Irvin can be found in the Stations of #bVictoria, Ereve, Edelstein, Orbis, Mu Lung, Ariant, or Leafre#k. So if you want to rent an airplane, you should go see him!")
else:
    sm.sendNext("Oh, come on! I KNOW you're interested! Just admit it.")
