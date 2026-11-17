# 11551 - Eco Mapleteer Day 10!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hi, #b#h0##k. Thank you so much for doing such a great job as an Eco Mapleteer! I think you've made a big difference in Maple World. Since today is the 10th and final day of the event, I have an awesome present for you! Want it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Okay, but don't wait too long! It really is awesome!")
