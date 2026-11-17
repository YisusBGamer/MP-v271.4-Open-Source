# NPC script: GL_TheDarkRide_slime_out
# NPCs: 9201655 (Gate)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Gate.")
