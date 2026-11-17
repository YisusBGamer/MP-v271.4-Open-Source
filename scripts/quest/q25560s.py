# Created by MechAviv
# Quest ID :: 25560
# Containing the Darkness
from net.swordie.ms.enums import UIType


sm.curNodeEventEnd(True)
sm.setTemporarySkillSet(0)
sm.setInGameDirectionMode(True, True, False, False)
sm.sendDelay(1000)


sm.setSpeakerID(0)
sm.removeEscapeButton()
sm.flipDialoguePlayerAsSpeaker()
sm.setSpeakerType(3)
sm.sendNext("Is this how the Black Mage saw the world? Now I see everyone else beneath me!")


sm.setSpeakerID(0)
sm.removeEscapeButton()
sm.flipDialoguePlayerAsSpeaker()
sm.setSpeakerType(3)
sm.sendSay("My soul was nearly lost to the power of darkness. I can see its allure, but I won't let it consume who I am. I will learn to harness it, and make it my own.")


sm.setSpeakerID(0)
sm.removeEscapeButton()
sm.flipDialoguePlayerAsSpeaker()
sm.setSpeakerType(3)
sm.sendSay("But first, I must master my new magic. My old weapons of Light will now become tools of Darkness.")


sm.giveAndEquip(1212001)
sm.giveAndEquip(1352400)
sm.giveItem(2001502, 30)
sm.giveItem(2001506, 30)
sm.startQuest(25560)
sm.completeQuest(25560)
sm.setSpeakerID(0)
sm.removeEscapeButton()
sm.flipDialoguePlayerAsSpeaker()
sm.setSpeakerType(3)
sm.sendSay("Yes, it feels good to have a weapon in my hands again. Now, who shall I test my new power on first...")


sm.sendDelay(900)


sm.progressMessageFont("Level up to raise your stats. Hotkey [S] / Secondary key [C]")
sm.sendDelay(1500)


sm.openUI(UIType.STAT)
sm.sendDelay(900)


sm.setTemporarySkillSet(0)
sm.setInGameDirectionMode(False, True, False, False)