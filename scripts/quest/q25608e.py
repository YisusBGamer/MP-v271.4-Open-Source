# 25608 - The Fourth Augury
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Vieren (1032209)

sm.setSpeakerID(1032209)  # Vieren
if sm.sendNext("You got the fourth Augury back. Did anything unusual happen in Riena? \r\n#L0##bI witnessed people who were once enemies gathering their powers to overcome difficult times. I intend to walk my own path of destiny as well.#l\r\n#k") != 0:
    sm.dispose()
sm.sendSay("Sounds like you learned quite a bit. More than I expected, anyway. I'm sure your future is bri...positive, Luminous. Now, this is the last Augury.")
sm.sendSay("That's the spirit. Just one Augury to go!")
sm.tryCompleteQuest(parentID)
