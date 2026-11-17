# Executor (Mirror-touched Sea 7): [Esfera] A Sunken Thing (37866) is finished by Will's ambush after the fight with
# the Light Executors (450007420 -> 450007430). Whoever ran out of time or fell there goes back in from here.
if sm.hasQuest(37866) and not sm.hasQuestWithValue(37866, "clear=c1"):
    sm.setSpeakerID(3003500)  # Ollie
    sm.setBoxChat()
    if sm.sendAskYesNo("#face0#Tana is still down there. We have to try again!\r\n\r\n"
                       "#b(If you accept, you'll go back to where the Light Executors guard her.)#k"):
        sm.warpInstanceIn(450007420)
        sm.setInstanceTime(10 * 60, 450007170)
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
