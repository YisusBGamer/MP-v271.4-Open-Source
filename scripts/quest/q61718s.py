# Erel Light story: [Erel Light] Into Verne Mine, as GMS.
from net.swordie.ms.connection.packet.field import ScenePacket
sm.setSpeakerType(3)
sm.setSpeakerID(0)
sm.setParam(44)
sm.setColor(1)
sm.playExclSoundWithDownBGM('GL_Erel.img/ErelVoice/male/899', 100)
sm.setInnerOverrideSpeakerTemplateID(9201711)
sm.sendNext('#face0##fc0xFFbfbfbf#(I made it all the way to the Power Plant without incident, thanks to Checky.)')
sm.playExclSoundWithDownBGM('GL_Erel.img/ErelVoice/male/900', 100)
sm.sendSay('#face0##fc0xFFbfbfbf#(I should go further in.)')
sm.startQuestNoCheck(61718)
sm.getChr().write(ScenePacket.navigationToNpc(310050400, 2154008))
sm.warp(310050400, 1)
