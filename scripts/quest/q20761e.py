# 20761 - The Mind of a Knight: Beginnings
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Neinheart (1101002)

sm.setSpeakerID(1101002)  # Neinheart
if sm.sendNext("I see you took my earlier lack of urgency to heart... \r\n#L0##bYou said I had time! What's going on?#l\r\n#k") != 0:
    sm.dispose()
if sm.sendSay("You must learn to read between the lines, if you're going to be a member of this team.\r\n#L0##bWhat team?#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("The Cygnus Knights. We require strength, dedication, and a #bnimble mind#k. #h0#, I will expect you to continue on the journey to improve yourself.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Remember, see the truth before you.")
    sm.sendSay("Continue your training. I will call for you when I need you.")
