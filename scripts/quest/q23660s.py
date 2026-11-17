# 23660 - A Scholar's Pride
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Promathus (2300002)

sm.setSpeakerID(2300002)  # Promathus
sm.sendNext("What?\r\n#bI was wondering if you needed any help.#k")
sm.sendSay("Interesting, you appear to be one of #p2154009#'s... Are you new here?\r\n#bDo you know #p2154009#?#k")
sm.sendSay("Yes, yes, we worked together for a long time. Excellent biomachinist, nearly limitless intelligence.")
sm.sendSay("His choice of allegiances was... unfortunate. The Black Wings are not known for being good research partners. We lost touch long ago.")
sm.sendSay("Rumor had it, he created the ultimate weapon. I am assuming that would be you. As usual, #p2154009# did his work too well, hehehe...")
sm.sendSay("A scientist must work responsibly, not for personal gain. Any genius who doesn't know that is no genius in my book. Gelimer was a lost cause the moment he took their side.")
if sm.sendAskAccept("I have nothing in particular to say to you."):
    sm.startQuest(parentID)
    sm.sendNext("You are nothing but Gelimer's tool for destruction. I have nothing to say to you.")
else:
    sm.sendNext("Didn't I tell you to go away?")
