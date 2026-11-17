# NPC script: npc_9062958
# NPCs: 9062958 (Snowy Healing Altar), 9062959 (Healing Altar)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Snowy Healing Altar.")
