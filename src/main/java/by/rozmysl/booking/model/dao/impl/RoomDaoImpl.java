package by.rozmysl.booking.model.dao.impl;

import by.rozmysl.booking.model.dao.RoomDao;
import by.rozmysl.booking.model.entity.hotel.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.rozmysl.booking.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `ROOM` table DAO with the <b>ProxyConnection</b> properties.
 */
public class RoomDaoImpl extends DaoImpl<Room, Integer> implements RoomDao {

    @Override
    public Room buildEntity(ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getInt(ROOM_COLUMN_ROOMNUMBER),
                resultSet.getString(ROOM_COLUMN_TYPE),
                resultSet.getInt(ROOM_COLUMN_SLEEPS),
                resultSet.getBigDecimal(ROOM_COLUMN_PRICE));
    }
}
