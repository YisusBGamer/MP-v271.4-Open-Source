# NPC script: seal_Change
# NPCs: 1541018 (Cracked Seal Stone of Time)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Cracked Seal Stone of Time.")
