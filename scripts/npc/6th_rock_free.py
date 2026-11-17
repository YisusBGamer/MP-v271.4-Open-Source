# NPC script: 6th_rock_free
# NPCs: 9063201 (Rock Spirit), 9063203 (Rock Spirit), 9063204 (Rock Spirit), 9063205 (Rock Spirit), 9063206 (Rock Spirit)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Rock Spirit.")
