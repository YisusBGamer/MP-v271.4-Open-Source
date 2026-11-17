# NPC script: npc_9062853
# NPCs: 9062853 (Orange Mushroom Safe)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Orange Mushroom Safe.")
