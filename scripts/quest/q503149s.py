# [Legion] Artifact of Mysterious Power
from net.swordie.ms.client.character.union import UnionArtifactModule

NPC = 9010106

sm.setSpeakerID(NPC)
sm.setBoxChat()

sm.sendNext("#face0##b#h0##k! You came.\r\nI just found an #eArtifact#n that holds #ran unknown source of power#k, and it is reacting very strongly to you.")
sm.sendNext("#face0# An Artifact like this can #estore the mark of your adventures#n.\r\nEvery quest, every victory... it all turns into power that makes your #bLegion#k stronger.")
sm.sendNext("#face0# Hold on to the Artifact and continue your journey.\r\nAs your power builds up, you will unlock new effects.")
sm.sendNext("#face0# All right, I have recorded this.\r\nNow go and prove your strength.")
sm.completeQuestNoCheck(parentID)
UnionArtifactModule.onLogin(chr)
