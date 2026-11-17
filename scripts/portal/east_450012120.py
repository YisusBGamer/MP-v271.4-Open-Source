# 450012120 World's Sorrow Midpoint 3, top portal: to Final Horizon Fore.
# during The Dark Knights (35843; 35805 before), it leads to the top of the waterfall, where the Black Mage's
# Elites caught up with Orchid (993070074, field/enter_993070074).
if sm.hasQuest(35843) or sm.hasQuest(35805):
    sm.warpInstanceIn(993070074, 0)
else:
    sm.warp(450012200, 3)
