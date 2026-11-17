# id 450003720 (Lachelein Alley : Lachelein Alley), field 450003720
if not sm.hasQuestCompleted(34302):
    # Check.img completes 34302 only after the shared scene record says the player attempted the red fog. The
    # original field scene omitted this client-side flag, leaving players unable to turn it in.
    if sm.hasQuest(34302) and sm.getQRValue(34340, "enter") != "2":
        sm.setQRValue(34340, "enter", "2")
    sm.lockInGameUI(True, False)
    sm.blind(True, 255, 0, 0, 0, 0)
    sm.setSpeakerType(3)
    sm.setParam(37)
    sm.setColor(1)
    sm.setInnerOverrideSpeakerTemplateID(3003209) # Gray Mask
    sm.sendNext("Oh! There's something important I forgot to tell you! ")
    sm.sendSay("I wouldn't try to escape by following the river, if I were you. It's an extremely dangerous place.")
    sm.sendSay("Those who fall in are #bsapped of their energy#k. And soon, they are #btorn apart into their component Erdas.#k Isn't that terrible?")
    sm.blind(True, 255, 0, 0, 0, 0)
    sm.sendDelay(1200)
    sm.blind(False, 0, 0, 0, 0, 1000)
    sm.sendDelay(1400)
    sm.lockInGameUI(False, True)
