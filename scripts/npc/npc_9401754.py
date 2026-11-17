# NPC script: npc_9401754
# NPCs: 9401754 (Mr. Bouffon)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Mr. Bouffon.")
