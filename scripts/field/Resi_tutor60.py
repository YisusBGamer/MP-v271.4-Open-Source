# 931000020

sm.setSpeakerID(2159007)
sm.sendNext("It's been...a really long time since I've been outside the laboratory. Where are we?")

sm.setPlayerAsSpeaker()
sm.sendSay("This is the road that leads to Edelstein, where I live! Let's get out of here before the Black Wings follow us.")

sm.avatarOriented("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow1")

# v271's map has none of the touch portals (tutor00-02) where Schiller catches up: he does so here
execfile("scripts/portal/Resi_tutor61.py")
