# NPC script: maple_behind
# NPCs: 2500009 (Book of Forgotten History)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Book of Forgotten History.")
