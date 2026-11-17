package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Cooking with Tangyoon (Nautilus party quest), as on live servers (maplestorywiki.net/w/Cooking_with_Tangyoon):
 * <ul>
 *     <li>Level 60+, a party of 1-3, 5 entries a day, 20 minutes.</li>
 *     <li>Tangyoon picks a dish. For each of 5 ingredient stages the party leader picks one of 5 monsters (only one is
 *     right for the dish); it keeps coming for 90 seconds, then the wave must be cleared.</li>
 *     <li>Stage 4 also brings 5 Tasteburners; if any is left when the stage ends, the dish is ruined.</li>
 *     <li>A good dish summons Mr. Chomps, a ruined one the Trash Can. Defeating it ends the quest: 2 Chef Certificates
 *     for a good dish, 1 for a ruined one (2 make the Chef Outfit, 3 the Chef Hat).</li>
 * </ul>
 * Completion EXP: 21,726 * 1.06^(level - 30) for levels 60-69, 220,455 * 1.037^(level - 70) for 70-90 (times the
 * server's EXP rate), split over the stages.
 */
public class TangyoonInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "tangyoon";
    public static final int MIN_LEVEL = 60;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 3;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int KITCHEN = 912080000;
    public static final int CAULDRON = 912080100;
    public static final int KITCHEN_EXIT = 912080110;
    public static final int CHEF_CERTIFICATE = 4033668;
    /** "Tangyoon approved cook." (medal): 3 good dishes, counted on record 30102. */
    private static final int APPROVED_COOK = 30101;
    private static final int APPROVED_COOK_RECORD = 30102;
    private static final int APPROVED_COOK_DISHES = 3;

    public static final String[] DISHES = {"Stir-fried Pork", "Escargot", "Cold Jellyfish Salad", "Mushroom Chow Fun",
            "Slime Pudding"};
    private static final int[][] INGREDIENTS = DataTable.read("tangyoon-ingredients").rows().stream()
            .map(r -> r.ints("ingredients")).toArray(int[][]::new);
    private static final int TASTEBURNER = 9300679;
    private static final int MR_CHOMPS = 9300680;
    private static final int TRASH_CAN = 9300681;
    private static final int STAGES = 5;
    private static final int WAVE_SECONDS = 90;
    private static final int WAVE_SIZE = 8;
    private static final int STAGE_PERCENT = 12; // 5 stages = 60%, the dish (boss) = 40%

    private int dish;
    private int stage; // 0-4 ingredient stages, 5 = boss
    private int[] choices;
    private boolean waveRunning;
    private int waveMob;
    private boolean ruined;
    private boolean bossSpawned;
    private boolean finished;
    private ScheduledFuture waveTimer;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        dish = Util.getRandom(DISHES.length - 1);
        makeChoices();
        instance.setForcedReturn(KITCHEN);
        instance.setTimeout(TIME_LIMIT_SECONDS);
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Make a party first (you can come alone in a party of 1).";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Just have your Party Leader enter for you.";
        }
        var members = party.getPartyMembersInField(leader);
        if (members.size() < MIN_PARTY || members.size() > MAX_PARTY) {
            return "You know what they say about too many cooks. You need a party of 3 or less here.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + " to cook.";
            }
            if (PartyQuestRecords.getTodayEntries(c, RECORD_KEY) >= DAILY_ENTRIES) {
                return c.getName() + " has already cooked " + DAILY_ENTRIES + " times today. Come back tomorrow!";
            }
        }
        return null;
    }

    public static void recordEntries(Char leader) {
        for (Char c : leader.getParty().getPartyMembersInField(leader)) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    public static int getRemainingEntries(Char chr) {
        return Math.max(0, DAILY_ENTRIES - PartyQuestRecords.getTodayEntries(chr, RECORD_KEY));
    }

    // ---------------------------------------------------------------------------------------------------------- stages

    private void makeChoices() {
        if (stage >= STAGES) {
            choices = new int[0];
            return;
        }
        List<Integer> pool = new ArrayList<>();
        for (int d = 0; d < INGREDIENTS.length; d++) {
            if (d != dish) {
                for (int m : INGREDIENTS[d]) {
                    pool.add(m);
                }
            }
        }
        Collections.shuffle(pool);
        List<Integer> picks = new ArrayList<>(pool.subList(0, 4));
        picks.add(INGREDIENTS[dish][stage]);
        Collections.shuffle(picks);
        choices = picks.stream().mapToInt(Integer::intValue).toArray();
    }

    public String getDishName() {
        return DISHES[dish];
    }

    public int getStage() {
        return stage;
    }

    public boolean isWaveRunning() {
        return waveRunning;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isBossStage() {
        return stage >= STAGES;
    }

    /** @return the 5 monsters the leader can pick from this stage */
    public int[] getChoices() {
        return choices;
    }

    /**
     * The leader picked an ingredient (index into getChoices()); its monster keeps coming for 90 seconds.
     */
    public synchronized String pickIngredient(Char chr, int choice) {
        if (waveRunning || stage >= STAGES || choice < 0 || choice >= choices.length) {
            return "Let's finish this ingredient first!";
        }
        Field field = getInstance().getField(CAULDRON);
        if (!field.getMobs().isEmpty()) {
            return "Clear out the cauldron first!";
        }
        waveMob = choices[choice];
        if (waveMob != INGREDIENTS[dish][stage]) {
            ruined = true;
        }
        waveRunning = true;
        if (stage == 3) {
            for (int i = 0; i < 5; i++) {
                spawnAt(field, TASTEBURNER, true);
            }
            notice(field, "Tasteburners! Someone has to put them out, or the dish is ruined!");
        }
        for (int i = 0; i < WAVE_SIZE; i++) {
            spawnAt(field, waveMob, false);
        }
        notice(field, "Ingredient " + (stage + 1) + " of " + STAGES + ": hunt for " + WAVE_SECONDS + " seconds, "
                + "then clear the cauldron!");
        waveTimer = addTimer(EventManager.addEvent(this::endWave, WAVE_SECONDS * 1000L));
        return null;
    }

    private void spawnAt(Field field, int template, boolean bottom) {
        Position pos = field.getRandomPosOnWalkableFoothold(50, false);
        if (pos != null) {
            field.spawnMob(template, pos.getX(), bottom ? pos.getY() : pos.getY(), false, -1);
        }
    }

    private synchronized void endWave() {
        waveRunning = false;
        Field field = getInstance().getField(CAULDRON);
        notice(field, "That's enough of that ingredient! Clear the cauldron.");
        checkWaveCleared(field);
    }

    private void checkWaveCleared(Field field) {
        if (waveRunning || stage >= STAGES || !field.getMobs().isEmpty() || waveMob == 0) {
            return;
        }
        waveMob = 0;
        clearStage(stage + 1, field, STAGE_PERCENT);
        stage++;
        if (stage >= STAGES) {
            notice(field, ruined ? "Hmm... something went wrong with this dish." : "It smells delicious!");
            if (!bossSpawned) {
                bossSpawned = true;
                spawnAt(field, ruined ? TRASH_CAN : MR_CHOMPS, false);
            }
        } else {
            makeChoices();
            notice(field, "Talk to Tangyoon to pick the next ingredient.");
        }
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        if (field.getId() == CAULDRON) {
            int t = mob.getTemplateId();
            if (waveRunning && t == waveMob) {
                spawnAt(field, waveMob, false); // the ingredient keeps coming for 90 seconds
            } else if ((t == MR_CHOMPS || t == TRASH_CAN) && !finished) {
                finished = true;
                clearStage(STAGES + 1, field, 100 - STAGES * STAGE_PERCENT);
                int certs = ruined ? 1 : 2;
                for (Char c : getInstance().getChars()) {
                    PartyQuestRecords.addClear(c, RECORD_KEY);
                    if (!ruined) {
                        countApprovedDish(c);
                    }
                    if (c.canHold(CHEF_CERTIFICATE, certs)) {
                        c.addItemToInventory(CHEF_CERTIFICATE, certs);
                    }
                }
                getInstance().chatMessage((ruined ? "The dish was a mess, but you tried. " : "A perfect dish! ")
                        + "You got " + certs + " Chef Certificate" + (certs > 1 ? "s" : "") + ".");
                getInstance().setForcedReturn(KITCHEN_EXIT);
                addTimer(EventManager.addEvent(() -> getInstance().clear(), 5000));
            } else {
                checkWaveCleared(field);
            }
        }
        super.onMobDeath(field, mob);
    }

    /** A good dish (Mr. Chomps) counts for "Tangyoon approved cook." while that quest is held. */
    private static void countApprovedDish(Char c) {
        var qm = c.getQuestManager();
        if (!qm.hasQuestInProgress(APPROVED_COOK)) {
            return;
        }
        var q = qm.getOrCreateQuestById(APPROVED_COOK_RECORD);
        if (q == null) {
            return;
        }
        String v = q.getQRValue();
        int dishes = Util.isNumber(v) ? Integer.parseInt(v) : 0;
        if (dishes >= APPROVED_COOK_DISHES) {
            return;
        }
        q.setQrValue(String.valueOf(dishes + 1));
        c.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
        c.chatMessage("Tangyoon approved dishes: " + (dishes + 1) + "/" + APPROVED_COOK_DISHES);
    }

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return false; // the cauldron only has what the cooks pick
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
    }

    @Override
    public long getCompletionExp(Char chr) {
        int lv = Math.max(60, Math.min(90, chr.getLevel()));
        double exp = lv < 70 ? 21_726 * Math.pow(1.06, lv - 30) : 220_455 * Math.pow(1.037, lv - 70);
        return (long) (exp * Math.max(1, ServerRates.getExpRate()));
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.emptyList();
    }
}
