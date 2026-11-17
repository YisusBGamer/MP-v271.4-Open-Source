#   [Job Adv] (5th job)   5th Job : Call of the Erdas
# Streamlined: the vision of the Erda flow is optional, and the Temple Keeper's "Preparing for Power" (1481) is
# done along with this quest, so the next step is right here with the Memory Keeper.

PREPARING_FOR_POWER = 1481


def finish():
    if sm.completeQuest(parentID):
        if not sm.hasQuestCompleted(PREPARING_FOR_POWER):
            sm.completeQuestNoCheck(PREPARING_FOR_POWER)
        sm.sendSayOkay("Talk to me again when you are ready. I will help you meet the goddesses... without having to "
                       "travel the world to find them.")


sm.setSpeakerID(2140001)

if "2" == sm.getQRValue(parentID):
    sm.sendNext("Do you see now? Do you understand the importance of the Erdas? \r\n\r\n #L4##bI... talked to them.#l")
    sm.sendNext("You spoke to the Erdas? Can this be true? I have observed the Erdas my whole life, but never have I communicated with them in any way.")
    sm.sendNext("If the Erdas spoke to you... Then you have a grander fate than I suspected. The Erdas wish to give you their power, so that you can protect them.")
    finish()
elif sm.hasQuest(parentID):
    sm.sendNext("#h #... I've heard many great things about you.\r\n\r\nHave you heard about #eErda#n, the energy that forms this world? It forms and supports Maple World and many other worlds, and lately it has been vanishing little by little.")
    sel = sm.sendNext("I can help you see the flow of Erda for yourself, if you wish.\r\n\r\n"
                      "#b#L0#Show me the flow of Erda.#l\r\n"
                      "#L1#I believe you. Let's continue.#l")
    if sel == 0:
        sm.warpInstanceIn(450000200)
    else:
        sm.sendNext("Very well. The Erdas wish to give their power to those who would protect them, and I believe you are one of them.")
        finish()
