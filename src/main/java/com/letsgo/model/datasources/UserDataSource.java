package com.letsgo.model.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.letsgo.model.DBHelper;
import com.letsgo.model.Event;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.utils.Constants;

import java.util.List;

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
    public boolean editUsername(String email, String newUsername) {
        return false;
    }

    @Override
    public boolean editEmail(String email, String newEmail) {
        return false;
    }

    @Override
    public boolean editPassword(String email, String newPassword) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public boolean addEventToWatchlist(String emial, String event_name) {
        return false;
    }

    @Override
    public boolean removeEventFromWatchlist(String emial, String eventName) {
        return false;
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
    public List<Event> showWathclist() {
        return null;
    }

//    for testing
    @Override
    public String showAllUsers() {
        StringBuffer allUsers = new StringBuffer();
        String[] columns = {Constants.USERS_NAME, Constants.USERS_EMAIL,Constants.USERS_PASSWORD};
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

}
