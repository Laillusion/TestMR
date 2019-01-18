package com.example.user.testmr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "carstore"; //название БД
    private static final int DB_VERSION = 1; // версия БД
    static final String TABLE_CAR = "cars"; //название таблицы в БД


    // Название столбцов
    public static final String CAR_ID = "_id";
    public static final String CAR_MODEL = "model";
    public static final String CAR_PRICE = "price";
    public static final String CAR_YEARS = "years";
    public static final String CAR_PROPELLANT = "propellant";
    public static final String CAR_TM = "tm";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /// create table Cars ///
        db.execSQL("CREATE TABLE cars (" + CAR_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAR_MODEL
                + " TEXT, " + CAR_PRICE + " INTEGER, "
                + CAR_YEARS + " INTEGER, " + CAR_PROPELLANT + " TEXT, "
                + CAR_TM + " TEXT"
                + ");");

        //добавление начальных данных
        insertCars(db, "X1", "30093", "2012", "бензин", "автомат");
        insertCars(db, "X3", "55887", "2012", "дизель", "автомат");
        insertCars(db, "A4", "30091", "2012", "бензин", "механика");
        insertCars(db, "A6", "26869", "2009", "дизель", "автомат");
        insertCars(db, "Golf", "14939", "2008", "бензин", "автомат");
        insertCars(db, "Polo", "10748", "2008", "бензин", "механика");
        insertCars(db, "С3", "8813", "2007", "дизель", "механика");
        insertCars(db, "С4", "10640", "2008", "бензин", "автомат");
        insertCars(db, "Duster", "19883", "2013", "бензин", "автомат");
        insertCars(db, "Clio", "23000", "2015", "дизель", "механика");
        insertCars(db, "Logan", "10640", "2011", "бензин", "механика");
        insertCars(db, "Camry", "22140", "2007", "бензин", "автомат");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);

    }

    private static void insertCars (SQLiteDatabase db, String model, String price, String years,
                                    String propellant, String tm) {
        ContentValues carsValues = new ContentValues();
        carsValues.put("MODEL", model);
        carsValues.put("PRICE", price);
        carsValues.put("YEARS", years);
        carsValues.put("PROPELLANT", propellant);
        carsValues.put("TM", tm);
        db.insert("cars", null, carsValues);
    }
}
