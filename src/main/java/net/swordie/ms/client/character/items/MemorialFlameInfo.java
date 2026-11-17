package net.swordie.ms.client.character.items;

import java.util.List;

/**
 * A Black Rebirth Flame result waiting for the player's pick: the equip it was used on and copies holding the new
 * bonus stats (one per reset; a meso reset can roll 3 at once).
 */
public class MemorialFlameInfo {
    private final Equip equip;
    private final List<Equip> results;
    private final int flameId;

    public MemorialFlameInfo(Equip equip, Equip result, int flameId) {
        this(equip, List.of(result), flameId);
    }

    public MemorialFlameInfo(Equip equip, List<Equip> results, int flameId) {
        this.equip = equip;
        this.results = List.copyOf(results);
        this.flameId = flameId;
    }

    public Equip getEquip() {
        return equip;
    }

    public Equip getResult() {
        return results.get(0);
    }

    /** @return the result at the window's pick index, or null */
    public Equip getResult(int pick) {
        return pick >= 0 && pick < results.size() ? results.get(pick) : null;
    }

    public List<Equip> getResults() {
        return results;
    }

    public int getFlameId() {
        return flameId;
    }
}
