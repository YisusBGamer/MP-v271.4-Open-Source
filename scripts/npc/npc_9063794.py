# NPC script: npc_9063794
# NPCs: 9063794 (NLM Coin Vending Machine)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am NLM Coin Vending Machine.")
