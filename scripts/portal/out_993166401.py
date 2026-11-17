# A Path Out of the Forgotten Forest (993166401) exit: on to Ereve Forest Road once Kimu has sent you (36452).
if sm.hasQuest(36452) or sm.hasQuestCompleted(36452):
    sm.warp(993166403, 0)
else:
    sm.chat("Talk to Kimu before you leave.")
sm.dispose()
