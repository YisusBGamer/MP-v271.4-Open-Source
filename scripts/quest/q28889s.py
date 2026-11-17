# 28889 - Delivering A Letter To All The Towns
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Lucas (9201203)

sm.setSpeakerID(9201203)  # Lucas
if sm.sendAskAccept("Ah, you must be the new Explorer. I have a favor to ask of you..."):
    sm.startQuest(parentID)
    sm.sendNext("Deliver this letter to the Job Instructors in every town. It's an invitation to the upcoming Maple 3rd Anniversary, but hardly anyone has RSVPed. They might be interested if an interesting-looking person like you invited them, though!")
    sm.sendSay("Use the portal to right in Lith Harber to get to your destination.")
else:
    sm.sendNext("I have to get ready for the celebration...")
