# Black Mage entry (Limina)
from net.swordie.ms.enums import BossCooldown

# mode, req level, map, death count
destinations = [
    ["Hard", 255, 450013100, 12],  # official (maplestorywiki.net, GMS): level 255, 60 minutes, 12 deaths
]

def is_party_eligible(reqlevel, party):
    # TODO: check prequest
    for member in party.getMembers():
        if member.getLevel() < reqlevel:
            return False

    return True

sm.flipSpeaker()
sm.flipDialoguePlayerAsSpeaker()
sm.setBoxChat()

dialog = "Do you want to head to the #rTemple of Darkness#k to fight the Black Mage??\r\n"

for i in range(len(destinations)):
    dialog += "#L%d#Go to the Temple of Darkness (%s Mode). (Lv. %d+)#l\r\n" % (i, destinations[i][0], destinations[i][1])

# Extreme (maps 450013910..980), run by BlackMageInstanceHelper
dialog += "#L50#Go to the Temple of Darkness (Extreme Mode). (Lv. 255+)#l\r\n"

dialog += "#L99#Never mind."
response = sm.sendSay(dialog)

cd = BossCooldown.BlackMage

if response == 50:
    if sm.getParty() is None:
        sm.createSoloParty()
    if not sm.isPartyLeader():
        sm.sendSayOkay("Please have your party leader talk to me if you wish to face the Black Mage.")
    elif sm.checkParty(cd) and is_party_eligible(255, sm.getParty()):
        sm.warpInstanceIn(450013910, 0, True)
        sm.setDeathCount(12)
        sm.setInstanceTime(30 * 60)  # GMS Extreme: 30 minutes
        sm.setBossCooldown(cd)
    response = 99

if sm.getParty() is None:
    sm.createSoloParty()

if not sm.isPartyLeader():
    sm.sendSayOkay("Please have your party leader talk to me if you wish to face the Black Mage.")

elif sm.checkParty(cd) and response != 99:
    if is_party_eligible(destinations[response][1], sm.getParty()):
        sm.warpInstanceIn(destinations[response][2], 0, True)
        sm.setDeathCount(destinations[response][3])
        sm.setInstanceTime(60 * 60)  # GMS Hard: 60 minutes
        sm.setBossCooldown(cd)

    else:
        sm.sendSayOkay("One or more party members are lacking the prerequisite entry quests, or are below level %d." % destinations[response][1])