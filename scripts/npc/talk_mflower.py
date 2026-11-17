# NPC script: talk_mflower
# NPCs: 2131013 (Flower Bloomed in Mushroom Hill)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Flower Bloomed in Mushroom Hill.")
