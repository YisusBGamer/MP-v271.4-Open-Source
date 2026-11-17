package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Quest record ("infoex") requirement, as found in Quest.wz/Check.img/[id]/[0|1]/infoex.
 * <p>
 * Each entry compares a value from the quest record of quest <code>infoNumber</code> (or of the quest itself when
 * there is no infoNumber):
 * <ul>
 *     <li>without <code>exVariable</code>, the whole record value is compared</li>
 *     <li>with <code>exVariable</code>, the record is parsed as <code>key=value;...</code> and that key is compared</li>
 * </ul>
 * <code>cond</code> selects the comparison: 0 (or absent) = equal, 1 = greater than or equal, 2 = less than or equal.
 * Entries are OR'ed together unless <code>QuestRecordAndOption</code> is set, in which case all must match.
 */
public class QuestRecordRequirement implements QuestStartRequirement {

    public static final int COND_EQUAL = 0;
    public static final int COND_GREATER_OR_EQUAL = 1;
    public static final int COND_LESS_OR_EQUAL = 2;

    private int questID; // the quest whose record should be checked
    private boolean andOption;
    private List<Entry> entries = new ArrayList<>();

    public QuestRecordRequirement() {
    }

    public QuestRecordRequirement(int questID, boolean andOption) {
        this.questID = questID;
        this.andOption = andOption;
    }

    public int getQuestID() {
        return questID;
    }

    public boolean isAndOption() {
        return andOption;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void addEntry(String value, int cond, String exVariable) {
        getEntries().add(new Entry(value, cond, exVariable));
    }

    @Override
    public boolean hasRequirements(Char chr) {
        if (getEntries().isEmpty()) {
            return true;
        }
        Quest quest = chr.getQuestManager().getQuestById(getQuestID());
        if (isAndOption()) {
            return getEntries().stream().allMatch(e -> e.matches(quest));
        }
        return getEntries().stream().anyMatch(e -> e.matches(quest));
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getQuestID());
        dos.writeBoolean(isAndOption());
        dos.writeShort(getEntries().size());
        for (Entry e : getEntries()) {
            dos.writeUTF(e.getValue());
            dos.writeByte(e.getCond());
            dos.writeUTF(e.getExVariable());
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestRecordRequirement qrr = new QuestRecordRequirement(dis.readInt(), dis.readBoolean());
        short size = dis.readShort();
        for (int i = 0; i < size; i++) {
            qrr.addEntry(dis.readUTF(), dis.readByte(), dis.readUTF());
        }
        return qrr;
    }

    public static class Entry {
        private final String value;
        private final int cond;
        private final String exVariable;

        public Entry(String value, int cond, String exVariable) {
            this.value = value == null ? "" : value;
            this.cond = cond;
            this.exVariable = exVariable == null ? "" : exVariable;
        }

        public String getValue() {
            return value;
        }

        public int getCond() {
            return cond;
        }

        public String getExVariable() {
            return exVariable;
        }

        /**
         * Gets the current value this entry refers to. A missing quest or key counts as an empty string.
         */
        private String getCurrentValue(Quest quest) {
            if (quest == null) {
                return "";
            }
            if (getExVariable().isEmpty()) {
                String qr = quest.getQRValue();
                return qr == null ? "" : qr;
            }
            // parse without touching the quest's stored record (Quest#getProperty rewrites it)
            String val = findKey(quest.getQRValue(), getExVariable());
            if (val == null) {
                val = findKey(quest.getQRExValue(), getExVariable());
            }
            return val == null ? "" : val;
        }

        private static String findKey(String record, String key) {
            if (record == null || record.isEmpty()) {
                return null;
            }
            for (String prop : record.split(";")) {
                int idx = prop.indexOf('=');
                if (idx > 0 && prop.substring(0, idx).equals(key)) {
                    return prop.substring(idx + 1);
                }
            }
            return null;
        }

        public boolean matches(Quest quest) {
            String current = getCurrentValue(quest);
            switch (getCond()) {
                case COND_GREATER_OR_EQUAL:
                    return compareNumeric(current, getValue()) >= 0;
                case COND_LESS_OR_EQUAL:
                    return compareNumeric(current, getValue()) <= 0;
                default:
                    if (current.equals(getValue())) {
                        return true;
                    }
                    // "0" and an empty/missing record are equivalent for numeric comparisons
                    return isNumber(getValue()) && isNumber(current.isEmpty() ? "0" : current)
                            && compareNumeric(current, getValue()) == 0;
            }
        }

        private static boolean isNumber(String s) {
            if (s == null || s.isEmpty()) {
                return false;
            }
            try {
                Long.parseLong(s.trim());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private static int compareNumeric(String current, String required) {
            long cur = isNumber(current) ? Long.parseLong(current.trim()) : 0;
            long req = isNumber(required) ? Long.parseLong(required.trim()) : 0;
            return Long.compare(cur, req);
        }
    }
}
