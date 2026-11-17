# NPC script: npc_9062663
# NPCs: 9062663 (Healthy Hamburger)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Healthy Hamburger.")
