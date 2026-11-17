# NPC script: npc_9062667
# NPCs: 9062667 (Magic Wardrobe)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Magic Wardrobe.")
