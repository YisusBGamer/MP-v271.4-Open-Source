from net.swordie.ms.client.character import BeautyModule
options = list(BeautyModule.getSkins())  # every skin tone of the game data
answer = sm.sendAskAvatar("We have the latest in beauty equipment. "
                          "With our technology, you can preview what your skin will look like in advance! "
                          "Which treatment would you like?", False, chr.isZeroBeta(), options)
sm.changeCharacterLook(answer)
