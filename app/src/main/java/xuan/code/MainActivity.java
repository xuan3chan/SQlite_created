package xuan.code;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_1;
    Button btn_1;
    RadioButton rd_1,rd_2,rd_3,rd_4;
    int socau=4;
    int index=0;
    List<cauhoi> dscauhoi;
    cauhoi cauhientai;
    int caudung=0;

    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_1 = findViewById(R.id.txt_1);
        btn_1 = (Button) findViewById(R.id.btn_1);
        rd_1 = (RadioButton) findViewById(R.id.rd_1);
        rd_2 = (RadioButton) findViewById(R.id.rd_2);
        rd_3 = (RadioButton) findViewById(R.id.rd_3);
        rd_4 = (RadioButton) findViewById(R.id.rd_4);



        // Create an instance of the quanlycauhoi class and attempt to create the database
        quanlycauhoi db = new quanlycauhoi(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating database", Toast.LENGTH_SHORT).show();
        }

//        // Retrieve data from the database using the laytacacauhoi method and display it in the TextView
//        Cursor cursor = db.laytacacauhoi();
//        if (cursor != null && cursor.moveToFirst()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            do {
//                stringBuilder.append(cursor.getString(0)).append(" ");
//                stringBuilder.append(cursor.getString(1)).append("\n");
//            } while (cursor.moveToNext());
//            txt_1.setText(stringBuilder.toString());
//            cursor.close();
//        }
        dscauhoi=new ArrayList<cauhoi>();
        dscauhoi=db.layngaunhien(socau);
        hienthi(index);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String cautraloi="";
              if(rd_1.isChecked()==true)
                  cautraloi="a";
              else if(rd_2.isChecked()==true)
                  cautraloi="b";
              else if(rd_3.isChecked()==true)
                  cautraloi="c";
              else if(rd_4.isChecked()==true)
                  cautraloi="d";
              else
                  cautraloi="bo qua";
            Toast.makeText(getApplicationContext(),"cau tra loi "+
                    cautraloi,Toast.LENGTH_SHORT).show();
            if(cautraloi.equalsIgnoreCase(cauhientai.dap_an)){
                caudung=caudung+1;
            }
            index++;
            if (index<socau)
                hienthi(index);
            else{
                Toast.makeText(getApplicationContext(),"cau dung"+caudung,Toast.LENGTH_SHORT).show();
            }
            }

        });


    }
    public void hienthi(int vitri){
        cauhientai = dscauhoi.get(vitri);
        txt_1.setText(cauhientai.cauhoi);
        rd_1.setText("a."+cauhientai.cau_a);
        rd_2.setText("b."+cauhientai.cau_b);
        rd_3.setText("c."+cauhientai.cau_c);
        rd_4.setText("d."+cauhientai.cau_d);

    }
}
