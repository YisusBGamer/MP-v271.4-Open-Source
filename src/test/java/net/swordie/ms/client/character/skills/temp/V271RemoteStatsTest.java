package net.swordie.ms.client.character.skills.temp;

import org.junit.Assert;
import org.junit.Test;

/** The speed byte at the end of another player's buff block: 100 + speed, held between 100 and 255. */
public class V271RemoteStatsTest {

    @Test
    public void noBonusIsBaseSpeed() {
        Assert.assertEquals(100, V271RemoteStats.speedByte(0));
    }

    @Test
    public void speedBonusAddsToTheBase() {
        Assert.assertEquals(140, V271RemoteStats.speedByte(40));
    }

    @Test
    public void speedStaysInByteRange() {
        Assert.assertEquals(100, V271RemoteStats.speedByte(-30));
        Assert.assertEquals(255, V271RemoteStats.speedByte(300));
    }
}
