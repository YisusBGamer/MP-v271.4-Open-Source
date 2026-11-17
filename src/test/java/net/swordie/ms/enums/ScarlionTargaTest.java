package net.swordie.ms.enums;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Scarlion and Targa have a spawn script and count as their boss when their final forms die.
 * Contributed by Dapsy (from the Discord).
 */
public class ScarlionTargaTest {

    @Test
    public void finalFormsAreTheBoss() {
        Assert.assertEquals(BossCooldown.ScarlionTarga, BossCooldown.byTemplateId(9420544)); // Furious Targa
        Assert.assertEquals(BossCooldown.ScarlionTarga, BossCooldown.byTemplateId(9420549)); // Furious Scarlion Boss
        Assert.assertEquals(BossCooldown.Limit.DAILY, BossCooldown.ScarlionTarga.getLimit());
    }

    @Test
    public void venueScriptSpawnsBothOnce() throws Exception {
        String script = Files.readString(Path.of("scripts/field/FantasticAnimalShow.py"));
        Assert.assertTrue(script.contains("9420542"));
        Assert.assertTrue(script.contains("9420547"));
        Assert.assertTrue(script.contains("fieldHasProperty"));
    }
}
