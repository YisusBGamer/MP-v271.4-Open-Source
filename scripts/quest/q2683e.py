# 2683 - Henesys, Here I Come!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Athena Pierce (1012100)

sm.setSpeakerID(1012100)  # Athena Pierce
sm.sendNext("Hello, #h0#. I've heard a lot about you from Kyrin. My name is Athena Pierce, and I am the Bowman Instructor here in Henesys. Nice to meet you.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I can assure you Henesys is one of the safest towns in Maple World. This would be a good place to stay until you find your feet again.\r\n\r\nLeave through the portal on the right and let Camila guide you through Henesys. Good luck on your journey.")
