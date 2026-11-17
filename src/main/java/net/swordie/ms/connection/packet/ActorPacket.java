package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;

/**
 * The v271 client's actor replication (the WZ2 actor system: ALynnController, AKannaController, AUserController ...).
 * <p>
 * Object stream (0x146676560): per object int id (0 ends the stream), int class hash, byte mode. Mode 4 destroys the
 * object (+ 1 byte). Modes 1/2/3 create or update it (1: + int, 2: + int + 8 bytes; 3: nothing more), then records:
 * the first record's type is the mode, each next one is a byte (0 ends the object). A create/update record is a
 * component list (byte index + 1, int net id; byte 0 ends), the properties as (u16 slot + 1, value) ended by u16 0,
 * then length-prefixed sub-blocks ended by u16 0.
 * <p>
 * Slots count the replicated properties of the class and its bases in order, an array property taking one slot per
 * element. The AActor base fills slots 1-19: 8 Location (2 floats), 16 NetOwnerID (int user, int character),
 * 17 role (byte, the client's own role: 1 simulated, 2 autonomous), 18 bReplicateMovement, 19 bHidden.
 * An object reference is int object id, int 0.
 * <p>
 * RPC calls (USER_ACTOR_REPLICATION with byte 9, read by 0x146685760): per call u16 length of the rest, int object,
 * u16 RPC index, arguments. RPC indexes count the class's bases first: AActor + UObject have 50.
 */
public class ActorPacket {

    public static final int LYNN_CONTROLLER_HASH = 0x243ff351;
    public static final int USER_CONTROLLER_HASH = 0x825ad512;
    public static final int BASE_RPC_COUNT = 50;
    public static final byte ROLE_SIMULATED = 1;
    public static final byte ROLE_AUTONOMOUS = 2;
    private static final byte MODE_UPDATE = 3;
    private static final byte MODE_DESTROY = 4;
    private static final byte RPC_CALLS = 9;
    /** CWvsContext::OnExtraSystemResult's key for the replication clock messages. */
    private static final int CLOCK_KEY = 0x3f7d24b3; // wire B3 24 7D 3F, as GMS
    private static final long CLOCK_EPOCH = System.currentTimeMillis();

    public static void beginObject(OutPacket outPacket, int objectId, int classHash) {
        outPacket.encodeInt(objectId);
        outPacket.encodeInt(classHash);
        outPacket.encodeByte(MODE_UPDATE);
        outPacket.encodeByte(0); // no components
    }

    public static void endObject(OutPacket outPacket) {
        outPacket.encodeShort(0); // properties
        outPacket.encodeShort(0); // sub-blocks
        outPacket.encodeByte(0); // records
    }

    public static void destroyObject(OutPacket outPacket, int objectId, int classHash) {
        outPacket.encodeInt(objectId);
        outPacket.encodeInt(classHash);
        outPacket.encodeByte(MODE_DESTROY);
        outPacket.encodeByte(0);
    }

    /** AActor slots 16-19 for an actor owned by the given user. */
    public static void encodeActorBase(OutPacket outPacket, int userId, int charId, byte role) {
        outPacket.encodeShort(16);
        outPacket.encodeInt(userId);
        outPacket.encodeInt(charId);
        outPacket.encodeShort(17);
        outPacket.encodeByte(role);
        outPacket.encodeShort(18);
        outPacket.encodeByte(0);
        outPacket.encodeShort(19);
        outPacket.encodeByte(0);
    }

    public static void encodeActorBase(OutPacket outPacket, int userId, int charId) {
        encodeActorBase(outPacket, userId, charId, ROLE_SIMULATED);
    }

    /** AActor slot 8: the replicated location. */
    public static void encodeLocation(OutPacket outPacket, float x, float y) {
        outPacket.encodeShort(8);
        outPacket.encodeInt(Float.floatToIntBits(x));
        outPacket.encodeInt(Float.floatToIntBits(y));
    }

    public static void encodeObjectRef(OutPacket outPacket, int slot, int objectId) {
        outPacket.encodeShort(slot);
        outPacket.encodeInt(objectId);
        outPacket.encodeInt(0);
    }

    /** Objects for a user's channel; {@code objects} is an object stream without the closing 0. */
    public static OutPacket userActorUpdate(int charId, byte[] objects) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_ACTOR_REPLICATION);
        outPacket.encodeInt(charId);
        outPacket.encodeByte(0);
        outPacket.encodeArr(objects);
        outPacket.encodeInt(0);
        return outPacket;
    }

    /** One RPC call on an object of a user's channel; {@code rpcIndex} counts the bases' functions too. */
    public static OutPacket userActorRpc(int charId, int objectId, int rpcIndex, byte[] args) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_ACTOR_REPLICATION);
        outPacket.encodeInt(charId);
        outPacket.encodeByte(RPC_CALLS);
        outPacket.encodeShort(4 + 2 + args.length);
        outPacket.encodeInt(objectId);
        outPacket.encodeShort(rpcIndex);
        outPacket.encodeArr(args);
        return outPacket;
    }

    /** Seconds on the replication clock the client keeps after {@link #clockSync()}. */
    public static double clockNow() {
        return (System.currentTimeMillis() - CLOCK_EPOCH) / 1000D;
    }

    /**
     * Sets the client's replication clock, which time properties such as ALynnController.PeckingBladeAssaultEndTime
     * are compared with: key, u16 1, double seconds, 8 bytes.
     */
    public static OutPacket clockSync() {
        OutPacket outPacket = new OutPacket(OutHeader.EXTRA_SYSTEM_RESULT);
        outPacket.encodeInt(CLOCK_KEY);
        outPacket.encodeShort(1);
        outPacket.encodeDouble(clockNow());
        outPacket.encodeFT(FileTime.currentTime());
        return outPacket;
    }
}
