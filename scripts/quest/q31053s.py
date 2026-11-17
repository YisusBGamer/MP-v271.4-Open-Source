# 31053 - [Hilla] Find the Passage
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Longorias (2183002)

sm.setSpeakerID(2183002)  # Longorias
if sm.sendAskAccept("Good to see you here. I'll get right to the point. There are several underground passageways in Azwan that let you get to the surface without passing through here. Some of the trespassers have found them, and are using them to invade. Can you clear the villains out?"):
    sm.startQuest(parentID)
    sm.sendNext("Use the portal on the right and start your search from there.")
else:
    sm.sendNext("I'm sorry. I guess I asked too much of you.")
