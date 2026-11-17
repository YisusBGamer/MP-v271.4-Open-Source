package net.swordie.ms.client.character.union;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.mockutil.MockUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnionTest {

    private Account account;
    private Union union;

    @Before
    public void setup() {
        account = mock(Account.class);
        union = new Union();
        union.setPresets(3);
        union.setUnionBoards(new ArrayList<>());
        union.getUnionBoards().add(new UnionBoard(true));

        union.setAccount(account);
        when(account.getUnion()).thenReturn(union);
    }

    @Test
    public void getEligibleUnionChars_noChars_emptySet() {
        when(account.getCharacters()).thenReturn(new HashSet<>());

        var result = union.getEligibleUnionChars();
        Assert.assertEquals(new ArrayList<Char>(), result);
    }

    @Test
    public void getEligibleUnionChars_lowLevelChar_emptySet() {
        var set = new HashSet<Char>();
        set.add(createChar(1, 10, 112));

        when(account.getCharacters()).thenReturn(set);

        var result = union.getEligibleUnionChars();
        Assert.assertEquals(new ArrayList<Char>(), result);
    }

    // v271 rules (Union.isEligibleUnionChar): Lv. 60+ and the 2nd job; only the highest MAX_CHARS characters count
    @Test
    public void getEligibleUnionChars_not2ndjob_emptySet() {
        var set = new HashSet<Char>();
        set.add(createChar(1, 200, 100));

        when(account.getCharacters()).thenReturn(set);

        var result = union.getEligibleUnionChars();
        Assert.assertEquals(new ArrayList<>(), result);
    }

    @Test
    public void getEligibleUnionChars_validChar_returnsValidChar() {
        var set = new HashSet<Char>();
        set.add(createChar(1, 200, 112));

        when(account.getCharacters()).thenReturn(set);

        var result = union.getEligibleUnionChars();
        Assert.assertTrue(result.stream().anyMatch(c -> c.getId() == 1));
    }

    @Test
    public void getEligibleUnionChars_overMaxChars_listsAllButCountsHighest() {
        var set = new HashSet<Char>();
        var lowChar1 = createChar(150, 150, 112);
        var lowChar2 = createChar(200, 120, 3112);
        for (int i = 0; i < 50; i++) {
            set.add(createChar(i, 200, 112));
        }
        set.add(lowChar1);
        set.add(lowChar2);

        when(account.getCharacters()).thenReturn(set);

        var result = union.getEligibleUnionChars();

        // every eligible character is listed, highest level first; only MAX_CHARS of them count towards the level
        Assert.assertEquals(set.size(), result.size());
        Assert.assertTrue(result.subList(0, 50).stream().allMatch(c -> c.getLevel() == 200));
        Assert.assertEquals(Union.MAX_CHARS * 200, union.getUnionLevel());
    }

    @Test
    public void getEligibleUnionChars_placedChars_comeFirst() {
        var set = new HashSet<Char>();
        var placed = createChar(150, 150, 112);
        for (int i = 0; i < 5; i++) {
            set.add(createChar(i, 200, 112));
        }
        set.add(placed);

        when(account.getCharacters()).thenReturn(set);
        union.getUnionBoards().get(0).getActiveMembers().add(placed.createUnionMember());

        var result = union.getEligibleUnionChars();

        Assert.assertEquals(6, result.size());
        Assert.assertEquals(placed.getId(), result.get(0).getId());
    }

    private static Char createChar(int id, int level, int job) {
        var chr = MockUtil.createChar();
        chr.setId(id);
        chr.getAvatarData().getCharacterStat().setLevel(level);
        chr.getAvatarData().getCharacterStat().setJob(job);
        return chr;
    }

}
