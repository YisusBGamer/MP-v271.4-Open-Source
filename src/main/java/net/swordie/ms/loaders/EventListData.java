package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.connection.OutPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 25/12/2025.
 *
 * @author Someone who played SwordieMS
 */
public final class EventListData {
    private static final Logger log = LogManager.getRootLogger();
    private static final Path CONFIG_PATH = Path.of(ServerConstants.LOADINS_DIR + "/EventLists.json");
    private static final List<EventListDataRecord> EVENTS = new ArrayList<>();

    public record EventListDataRecord(
            int id,
            String title,
            String description,
            int startTime,
            int endTime,
            int startDate,
            int endDate,
            List<Integer> rewardItems,
            String requirements,
            String tag,
            int tab
    ) {
        private static String formatPeriod(int startDate, int startTime, int endDate, int endTime) {
            return formatDateTime(startDate, startTime) + " ~ " + formatDateTime(endDate, endTime);
        }

        private static String formatDateTime(int date, int time) {
            int hourMinute = time / 100;
            return String.format("%04d.%02d.%02d %02d:%02d",
                    date / 10000, date / 100 % 100, date % 100,
                    hourMinute / 100, hourMinute % 100);
        }

        public void encode(OutPacket outPacket) {
            // v271 sub_1459ECA60 event entry (property keys in comments)
            outPacket.encodeInt(id); // key of the entry
            outPacket.encodeString(title); // "name"
            outPacket.encodeString(description); // "collectTip"
            outPacket.encodeInt(startTime); // "timeStart"
            outPacket.encodeInt(endTime); // "timeEnd"
            outPacket.encodeInt(startDate); // "dateStart"
            outPacket.encodeInt(endDate); // "dateEnd"

            outPacket.encodeInt(0); // "UI"
            outPacket.encodeInt(0); // "prior"
            outPacket.encodeByte(0); // "hot"
            outPacket.encodeByte(0); // "expEvent"
            outPacket.encodeByte(0); // "attend"
            outPacket.encodeByte(0); // "invisible"
            outPacket.encodeByte(0); // "continue"

            outPacket.encodeInt(rewardItems != null ? rewardItems.size() : 0); // "reward"
            if (rewardItems != null) {
                rewardItems.forEach(outPacket::encodeInt);
            }

            outPacket.encodeInt(0); // "quest" entries
            outPacket.encodeByte(0); // no "questIDs"/toolTip block
            outPacket.encodeByte(0); // no "lvmin"/"lvmax"
        }

        // v271 tabbed EVENT LIST entry (sub_140EE2310), field offsets as comments
        public void encodeV271(OutPacket outPacket) {
            String start = String.format("%08d%04d", startDate, startTime / 100);
            String end = String.format("%08d%04d", endDate, endTime / 100);

            outPacket.encodeString(title); // +32
            outPacket.encodeString(description); // +40
            outPacket.encodeString(requirements == null ? "" : requirements); // +48
            outPacket.encodeString(formatPeriod(startDate, startTime, endDate, endTime)); // +56
            outPacket.encodeString(end); // +72 upper bound: client needs start(+168) < now <= end(+72)
            outPacket.encodeString(end); // +120 hide-after bound
            outPacket.encodeString(start); // +168 start
            outPacket.encodeInt(0); // +240 key
            outPacket.encodeString(""); // +240 value
            outPacket.encodeInt(0); // +256 key
            outPacket.encodeString(""); // +256 value
            outPacket.encodeByte(0); // +272
            outPacket.encodeByte(0); // +273
            outPacket.encodeByte(0); // +274
            outPacket.encodeInt(id); // +20 group map key, must be unique per event
            outPacket.encodeInt(0); // +276
            outPacket.encodeInt(id); // +24 sort/pin id
            outPacket.encodeInt(tab); // +28 tab id: 0 Events / 1 Coin Shops / 2 Cash
            outPacket.encodeString(""); // +8 flags key (/flags/<key>)
            outPacket.encodeByte(0); // +16
            outPacket.encodeString(""); // +216 icon
            outPacket.encodeString(""); // +224 backgrnd
            outPacket.encodeString(tag == null ? "" : tag); // +232 eventTag (/eventTag/<tag>)

            outPacket.encodeInt(0); // +288
            outPacket.encodeInt(0); // +296 int list
            outPacket.encodeInt(0); // +320 int list
            outPacket.encodeInt(0); // +344 int list
            outPacket.encodeInt(0); // +368 tagged list
            outPacket.encodeByte(0);

            outPacket.encodeInt(0); // +480 entries

            outPacket.encodeInt(0); // +544 tagged list
            outPacket.encodeByte(0);

            outPacket.encodeInt(rewardItems != null ? rewardItems.size() : 0); // +656 int list
            if (rewardItems != null) {
                rewardItems.forEach(outPacket::encodeInt);
            }

            outPacket.encodeByte(0); // +285
            outPacket.encodeByte(0); // +284
            outPacket.encodeInt(0); // +280
        }
    }

