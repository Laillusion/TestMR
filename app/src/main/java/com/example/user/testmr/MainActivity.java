package com.example.user.testmr;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    ListView lvCars;
    EditText carFilter;
    DBHelper DbHelper;
    SQLiteDatabase db;
    Cursor carCursor;
    SimpleCursorAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCars = findViewById(R.id.lvCars);
        carFilter = findViewById(R.id.carFilter);

        // открываем подключение
        DbHelper = new DBHelper(getApplicationContext());

    }
    @Override
    public void onResume() {
        super.onResume();

        try {
            db = DbHelper.getWritableDatabase();

            //получаем данные из БД в виде курсора
            carCursor = db.rawQuery("SELECT * FROM cars", null);

            String[] from = new String[]{"model", "price", "years", "propellant", "tm"};
            int[] to = new int[]{R.id.tvModel, R.id.tvPrice, R.id.tvYears, R.id.tvPropellant, R.id.tvTm};

            //создаем адаптер, передаем в него курсор
            carAdapter = new SimpleCursorAdapter(this, R.layout.car_item, carCursor, from, to);
            //lvCars.setAdapter(carAdapter);

            //если в текстовом поле есть текст, то выполняем фильтрацию
            if(!carFilter.getText().toString().isEmpty())
                carAdapter.getFilter().filter(carFilter.getText().toString());

            //Установка слушателя изменения текста
            carFilter.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) { }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                //При ззменении текста выполним фильтрацию
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    carAdapter.getFilter().filter(s.toString());
                }

            });
            //Устанавливаем провайдер фильтрации
            carAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {
                        return db.rawQuery("select * from cars", null);
                    } else {
                        return db.rawQuery("select * from cars where model like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            //сортировка по цены по возрастанию67
            Button btnPrice = findViewById(R.id.btnPrice);
            btnPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carCursor = db.rawQuery("SELECT * FROM cars ORDER BY price ASC", null);
                    carAdapter.swapCursor(carCursor); //отдаем новые данные адаптеру
                    carAdapter.notifyDataSetChanged();

                }
            });


            lvCars.setAdapter(carAdapter);

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailabel", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //закрываем подключение и курсор
        carCursor.close();
        db.close();
    }
}
