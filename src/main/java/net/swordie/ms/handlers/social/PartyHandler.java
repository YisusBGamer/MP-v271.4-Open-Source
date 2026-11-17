package net.swordie.ms.handlers.social;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.party.PartyRecruitment;
import net.swordie.ms.client.party.PartyResult;
import net.swordie.ms.client.party.PartyType;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.ChatType.SystemNotice;

public class PartyHandler {

    private static final Logger log = LogManager.getLogger();


    @Handler(op = InHeader.PARTY_INVITABLE_SET)
    public static void handlePartyInvitableSet(Client c, InPacket inPacket) {
        c.getChr().setPartyInvitable(inPacket.decodeByte() != 0);
    }

    @Handler(op = InHeader.PARTY_REQUEST)
    public static void handlePartyRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        byte type = inPacket.decodeByte();
        if (type == 11) {
            // v271 only (sender 0x142dca980): the leader's Discord party link, B8 lobby id; no Discord here
            return;
        }
        if (type >= 0xe) {
            type--; // v271 senders: 0-12 as v265, 14 and up one higher
        }
        if (PartyRecruitment.isRequest(type)) {
            PartyRecruitment.handle(chr, type, inPacket); // Party Finder
            return;
        }
        PartyType prt = PartyType.getByVal(type);
        Party party = chr.getParty();
        if (prt == null) {
            log.debug(String.format("Unknown party request type %d", type));
            return;
        }
        switch (prt) {
            case PartyReq_CreateNewParty:
                if (party != null) {
                    chr.chatMessage("You are already in a party.");
                    return;
                }
                String name = inPacket.decodeString();
                var appliable = inPacket.decodeByte() != 0;
                var masterLoot = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() != 0 : inPacket.decodeByte() != 0; // v265: int loot setting

                party = Party.createNewParty(appliable, masterLoot, name, chr.getClient().getWorld());
                party.addPartyMember(chr);
                party.broadcast(WvsContext.partyResult(PartyResult.createNewParty(party)));
                break;
            case PartyReq_WithdrawParty:
                if (inPacket.getUnreadAmount() > 0) {
                    inPacket.decodeByte(); // v265: one more byte
                }
                if (party == null) {
                    return;
                }
                var leaver = party.getPartyMemberByID(chr.getId());
                if (leaver == null) {
                    // weird possibility with clashing ids after restart
                    chr.setParty(null);
                    chr.setPartyID(0);

                    var tempPartyMember = new PartyMember(chr);
                    chr.write(WvsContext.partyResult(PartyResult.withdrawParty(party, tempPartyMember, false, false, chr)));
                    break;
                }
                if (party.getMembers().size() <= 1) {
                    party.disband(); // the last one out
                    break;
                }
                if (party.hasCharAsLeader(chr)) {
                    // the leader leaving passes the lead on (an online member first) instead of ending the party
                    party.passLeadership(chr.getId(), 0);
                }
                // everyone (the leaver too) is told this member left; the party goes on
                party.broadcastLeave(leaver, false);
                party.removePartyMember(leaver);
                party.updateFull();
                break;
            case PartyReq_InviteParty:
                String invitedName = inPacket.decodeString();
                if (inPacket.getUnreadAmount() >= 4) {
                    inPacket.decodeInt();
                }
                if (party != null && !party.hasCharAsLeader(chr)) {
                    chr.chatMessage(SystemNotice, "Only the party leader can invite.");
                    return;
                }
                // anyone online in the world, as in GMS (it used to be only the inviter's map)
                Char invited = chr.getField().getCharByName(invitedName);
                if (invited == null) {
                    invited = chr.getClient().getWorld().getCharByName(invitedName);
                    if (invited != null && invited.isHidden()) {
                        invited = null; // a hidden GM can't be found by name
                    }
                }
                if (invited == null) {
                    chr.write(WvsContext.partyResult(PartyResult.inviteResult(PartyResult.INVITE_NOT_FOUND, invitedName)));
                    return;
                }

                if (invited.hasChrBlacklisted(chr.getId())) {
                    chr.write(WvsContext.partyResult(PartyResult.inviteResult(1, invitedName)));
                    return;
                }

                if (party == null) {
                    party = Party.createNewParty(true, false,"Party with me!", chr.getClient().getWorld());
                    PartyMember pm = new PartyMember(chr);
                    party.setPartyLeaderID(pm.getCharID());
                    party.getPartyMembers()[0] = pm;
                    chr.setParty(party);
                    chr.write(WvsContext.partyResult(PartyResult.createNewParty(party)));
                }
                PartyMember inviter = party.getPartyLeader();
                if (!invited.isPartyInvitable()) {
                    chr.write(WvsContext.partyResult(PartyResult.inviteResult(1, invitedName)));
                } else if (invited.getParty() == null) {
                    party.addPendingInvite(invited.getId());
                    invited.write(WvsContext.partyResult(PartyResult.applyParty(party, inviter)));
                    chr.write(WvsContext.partyResult(PartyResult.inviteResult(0, invitedName)));
                } else {
                    chr.write(WvsContext.partyResult(PartyResult.inviteResult(10, invitedName)));
                }
                break;
            case PartyReq_KickParty:
                int expelID = inPacket.decodeInt();
                if (party == null || !party.hasCharAsLeader(chr) || expelID == chr.getId()) {
                    break; // only the leader can kick (any member used to be able to, the leader included)
                }
                party.expel(expelID);
                break;
            case PartyReq_ChangePartyBoss:
                int newLeaderID = inPacket.decodeInt();
                if (party == null || !party.hasCharAsLeader(chr) || party.getPartyMemberByID(newLeaderID) == null) {
                    break; // only the leader, and only to a member
                }
                party.setPartyLeaderID(newLeaderID);
                party.broadcast(WvsContext.partyResult(PartyResult.changePartyBoss(party, 0))); // 0: handed over
                break;
            case PartyReq_ApplyParty:
                int partyID = inPacket.decodeInt();
                party = chr.getField().getChars().stream()
                        .filter(ch -> ch.getParty() != null && ch.getParty().getId() == partyID)
                        .map(Char::getParty)
                        .findFirst()
                        .orElse(null);
                if (party == null || chr.getParty() != null || party.getPartyLeader() == null
                        || party.getPartyLeader().getChr() == null) {
                    break;
                }
                if (party.getApplyingChar() == null) {
                    var partyLeaderChr = party.getPartyLeader().getChr();

                    if (partyLeaderChr.hasChrBlacklisted(chr.getId())) {
                        chr.chatMessage(String.format("%s is not accepting applications.", partyLeaderChr.getName()));
                        chr.dispose();
                        return;
                    }

                    party.setApplyingChar(chr);
                    partyLeaderChr.write(WvsContext.partyResult(PartyResult.inviteIntrusion(party, chr)));
                } else {
                    chr.chatMessage(SystemNotice, "That party already has an applier. Please wait until the applier is accepted or denied.");
                }
                break;
            case PartyReq_PartySetting:
                var partyName = inPacket.decodeString();
                appliable = inPacket.decodeByte() != 0;
                masterLoot = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() != 0 : inPacket.decodeByte() != 0;
                if (party == null || !party.hasCharAsLeader(chr)) {
                    break; // only the leader changes the settings
                }

                party.setName(partyName);
                party.setAppliable(appliable);
                party.setMasterLoot(masterLoot);

                party.broadcast(WvsContext.partyResult(PartyResult.settingDone(party)));
                break;
            default:
                log.error(String.format("Unhandled party request type %s", prt));
                break;
        }
        chr.dispose();
    }

    @Handler(op = InHeader.PARTY_RESULT)
    public static void handlePartyResult(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        byte type = inPacket.decodeByte();
        int partyID = inPacket.decodeInt();
        if (type >= 47 && type <= 49) {
            type -= 3; // v271 replies 47-49 are the v265 44-46
        }
        PartyType pt = PartyType.getByVal(type);
        if (pt == null) {
            log.error(String.format("Unknown party request result type %d", type));
            return;
        }
        switch (pt) {
            case PartyRes_ApplyParty_Accepted:
                Char leader = chr.getField().getChars().stream()
                        .filter(l -> l.getParty() != null
                                && l.getParty().getId() == partyID).findFirst().orElse(null);
                if (leader == null) {
                    chr.chatMessage("Could not find the leader.");
                    return;
                }

                Party party = leader.getParty();
                if (chr.getParty() != null) {
                    chr.chatMessage("You are already in a party.");
                    return;
                }
                if (!party.consumeInvite(chr.getId())) {
                    return; // never invited to this party
                }
                if (!party.isFull()) {
                    party.addPartyMember(chr);
                    for (Char onChar : party.getOnlineMembers().stream().map(PartyMember::getChr).collect(Collectors.toList())) {
                        onChar.write(WvsContext.partyResult(PartyResult.joinParty(party, chr.getName())));
                        chr.write(UserRemote.receiveHP(onChar));
                    }
                    party.broadcast(UserRemote.receiveHP(chr));
                } else {
                    chr.write(WvsContext.partyResult(PartyResult.msg(PartyType.PartyRes_JoinParty_AlreadyFull)));
                }
                break;
            case PartyRes_ApplyParty_Rejected:
                leader = chr.getField().getChars().stream()
                        .filter(l -> l.getParty() != null
                                && l.getParty().getId() == partyID).findFirst().orElse(null);
                if (leader != null && leader.getParty().consumeInvite(chr.getId())) {
                    leader.chatMessage(SystemNotice, String.format("%s has declined your invite.", chr.getName()));
                }
                break;
            case PartyRes_InviteIntrusion_Accepted:
                party = chr.getClient().getWorld().getPartybyId(partyID);
                if (party == null || !party.hasCharAsLeader(chr) || party.getApplyingChar() == null) {
                    return; // only the leader of that party answers its applications
                }
                Char applier = party.getApplyingChar();
                if (applier.getParty() != null) {
                    party.getPartyLeader().getChr().chatMessage(SystemNotice, String.format("%s is already in a party.", applier.getName()));
                } else if (!party.isFull()) {
                    party.addPartyMember(applier);
                    for (Char onChar : party.getOnlineMembers().stream().map(PartyMember::getChr).collect(Collectors.toList())) {
                        onChar.write(WvsContext.partyResult(PartyResult.joinParty(party, applier.getName())));
                    }
                } else {
                    applier.write(WvsContext.partyResult(PartyResult.msg(PartyType.PartyRes_JoinParty_AlreadyFull)));
                }
                party.setApplyingChar(null);
                break;
            case PartyRes_InviteIntrusion_Rejected:
                party = chr.getClient().getWorld().getPartybyId(partyID);
                if (party == null || !party.hasCharAsLeader(chr)) {
                    return;
                }
                applier = party.getApplyingChar();
                if (applier != null) {
                    applier.chatMessage(SystemNotice, "Your party apply request has been denied.");
                    party.setApplyingChar(null);
                }
                break;
            case PartyRes_InviteIntrusion_Sent:
                // Basically act like acks
                break;
            default:
                log.error(String.format("Unhandled party request result type %s", pt));
                break;
        }
        chr.dispose();
    }

    @Handler(op = InHeader.PARTY_MEMBER_CANDIDATE_REQUEST)
    public static void handlePartyMemberCandidateRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        chr.write(WvsContext.partyMemberCandidateResult(field.getChars().stream()
                .filter(ch -> ch.isPartyInvitable() && !ch.equals(chr) && ch.getParty() == null)
                .collect(Collectors.toSet())));
    }

    @Handler(op = InHeader.PARTY_CANDIDATE_REQUEST)
    public static void handlePartyCandidateRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr.getParty() != null) {
            chr.write(WvsContext.partyCandidateResult(new HashSet<>()));
            return;
        }
        Field field = chr.getField();
        Set<Party> parties = new HashSet<>();
        for (Char ch : field.getChars()) {
            if (ch.getParty() != null && ch.getParty().hasCharAsLeader(ch)) {
                parties.add(ch.getParty());
            }
        }
        chr.write(WvsContext.partyCandidateResult(parties));
    }

}
