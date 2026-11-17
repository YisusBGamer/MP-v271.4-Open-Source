# Ellin Forest Milepost (2133002) | Forest of Poison Haze, Forest of Haze
sm.setSpeakerID(2133002)
if sm.sendAskYesNo("#bThe haze is thick. Lost? Find Ellin at the bottom right of the forest.#k\r\n\r\n"
                   "Do you want to give up and leave the Forest of Poison Haze?"):
    sm.warpInstanceOut(930000800, 0)
sm.dispose()
