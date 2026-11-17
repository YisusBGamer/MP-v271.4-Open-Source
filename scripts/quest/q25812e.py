# 25812 - Alliance Invitation
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cygnus (1101000)

sm.setSpeakerID(1101000)  # Cygnus
sm.sendNext("It's a pleasure to meet you. I am Cygnus. Thank you very much for coming on such short notice.")
sm.sendSay("I've heard of your accomplishments in Maple World. Your help will mean a great deal for us. And our alliance will lend you a hand when you need it.")
sm.sendSay("I've prepared a small gift to solidify our new alliance.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("This will prove that you are a member of the Maple Alliance. I hope you will find it useful. May the alliance serve the needs of all.\r\n")
