# NPC script: npc_9062710
# NPCs: 9062710 (Wonky the Fairy), 9201631 (Dr. Lim)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Wonky the Fairy.")
