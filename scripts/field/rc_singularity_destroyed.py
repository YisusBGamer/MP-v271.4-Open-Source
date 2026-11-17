# Death script of the quest Singularity Maker (8641059), set by portal/east_450014240.py:
# marks [Reverse City] Long Awaited (37619) done for everyone fighting it and sends them back to the top floor.
for c in list(field.getChars()):
    if c.hasQuestInProgress(37619):
        c.getScriptManager().setQRValue(37619, "dummy", "1")
        c.chatMessage("You destroyed the Singularity Maker! Talk to Barr on the M Tower Top Floor.")
    c.getScriptManager().invokeAfterDelay(3000, "warpInstanceOut", 450014240, 0)
