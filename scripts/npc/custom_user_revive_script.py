#  Custom revival script to revive players
from net.swordie.ms.handlers.user import UserStatHandler

if chr.getHP() <= 0:
    if chr.getInstance() is not None:
        option = sm.sendNext("#L1#Revive#l")
        if option == 1:
            UserStatHandler.handleUserInBossReviveRequest(chr, None)
    else:
        option = sm.sendNext("#L1#Revive#l \r\n#L2#Move back to the nearest town#l")
        if option == 1:
            UserStatHandler.handleUserInBossReviveRequest(chr, None)
        elif option == 2:
            if sm.sendAskYesNo("Are you sure you want to move back to the nearest town? #rIf you press no, you will be respawned in the current map."):
                UserStatHandler.handleUserInBossReviveRequest(chr, None)
                toWarpId = 100000000
                fieldInfo = chr.getField().getInfo()
                if 0 < fieldInfo.getForcedReturn() < 999999999:
                    toWarpId = fieldInfo.getForcedReturn()
                elif 0 < fieldInfo.getReturnMap() < 999999999:
                    toWarpId = fieldInfo.getReturnMap()
                sm.warp(toWarpId)
            else:
                UserStatHandler.handleUserInBossReviveRequest(chr, None)



