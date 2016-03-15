package com.letsgo.model.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.letsgo.model.DBHelper;
import com.letsgo.model.Event;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public class UserDataSource extends DataSource implements UserDao {

    public UserDataSource(Context context) {
        super(context);
    }

    @Override
    public boolean loginUser(String email, String password) {


//          FOR TEST - adding events
//        database.execSQL("insert into locations(location_name,location_address,location_contact) values('zala','sofia','tel 8456'),('kino','varna','tel 654')");
//
//        database.execSQL("insert into types(type) values('kocert'),('film'),('postanovka')");
//
//        database.execSQL("insert into events (event_name,event_description,event_ticket_price,event_date,event_location_fk,event_type_fk) " +
//                "values " +
//                "('Metallica','Through the never live concert',60 ,'2013-10-04'," +
//                "(SELECT _id_pk FROM locations WHERE location_name = 'kino')," +
//                "(SELECT _id_pk FROM types WHERE type = 'kocert')" +
//                ")");
//
//
//        database.execSQL("insert into locations(location_name,location_address,location_contact) values('Yalta Club','sofia tsar Osvoboditel','tel 3156'),('Palace of Varna','Varna Kniaz Boris','tel 052')");
//
//        database.execSQL("insert into types(type) values('party'),('show')");
//
//        database.execSQL("insert into events (event_name,event_description,event_ticket_price,event_date,event_location_fk,event_type_fk) " +
//                "values " +
//                "('Armin Only','Armin van Buuren is back in Bulgaria',60 ,'2016-09-17'," +
//                "(SELECT _id_pk FROM locations WHERE location_name = 'Yalta Club')," +
//                "(SELECT _id_pk FROM types WHERE type = 'party')" +
//                ")");
//
//        database.execSQL("insert into locations(location_name,location_address,location_contact) values('Kino Arena','sofia mladost 4','tel 33156')");
//        database.execSQL("insert into events (event_name,event_description,event_ticket_price,event_date,event_location_fk,event_type_fk) " +
//                "values " +
//                "('Warcraft','Long anticipated Blizzard movie of the popular game ',20 ,'2016-06-10'," +
//                "(SELECT _id_pk FROM locations WHERE location_name = 'Kino Arena')," +
//                "(SELECT _id_pk FROM types WHERE type = 'film')" +
//                ")");
//
//        database.execSQL("insert into locations(location_name,location_address,location_contact) values('Arena Armeec Sofia','sofia Asen Yordanov Blvd.','tel 356')");
//
//
//        String descr = "12-ти декември е датата на която ще бъде коронясан шампиона " +
//                "в Световния шампионат по мотокрос фристайл – Night of the Jumps в Арена Армеец, град София. " +
//                "Дванадесет състезатели от Австралия, Франция, Чехия, Испания, Германия, САЩ и Италия ще премерят " +
//                "своите сили и умения на 4 различни рампи и новото предизвикателство – quarter pipe. " +
//                "Част от програмата на вечерта е състезанието Highest Air (най-висок скок), " +
//                "както и позабравеното състезание Race&Style, което сме наблюдавали само един път в далечната 2011-та. " +
//                "По традиция, всички закупени билети участват в томбола с награда автомобил Great Wall H5.";
//
//        database.execSQL("insert into events (event_name,event_description,event_ticket_price,event_date,event_location_fk,event_type_fk) " +
//                "values " +
//                "('The Night of the Jumps','"+descr+"',35 ,'2015-12-12'," +
//                "(SELECT _id_pk FROM locations WHERE location_name = 'Arena Armeec Sofia')," +
//                "(SELECT _id_pk FROM types WHERE type = 'show')" +
//                ")");
//
//
//        String descr1 = "Евгени Плюшченко е от онези спортисти, за които не съществуват подходящи епитети или определения. Той е най-големият майстор в света на фигурното пързаляне и вече се надпреварва само и единствено със себе си. Подобрил е всички рекорди, извоювал е четири Олимпийски отличия в една от най-трудните зимни дисциплини и е на път да стане единственият спортист с медал от пет състезания от този клас с участието си в Пьончан (Южна Корея) през 2018 г.\n" +
//                "\n" +
//                "След травма и кратко оттегляне от леда той отново е на върха: \"Защо искам да продължа ли? Просто искам да направя нещо наистина невероятно. Нещо, което е невъзможно да бъде повторено. Никой фигурист не е участвал на пет Олимпиади. А аз мисля дори за медал\", заяви Плюшченко, цитиран от БТА.\n" +
//                "\n" +
//                "По пътя си руският фигурист е решил да отдаде заслуженото и на българските фенове на това изкуство. За целта той ще събере световния елит за една специална и приказна вечер в столичната Арена Армеец.\n" +
//                "\n" +
//                "На 23-ти април 2016 на леда ще излязат кралете на фигурното пързаляне от Русия, Франция, Чехия, Япония. До момента са потвърдени пет от най-големите и актуални имена във фигурното пързаляне, като предстои да бъдат добавени още цели 7.\n" +
//                "\n" +
//                "Западната школа ще има своя атрактивен представител в шоуто, разбивача на женски сърца, Браян Жобер (Франция). Най-великият френски фигурист за всички времена е носител на шест медала от Световно първенство, десет отличия в Европейския шампионат и е четирикратен финалист на Олимпийски игри.\n" +
//                "\n" +
//                "Музикалният съпровод на невероятните изпълнения на леда също няма да е случаен. Виртуозният музикант и носител на награда „Еми“, принцът на цигулката Едвин Мартон ще свири на живо, редом до фигуристите.\n" +
//                "\n" +
//                "Самият Плюшченко обещава в следващите 2 месеца да обяви и целия състав за шоуто, както и една строго пазена музикална тайна. Нещо, което ще се случи за първи път в историята на това турне на „Кралете на леда“.\n" +
//                "\n" +
//                "Нека всички фенове на фигурното пързаляне си запазят датата 23-ти април 2016 г., защото тогава Олимпийските игри ще се пренесат поне за една вечер в София. ";
//
//        database.execSQL("insert into events (event_name,event_description,event_ticket_price,event_date,event_location_fk,event_type_fk) " +
//                "values " +
//                "('KINGS ON ICE','"+descr1+"',58 ,'2016-04-23'," +
//                "(SELECT _id_pk FROM locations WHERE location_name = 'Arena Armeec Sofia')," +
//                "(SELECT _id_pk FROM types WHERE type = 'show')" +
//                ")");
//
//

        String[] columns = {Constants.USERS_EMAIL, Constants.USERS_PASSWORD};
        String selection = Constants.USERS_EMAIL + " =? AND " + Constants.USERS_PASSWORD + " =?";
        String[] selArgs = {email, password};
        Cursor cursor = database.query(Constants.TABLE_USERS, columns, selection, selArgs, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    public User createUser(String username, String email, String password) {
        User user = new User();
        //        check if email exists
        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_EMAIL, email)) {
            user.setUsername(Constants.EXISTING_EMAIL);
            return user;
        }
        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_NAME, username)) {
            user.setUsername(Constants.EXISTING_USERNAME);
            return user;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.USERS_NAME, username);
        values.put(Constants.USERS_PASSWORD, password);
        values.put(Constants.USERS_EMAIL, email);
        long inserId = database.insert(Constants.TABLE_USERS, null, values);
        if (inserId < 0) {
            user.setUsername("Could not add user");
        }

        Cursor cursor = database.query(Constants.TABLE_USERS, Constants.USERS_ALL_COLUMNS, Constants.AUTOINCREMETN_COLUMN + " = "
                + inserId, null, null, null, null);
        cursor.moveToFirst();
        user = cursorToUser(cursor);
        cursor.close();
        return user;
    }

    @Override
    public User showUser(String email) {

        Cursor cursor = database.query(Constants.TABLE_USERS, Constants.USERS_ALL_COLUMNS, Constants.USERS_EMAIL + " = "
                + "'" + email + "'", null, null, null, null);
        cursor.moveToFirst();
        User user = cursorToUser(cursor);
        cursor.close();
        return user;

    }

    @Override
    public boolean editUsername(long userId, String newUsername) {

        ContentValues values = new ContentValues();
        values.put(Constants.USERS_NAME, newUsername);
        String where = Constants.AUTOINCREMETN_COLUMN + " =?";
        String[] args = {String.valueOf(userId)};
        int result = database.update(Constants.TABLE_USERS, values, where, args);

        if (result > 0)
            return true;

        return false;
    }

    @Override
    public boolean editEmail(long userId, String newEmail) {
        return false;
    }

    @Override
    public boolean editPassword(long userId, String newPassword) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public boolean addEventToWatchlist(long userId, String eventName) {
//        insert into Constants.TABLE_USERS_FAV_EVENTS (Constants.FKEY_USER_ID,Constants.FKEY_EVENT_ID)
//        values
//        (select Constants.TABLE_USERS.Constants.AUTOINCREMETN_COLUMN from Constants.TABLE_USERS where Constants.USERS_EMAIL = email,
//          select Constants.TABLE_EVENTS.AUTOINCREMETN_COLUMN from Constants.TABLE_EVENTS where Constants.EVENTS_NAME = event_name)

        long eventId = getEventIdByName(eventName);

        ContentValues values = new ContentValues();
        values.put(Constants.FKEY_USER_ID, userId);
        values.put(Constants.FKEY_EVENT_ID, eventId);

        long insertedRow = database.insert(Constants.TABLE_USERS_FAV_EVENTS, null, values);

        if (insertedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean removeEventFromWatchlist(long userId, String eventName) {
        long eventId = getEventIdByName(eventName);
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.FKEY_USER_ID, userId);
//        values.put(Constants.FKEY_EVENT_ID, eventId);

        String[] args = {String.valueOf(userId), String.valueOf(eventId)};

        long deletedRow = database.delete(Constants.TABLE_USERS_FAV_EVENTS, Constants.FKEY_USER_ID + " = ? AND " + Constants.FKEY_EVENT_ID + " = ? ", args);

        if (deletedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean removeAllFromWatchlist(long userId) {

        String[] args = {String.valueOf(userId)};

        long deletedRow = database.delete(Constants.TABLE_USERS_FAV_EVENTS, Constants.FKEY_USER_ID + " = ? ", args);

        if (deletedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean buyTicket(String date, long userId, long eventId, int quantity) {

        ContentValues values = new ContentValues();
        values.put(Constants.TICKET_PURCHASE_DATE, date);
        values.put(Constants.FKEY_EVENT_ID, eventId);
        values.put(Constants.FKEY_USER_ID, userId);
        values.put(Constants.TICKET_QUANTITY, quantity);
        long insertedRow = database.insert(Constants.TABLE_TICKETS_EVENTS_USERS, null, values);
        if (insertedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public ArrayList<Map<String, Map<Event, Integer>>> selectTicketsForUser(long userId) {

        /**
         * select Constants.EVENTS_NAME,Constants.EVENTS_DATE,Constants.TICKET_QUANTITY
         * from TABLE_TICKETS_EVENTS_USERS teu
         * join TABLE_EVENTS e
         * on teu.FKEY_EVENT_ID = e.AUTOINCREMETN_COLUMN
         * where teu.FKEY_USER_ID = userid
         */

        ArrayList<Map<String, Map<Event, Integer>>> bindEventsTickets = new ArrayList<>();

        String[] columns = {Constants.TICKET_PURCHASE_DATE, Constants.EVENTS_NAME, Constants.EVENTS_DATE, Constants.TICKET_QUANTITY};
        String selection = Constants.FKEY_USER_ID + " = ? ";
        String[] args = {String.valueOf(userId)};

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Constants.TABLE_TICKETS_EVENTS_USERS +
                " join " + Constants.TABLE_EVENTS +
                " on " + Constants.TABLE_TICKETS_EVENTS_USERS + "." + Constants.FKEY_EVENT_ID + " = " +
                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN);

        Cursor cursor = queryBuilder.query(database, columns, selection, args, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String dateOfPurchase = cursor.getString(cursor.getColumnIndex(Constants.TICKET_PURCHASE_DATE));
            Event event = new Event();
            event.setEventName(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_NAME)));
            event.setEventDate(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_DATE)));

            Map<Event, Integer> eventTicketEntry = new HashMap<>();
            eventTicketEntry.put(event, cursor.getInt(cursor.getColumnIndex(Constants.TICKET_QUANTITY)));

            Map<String, Map<Event, Integer>> dateEventQuantity = new HashMap<>();
            dateEventQuantity.put(dateOfPurchase, eventTicketEntry);

            bindEventsTickets.add(dateEventQuantity);
            cursor.moveToNext();
        }
        cursor.close();
        return bindEventsTickets;
    }

    @Override
    public boolean checkForExisting(String table, String column, String selectionArg) {
        String[] columns = {column};
        String[] selectionArgs = {selectionArg};
        Cursor cursor = database.query(table, columns, column + " =? ", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    @Override
    public Event showEventFromWatch(String eventName) {
        return null;
    }

    @Override
    public ArrayList<Event> showWathclist(long userId) {

        ArrayList<Event> watchList = new ArrayList<>();
//        String[] args = {String.valueOf(userId)};
//        String[] evnameColumn = {Constants.EVENTS_NAME};
//        Cursor cursorEventName = joinFavsEventsLocations().query(database,evnameColumn,Constants.FKEY_USER_ID + " = ? ", args,null,null,null);
//        cursorEventName.moveToFirst();
//        while (!cursorEventName.isAfterLast()){
//            String name = cursorEventName.getString(cursorEventName.getColumnIndex(Constants.EVENTS_NAME));
//            Cursor cursor = EventDataSource.joinEventsLocationsTypes().query(database, eventColumns(),Constants.EVENTS_NAME + " = "
//                    + "'" + name + "'" , null, null, null, null, null);
////            Cursor cursor = database.query(Constants.TABLE_EVENTS, Constants.EVENTS_ALL_COLUMNS, Constants.EVENTS_NAME + " = "
////                    + "'" + name + "'", null, null, null, null);
//            cursor.moveToFirst();
//            Event event = EventDataSource.cursorToEvent(cursor);
//            cursor.close();
//            watchList.add(event);
//        }

//        Cursor cursor = joinFavsEventsLocations().query(database,eventColumns(),Constants.FKEY_USER_ID + " = ? ", args,null,null,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            Event event = EventDataSource.cursorToEvent(cursor);
//            watchList.add(event);
//            cursor.moveToNext();
//        }
//        cursor.close();

//        String longQuery = "select e._id_pk," +
//                "event_name," +
//                "event_description," +
//                "event_ticket_price," +
//                "event_date," +
//                "(select location_name from Events e join Locations loc on e.event_location_" +
//                "fk = loc._id_pk " +
//                "where  e._id_pk =(select Users_Favs_Events.event_id_fk from Users_Favs_Events where user_id_fk ="+userId+") ) as "+Constants.LOCATIONS_NAME+"" +
//                ",(select type from Events e join Types t on e.event_type_fk = t._id_pk " +
//                "where  e._id_pk =(select Users_Favs_Events.event_id_fk from Users_Favs_Events where user_id_fk ="+userId+") ) as "+Constants.TYPES_TYPE+" " +
//                "from Users_Favs_Events ufe  " +
//                "join Events e " +
//                "on ufe.event_id_fk = e._id_pk " +
//                "where user_id_fk = "+userId;

        Cursor cursor = database.query(Constants.TABLE_USERS_FAV_EVENTS, new String[]{Constants.FKEY_EVENT_ID}, Constants.FKEY_USER_ID + " = ? ", new String[]{String.valueOf(userId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long eventId = cursor.getLong(cursor.getColumnIndex(Constants.FKEY_EVENT_ID));

            Cursor c = EventDataSource.joinEventsLocationsTypes().query(database, EventDataSource.eventColumns(), Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN + " = ? ", new String[]{String.valueOf(eventId)}, null, null, null);
            c.moveToFirst();
            Event event = EventDataSource.cursorToEvent(c);
            watchList.add(event);
            c.close();
            cursor.moveToNext();
        }
        cursor.close();
        return watchList;
    }
//TODO join types .. fix Locations
//    private SQLiteQueryBuilder joinFavsEventsLocations() {
//        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//        queryBuilder.setTables(Constants.TABLE_USERS_FAV_EVENTS +
//                " join " + Constants.TABLE_EVENTS +
//                " on " + Constants.TABLE_USERS_FAV_EVENTS + "." + Constants.FKEY_EVENT_ID + " = " +
//                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN);
//        return queryBuilder;
//    }

//    private String[] eventColumns() {
//        String[] columns = {
//                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN,
//                Constants.EVENTS_NAME,
//                Constants.EVENTS_DESCRIPTION,
//                Constants.EVENTS_TICKET_PRICE,
//                Constants.EVENTS_DATE,
//                Constants.LOCATIONS_NAME};
//        return columns;
//    }

    //    for testing
    @Override
    public String showAllUsers() {
        StringBuffer allUsers = new StringBuffer();
        String[] columns = {Constants.USERS_NAME, Constants.USERS_EMAIL, Constants.USERS_PASSWORD};
        Cursor cursor = database.query(Constants.TABLE_USERS, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndex(Constants.USERS_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndex(Constants.USERS_EMAIL));
            String userPass = cursor.getString(cursor.getColumnIndex(Constants.USERS_PASSWORD));
            allUsers.append(userName + " " + userEmail + " " + userPass + "\n");
        }
        cursor.close();
        return allUsers.toString();
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Constants.USERS_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.USERS_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.USERS_PASSWORD)));

        if (cursor.getInt(cursor.getColumnIndex(Constants.USERS_ISADMIN)) == 1)
            user.setIsAdmin(true);
        else
            user.setIsAdmin(false);

        return user;
    }

    private long getEventIdByName(String eventName) {
        String[] columns = {Constants.AUTOINCREMETN_COLUMN};
        String[] eventsSelectArgs = {eventName};
        Cursor cursor = database.query(Constants.TABLE_EVENTS, columns, Constants.EVENTS_NAME + " = ? ", eventsSelectArgs, null, null, null);
        cursor.moveToFirst();
        long eventId = cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN));
        cursor.close();
        return eventId;
    }
}
