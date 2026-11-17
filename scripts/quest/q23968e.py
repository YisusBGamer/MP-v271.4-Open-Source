# 23968 - Muscat-2000's Thoughtfulness
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Zechtok-2000 (2154007)

sm.setSpeakerID(2154007)  # Zechtok-2000
sm.sendNext("#b(You see the inoperable #p2154007#. Place the new heart where the old heart used to be and press the button to turn #p2154007# back on.)#k")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("It is wonderful to see you again, #h0#. Without you, our plans would never have been successful. Although #p2154012# won't express it I know that deep inside she also appreciates all that you've done.")
