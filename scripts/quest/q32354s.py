# 32354 - The Shadow Returns
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Sugar (1520052)

sm.setSpeakerID(1520052)  # Sugar
sm.sendNext("?!")
sm.sendSay("#h0#! Do you feel that? Something really, really evil has shown up in town!")
if sm.sendAskAccept("I can't do this alone... Come with me, #h0#! I'm always braver when you're around."):
    sm.startQuest(parentID)
    sm.sendNext("Let's get to Amherst as fast as we can.")
else:
    sm.sendNext("But I'm t-too scared...")
