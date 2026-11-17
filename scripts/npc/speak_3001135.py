# NPC script: speak_3001135
# NPCs: 3001135 (Resistance Soldier)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Resistance Soldier.")
