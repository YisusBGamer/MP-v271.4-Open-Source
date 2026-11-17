# NPC script: npc_1541326
# NPCs: 1541326 (Suspicious Ice Chunk)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Suspicious Ice Chunk.")
