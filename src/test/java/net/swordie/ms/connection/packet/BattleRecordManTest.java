package net.swordie.ms.connection.packet;

import net.swordie.ms.handlers.header.OutHeader;
import org.junit.Assert;
import org.junit.Test;

/**
 * Battle Analysis "Enemies Defeated" - v271 Out 2933 (it was an unverified number, so never sent).
 * Contributed by Dapsy (from the Discord).
 */
public class BattleRecordManTest {

    @Test
    public void enemiesDefeatedIsOut2933() {
        Assert.assertEquals(2933, OutHeader.MOBS_DEFEATED_INC.getValue());
    }
}
