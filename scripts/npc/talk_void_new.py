# NPC script: talk_void_new
# NPCs: 1541013 (Sealed Black Mage)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Sealed Black Mage.")
