# Lauren (1012123) | Henesys Hair Salon: Android Beautician.
# Styles come from each android's own options in the game data (Etc/Android), like the official android coupons.
# Styling is free on this server, like the other beauty NPCs. The new look is saved with the android.
sm.setSpeakerID(1012123)
android = sm.getAndroid()
if android is None:
    sm.sendSayOkay("Hello, I'm Lauren, the Android Beautician! Bring your #bAndroid#k with you "
                   "(equipped with a working Mechanical Heart) and I'll give it a whole new look.")
else:
    sel = sm.sendNext("Hello, I'm Lauren, the Android Beautician! What would you like to do for your Android?\r\n\r\n#b"
                      "#L0#Change its hairstyle#l\r\n"
                      "#L1#Dye its hair#l\r\n"
                      "#L2#Change its face#l\r\n"
                      "#L3#Change its eye colour#l\r\n"
                      "#L4#Change its skin#l")
    options = []
    text = ""
    if sel == 0:
        options = [h for h in sm.getAndroidHairs()]
        text = "Which hairstyle would you like your Android to have?"
    elif sel == 1:
        base = (android.getHair() // 10) * 10
        options = [base + i for i in range(8)]
        text = "Which hair colour would you like for your Android?"
    elif sel == 2:
        options = [f for f in sm.getAndroidFaces()]
        text = "Which face would you like your Android to have?"
    elif sel == 3:
        face = android.getFace()
        base = face - ((face // 100) % 10) * 100
        options = [base + i * 100 for i in range(9)]
        text = "Which eye colour would you like for your Android?"
    elif sel == 4:
        options = [s for s in sm.getAndroidSkins()]
        text = "Which skin tone would you like for your Android?"
    if len(options) == 0:
        sm.sendSayOkay("Hmm, I don't have any styles for this kind of Android.")
    else:
        answer = sm.sendAskAndroid(text, options)
        if answer != 100000000:
            sm.changeAndroidLook(answer)
            sm.sendSayOkay("All done! Your Android looks fantastic.")
sm.dispose()
