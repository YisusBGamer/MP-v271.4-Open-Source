# NPC script: winner_npc
# NPCs: 9201705 (Candidate Desert Rabbit), 9201706 (Candidate Pepe), 9201707 (Candidate Lunar Pixie), 9201747 (Candidate Desert Rabbit), 9201748 (Candidate Pepe), 9201749 (Candidate Lunar Pixie)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Candidate Desert Rabbit.")
