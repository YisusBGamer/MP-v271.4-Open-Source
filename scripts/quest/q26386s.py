# 26386 - [Gingerbread] Get Your Bread On
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gingerbread Man (9000177)

sm.setSpeakerID(9000177)  # Gingerbread Man
if sm.sendAskAccept("You're here to bake my doughy little friends, right? Are you ready?! "):
    sm.startQuest(parentID)
    sm.sendNext("Awesome! Just hold onto this Half-baked Gingerbread Cookie and #bstay online for 30 minutes#k... The warmth from your sweet little body will turn it into a fully-baked Gingerbread Cookie. Just #rdon't log out, or the cookie will crumble to pieces#k!")
    sm.sendSay("How does the cookie bake so well? You just need to give it all your love! Anyway come to me when the cookie is ready! ")
else:
    sm.sendNext("Whaaat? Then why did you talk to me? ")
