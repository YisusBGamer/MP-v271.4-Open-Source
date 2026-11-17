# 25766 - A World Apart!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cartalion (3000000)

sm.setSpeakerID(3000000)  # Cartalion
if sm.sendAskAccept("Kaiser... Before you enter the Interdimensional Portal, I want to tell you something."):
    sm.startQuest(parentID)
    sm.sendNext("It is true that the decision of the Council is more than what we can handle. However, all the people of Nova want to free Tyrant's Castle and retake Heliseum. Our hope lies with you. If you can find the Transcendent and use their power, we may have a chance.")
    sm.sendSay("And...let me give you one additional mission. Bring any unique materials that you find from that world to #p3000003#. It will help us to understand that world.")
    sm.sendSay("The Angelic Buster? She already left, actually. I hope she'll be okay...and you, as well.")
else:
    sm.sendNext("All right, then. Please come see me after you are done with your tasks.")
