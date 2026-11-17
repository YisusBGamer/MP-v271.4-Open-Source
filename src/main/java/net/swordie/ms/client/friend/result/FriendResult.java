package net.swordie.ms.client.friend.result;

import net.swordie.ms.client.friend.Friend;
import net.swordie.ms.client.friend.FriendType;
import net.swordie.ms.connection.OutPacket;

import java.util.Collection;
import java.util.Map;

/**
 * FRIEND_RESULT, v271 layouts (client 0x145b6fc30). The client ignores the packet until its friend manager exists.
 */
public class FriendResult {

    private FriendType friendType;
    private Friend friend;
    private Collection<Friend> friends;
    private Map<Integer, String> groups;
    private String str;
    private int level;
    private int job;
    private int subJob;

    public FriendResult(FriendType friendType) {
        this.friendType = friendType;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(friendType.getVal());
        switch (friendType) {
            case FriendRes_LoadFriend_Done, FriendRes_RejectAll_Done -> {
                outPacket.encodeInt(friends.size());
                friends.forEach(f -> f.encodeV271Raw(outPacket));
            }
            case FriendRes_NotifyChange_FriendInfo -> {
                outPacket.encodeInt(friend.getFriendID());
                outPacket.encodeInt(friend.getFriendAccountID());
                friend.encodeV271(outPacket);
            }
            case FriendRes_GroupNames -> {
                outPacket.encodeInt(groups.size());
                groups.forEach((k, v) -> {
                    outPacket.encodeInt(k);
                    outPacket.encodeString(v);
                });
            }
            case FriendRes_Invite -> {
                outPacket.encodeInt(friend.getFriendID());
                outPacket.encodeInt(friend.getFriendAccountID());
                outPacket.encodeString(friend.getName());
                outPacket.encodeInt(level);
                outPacket.encodeInt(job);
                outPacket.encodeInt(subJob);
                friend.encodeV271(outPacket);
            }
            case FriendRes_SendSingleFriendInfo -> friend.encodeV271(outPacket);
            case FriendRes_SendFriendInfos -> {
                outPacket.encodeInt(friends.size());
                friends.forEach(f -> f.encodeV271(outPacket));
            }
            case FriendRes_DeleteFriend_Done -> outPacket.encodeInt(friend.getFriendAccountID());
            case FriendRes_SetAllowRequests -> outPacket.encodeByte(level);
            case FriendRes_SetFriend_Unknown -> {
                outPacket.encodeByte(str != null);
                if (str != null) {
                    outPacket.encodeString(str);
                }
            }
            case FriendRes_SetFriend_BlackListed -> {
                outPacket.encodeString(str);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
            }
            case FriendRes_SetFriend_Done, FriendRes_Refused, FriendRes_NotAccepting,
                 FriendRes_EventBestFriend_Refused -> outPacket.encodeString(str);
            case FriendRes_LoginNotice -> {
                outPacket.encodeInt(level); // char id
                outPacket.encodeInt(job); // account id
                outPacket.encodeInt(subJob); // channel, -1 offline
                outPacket.encodeByte(0);
                outPacket.encodeByte(1); // record +0x16b (client default 1)
                outPacket.encodeString(str);
                outPacket.encodeLong(0);
            }
            case FriendRes_EventBestFriend_Close -> outPacket.encodeInt(level);
            case FriendRes_EventBestFriend_Invite -> {
                outPacket.encodeInt(level);
                outPacket.encodeString(str);
            }
            default -> {
                // message types without a body
            }
        }
    }

    public static FriendResult loadFriends(Collection<Friend> friends) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_LoadFriend_Done);
        fr.friends = friends;
        return fr;
    }

    public static FriendResult rejectAllDone(Collection<Friend> friends) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_RejectAll_Done);
        fr.friends = friends;
        return fr;
    }

    public static FriendResult updateFriend(Friend friend) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_NotifyChange_FriendInfo);
        fr.friend = friend;
        return fr;
    }

    public static FriendResult singleFriend(Friend friend) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_SendSingleFriendInfo);
        fr.friend = friend;
        return fr;
    }

    public static FriendResult groupNames(Map<Integer, String> groups) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_GroupNames);
        fr.groups = groups;
        return fr;
    }

    public static FriendResult friendInvite(Friend friend, int level, int job, int subJob) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_Invite);
        fr.friend = friend;
        fr.level = level;
        fr.job = job;
        fr.subJob = subJob;
        return fr;
    }

    public static FriendResult deleteFriend(Friend friend) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_DeleteFriend_Done);
        fr.friend = friend;
        return fr;
    }

    public static FriendResult allowRequests(boolean allow) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_SetAllowRequests);
        fr.level = allow ? 1 : 0;
        return fr;
    }

    public static FriendResult loginNotice(int charId, int accountId, int channel, String charName) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_LoginNotice);
        fr.level = charId;
        fr.job = accountId;
        fr.subJob = channel;
        fr.str = charName;
        return fr;
    }

    public static FriendResult bestFriendInvite(int requesterCharId, String requesterName) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_EventBestFriend_Invite);
        fr.level = requesterCharId;
        fr.str = requesterName;
        return fr;
    }

    public static FriendResult bestFriendClose(int requesterCharId) {
        FriendResult fr = new FriendResult(FriendType.FriendRes_EventBestFriend_Close);
        fr.level = requesterCharId;
        return fr;
    }

    public static FriendResult message(FriendType friendType) {
        return new FriendResult(friendType);
    }

    public static FriendResult message(FriendType friendType, String str) {
        FriendResult fr = new FriendResult(friendType);
        fr.str = str;
        return fr;
    }
}
