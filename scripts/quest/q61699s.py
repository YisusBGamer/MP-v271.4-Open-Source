# Erel Light story: [Erel Light] The People of Katanos, as GMS.
from net.swordie.ms.connection.packet.field import ScenePacket
sm.setSpeakerType(3)
sm.setSpeakerID(0)
sm.setParam(45)
sm.setColor(1)
sm.playExclSoundWithDownBGM('GL_Erel.img/ErelVoice/male/149', 100)
sm.setInnerOverrideSpeakerTemplateID(9201711)
sm.sendNext('#face0#Time to introduce myself.')
sm.playExclSoundWithDownBGM('GL_Erel.img/ErelVoice/male/150', 100)
sm.sendSay('#face0#Oh, hey. I can start with her!')
sm.startQuestNoCheck(61699)
sm.getChr().write(ScenePacket.navigationToNpc(693007002, 9201724))
