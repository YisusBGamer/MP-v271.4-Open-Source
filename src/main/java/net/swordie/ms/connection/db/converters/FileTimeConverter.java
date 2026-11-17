package net.swordie.ms.connection.db.converters;

import net.swordie.ms.util.FileTime;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.*;

/**
 * @author Sjonnie
 * Created on 9/13/2018.
 */
public class FileTimeConverter implements AttributeConverter<FileTime, Timestamp> {

    // create "custom" LDTs for our own min/max filetime values (max FT value is invalid, as it has 5 year digits)
    private static final LocalDateTime MAX_LDT = LocalDateTime.of(LocalDate.of(9999, 1, 1), LocalTime.of(0, 0, 1));
    private static final LocalDateTime MIN_LDT = LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.of(0, 0, 1));
    // the range a MySQL DATETIME holds; anything outside it fails the whole save ("Data truncated for column")
    private static final LocalDateTime DATETIME_MIN = LocalDateTime.of(LocalDate.of(1000, 1, 1), LocalTime.MIDNIGHT);
    private static final LocalDateTime DATETIME_MAX = LocalDateTime.of(LocalDate.of(9999, 12, 31),
            LocalTime.of(23, 59, 59, 999_000_000));

    @Override
    public Timestamp convertToDatabaseColumn(FileTime fileTime) {
        return toDbColumn(fileTime);
    }

    @Override
    public FileTime convertToEntityAttribute(Timestamp ts) {
        LocalDateTime ldt = null;
        if (ts != null) {
            ldt = ts.toLocalDateTime();
        }
        FileTime ft;
        if (ldt == null) {
            ft = null;
        } else if (ldt.getYear() == MAX_LDT.getYear()) {
            ft = FileTime.fromType(FileTime.Type.MAX_TIME);
        } else if (ldt.getYear() == MIN_LDT.getYear()) {
            ft = FileTime.fromType(FileTime.Type.ZERO_TIME);
        } else {
            ft = FileTime.fromDate(ts.toLocalDateTime());
        }
        return ft;
    }

    public static Timestamp toDbColumn(FileTime fileTime) {
        if (fileTime == null) {
            return null;
        }
        LocalDateTime ldt;
        if (fileTime.isMaxTime()) {
            ldt = MAX_LDT;
        } else if (fileTime.isMinTime()) {
            ldt = MIN_LDT;
        } else {
            // a client-format FileTime with a small raw value decodes far past the year 9999
            ldt = fileTime.toLocalDateTime();
            if (ldt.isBefore(DATETIME_MIN)) {
                ldt = DATETIME_MIN;
            } else if (ldt.isAfter(DATETIME_MAX)) {
                ldt = DATETIME_MAX;
            }
        }
        return Timestamp.valueOf(ldt);
    }

    public static FileTime fromDbColumn(Timestamp ts) {
        LocalDateTime ldt = null;
        if (ts != null) {
            ldt = ts.toLocalDateTime();
        }
        FileTime ft;
        if (ldt == null) {
            ft = null;
        } else if (ldt.getYear() == MAX_LDT.getYear()) {
            ft = FileTime.fromType(FileTime.Type.MAX_TIME);
        } else if (ldt.getYear() == MIN_LDT.getYear()) {
            ft = FileTime.fromType(FileTime.Type.ZERO_TIME);
        } else {
            ft = FileTime.fromDate(ts.toLocalDateTime());
        }
        return ft;
    }


}
