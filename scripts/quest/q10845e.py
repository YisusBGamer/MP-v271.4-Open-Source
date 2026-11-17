# 10845 - Missing Crewman
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lost Crewman (9000084)

sm.setSpeakerID(9000084)  # Lost Crewman
sm.sendNext("I'm sorry, but where am I? I looked at the map, and I still can't figure it out. I don't know how i got here... *sniffle sniffle*")
sm.sendSay("Hmm? Gold Richie sent you here? Phew, what a relief! I didn't know how I'd get back... There's no one here.")
sm.sendSay("A Return Scroll? Thank you! Now I can finally go back. I'm going to shower and eat as soon as I get back. I've been lost for a few days now, and I feel so disgusting. Not to mention how hungry I am!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Land is such a scary place to be... I'm going back. Thank you so much for helping me. Oh! When I go back, I'll make sure to tell Gold Richie how helpful you were.")
