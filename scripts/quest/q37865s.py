# 37865 [Esfera] Her Barrier (start)
LIGHT_EXECUTOR = 3003504
OLLIE = 3003500

sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#We will break her barrier.")
sm.sendNext("#face0#To do that, we need greater power.")
sm.sendNext("#face0#Defeat the Keepers here. Their power will flow into us.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
sm.sendNext("#face0#To break Tana's barrier, we need Tana's power.")
sm.setSpeakerID(LIGHT_EXECUTOR)
sm.setBoxChat()
sm.sendNext("#face0#Yes. This task falls to you. As an Executor, I cannot harm the Keepers.")
sm.setSpeakerID(OLLIE)
sm.setBoxChat()
if sm.sendAskYesNo("#face0#Looks like we need your help again. Just take down about 200 #bKeepers of Darkness#k nearby."):
    sm.startQuest(parentID)
else:
    sm.sendNext("#face0#Oh... Is something wrong?")
