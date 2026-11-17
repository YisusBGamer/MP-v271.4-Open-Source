# Mihile's intro map: on to Ereve. With a story skip pending, go now, so the skip's dialogs open in Ereve and a
# timed warp can't land in the middle of them.
from net.swordie.ms.client.character import StorySkipModule

if StorySkipModule.isPending(chr):
    sm.warp(130000000)
else:
    sm.invokeAfterDelay(4000, "warp", 130000000)
