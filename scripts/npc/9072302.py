# The Quick Move button's "Menu" entry and @menu open scripts/npc/menu.py directly (MenuModule); this NPC (the old
# @qm quick move NPC) opens it too if it's reached any other way.
from net.swordie.ms.client.character import MenuModule

MenuModule.openAfterMenu(chr, "menu")
sm.dispose()
