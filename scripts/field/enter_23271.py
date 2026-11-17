# Desolate Desert 1 (926030000): The Chase and the Conspiracy (23271). You catch up with the two Suspicious Magicians
# (2159325, 2159326) who took the Forbidden Book of Alchemy; they turn on you in Desolate Desert 2 (926030010).
# Anyone else (or a login here, forcedReturn) goes back to the Desert of Red Sand.
if chr.getInstance() is not None and sm.hasQuest(23271) and not sm.hasItem(4032975):
    sm.chatScript("Suspicious Magician: I want to be important too.")
    sm.chatScript("Suspicious Magician: I'll show Arkarium! I'll be honored! Ha ha!")
    sm.chatScript("The magicians have noticed you! Get the Forbidden Book of Alchemy back!")
    sm.invokeAfterDelay(3000, "warp", 926030010)
elif chr.getInstance() is not None:
    sm.invokeAfterDelay(500, "warpInstanceOut", 260020300, 3)
else:
    sm.invokeAfterDelay(500, "warp", 260020300, 3, False)
