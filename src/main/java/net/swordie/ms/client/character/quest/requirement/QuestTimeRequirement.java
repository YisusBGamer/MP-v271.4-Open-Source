package net.swordie.ms.client.character.quest.requirement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.loaders.DatSerializable;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Day-of-week / hour-of-day window from Quest.wz/Check.img/[id]/[0|1]/dayOfWeek and hourOfDay.
 * Uses the reset time zone (ResetClock; the client's clock runs in it too).
 */
public class QuestTimeRequirement implements QuestStartRequirement {

    private Set<DayOfWeek> days = new HashSet<>();
    private Set<Integer> hours = new HashSet<>();

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public Set<Integer> getHours() {
        return hours;
    }

    public void addDay(DayOfWeek day) {
        getDays().add(day);
    }

    public void addHour(int hour) {
        getHours().add(hour);
    }

    public boolean isEmpty() {
        return getDays().isEmpty() && getHours().isEmpty();
    }

    /**
     * Converts the WZ day names (sun, mon, tue, wed, thu, fri, sat) to a {@link DayOfWeek}.
     */
    public static DayOfWeek dayByWzName(String name) {
        if (name == null) {
            return null;
        }
        switch (name.toLowerCase()) {
            case "sun": return DayOfWeek.SUNDAY;
            case "mon": return DayOfWeek.MONDAY;
            case "tue": return DayOfWeek.TUESDAY;
            case "wed": return DayOfWeek.WEDNESDAY;
            case "thu": return DayOfWeek.THURSDAY;
            case "fri": return DayOfWeek.FRIDAY;
            case "sat": return DayOfWeek.SATURDAY;
            default: return null;
        }
    }

    @Override
    public boolean hasRequirements(Char chr) {
        ZonedDateTime now = net.swordie.ms.util.ResetClock.now();
        if (!getDays().isEmpty() && !getDays().contains(now.getDayOfWeek())) {
            return false;
        }
        return getHours().isEmpty() || getHours().contains(now.getHour());
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeByte(getDays().size());
        for (DayOfWeek day : getDays()) {
            dos.writeByte(day.getValue());
        }
        dos.writeByte(getHours().size());
        for (int hour : getHours()) {
            dos.writeByte(hour);
        }
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestTimeRequirement qtr = new QuestTimeRequirement();
        byte size = dis.readByte();
        for (int i = 0; i < size; i++) {
            qtr.addDay(DayOfWeek.of(dis.readByte()));
        }
        size = dis.readByte();
        for (int i = 0; i < size; i++) {
            qtr.addHour(dis.readByte());
        }
        return qtr;
    }
}
