# NPC script: npc_9062941
# NPCs: 9062941 (Healthy Hamburger)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Healthy Hamburger.")
