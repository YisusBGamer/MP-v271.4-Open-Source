package net.swordie.ms.client.dailies;

import net.swordie.ms.connection.db.TrackedObject;
import net.swordie.ms.enums.DailyEntry;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.ResetClock;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ContentReset extends TrackedObject {
    private long id = 0;
    private DailyEntry dailyEntry;
    private FileTime startDate;

    public ContentReset() {
    }

    public ContentReset(DailyEntry dailyEntry) {
        this.dailyEntry = dailyEntry;
        setPeriodDay(periodStart(ResetClock.today(), dailyEntry.getDaysPerReset()));
    }

    /** Periods count from Friday 2022-04-22 (with the SSB rotation), so weekly ones turn on Fridays. */
    private static final LocalDate ANCHOR = LocalDate.of(2022, 4, 22);

    /** @return the first day of the period of {@code days} days that holds {@code day} */
    public static LocalDate periodStart(LocalDate day, int days) {
        return day.minusDays(Math.floorMod(day.toEpochDay() - ANCHOR.toEpochDay(), Math.max(1, days)));
    }

    /** @return the first day of the period this reset was made for (saved 10 minutes before that day's reset) */
    public LocalDate getPeriodDay() {
        return ResetClock.dayOf(startDate.toMillis() + TimeUnit.HOURS.toMillis(1));
    }

    public void setPeriodDay(LocalDate day) {
        this.startDate = FileTime.fromEpochMillis(ResetClock.startOfDay(day) - TimeUnit.MINUTES.toMillis(10));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getTransactionalHash() {
        return Objects.hash(dailyEntry, startDate);
    }

    public DailyEntry getDailyEntry() {
        return dailyEntry;
    }

    public void setDailyEntry(DailyEntry dailyEntry) {
        this.dailyEntry = dailyEntry;
    }

    public FileTime getStartDate() {
        return startDate;
    }

    public void setStartDate(FileTime startDate) {
        this.startDate = startDate;
    }

}
