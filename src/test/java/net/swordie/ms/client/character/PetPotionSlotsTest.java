package net.swordie.ms.client.character;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * A new character's pet auto-potion slots are kept (they were lost at the next Cash Shop trip or relog).
 * Contributed by Dapsy (from the Discord).
 */
public class PetPotionSlotsTest {

    @Test
    public void newCharacterKeepsItsPotions() {
        Char chr = mock(Char.class, CALLS_REAL_METHODS); // no saved slots yet, like a new character
        chr.setPetHpPotion(2000005);
        chr.setPetMpPotion(2000006);
        chr.setPetAcPotion(2050004);
        Assert.assertEquals(java.util.List.of(2000005, 2000006, 2050004), chr.getPetFuncKeyMap());
    }
}
