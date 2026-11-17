# Spiegelmann's level-up offer (ArcaneRiverSkipModule.onLevelUp): the player reached the next Arcane River or Grandis
# area's level while still on an earlier area's story. Opened by the server, not by clicking an NPC. The player takes
# and deals no damage while this is open; every way out of the script ends that (endArcaneSkipPrompt in finally), and
# the server ends it anyway after ARCANE_SKIP_PROMPT_SECONDS or on a map change.
sm.setSpeakerID(3003146)  # Spiegelmann
try:
    nextArea = sm.getArcaneSkipPromptArea()
    skipped = sm.getArcaneSkipPromptSkippedArea()
    region = sm.getArcaneSkipPromptRegion()
    grandis = region == "Grandis"
    symbols = "Sacred Symbols" if grandis else "Arcane Symbols"
    REMINDER = "" if grandis else ("\r\n\r\n#b(You can talk to me in any Arcane River town at any time to skip the "
                                   "story.)#k")
    if nextArea is not None:
        while True:
            text = ("Congratulations on reaching level #b{}#k! That's enough for #b{}#k.\r\n\r\n"
                    "You're still in the middle of the {} story, though. If you like, I can see to it that "
                    "the story is finished for you up to and including #b{}#k, and send you straight to #b{}#k.\r\n\r\n"
                    "You won't get the skipped quests' EXP, mesos or items, but you'll receive the {} of "
                    "the skipped areas you don't have yet, and your symbol slots will open as if you had done them."
                    "\r\n\r\n#e(You're protected from all damage while we talk.)#n\r\n\r\n#b"
                    "#L0#Yes, skip the story and take me to {}.#l\r\n"
                    "#L1#No, I'll continue the story.#l").format(
                nextArea.getLevel(), nextArea.getName(), region, skipped.getName(), nextArea.getName(), symbols,
                nextArea.getName())
            sel = sm.sendNext(text)
            if sel == 0:
                if sm.sendAskYesNo("Skip the {} story up to and including #b{}#k and go to #b{}#k?"
                                   "\r\n\r\n#r[Warning]#k You won't experience the story as it was meant to be "
                                   "played, and this can't be undone.".format(region, skipped.getName(),
                                                                             nextArea.getName())):
                    sm.sendSayOkay(sm.acceptArcaneSkipPrompt() + REMINDER)
                    break
            elif sel == 1:
                if sm.sendAskYesNo("Continue the {} story as it is?".format(region) + REMINDER):
                    sm.endArcaneSkipPrompt()
                    sm.sendSayOkay("Then good luck on your journey!" + REMINDER)
                    break
            else:
                break
finally:
    sm.endArcaneSkipPrompt()
