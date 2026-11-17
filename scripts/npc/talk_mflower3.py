# NPC script: talk_mflower3
# NPCs: 2131015 (Flower Bloomed in Mushroom Hill)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Flower Bloomed in Mushroom Hill.")
