package net.swordie.ms.connection.db.converters;

import net.swordie.ms.connection.db.DatabaseException;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobByteArrayConverter {

    public static byte[] fromDbColumn(Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }
        return blob.getBytes(1, (int) blob.length());
    }

    public static Blob toDbColumn(byte[] arr) {
        if (arr == null) {
            return null;
        }
        try {
            return new SerialBlob(arr);
        } catch (SQLException e) {
            // writing NULL instead would wipe the column
            throw new DatabaseException("Could not convert " + arr.length + " bytes to a blob", e);
        }
    }
}
