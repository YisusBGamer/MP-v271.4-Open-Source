# 2614 - Helping Nella with Her Problem
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Nella (1052103)

sm.setSpeakerID(1052103)  # Nella
sm.sendNext("Did you clear out the basement? Awesome! Now Grandpa won't scold me for slacking on my chores. Thanks! ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Huh? You want to know what the Dark Lord is like? He's SUPER nice. All the other kids and I love him and not just because he always sends people to help us with our chores, honest!")
    sm.sendSay("#b(That's all Nella has to say. Who's next? There's Don Giovanni of the Hair Salon and JM From tha Streetz...)")
