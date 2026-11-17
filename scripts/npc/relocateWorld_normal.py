# NPC script: relocateWorld_normal
# NPCs: 9063763 (Mover), 9201661 (Mover)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Mover.")
