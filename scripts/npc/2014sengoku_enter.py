# Ayame (9130100) | Hieizan Temple Plaza: Princess No
from net.swordie.ms.world.field.instance.instancehelper.pq import PrincessNoInstanceHelper as PN
from net.swordie.ms.client.character import PlayStyleModule

sm.setSpeakerID(9130100)
sel = sm.sendNext("#e<Princess No>#n\r\n\r\nPrincess No waits on the Temple's 5th floor. Will you face her?\r\n\r\n"
                  "#b#L0#Enter the Temple.#l\r\n#L1#Tell me more.#l")
if sel == 0:
    # Repair existing Express characters in memory, using the quest API and no story rewards.
    # This also works on the running build before the Java entry-gate update is deployed.
    party = chr.getParty()
    if party is None or party.getPartyLeaderID() == chr.getId():
        members = [chr] if party is None else party.getPartyMembersInField(chr)
        for member in members:
            if member.getLevel() >= PN.MIN_LEVEL and PlayStyleModule.autoSkips(member):
                qm = member.getQuestManager()
                if not qm.hasQuestCompleted(PN.PREQUEST):
                    qm.completeQuest(PN.PREQUEST, False)
    blocker = PN.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        sm.warpInstanceIn(PN.WAITING_ROOM, 0, chr.getParty() is not None)
elif sel == 1:
    sm.sendSayOkay("#e<Princess No>#n\r\n#e-Level#n: " + str(PN.MIN_LEVEL) + " or above\r\n"
                   "#e-Story#n: required for Classic; skipped for Express\r\n"
                   "#e-Players#n: 1 to " + str(PN.MAX_PARTY) + "\r\n#e-Clears#n: once a week\r\n#e-Time Limit#n: 30 min"
                   "\r\n\r\nGo up to the Temple's 5th floor and defeat Princess No.")
