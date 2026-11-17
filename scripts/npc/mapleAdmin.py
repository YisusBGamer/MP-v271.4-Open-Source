# Maple Administrator (9010000), in most towns. He opens the server's menu (the same as @menu): travel, shops, bosses,
# guides and more, and shows the onboarding checklist and the next steps for new players.
from net.swordie.ms.client.character import MenuModule
from net.swordie.ms.client.character import NewPlayerModule
try:
    from net.swordie.ms.client.character.hexa import SixthJobQuest as SJ
except ImportError:
    SJ = None

sm.setSpeakerID(9010000)
if SJ is not None and sm.hasQuest(SJ.NEW_AGE):
    # [6th Job] A New Age is handed in here: the 6th job, then the goddess's two lessons
    if sm.questEndDialogue(SJ.NEW_AGE, 9010000):
        SJ.advance(chr)
        for q in (SJ.NEW_POWER, SJ.HEXA_MATRIX):
            if sm.questStartDialogue(q, 3006384):
                sm.completeQuest(q)
else:
    sixth = SJ is not None and SJ.eligible(chr)
    done = NewPlayerModule.getStepsDone(chr)
    total = NewPlayerModule.getStepCount()
    # "What should I do next?" can be switched off by the server (builds without the switch: always on)
    suggest = not hasattr(NewPlayerModule, "isNextHelperOn") or NewPlayerModule.isNextHelperOn()
    text = "Hi #h0#! How can I help?\r\n\r\n" \
           "#L0##bOpen the menu#k#l\r\n"
    if suggest:
        text += "#L1##bWhat should I do next?#k#l\r\n"
    text += "#L2##bOnboarding checklist#k  ({}/{})#l".format(done, total)
    if sixth:
        text += "\r\n#L3##b[6th Job] The Barrier Between Worlds#k#l"
    sel = sm.sendNext(text)
    if sel == 0:
        MenuModule.openAfterMenu(chr, "menu")
    elif sel == 1 and suggest:
        NewPlayerModule.markStep(chr, "menu")
        steps = NewPlayerModule.getNextSteps(chr)
        lines = "#e#bWhat should I do next?#k#n\r\n\r\n"
        for i in range(steps.size()):
            lines += "- " + steps.get(i)[0] + "\r\n"
        if steps.size() == 0:
            lines += "You're all set!\r\n"
        lines += "\r\n#dType @next to go straight to any of these.#k"
        sm.sendSayOkay(lines)
    elif sel == 2:
        sm.sendSayOkay(NewPlayerModule.getChecklistText(chr))
    elif sel == 3 and sixth:
        if sm.sendAskYesNo("The worlds are merging, and an unfamiliar Erda has appeared at the #bBarrier Between Worlds#k. Shall I send you there?"):
            SJ.enter(chr)
sm.dispose()
