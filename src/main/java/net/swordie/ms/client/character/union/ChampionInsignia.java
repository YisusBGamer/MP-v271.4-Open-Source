package net.swordie.ms.client.character.union;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;

/**
 * Champion Insignia (Etc/unionChampion.img stat: 5 types, Skill.wz 800038 80003819-80003823, passive, max level 6).
 * Each rank a champion reaches earns one insignia, and the insignia add up over the champions: the champion
 * characters get insignia k (0 All Stats/HP/MP, 1 ATT/M.ATT, 2 Boss Damage, 3 Critical Damage, 4 Ignore Defense) at
 * the number of champions of rank k + 1 (B for the first ... SSS for the last) or above, at most 6 (= 6 slots,
 * 30 levels in all as info/stat/maxLevel says). Non-champions have none.
 */
public class ChampionInsignia {

    public static final int FIRST_SKILL = 80003819;
    public static final int TYPES = 5;
    public static final int MAX_LEVEL = 6;

    public static int getLevel(Account acc, int type) {
        return Math.min(MAX_LEVEL, ChampionModule.countAtRank(acc, type + 1));
    }

    /** Sets this character's insignia skills from the account's champions (none when it isn't a champion). */
    public static void refresh(Char chr) {
        if (chr == null || chr.getAccount() == null) {
            return;
        }
        boolean champion = ChampionModule.getSlotOf(chr) >= 0;
        boolean changed = false;
        for (int t = 0; t < TYPES; t++) {
            int id = FIRST_SKILL + t;
            int want = champion ? getLevel(chr.getAccount(), t) : 0;
            Skill have = chr.getSkill(id);
            int cur = have == null ? 0 : have.getCurrentLevel();
            if (cur == want) {
                continue;
            }
            changed = true;
            if (want <= 0) {
                chr.removeSkillAndSendPacket(id);
            } else if (have != null) {
                have.setCurrentLevel(want);
                have.setMasterLevel(MAX_LEVEL);
                chr.write(net.swordie.ms.connection.packet.WvsContext.changeSkillRecordResult(have));
            } else {
                chr.addSkill(id, want, MAX_LEVEL);
            }
        }
        if (changed) {
            chr.initBaseStats();
        }
    }

    /** After a change to the account's champions: every online character of the account. */
    public static void refreshAccount(Account acc) {
        if (acc == null) {
            return;
        }
        for (Char c : acc.getCharacters()) {
            if (c.getClient() != null && c.getField() != null) {
                refresh(c);
            }
        }
    }
}
