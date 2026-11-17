# Gollux: Heart (863010700), exit portal out00 -> Tynerum (863000100, in00).
# Asks before leaving while Gollux's Corrupted Heart (the reward monster) is still alive, so its loot isn't missed.
# Set on the portal by CustomPortalScript.golluxHeartExit.
EXIT_MAP = 863000100
EXIT_PORTAL = 1  # in00
# Gollux's Corrupted Heart, Easy to Hell. When one dies it revives as Gollux's Purified Heart (9601463, invincible,
# for show), so the Purified Heart must not count as a monster left.
CORRUPTED_HEARTS = (9601459, 9601460, 9601461, 9601462)

heartAlive = False
for mob in field.getMobs():
    if mob.getTemplateId() in CORRUPTED_HEARTS and mob.getHp() > 0:
        heartAlive = True

if heartAlive:
    if sm.sendAskYesNo("#rGollux's Corrupted Heart is still alive!#k\r\n\r\n"
                       "If you leave now, you'll miss the loot it drops when it's defeated.\r\n\r\n"
                       "Leave anyway?"):
        sm.warp(EXIT_MAP, EXIT_PORTAL)
    else:
        sm.dispose()
else:
    sm.warp(EXIT_MAP, EXIT_PORTAL)
