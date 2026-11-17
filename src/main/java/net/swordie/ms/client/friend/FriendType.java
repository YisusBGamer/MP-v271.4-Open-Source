package net.swordie.ms.client.friend;

/**
 * Created on 3/31/2018.
 * Thanks to PacketBakery for these
 */
public enum FriendType {
    // v271 requests (FRIEND_REQUEST 516; senders 0x143468bc0..0x143469d70, 0x144f7d8a0, 0x144f80310, 0x145a6a180).
    // Friends are account friends only; every id below is the account id.
    FriendReq_SetFriend(1), // S name, S group, S memo, S nickname
    FriendReq_AcceptFriend(2), // 4 account id (the request dialog's yes)
    FriendReq_DeleteFriend(3), // 4 account id, S
    FriendReq_RefuseFriend(4), // 4 account id, 1, S (the dialog's no; also sent for blocked users)
    FriendReq_RefuseAll(5), // no body
    FriendReq_ModifyFriend(6), // 4, 4 (ids), S nickname (<= 12), S memo (<= 200)
    FriendReq_SetGroupNames(7), // 4 n, n x (4 group, S name <= 16)
    FriendReq_SetAllowRequests(8), // 1
    FriendReq_EventBestFriend(9), // 4 char id: a friend in the same map (0x14549f930 -> 0x143469cf0)
    FriendReq_AcceptEventBestFriend(10), // 4 requester's char id (the 45 dialog's yes)
    FriendReq_RefuseEventBestFriend(11), // 4 requester's char id (the dialog's no / close)
    FriendReq_SetFriendGroups(12), // 4 n, n x (1 group, S name)

    // v271 results (FRIEND_RESULT 224, client 0x145b6fc30)
    FriendRes_LoadFriend_Done(13), // 4 n, B(n x 0x174) records
    FriendRes_NotifyChange_FriendInfo(15), // 4 char id, 4 account id, record
    FriendRes_GroupNames(16), // 4 n, n x (4 group, S name)
    FriendRes_Invite(17), // 4 char id, 4 account id, S name, 4 level, 4 job, 4 sub job, record
    FriendRes_SetFriend_Done(18), // S name: "request sent to %s"
    FriendRes_MaxFriendMe(19),
    FriendRes_MaxSendRequestMe(20),
    FriendRes_MaxFriendOther(21),
    FriendRes_MaxReceiveRequestOther(22),
    FriendRes_SetFriend_RemainCharacterFriend(23), // still waiting to be added
    FriendRes_SetFriend_AlreadyRequested(24),
    FriendRes_SetFriend_FullMe(25),
    FriendRes_SetFriend_CantSelf(26),
    FriendRes_SetFriend_AlreadySet(27),
    FriendRes_SetFriend_FullOther(28),
    FriendRes_SetFriend_Unknown(29), // 1 has, [S text]; else "unknown error"
    FriendRes_SetFriend_BlackListed(30), // S name, 4, 4
    FriendRes_SetAllowRequests(31), // 1
    FriendRes_SendSingleFriendInfo(32), // record (added, or replaced by account id)
    FriendRes_SendFriendInfos(33), // 4 n, n x record
    FriendRes_DeleteFriend_Done(35), // 4 account id
    FriendRes_RejectAll_Done(37), // as the load, plus "all requests rejected"
    FriendRes_AcceptFriend_Unknown(38),
    FriendRes_RejectAll_NoRequest(39),
    FriendRes_LoginNotice(40), // 4 char id, 4 account id, 4 channel (-1 off), 1, 1, S char name, B8: updates the
                               // record, "%s has logged in" when it goes from offline to a channel
    FriendRes_AlreadyMadeRequest(41),
    FriendRes_Refused(43), // S name: declined the request
    FriendRes_EventBestFriend_Close(44), // 4 char id: drops the pending best-friend notice of that requester
    FriendRes_EventBestFriend_Invite(45), // 4 requester's char id, S name: the accept / refuse dialog
    FriendRes_EventBestFriend_Refused(46), // S name
    FriendRes_NotAccepting(47), // S name
    ;

    private int val;

    FriendType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