    public static synchronized void load() {
        EVENTS.clear();

        if (!Files.exists(CONFIG_PATH)) {
            log.warn("EventLists.json not found: {}", CONFIG_PATH);
            return;
        }

        JSONParser parser = new JSONParser();

        try {
            String content = Files.readString(CONFIG_PATH, StandardCharsets.UTF_8);
            JSONArray root = (JSONArray) parser.parse(content);

            for (Object obj : root) {
                JSONObject data = (JSONObject) obj;

                int id = ((Long) data.get("id")).intValue();
                String title = (String) data.get("title");
                String description = (String) data.get("description");
                int startTime = ((Long) data.get("startTime")).intValue();
                int endTime = ((Long) data.get("endTime")).intValue();
                int startDate = ((Long) data.get("startDate")).intValue();
                int endDate = ((Long) data.get("endDate")).intValue();

                List<Integer> rewardItems = new ArrayList<>();
                JSONArray rewards = (JSONArray) data.get("rewardItems");
                if (rewards != null) {
                    for (Object r : rewards) {
                        rewardItems.add(((Long) r).intValue());
                    }
                }

                String requirements = (String) data.get("requirements");
                String tag = (String) data.get("tag");
                Long tabValue = (Long) data.get("tab");
                int tab = tabValue == null ? 0 : tabValue.intValue();

                EVENTS.add(new EventListDataRecord(
                        id,
                        title,
                        description,
                        startTime,
                        endTime,
                        startDate,
                        endDate,
                        rewardItems,
                        requirements,
                        tag,
                        tab
                ));
            }

            log.info("Loaded EventList {} event(s).", EVENTS.size());

        } catch (IOException | ParseException e) {
            log.error("Failed to load EventLists.json", e);
        }
    }

    public static void reload() {
        // Reloadable via admin commands
        load();
    }

    public static List<EventListDataRecord> getActiveEvents() {
        int today = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return EVENTS.stream()
                .filter(e -> e.endDate() >= today)
                .toList();
    }

    public static List<EventListDataRecord> getAllEvents() {
        return List.copyOf(EVENTS);
    }

    public static void encode(OutPacket outPacket, List<EventListDataRecord> events) {
        outPacket.encodeInt(events.size());
        for (EventListDataRecord e : events) {
            e.encode(outPacket);
        }
    }

    // v271 tabbed EVENT LIST (s2c 378), parsed by sub_144D29200 -> sub_140EE4080 -> sub_140EE2310
    public static void encodeV271(OutPacket outPacket, List<EventListDataRecord> events) {
        outPacket.encodeInt(1); // outer/group count

        outPacket.encodeString("Maple Events"); // outer+8, group name
        outPacket.encodeString("Maple Events"); // outer+48
        outPacket.encodeString("");             // outer+56
        outPacket.encodeByte(0);                // outer+16
        outPacket.encodeInt(1);                 // outer+24, group id
        outPacket.encodeInt(0);                 // outer+28
        outPacket.encodeInt(events.size());     // inner count

        for (EventListDataRecord e : events) {
            e.encodeV271(outPacket);
        }

        // favourites/config tail (sub_140EE47A0)
        outPacket.encodeString("");
        outPacket.encodeInt(0);
        outPacket.encodeString("");
        outPacket.encodeString("");
        outPacket.encodeInt(0);
    }
}