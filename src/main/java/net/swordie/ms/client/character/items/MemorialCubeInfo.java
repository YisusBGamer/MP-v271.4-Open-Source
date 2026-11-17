package net.swordie.ms.client.character.items;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.ItemGrade;

import java.util.ArrayList;
import java.util.List;

/**
 * A cube roll waiting for the player's pick in the before/after popup. The equip keeps its old lines until one of
 * the results is taken; dropping this (no pick, logout, map change) keeps the old ones. Reset x3 holds three results.
 */
public class MemorialCubeInfo {
    private Equip equip;
    private int cubeItemID;
    private List<Equip> results = new ArrayList<>();
    private short cubeSlot;

    public MemorialCubeInfo() {
    }

    /**
     * @param equip the cubed equip (unchanged)
     * @param cubeItemID the cube's item id (-1 / -2 for the meso-only reset)
     * @param result a copy of the equip carrying the new lines
     */
    public MemorialCubeInfo(Equip equip, int cubeItemID, Equip result) {
        this(equip, cubeItemID, List.of(result), (short) 0);
    }

    public MemorialCubeInfo(Equip equip, int cubeItemID, List<Equip> results, short cubeSlot) {
        this.equip = equip;
        this.cubeItemID = cubeItemID;
        this.results = new ArrayList<>(results);
        this.cubeSlot = cubeSlot;
    }

    /**
     * v265 (0x1406501e0 / 0x14064fff0): byte has; long serial, int cube, short cube slot, long, int, int equip slot
     * (must be non-zero); byte n, n x (byte grade, 6 option shorts, byte bonus grade, int) (v271 0x140792530).
     */
    public void encode(OutPacket outPacket) {
        Equip equip = getEquip();
        outPacket.encodeByte(equip != null);
        if (equip != null) {
            outPacket.encodeLong(equip.getSerialNumber());
            outPacket.encodeInt(getCubeItemID());
            outPacket.encodeShort(cubeSlot);
            outPacket.encodeLong(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(equip.getBagIndexWithEquipped());
            outPacket.encodeByte(getResultCount());
            for (int r = 0; r < getResultCount(); r++) {
                List<Integer> options = getResult(r).getOptions();
                outPacket.encodeByte(ItemGrade.getGradeByOption(options.get(0)).getVal());
                for (int i = 0; i < 6; i++) {
                    outPacket.encodeShort(i < options.size() ? options.get(i) : 0);
                }
                outPacket.encodeByte(ItemGrade.getGradeByOption(options.get(3)).getVal());
                outPacket.encodeInt(0); // v271 (0x140792530): low 4 bits = the bonus potential's kept tier
            }
        }
    }

    public Equip getEquip() {
        return equip;
    }

    public void setEquip(Equip equip) {
        this.equip = equip;
    }

    public int getCubeItemID() {
        return cubeItemID;
    }

    public void setCubeItemID(int cubeItemID) {
        this.cubeItemID = cubeItemID;
    }

    public int getResultCount() {
        return Math.max(1, results.size());
    }

    /** @return the equip as it would be with the first result's lines */
    public Equip getResult() {
        return getResult(0);
    }

    public Equip getResult(int index) {
        return index >= 0 && index < results.size() ? results.get(index) : equip;
    }

    public List<Integer> getNewOptions() {
        return getResult().getOptions();
    }

    /** Puts the first result's lines on the equip. */
    public void applyNewOptions() {
        applyNewOptions(0);
    }

    /** Puts the picked result's lines on the equip. @return false for an index with no result */
    public boolean applyNewOptions(int index) {
        if (index < 0 || index >= results.size()) {
            return false;
        }
        getEquip().setOptions(new ArrayList<>(results.get(index).getOptions()));
        return true;
    }
}
