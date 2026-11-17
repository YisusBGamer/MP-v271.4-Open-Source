package net.swordie.ms.client.friend;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;

/**
 * Created on 3/31/2018.
 */
public class Friend {

    private int id;

    private String group;

    private int friendID;
    private String name;
    private byte flag; // 5 through 8 = account friend
    private byte mobile;
    private int friendAccountID;
    private String nickname;
    private String memo;

    // Transient
    private int channelID;
    private Char chr;
    private boolean inShop;

    /** v271 flags: 7 friend (channel -1 = offline), 6 request to this user, 5 waiting for the other's answer. */
    public static final int V271_FRIEND = 7, V271_REQUEST = 6, V271_WAITING = 5;

    public int getV271Flag() {
        int f = getFlag();
        if (f == FriendFlag.FriendRequest.getVal() || f == FriendFlag.AccountFriendRequest.getVal()) {
            return V271_REQUEST;
        }
        return f == FriendFlag.Waiting.getVal() ? V271_WAITING : V271_FRIEND;
    }

    /** Request or waiting entries keep their flag through logins (only friends go online/offline). */
    public boolean isPending() {
        return getV271Flag() != V271_FRIEND;
    }

    private transient Integer shownChannel; // test override

    public void setShownChannel(Integer shownChannel) {
        this.shownChannel = shownChannel;
    }

    /** 0-based channel of an online friend, -1 offline. */
    public int getV271Channel() {
        if (shownChannel != null) {
            return shownChannel;
        }
        return isOnline() ? getChr().getChannel() - 1 : -1;
    }

    /**
     * v271 record (0x14078b6b0, list stride 0x174): 4 char id, B13 name, 1 flag, 4 channel, B17 group, 4 account id,
     * B13 nickname; flag 7: B256 memo, 1, B8; other flags: B51.
     */
    public void encodeV271(OutPacket outPacket) {
        int flag = getV271Flag();
        outPacket.encodeInt(getFriendID());
        encodeFixed(outPacket, getName(), 13);
        outPacket.encodeByte(flag);
        outPacket.encodeInt(flag == V271_FRIEND ? getV271Channel() : -1);
        encodeFixed(outPacket, getGroup(), 17);
        outPacket.encodeInt(getFriendAccountID());
        encodeFixed(outPacket, getNickname() == null || getNickname().isEmpty() ? getName() : getNickname(), 13);
        if (flag == V271_FRIEND) {
            encodeFixed(outPacket, getMemo(), 256);
            outPacket.encodeByte(1); // client default
            outPacket.encodeLong(0);
        } else {
            outPacket.encodeArr(new byte[51]);
        }
    }

    /** The load's raw form: the client copies n x 0x174 bytes straight into its records. */
    public void encodeV271Raw(OutPacket outPacket) {
        int flag = getV271Flag();
        outPacket.encodeInt(getFriendID());
        encodeFixed(outPacket, getName(), 13);
        outPacket.encodeByte(flag);
        outPacket.encodeInt(flag == V271_FRIEND ? getV271Channel() : -1);
        encodeFixed(outPacket, getGroup(), 17);
        outPacket.encodeInt(getFriendAccountID());
        encodeFixed(outPacket, getNickname() == null || getNickname().isEmpty() ? getName() : getNickname(), 13);
        encodeFixed(outPacket, getMemo(), 256);
        outPacket.encodeArr(new byte[51]);
        outPacket.encodeByte(1);
        outPacket.encodeLong(0);
    }

    /** Exactly n bytes, zero padded, the last one always 0. */
    private static void encodeFixed(OutPacket outPacket, String s, int n) {
        byte[] out = new byte[n];
        if (s != null) {
            byte[] b = s.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
            System.arraycopy(b, 0, out, 0, Math.min(b.length, n - 1));
        }
        outPacket.encodeArr(out);
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getFriendID());
        outPacket.encodeString(getName(), 13);
        outPacket.encodeByte(getFlag());
        outPacket.encodeInt(getChannelID());
        outPacket.encodeString(getGroup(), 17);
        outPacket.encodeByte(getMobile());
        outPacket.encodeInt(getFriendAccountID());
        outPacket.encodeString(getNickname(), 13);
        outPacket.encodeString(getMemo(), 256);
        outPacket.encodeInt(isInShop() ? 1 : 0);
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void setFlag(FriendFlag flag) {
        this.flag = (byte) flag.getVal();
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public byte getMobile() {
        return mobile;
    }

    public void setMobile(byte mobile) {
        this.mobile = mobile;
    }

    public int getFriendAccountID() {
        return friendAccountID;
    }

    public void setFriendAccountID(int friendAccountID) {
        this.friendAccountID = friendAccountID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isInShop() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop = inShop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAccount() {
        return getFlag() > 4;
    }

    public Char getChr() {
        return chr;
    }

    public void setChr(Char chr) {
        this.chr = chr;
    }

    public void write(OutPacket outPacket) {
        if (isOnline()) {
            chr.write(outPacket);
        }
    }

    public boolean isOnline() {
        return getChr() != null && getChr().getUser() != null;
    }

}
