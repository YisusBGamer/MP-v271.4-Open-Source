package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.hyperstats.HyperStatsModule;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.FuncKeyMappedMan;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.FirstEnterRewardType;
import net.swordie.ms.enums.InventoryOperation;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.StringData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Rebirth: a character at {@link GameConstants#REBIRTH_MIN_LEVEL} or higher goes back to level
 * {@link GameConstants#JOB_PATH_MIN_LEVEL} as a Beginner (job 0, whatever its class), in Henesys. Its skills keep their
 * levels. Right away it chooses a job path again with @job (its previous class is offered first) and then advances automatically, see
 * {@link JobPathModule}.
 * <p>
 * State is kept in a server-only quest record ({@link GameConstants#REBIRTH_QR}): "count=N;path=100,110,111,112", the
 * path being the one of the previous life, and "hs=N": the Hyper Stat points earned in earlier lives.
 */
public class RebirthModule {

    private static final Logger log = LogManager.getLogger(RebirthModule.class);
    private static final int HENESYS = 100000000;

    // ------------------------------------------------------------------------------------------------- record

    /**
     * Re-sends what would otherwise need a relog after a rebirth. The blessings follow the new Beginner job's skill ids,
     * the Legion member bonus follows the job, and the key map, pet potion slots and HP/MP are re-sent so the client and
     * server agree. Each step is on its own, so one that fails can't stop the rest.
     */
    private static void refreshAfterRebirth(Char chr) {
        try {
            HyperStatsModule.reapplyCurrentPreset(chr);
            HyperStatsModule.refreshPointDisplay(chr);
        } catch (Exception e) {
            log.error("Rebirth: Hyper Stats of " + chr.getName(), e);
        }
        try {
            chr.initBlessingSkills();
        } catch (Exception e) {
            log.error("Rebirth: blessings of " + chr.getName(), e);
        }
        try {
            if (chr.getUnion() != null && chr.getUnion().isActiveMember(chr.getId())) {
                chr.getUnion().updateUnionBonuses(chr);
            }
        } catch (Exception e) {
            log.error("Rebirth: Legion bonuses of " + chr.getName(), e);
        }
        try {
            chr.write(FuncKeyMappedMan.funcKeyMappedManInit(chr.getFuncKeyMap(), chr.getQuickslotKeys()));
            chr.initPetPotions();
        } catch (Exception e) {
            log.error("Rebirth: key map of " + chr.getName(), e);
        }
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.job, (short) chr.getJob());
        stats.put(Stat.hp, chr.getHP());
        stats.put(Stat.mp, chr.getMP());
        chr.write(WvsContext.statChanged(stats, 0)); // job with sub job 0
    }

    private static Map<String, String> readRecord(Char chr) {
        Map<String, String> res = new HashMap<>();
        String value = chr.getQuestManager().getHiddenRecord(GameConstants.REBIRTH_QR);
        if (value != null) {
            for (String part : value.split(";")) {
                int eq = part.indexOf('=');
                if (eq > 0) {
                    res.put(part.substring(0, eq), part.substring(eq + 1));
                }
            }
        }
        return res;
    }

    private static void writeRecord(Char chr, int count, List<Integer> path) {
        Map<String, String> rec = readRecord(chr); // keeps the other keys (hs)
        rec.put("count", String.valueOf(count));
        rec.put("path", JobPathModule.pathToString(path));
        saveRecord(chr, rec);
    }

    private static void saveRecord(Char chr, Map<String, String> rec) {
        StringBuilder sb = new StringBuilder();
        // count and path first, as before
        for (String key : new String[]{"count", "path"}) {
            if (rec.containsKey(key)) {
                sb.append(sb.length() == 0 ? "" : ";").append(key).append('=').append(rec.get(key));
            }
        }
        for (var e : rec.entrySet()) {
            if (!e.getKey().equals("count") && !e.getKey().equals("path")) {
                sb.append(sb.length() == 0 ? "" : ";").append(e.getKey()).append('=').append(e.getValue());
            }
        }
        chr.getQuestManager().setHiddenRecord(GameConstants.REBIRTH_QR, sb.toString());
    }

    // ---------------------------------------------------------------------------------- Hyper Stats

    /**
     * Hyper Stat points a reborn character brought from its earlier lives. A rebirth keeps the Hyper Stats that were
     * set, so without these the new life's points would all be "already spent" from Lv. 140 until the character passed
     * its old level. These come on top of the new life's points (given with Lv. 140's, see HyperStatsModule).
     * <p>
     * Characters without a saved value infer only the part of their current allocations that this life's earned points
     * cannot fund. That inference is stored once; a saved carryover must never be recalculated from later spending, or
     * spending points would manufacture more points.
     */
    public static int getCarriedHyperStatSp(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return 0;
        }
        Map<String, String> rec = readRecord(chr);
        String hs = rec.get("hs");
        if (hs != null) {
            try {
                return Math.max(0, Integer.parseInt(hs));
            } catch (NumberFormatException ignored) {
                // noted again below
            }
        }
        if (!isReborn(chr)) {
            return 0;
        }
        int spent = getMostSpentHyperStatSp(chr);
        int earnedThisLife = net.swordie.ms.constants.SkillConstants.getTotalHyperStatSpByLevel(chr.getLevel());
        int carried = Math.max(0, spent - earnedThisLife);
        rec.put("hs", String.valueOf(carried));
        saveRecord(chr, rec);
        return carried;
    }

    /** @return the Hyper Stat points the most expensive preset uses */
    public static int getMostSpentHyperStatSp(Char chr) {
        int most = chr.getSpentHyperSp(); // the active preset, as the skills have it
        var hsm = chr.getHyperStatsManager();
        if (hsm != null) {
            for (int i = 0; i < GameConstants.HYPER_STATS_MAX_PRESETS; i++) {
                int sp = 0;
                for (var info : hsm.getHyperStatSkillsByPreset(i)) {
                    sp += net.swordie.ms.constants.SkillConstants.getTotalNeededSpForHyperStatSkill(info.getLevel());
                }
                most = Math.max(most, sp);
            }
        }
        return most;
    }

    public static int getRebirthCount(Char chr) {
        try {
            return Integer.parseInt(readRecord(chr).getOrDefault("count", "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isReborn(Char chr) {
        return getRebirthCount(chr) > 0;
    }

    /**
     * Matrix slot enhancements are paid for with level-based points. A reborn character below level 200 cannot keep
     * enhancements earned in the previous life without showing a negative Matrix Point balance.
     */
    public static void resetMatrixSlotEnhancementsForRebornCharacter(Char chr) {
        if (chr == null || !isReborn(chr) || chr.getLevel() >= 200) {
            return;
        }
        int enhancements = chr.getTotalMatrixSlotsEnhancements();
        if (enhancements <= 0) {
            return;
        }

        chr.resetMatrixSlotEnhancements();
        chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
        log.info("Rebirth: reset {} Matrix slot enhancements for {} at level {}.", enhancements, chr.getName(), chr.getLevel());
    }

    /**
     * @return the job path of the Char's life before its last rebirth (empty if it was never reborn)
     */
    public static List<Integer> getPreviousPath(Char chr) {
        return JobPathModule.parsePath(readRecord(chr).get("path"));
    }

    // ------------------------------------------------------------------------------------------------- rebirth

    /**
     * @return why this Char can't be reborn right now, or null if it can
     */
    public static String getRebirthBlocker(Char chr) {
        String blocker = getRebirthBlockerIgnoringEquips(chr);
        if (blocker != null) {
            return blocker;
        }
        List<Equip> tooHigh = getEquipsBlockingRebirth(chr);
        if (!tooHigh.isEmpty()) {
            List<String> names = new ArrayList<>();
            for (Equip equip : tooHigh.subList(0, Math.min(5, tooHigh.size()))) {
                String name = StringData.getItemStringById(equip.getItemId());
                names.add(name == null ? String.valueOf(equip.getItemId()) : name);
            }
            return "Unequip your equipment and symbols first, they can't be worn at level "
                    + GameConstants.JOB_PATH_MIN_LEVEL + ": "
                    + String.join(", ", names)
                    + (tooHigh.size() > 5 ? " and " + (tooHigh.size() - 5) + " more." : ".");
        }
        return null;
    }

    /**
     * @return the equipped items (not cash) that can't be worn at the level a rebirth starts at, which must come off
     */
    public static List<Equip> getEquipsBlockingRebirth(Char chr) {
        List<Equip> res = new ArrayList<>();
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip && !item.isCash() && ((Equip) item).getRequiredLevel() > GameConstants.JOB_PATH_MIN_LEVEL) {
                res.add((Equip) item);
            }
        }
        return res;
    }

    /**
     * Unequips everything that blocks a rebirth ({@link #getEquipsBlockingRebirth}) into the Equip inventory, but only
     * if all of it fits.
     *
     * @return null if done, or why it wasn't done
     */
    public static synchronized String unequipForRebirth(Char chr) {
        List<Equip> equips = getEquipsBlockingRebirth(chr);
        if (equips.isEmpty()) {
            return null;
        }
        var equipInv = chr.getEquipInventory();
        if (equipInv.getEmptySlots() < equips.size()) {
            return String.format("You need %d free slots in your Equip inventory for your equipment (you have %d).",
                    equips.size(), equipInv.getEmptySlots());
        }
        for (Equip equip : equips) {
            int bodyPart = Math.abs(equip.getBagIndex());
            int slot = equipInv.getFirstOpenSlot();
            chr.unequip(equip);
            equip.setBagIndex(slot);
            chr.write(WvsContext.inventoryOperation(true, false, InventoryOperation.Move, (short) -bodyPart,
                    (short) slot, 0, equip));
        }
        chr.dispose();
        return null;
    }

    /**
     * @return why this Char can't be reborn right now apart from its equipment, or null if nothing else is in the way
     */
    public static String getRebirthBlockerIgnoringEquips(Char chr) {
        int job = chr.getJob();
        if (chr.getLevel() < GameConstants.REBIRTH_MIN_LEVEL) {
            return String.format("You must be at least level %d to be reborn.", GameConstants.REBIRTH_MIN_LEVEL);
        }
        int maxRebirths = net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.REBIRTH_MAX);
        if (maxRebirths > 0 && getRebirthCount(chr) >= maxRebirths) {
            return String.format("You have already been reborn the maximum of %d times.", maxRebirths);
        }
        if (JobConstants.isZero(job) || JobConstants.isPinkBean(job) || JobConstants.isYeti(job)
                || JobConstants.isGmJob(job) || JobConstants.isBeginnerJob(job)) {
            return "Your class can't be reborn.";
        }
        if (chr.getInstance() != null || chr.getHP() <= 0 || chr.getMiniRoom() != null || chr.getShop() != null
                || chr.isInTrunk()) {
            return "You can't be reborn right now. Leave any instance, trade or shop first.";
        }
        return null;
    }

    /**
     * Rebirths the Char. Check {@link #getRebirthBlocker(Char)} first.
     *
     * @return whether the rebirth happened
     */
    public static synchronized boolean rebirth(Char chr) {
        String blocker = getRebirthBlocker(chr);
        if (blocker != null) {
            chr.chatMessage(blocker);
            return false;
        }
        int oldJob = chr.getJob();
        var jobEnum = JobConstants.JobEnum.getJobById(oldJob);
        if (jobEnum == null) {
            chr.chatMessage("Your class can't be reborn.");
            return false;
        }
        // every class is reborn as the Explorer Beginner (job 0), not its own class's beginner (Demon 3001, Noblesse 1000,
        // ...); @job then offers any class's path, the previous one first
        int beginner = JobConstants.JobEnum.BEGINNER.getJobId();
        List<Integer> path = JobPathModule.getJobPath(oldJob);
        int count = getRebirthCount(chr) + 1;

        // 1. skills: all kept with their levels (only the passives of the current class count, see Char)

        // 2. V Matrix: unequip all cores (they're kept, and can be equipped again at level 200)
        boolean matrixChanged = false;
        for (MatrixRecord mr : chr.getMatrixRecords()) {
            if (mr.isActive()) {
                mr.setPosition(-1);
                matrixChanged = true;
            }
        }
        if (matrixChanged) {
            chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
        }

        // this life's Hyper Stat points go with the character (the Hyper Stats it set are kept)
        long completedLivesHyperStat = (long) getCarriedHyperStatSp(chr)
                + net.swordie.ms.constants.SkillConstants.getTotalHyperStatSpByLevel(chr.getLevel());
        int carriedHyperStat = (int) Math.min(Integer.MAX_VALUE, completedLivesHyperStat);

        // 3. job, level, EXP
        // no kill credit carried into the new life: a monster hit before the rebirth that dies after it would pay
        // its (Arcane River) EXP to the level 10 character, 100+ levels at once
        if (chr.getField() != null) {
            for (var mob : chr.getField().getMobs()) {
                mob.getDamageDone().remove(chr);
            }
        }
        chr.setJob(beginner);
        chr.getAvatarData().getCharacterStat().setSubJob(0); // no Dual Blade / Cannoneer / Pathfinder sub job
        // straight to the level a job is chosen at: levels 1-9 again only cost time
        int newLevel = GameConstants.JOB_PATH_MIN_LEVEL;
        chr.setStatAndSendPacket(Stat.level, newLevel);
        chr.setStatAndSendPacket(Stat.exp, 0);
        ProgressionLog.onRebirth(chr);
        BurningModule.onRebirth(chr); // burning is for the first life only

        // 4. stats: kept as they are (unspent AP too) plus the rebirth bonus AP, unless REBIRTH_RESET_STATS is on,
        // in which case they go back to a new character's (STR 12, DEX 5, INT 4, LUK 4, HP/MP 50)
        // (plus the AP of the levels skipped, 5 a level, as if they'd been played)
        int bonusAp = net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.REBIRTH_BONUS_AP) * count + (newLevel - 1) * 5;
        if (net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.REBIRTH_RESET_STATS)) {
            chr.setStatAndSendPacket(Stat.str, 12);
            chr.setStatAndSendPacket(Stat.dex, 5);
            chr.setStatAndSendPacket(Stat.inte, 4);
            chr.setStatAndSendPacket(Stat.luk, 4);
            chr.setStatAndSendPacket(Stat.ap, bonusAp);
            chr.setStatAndSendPacket(Stat.mhp, 50);
            chr.setStatAndSendPacket(Stat.mmp, 50);
            chr.setStatAndSendPacket(Stat.hp, 50);
            chr.setStatAndSendPacket(Stat.mp, 50);
        } else if (bonusAp > 0) {
            chr.addStatAndSendPacket(Stat.ap, bonusAp);
        }

        // 5. all unspent SP (skill levels are kept)
        var cs = chr.getAvatarData().getCharacterStat();
        if (cs.getExtendSP() != null) {
            for (var spSet : cs.getExtendSP().getSpSet()) {
                spSet.setSp(0);
            }
        }
        cs.setSp(0);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.sp, JobConstants.isExtendSpJob(chr.getJob()) ? cs.getExtendSP() : (short) 0);
        chr.write(WvsContext.statChanged(stats));

        // 6. remember the rebirth and the previous path; a new path is chosen with @job from level 10
        writeRecord(chr, count, path);
        Map<String, String> rec = readRecord(chr);
        rec.put("hs", String.valueOf(carriedHyperStat));
        saveRecord(chr, rec);
        resetMatrixSlotEnhancementsForRebornCharacter(chr);

        // 7. first rebirth: a gift of level 1 equipment any class can wear (claimed from the gift box)
        if (count == 1) {
            for (int itemId : GameConstants.FIRST_REBIRTH_GIFT) {
                chr.addFirstEnterReward(new FirstEnterReward(chr.getId(), itemId, 1, FirstEnterRewardType.GameItem,
                        "Gift for your first rebirth."));
            }
        }
        JobPathModule.setActivePath(chr, new ArrayList<>());

        chr.initBaseStats();
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.REBIRTH_RESET_STATS)) {
            chr.heal(chr.getMaxHP());
            chr.healMP(chr.getMaxMP());
        }
        refreshAfterRebirth(chr);
        if (chr.getFieldID() != HENESYS && chr.getInstance() == null) {
            chr.warp(HENESYS); // a new life starts in Henesys, among the maps for its level
        }
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.levelUpEffect()));
        chr.chatMessage(ChatType.Notice2, String.format("You have been reborn (rebirth #%d) at level %d in Henesys! " +
                "Your skills keep their levels. Type @job to choose your job path again.", count, newLevel));
        log.info(String.format("Char %d (%s) was reborn (#%d) from job %d.", chr.getId(), chr.getName(), count, oldJob));
        SwordieCoinRewards.onRebirth(chr); // 1 Fame (and Swordie Coins while they're on)
        return true;
    }
}
