package com.example.tattata.nankanmadeyondakke;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by tattata on 2017/08/17.
 */

public class Shelf {
    ArrayList<Book> books;
    SharedPreferences pref;
    Context context;
    LinearLayout oyaLayout;

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    public Shelf(Context context, LinearLayout oyaLayout) {
        this.context = context;
        this.oyaLayout = oyaLayout;
        books = new ArrayList<>();
        pref = context.getSharedPreferences("bookPref", MODE_PRIVATE);
    }
    public void addBook(String title, int kansu) {
        LinearLayout linearLayout = new LinearLayout(context);
        EditText titleEdit = new EditText(context);
        EditText kanEdit = new EditText(context);
        kanEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        titleEdit.setText(title);
        if(kansu != -1) {
            kanEdit.setText(String.valueOf(kansu));
        }
        titleEdit.setEms(10);
        kanEdit.setEms(3);

        linearLayout.addView(titleEdit, new LinearLayout.LayoutParams(WC, WC, 2.0f));
        linearLayout.addView(kanEdit, new LinearLayout.LayoutParams(WC, WC, 1.0f));
        oyaLayout.addView(linearLayout, new LinearLayout.LayoutParams(MP, MP));
        books.add(new Book(title, kansu, linearLayout));
    }
    public void refresh() {
        //EditTextの内容をShelfクラスに反映
        ArrayList<Book> newBooks = new ArrayList<>();
        for(Book book: books) {
            LinearLayout layout = book.getLayout();
            String title = ((EditText)layout.getChildAt(0)).getText().toString().trim();
            if(title.length() == 0) {
                continue;
            }
            int kansu;
            try {
                kansu = Integer.parseInt(((EditText) layout.getChildAt(1)).getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            if(title.length() == 0) {
                continue;
            }
            Book b = new Book(title, kansu, layout);
            newBooks.add(b);
        }
        books.clear();
        books = newBooks;
    }
    public void saveData() {
        refresh();
        int i = 0;
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        for(Book book: books) {
            editor.putString("index" + String.valueOf(i), book.toString());
            i++;
        }
        editor.commit();
    }
    public void loadData() {
        Map<String, ?> map = pref.getAll();
        for(int i = 0; i < map.size(); i++) {
            String data = map.get("index" + String.valueOf(i)).toString();
            String arr[] = data.split(" ");
            addBook(arr[0], Integer.parseInt(arr[1]));
        }
    }
}
