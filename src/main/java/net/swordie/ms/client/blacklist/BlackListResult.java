package net.swordie.ms.client.blacklist;

/**
 * Created on 05/06/2021.
 *
 * @author Asura
 */
public enum BlackListResult {
    // v265 client: 1 = an unknown entry update (Str, Str, int, int, Str)
    BlackListInsertRequestDone(3),
    BlackListDeleteRequestDone(5),
    EncodeBlackListView(6),
    // 7-9 and 12 are plain notices; which text each shows is unverified
    FailedRequest(7),
    CharacterNotInList(8),
    CannotAddAdminCharacter(9),
    IsAlreadyRegistered(10),
    IsAlreadyBeingUsed(11),
    IsInFriendList(13),
    IsInFriendList2(14),
    ;

    private int val;

    public int getVal() {
        return val;
    }

    BlackListResult(int val) {
        this.val = val;
    }
}
