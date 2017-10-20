package com.example.tattata.nankanmadeyondakke;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by tattata on 2017/08/17.
 * 本棚
 */

public class Shelf {
    private ArrayList<Book> books;
    private SharedPreferences pref;
    private Context context;
    private LinearLayout oyaLayout;

    private static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private static final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    public Shelf(Context context, LinearLayout oyaLayout) {
        this.context = context;
        this.oyaLayout = oyaLayout;
        books = new ArrayList<>();
        pref = context.getSharedPreferences("bookPref", MODE_PRIVATE);
    }
    public void addBook(String title, int kansu) {
        LinearLayout linearLayout = new LinearLayout(context);
        EditText titleEdit = new EditText(context);
        final EditText kanEdit = new EditText(context);
        kanEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        titleEdit.setText(title);
        if(kansu != -1) {
            kanEdit.setText(String.valueOf(kansu));
        }
        titleEdit.setEms(10);
        kanEdit.setEms(3);

        kanEdit.setSelectAllOnFocus(true);

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
            int kansu;
            try {
                kansu = Integer.parseInt(((EditText) layout.getChildAt(1)).getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                oyaLayout.removeView(layout);
                continue;
            }
            if(title.length() == 0) {
                oyaLayout.removeView(layout);
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
        editor.apply();
    }
    public void loadData() {
        Map<String, ?> map = pref.getAll();
        for(int i = 0; i < map.size(); i++) {
            String data = map.get("index" + String.valueOf(i)).toString();
            String arr[] = data.split(String.valueOf(0x1f));
            int kansu =Integer.parseInt(arr[1]);
            addBook(arr[0], kansu);
        }
    }
}
