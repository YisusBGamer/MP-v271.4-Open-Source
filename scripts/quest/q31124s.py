# 31124 - [Knight Stronghold] Scouting the Stronghold
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Chief Alex (2142001)

sm.setSpeakerID(2142001)  # Chief Alex
if sm.sendAskAccept("I've heard reports of movement in the Cygnus stronghold. They must be up to something. Will you help us?"):
    sm.startQuest(parentID)
    sm.sendNext("You can reach the stronghold through the Crossroads of Ereve. Go scout it out, but be careful!")
else:
    sm.sendNext("If Henesys falls, who will be left...?")
