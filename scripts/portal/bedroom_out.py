# Cannoneer tutorial bedroom (912060500) exit. With Cannoneer Trailblazing (2570) on: to Nautilus (120000101). It needs
# level 10; a character still below it (nothing to hunt in the bedroom) goes out to Nautilus Harbor, where Cutter
# waits, to train nearby first.
if sm.hasQuest(2570):
    sm.warp(120000101, 0)
elif chr.getLevel() < 10 and sm.hasQuestCompleted(2569):
    sm.chat("Cutter wants you to be level 10 first. Train a little around Nautilus Harbor, then talk to Cutter.")
    sm.warp(120000000, 0)
sm.dispose()
