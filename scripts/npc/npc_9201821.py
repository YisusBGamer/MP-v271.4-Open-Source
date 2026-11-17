# NPC script: npc_9201821
# NPCs: 9201821 (Crystal Altar)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Crystal Altar.")
