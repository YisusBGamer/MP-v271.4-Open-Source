package net.swordie.ms.client.character.hexa;

import org.junit.Assert;
import org.junit.Test;

/**
 * Using Sol Erda energy items from the "how many?" box never wastes any past a full Sol Erda.
 * Contributed by Dapsy (from the Discord).
 */
public class EnergyItemsToUseTest {
    private static final int MAX = 20, GAUGE = 1000;

    @Test
    public void usesWhatWasAskedWhenThereIsRoom() {
        Assert.assertEquals(2, HexaModule.energyItemsToUse(0, 0, MAX, GAUGE, 500, 2));
    }

    @Test
    public void stopsAtFull() {
        // 19 Sol Erda and 600 in the gauge: 400 more fills it, one Dense (500) is enough
        Assert.assertEquals(1, HexaModule.energyItemsToUse(19, 600, MAX, GAUGE, 500, 10));
        // with 200-energy items it takes 2
        Assert.assertEquals(2, HexaModule.energyItemsToUse(19, 600, MAX, GAUGE, 200, 10));
    }

    @Test
    public void noneWhenFullOrNothingAsked() {
        Assert.assertEquals(0, HexaModule.energyItemsToUse(MAX, 0, MAX, GAUGE, 500, 5));
        Assert.assertEquals(0, HexaModule.energyItemsToUse(0, 0, MAX, GAUGE, 500, 0));
        Assert.assertEquals(0, HexaModule.energyItemsToUse(0, 0, MAX, GAUGE, 0, 5));
    }
}
