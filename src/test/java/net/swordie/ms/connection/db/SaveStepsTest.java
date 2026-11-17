package net.swordie.ms.connection.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A save writes every part even when one fails, then reports the parts that weren't written.
 */
public class SaveStepsTest {

    @Test
    public void allPartsRunAndTheFailureIsReported() {
        List<String> ran = new ArrayList<>();
        var steps = new SaveSteps("character 7");
        steps.run("skills", () -> ran.add("skills"));
        steps.run("quests", () -> {
            throw new DatabaseException("batch failed");
        });
        steps.run("emoticons", () -> ran.add("emoticons"));
        Assert.assertEquals(List.of("skills", "emoticons"), ran);
        try {
            steps.finish();
            Assert.fail("a failed part must be reported");
        } catch (DatabaseException e) {
            Assert.assertTrue(e.getMessage().contains("character 7"));
            Assert.assertTrue(e.getMessage().contains("quests (batch failed)"));
            Assert.assertFalse(e.getMessage().contains("skills"));
        }
    }

    @Test
    public void nothingFailedNothingThrown() {
        var steps = new SaveSteps("login 1");
        steps.run("users row", () -> {
        });
        steps.finish();
    }

    @Test
    public void requiredStopsTheSave() {
        var steps = new SaveSteps("character 0");
        try {
            steps.required("characters row", () -> {
                throw new DatabaseException("insert failed");
            });
            Assert.fail("a failed required part must stop the save");
        } catch (DatabaseException e) {
            Assert.assertTrue(e.getMessage().contains("characters row"));
        }
    }
}
