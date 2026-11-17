# 59136 - The Beggar King
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Irena (1101005)

sm.setSpeakerID(1101005)  # Irena
sm.sendNext("#bMihile#k and #b#p1101004##k told me good things about you.")
sm.sendSay("#b#p1101002##k wanted me to give you this mission.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Your next stop is #bEl Nath#k. ")
    sm.sendSay("\r\n#b#p9390355##k is going around begging for money. There's something fishy about that guy. Talk to him, and find out what he's up to.")
    sm.sendSay("Go straight to #bEl Nath#k.")
