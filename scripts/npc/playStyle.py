# Play style question outside a class tutorial (classes that start without one here, characters made before play
# styles existed, or @menu > My character when none was chosen). Tutorials ask with sm.askTutorialSkip instead.
# See PlayStyleModule.
from net.swordie.ms.client.character import MenuModule
from net.swordie.ms.client.character import PlayStyleModule

if PlayStyleModule.askOutsideTutorial(sm, chr):
    MenuModule.openAfterMenu(chr, "job")  # Express: choose the job path next
sm.dispose()
