# 23903 - Can You Help Edelstein?
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Ferdi (2152000)

sm.setSpeakerID(2152000)  # Ferdi
sm.sendNext("You've brought all the Light Bulbs. Thank you. I will let the other townspeople know that there are good people like you in the #p1101000# Knights.")
sm.sendSay("Years have passed since this town was taken over by the Black Wings, and the hearts of the townspeople have hardened more and more. Still, I'm sure they will be able to trust an upstanding person like you.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Why do the townspeople hate the #p1101000# Knights? Need I really explain that? The leader of the #p1101000# Knights should be the one to explain... Don't worry, I know that you are not a coward like she is.")
