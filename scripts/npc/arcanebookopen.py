# NPC script: arcanebookopen
# NPCs: 2500005 (Book of Arcane River)
# Npc.wz names this script but no implementation exists in this or the
# reference repos; the NPC still answers so the client gets a speaker.

sm.setSpeakerID(parentID)
sm.setBoxChat()
sm.sendSayOkay("Hello! I am Book of Arcane River.")
