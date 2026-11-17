# NPC script: npc_9201815
# NPCs: 9201815 (Humanoid A)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Humanoid A.")
