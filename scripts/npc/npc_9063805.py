# NPC script: npc_9063805
# NPCs: 9063805 (NLM Token Vending Machine)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am NLM Token Vending Machine.")
