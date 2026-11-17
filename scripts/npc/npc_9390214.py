from net.swordie.ms.loaders import StringData

male_start = 20000
male_end = 21000
female_start = 21000
female_end = 22000

al = chr.getAvatarData().getAvatarLook() if not chr.isZeroBeta() else chr.getAvatarData().getZeroAvatarLook()
eyeColour = (al.getFace() // 100) % 10 * 100  # eye colour is the hundreds digit of a face id

start = female_start if al.getGender() else male_start
end = female_end if al.getGender() else male_end

options = list(StringData.getFaces(start, end))

temp = []
for o in options:
    if (o // 100) % 10 == 0:  # base faces only, recoloured to the current eye colour
        temp.append(o + eyeColour)
options = temp

answer = sm.sendAskAvatar("I can change your hair to anything you'd like!", False, chr.isZeroBeta(), options)
sm.changeCharacterLook(answer)
