# Princess Sakuno. Fort Asura Entrance (874000005) doubles as the Akechi Mitsuhide exit room.
# The v232 map has no exit portal, so Sakuno must provide the return trip.
from net.swordie.ms.constants import BossConstants

if sm.getFieldID() == BossConstants.AKECHI_ENTRANCE:
    if sm.sendAskYesNo("Would you like to return to Momijigaoka?"):
        sm.warp(807000000, 0)

sm.dispose()
