# 322000200 Tigun's House (Mirror World Ariant) - In Phantom's Name (41062): the riches are taken at Tigun's Wealth
# (npc/tigunwealth.py), the official way; this only points at it.
if sm.hasQuest(41062) and not sm.hasItem(4033810):
    sm.chatScript("Tigun's riches are right here. Take them and leave the forged Phantom letter.")
