# Will entry NPC (will_enterGate)
from net.swordie.ms.constants import BossConstants
from net.swordie.ms.enums import BossCooldown

# mode, req level, map, death count
destinations = [
    # actual map is -50, with some direction stuff
    ["Easy", 235, BossConstants.WILL_EASY_FIELD_P1, 10],
    ["Normal", 235, BossConstants.WILL_FIELD_P1, 10],
    ["Hard", 235, BossConstants.WILL_HARD_FIELD_P1, 10],
]
# GMS v271 waiting rooms (ptBossIn starts the fight; the fight gets its 30 minutes then)
WAITING = [450020300, 450020400, 450020500]

def is_party_eligible(reqlevel, party):
    # TODO: check prequest
    for member in party.getMembers():
        if member.getLevel() < reqlevel:
            return False

    return True

sm.flipSpeaker()
sm.flipDialoguePlayerAsSpeaker()
sm.setBoxChat()

dialog = "Are you ready to head to the #bDiffraction Hall#k to fight Will?\r\n"

for i in range(len(destinations)):
    dialog += "#L%d#Go to the #bDiffraction Hall (%s Mode).#k (Lv. %d or above)#l\r\n" % (i, destinations[i][0], destinations[i][1])

dialog += "#L99#Never mind."
response = sm.sendSay(dialog)

cd = [BossCooldown.WillEasy, BossCooldown.Will, BossCooldown.WillHard][response] if 0 <= response <= 2 else None

if sm.getParty() is None:
    sm.createSoloParty()

if not sm.isPartyLeader():
    sm.sendSayOkay("Please have your party leader talk to me if you wish to face Will.")

elif cd is not None and sm.checkParty(cd):
    if is_party_eligible(destinations[response][1], sm.getParty()):
        sm.warpInstanceIn(WAITING[response], 0, True)
        sm.setDeathCount(destinations[response][3])
        sm.setInstanceTime(10 * 60)
        sm.setBossCooldown(cd)

    else:
        sm.sendSayOkay("One or more party members are lacking the prerequisite entry quests, or are below level %d." % destinations[response][1])