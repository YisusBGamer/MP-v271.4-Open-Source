package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.enums.MessageType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * The Daily Gift calendar (the v265 client's Daily Gift window): one gift a day, the next of 28 each month, once the
 * day's hunting goal is met (enemies within 10 levels). The window's data is sent at login; the client asks for the
 * gift with DAILY_GIFT_REQUEST (the item id). Ported from the reference server's DailyGift.
 * <p>
 * Progress is per character: the client's hunting record (quest 501385, date and count) and a server-only record
 * ({@link #STATE_QR}: month, gifts claimed this month, last day claimed).
 */
public final class DailyGiftModule {

    private static final Logger log = LogManager.getLogger(DailyGiftModule.class);

    public static final int MONSTER_COUNT_QR = 501385; // date, count (the client shows the hunting goal from it)
    public static final int STATE_QR = 89999993; // server-only record: month, day (gifts claimed), last
    private static final int NX_RECORD_KEY = 15;
    private static final int DEFAULT_REQ_MONSTER = 300;
    private static final int DEFAULT_REQ_LEVEL = 33;
    private static final int MOB_LEVEL_RANGE = 10;

    /**
     * Swordie Coin gifts pay once per account per month and day: a later character on the account (or any character
     * while coins are off) gets {@link #COIN_FALLBACK_ITEM} instead.
     */
    public static final int COIN_GIFT = net.swordie.ms.constants.ItemConstants.SWORDIE_COIN;
    private static final int COIN_FALLBACK_ITEM = 2450064; // 2x EXP Coupon (30 min)
    private static final int COIN_FALLBACK_QUANTITY = 2;

    /** The built-in 28 gifts (also what Server Control's reset puts back): {item, quantity, days it lasts (0 = no limit)} */
    private static final Supplier<int[][]> DEFAULT_GIFTS = DataTable.cached("daily-gifts", t -> t.rows().stream()
            .map(r -> new int[]{r.i("item"), r.i("quantity"), r.i("days")}).toArray(int[][]::new));

        /** The calendar in effect: the daily_gifts / daily_gift_settings tables, or the built-in gifts if they can't be read */
    private static final class Config {
        final int[][] gifts;
        final int monsters;
        final int level;

        Config(int[][] gifts, int monsters, int level) {
            this.gifts = gifts;
            this.monsters = monsters;
            this.level = level;
        }
    }

    private static volatile Config config;

    private static Config config() {
        Config c = config;
        if (c == null) {
            reload();
            c = config;
        }
        return c;
    }

    private static int[][] gifts() {
        return config().gifts;
    }

    private static int reqMonster() {
        return config().monsters;
    }

    private static int reqLevel() {
        return config().level;
    }

    /** Reads the tables again; the next login (or calendar send) uses the new gifts. Keeps what's in effect on failure. */
    public static boolean reload() {
        java.util.List<int[]> rows = new java.util.ArrayList<>();
        int monsters = DEFAULT_REQ_MONSTER;
        int level = DEFAULT_REQ_LEVEL;
        try (java.sql.Connection con = net.swordie.orm.connection.HikariCPDataSource.getConnection()) {
            try (java.sql.Statement st = con.createStatement();
                 java.sql.ResultSet rs = st.executeQuery(
                         "SELECT itemid, quantity, expire_days FROM daily_gifts ORDER BY day")) {
                while (rs.next()) {
                    rows.add(new int[]{rs.getInt(1), Math.max(1, rs.getInt(2)), Math.max(0, rs.getInt(3))});
                }
            }
            try (java.sql.Statement st = con.createStatement();
                 java.sql.ResultSet rs = st.executeQuery("SELECT name, value FROM daily_gift_settings")) {
                while (rs.next()) {
                    int v = Integer.parseInt(rs.getString(2).trim());
                    if (rs.getString(1).equals("monster_goal")) {
                        monsters = Math.max(1, v);
                    } else if (rs.getString(1).equals("min_level")) {
                        level = Math.max(1, v);
                    }
                }
            }
            con.rollback();
        } catch (Exception e) {
            log.warn("Could not read the Daily Gift tables: " + e);
            if (config == null) {
                config = new Config(DEFAULT_GIFTS.get(), DEFAULT_REQ_MONSTER, DEFAULT_REQ_LEVEL);
            }
            return false;
        }
        if (rows.isEmpty()) {
            rows.addAll(java.util.Arrays.asList(DEFAULT_GIFTS.get()));
        }
        config = new Config(rows.toArray(new int[0][]), monsters, level);
        log.info("Daily Gift calendar loaded: " + rows.size() + " gifts, goal " + monsters + ", level " + level);
        return true;
    }

    private DailyGiftModule() {
    }

    private static String today() {
        return net.swordie.ms.util.ResetClock.today().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    private static String month() {
        return today().substring(0, 6);
    }

    private static Quest state(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (qm.getHiddenRecord(STATE_QR) == null) {
            qm.setHiddenRecord(STATE_QR, "month=" + month() + ";day=0;last=0");
        }
        Quest q = qm.getQuestById(STATE_QR);
        if (!month().equals(q.getProperty("month"))) { // a new month starts the calendar over
            q.setProperty("month", month());
            q.setProperty("day", 0);
        }
        return q;
    }

    private static int claimedDays(Char chr) {
        Object day = state(chr).getProperty("day");
        try {
            return day == null ? 0 : Integer.parseInt(day.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean claimedToday(Char chr) {
        return today().equals(String.valueOf(state(chr).getProperty("last")));
    }

    /** @return the item of the gift the character would get today (0 once all 28 of the month are claimed) */
    public static int todaysItem(Char chr) {
        int day = claimedDays(chr);
        return day < gifts().length ? gifts()[day][0] : 0;
    }

    private static Quest monsterCount(Char chr) {
        QuestManager qm = chr.getQuestManager();
        Quest q = qm.getQuestById(MONSTER_COUNT_QR);
        if (q == null || q.getQRValue() == null || !q.getQRValue().contains("date=" + today())) {
            qm.addQuestWithQr(chr, MONSTER_COUNT_QR, "date=" + today() + ";count=0;");
            q = qm.getQuestById(MONSTER_COUNT_QR);
        }
        q.convertQRValueToProperties(); // setProperty rebuilds the record from these; without them the date is lost
        return q;
    }

    public static void onLogin(Char chr) {
        try {
            if (chr.getLevel() < reqLevel()) {
                return;
            }
            monsterCount(chr);
            sendRecord(chr);
            chr.write(init(0, 0));
        } catch (Exception e) {
            log.error("Could not set up the Daily Gift of " + chr.getName(), e);
        }
    }

    /** Kills of enemies within 10 levels count toward the day's hunting goal (the top damage dealer's) */
    public static void onMobKilled(Mob mob) {
        Char chr = mob.getMostDamageChar();
        if (chr == null || chr.getLevel() < reqLevel() || Math.abs(mob.getLevel() - chr.getLevel()) > MOB_LEVEL_RANGE) {
            return;
        }
        Quest q = monsterCount(chr);
        int count = q.getIntProperty("count");
        if (count >= reqMonster()) {
            return;
        }
        q.setProperty("count", ++count);
        // Every kill, as v265: the client only re-reads the goal count from this record (its Reward button stays
        // disabled until the count it holds reaches the goal)
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** DAILY_GIFT_REQUEST: gives today's gift; the packet's int is ignored (the server picks the gift, as in v265) */
    public static void claim(Char chr) {
        int[][] gifts = gifts();
        int day = claimedDays(chr);
        if (chr.getLevel() < reqLevel() || day >= gifts.length) {
            chr.chatMessage("There's no Daily Gift for you right now.");
            chr.dispose();
            return;
        }
        if (claimedToday(chr)) {
            chr.chatMessage("You have already received today's gift. The next one comes after midnight.");
            chr.dispose();
            return;
        }
        // roll the hunting record to today first: a character that stayed logged in past midnight still holds
        // yesterday's count, which would otherwise let it claim the new day's gift without hunting
        int killed = monsterCount(chr).getIntProperty("count");
        if (killed < reqMonster()) {
            chr.chatMessage(String.format("Defeat %d enemies around your level today to receive the gift.", reqMonster()));
            chr.dispose();
            return;
        }
        int[] gift = gifts[day];
        int giveId = gift[0];
        int giveQuantity = gift[1];
        boolean coins = gift[0] == COIN_GIFT;
        if (coins && !SwordieCoinRewards.giftCoinsAvailable(chr, day + 1, month())) {
            coins = false;
            giveId = COIN_FALLBACK_ITEM;
            giveQuantity = COIN_FALLBACK_QUANTITY;
        }
        Item item = ItemData.getItemDeepCopy(giveId);
        if (item == null) {
            chr.chatMessage("Today's gift isn't available. Please report this.");
            chr.dispose();
            return;
        }
        if (!chr.canHold(giveId, giveQuantity)) {
            chr.chatMessage("You don't have enough inventory space to receive the gift.");
            chr.dispose();
            return;
        }
        if (giveQuantity > 1) {
            item.setQuantity(giveQuantity);
        }
        if (gift[2] > 0 && giveId == gift[0]) {
            item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusDays(gift[2])));
        }
        if (coins) {
            SwordieCoinRewards.markGiftCoins(chr, day + 1, month(), giveQuantity);
        } else if (gift[0] == COIN_GIFT) {
            chr.chatMessage(SwordieCoinRewards.on()
                    ? "Another character on your account already got this day's Swordie Coins this month, so here is a 2x EXP Coupon pack instead."
                    : "Swordie Coins are off right now, so here is a 2x EXP Coupon pack instead.");
        }
        chr.addItemToInventory(item);
        net.swordie.ms.client.character.achievement.MapleAchievements.onDailyGift(chr);
        Quest state = state(chr);
        state.setProperty("day", day + 1);
        state.setProperty("last", today());
        sendRecord(chr);
        chr.write(init(2, gift[0]));
        chr.write(init(0, 0));
        chr.dispose();
    }

    /** Lets the probe claim a given gift of the month next (0 = the month's first) */
    public static void setDayForProbe(Char chr, int claimed) {
        resetForProbe(chr);
        state(chr).setProperty("day", claimed);
    }

    public static String monthForProbe() {
        return month();
    }

    /** Lets the probe claim a gift: today unclaimed and the hunting goal met */
    public static void resetForProbe(Char chr) {
        Quest state = state(chr);
        state.setProperty("last", "0");
        if (claimedDays(chr) >= gifts().length) {
            state.setProperty("day", 0);
        }
        monsterCount(chr).setProperty("count", reqMonster());
    }

    /** The client's NX record 15: date, gifts claimed this month, whether today's is claimed */
    private static void sendRecord(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.MESSAGE);
        outPacket.encodeByte(MessageType.NX_RECORD_MESSAGE.getVal());
        outPacket.encodeInt(NX_RECORD_KEY);
        outPacket.encodeString(String.format("date=%s;day=%d;count=%d;", today(), claimedDays(chr),
                claimedToday(chr) ? 1 : 0));
        chr.write(outPacket);
    }

    /** DAILY_GIFT_INIT: 0 = the calendar, 2 = a gift was given (item) */
    private static OutPacket init(int type, int itemId) {
        OutPacket outPacket = new OutPacket(OutHeader.DAILY_GIFT_INIT);
        outPacket.encodeByte(type);
        if (type == 1) {
            outPacket.encodeInt(itemId);
        } else if (type == 2) {
            outPacket.encodeInt(type);
            outPacket.encodeInt(itemId);
        } else if (type == 0) {
            outPacket.encodeByte(true);
            encodeCalendar(outPacket);
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    private static void encodeCalendar(OutPacket outPacket) {
        int[][] gifts = gifts();
        outPacket.encodeFT(FileTime.currentTime());
        outPacket.encodeFT(FileTime.MAX_TIME());
        outPacket.encodeInt(gifts.length);
        outPacket.encodeInt(2);
        outPacket.encodeInt(MONSTER_COUNT_QR);
        outPacket.encodeInt(reqMonster());
        outPacket.encodeString("mvpTooltip_default");
        outPacket.encodeInt(gifts.length);
        for (int i = 0; i < gifts.length; i++) {
            outPacket.encodeInt(i + 1);
            outPacket.encodeInt(gifts[i][0]);
            outPacket.encodeInt(gifts[i][1]);
            outPacket.encodeByte(1);
            outPacket.encodeInt(10080);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(false);
        }
        outPacket.encodeInt(reqLevel());
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0); // v271: one more (int, int) list
    }
}
