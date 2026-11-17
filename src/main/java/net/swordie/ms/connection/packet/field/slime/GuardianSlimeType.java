package net.swordie.ms.connection.packet.field.slime;

/** GUARDIAN_ANGEL_SLIME_FIELD types (CField_GuardianAngelSlime, client 0x142d49510). */
public enum GuardianSlimeType {
    Wave(1),
    Pit(2),
    Objects(3),
    CrystalDrop(4),
    Setup(5),
    Phase(6),
    Magma(7),
    Crystals(8),
    Gauge(9),
    GroggyDamage(11),
    GoldenSlime(12),
    WaveLayout(14),
    ;

    private final int val;

    GuardianSlimeType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
