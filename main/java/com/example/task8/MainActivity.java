package com.example.task8;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,ContactsContract.Contacts.HAS_PHONE_NUMBER+"=1",null,"UPPER("+ContactsContract.Contacts.DISPLAY_NAME+")ASC");

        if(cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String email;
                String id = cur.getString(cur.getColumnIndex((ContactsContract.Contacts._ID)));
                String name = cur.getString(cur.getColumnIndex((ContactsContract.Contacts.DISPLAY_NAME)));
                String numb2=" ";
                //String birthdate=" " ;


                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor cursor = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    Cursor cursor1 = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (cursor.moveToNext()) {
                        String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (cursor.moveToNext())
                        {
                            numb2 = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }



                        if (cursor1.moveToNext()) {
                            email = cursor1.getString(cursor1.getColumnIndex((ContactsContract.CommonDataKinds.Email.DATA)));
                            arrayList.add("name :" + name + "\n Phone no:" + phoneNo + "\nEmail id :" + email+ "\nNum2:"+numb2);
                        }
                        else{
                            arrayList.add("name :" + name + "\n Phone no:" + phoneNo+"\nNum2:"+numb2 );
                        }



                    }

                    cursor.close();
                    cursor1.close();
                }

            }
        }
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<String>(new LinkedHashSet<String>(arrayList));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
}