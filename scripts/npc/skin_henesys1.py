# Ms. Tan (1012105) | Henesys Skin-Care
sm.sendNext("Welcome to Henesys Skin-Care! "
            "Styling is free here, so I can make your skin supple and glow-y, like mine! "
            "Trust me, you don't want to miss my facials.")
from net.swordie.ms.client.character import BeautyModule
options = list(BeautyModule.getSkins())  # every skin tone of the game data
answer = sm.sendAskAvatar("We have the latest in beauty equipment. "
                          "With our technology, you can preview what your skin will look like in advance! "
                          "Which treatment would you like?", False, chr.isZeroBeta(), options)
sm.changeCharacterLook(answer)
