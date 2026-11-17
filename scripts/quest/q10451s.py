# 10451 - Rainbow Week: Red Monday Magic
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Red Monday is a #bDesignated Monster Hunting Day.#k Are you ready to have fun hunting today?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("You might regret it if you miss out today!")
