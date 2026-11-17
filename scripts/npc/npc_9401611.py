# NPC script: npc_9401611
# NPCs: 9401611 (Tube Kiosk)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Tube Kiosk.")
