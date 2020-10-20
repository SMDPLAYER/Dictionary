package uz.smd.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import uz.smd.dictionary.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "dictionary.db";
    public static String DB_PATH = null;
    private static final int DB_VERSION = 1;
    public static final String abc = "abcdefghijklmnopqrstuvwxyz";
    private Context mCtx;
    private SQLiteDatabase myDataBase;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mCtx = context;
        DB_PATH = context.getFilesDir().getAbsolutePath();
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            try {
                getReadableDatabase();
                copyDataBase(this.mCtx);
            } catch (Error unused) {
                Toast.makeText(this.mCtx, "Failed to create/copy database.", 1).show();
            }
        }
    }

    private boolean checkDataBase() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DB_PATH);
        stringBuilder.append(DB_NAME);
        return new File(stringBuilder.toString()).exists();
    }

    private void copyDataBase(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DB_PATH);
        stringBuilder.append(DB_NAME);
        String stringBuilder2 = stringBuilder.toString();
        try {
            InputStream openRawResource = this.mCtx.getResources().openRawResource(R.raw.dictionary);
            FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openRawResource.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    openRawResource.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception unused) {
            Toast.makeText(this.mCtx, "error while copying database", 1).show();
        }
    }

    public void openDataBase() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DB_PATH);
        stringBuilder.append(DB_NAME);
        this.myDataBase = SQLiteDatabase.openDatabase(stringBuilder.toString(), null, 0);
    }

    public void closeDatabase() {
        this.myDataBase.close();
    }

    public ArrayList<String> searchEngUzb(String str) {
        String substring = str.substring(0, 1);
        ArrayList<String> arrayList = null;
        if (!abc.contains(substring)) {
            return null;
        }
        if (this.myDataBase != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("eng_");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("select eng, pron, uzb, pron_1, uzb_1, pron_2, uzb_2 from ");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(substring);
            stringBuilder.append(" where lower(eng) = '");
            stringBuilder.append(str);
            stringBuilder.append("'");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("sql::");
            stringBuilder.append(str);
            Log.e("123", stringBuilder.toString());
            try {
                Cursor rawQuery = this.myDataBase.rawQuery(str, null);
                if (rawQuery.moveToNext()) {
                    ArrayList<String> arrayList2 = new ArrayList();
                    try {
                        arrayList2.add(rawQuery.getString(0));
                        arrayList2.add(rawQuery.getString(1));
                        arrayList2.add(rawQuery.getString(2));
                        arrayList2.add(rawQuery.getString(3));
                        arrayList2.add(rawQuery.getString(4));
                        arrayList2.add(rawQuery.getString(5));
                        arrayList2.add(rawQuery.getString(6));
                        arrayList = arrayList2;
                    } catch (Exception unused) {
                        arrayList = arrayList2;
                    }
                }
                rawQuery.close();
            } catch (Exception unused2) {
            }
        }
        return arrayList;
    }

    public ArrayList<String> searchUzbEng(String str) {
        String substring = str.substring(0, 1);
        ArrayList<String> arrayList = null;
        if (!abc.contains(substring)) {
            return null;
        }
        str = str.replace("'", "’");
        if (this.myDataBase != null) {
            StringBuilder stringBuilder;
            if (str.startsWith("c")) {
                substring = "uzb_ch";
            } else if (str.startsWith("g’")) {
                substring = "uzb_g1";
            } else if (str.startsWith("o’")) {
                substring = "uzb_o1";
            } else if (str.startsWith("sh")) {
                substring = "uzb_sh";
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("uzb_");
                stringBuilder.append(substring);
                substring = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("select uzb, eng, eng_1, eng_2 from ");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(substring);
            stringBuilder.append(" where lower(uzb) = '");
            stringBuilder.append(str);
            stringBuilder.append("'");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("sql::");
            stringBuilder.append(str);
            Log.e("123", stringBuilder.toString());
            try {
                Cursor rawQuery = this.myDataBase.rawQuery(str, null);
                if (rawQuery.moveToNext()) {
                    ArrayList<String> arrayList2 = new ArrayList();
                    try {
                        arrayList2.add(rawQuery.getString(0).replace("’", "'"));
                        arrayList2.add(rawQuery.getString(1).replace("’", "'"));
                        arrayList2.add(rawQuery.getString(2).replace("’", "'"));
                        arrayList2.add(rawQuery.getString(3).replace("’", "'"));
                        arrayList = arrayList2;
                    } catch (Exception unused) {
                        arrayList = arrayList2;
                    }
                }
                rawQuery.close();
            } catch (Exception unused2) {
            }
        }
        return arrayList;
    }

    public ArrayList<String> searchEngUzbList(String str) {
        str = str.toLowerCase();
        ArrayList arrayList = new ArrayList();
        String substring = str.substring(0, 1);
        if (abc.contains(substring) && this.myDataBase != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("eng_");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("%");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("select eng from ");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(substring);
            stringBuilder.append(" where lower(eng) like '");
            stringBuilder.append(str);
            stringBuilder.append("' ORDER BY eng COLLATE NOCASE ASC");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("sql::");
            stringBuilder.append(str);
            Log.e("123", stringBuilder.toString());
            try {
                Cursor rawQuery = this.myDataBase.rawQuery(str, null);
                while (rawQuery.moveToNext()) {
                    arrayList.add(rawQuery.getString(0));
                }
                rawQuery.close();
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    public ArrayList<String> searchUzbEngList(String str) {
        ArrayList arrayList = new ArrayList();
        String substring = str.substring(0, 1);
        if (!abc.contains(substring)) {
            return arrayList;
        }
        str = str.toLowerCase().replace("'", "’");
        if (this.myDataBase != null) {
            StringBuilder stringBuilder;
            if (str.startsWith("c")) {
                substring = "uzb_ch";
            } else if (str.startsWith("g’")) {
                substring = "uzb_g1";
            } else if (str.startsWith("o’")) {
                substring = "uzb_o1";
            } else if (str.startsWith("sh")) {
                substring = "uzb_sh";
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("uzb_");
                stringBuilder.append(substring);
                substring = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("%");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("select uzb from ");
            stringBuilder.append(substring);
            substring = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(substring);
            stringBuilder.append(" where lower(uzb) like '");
            stringBuilder.append(str);
            stringBuilder.append("' ORDER BY uzb COLLATE NOCASE ASC");
            str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("sql::");
            stringBuilder.append(str);
            Log.e("123", stringBuilder.toString());
            try {
                Cursor rawQuery = this.myDataBase.rawQuery(str, null);
                while (rawQuery.moveToNext()) {
                    arrayList.add(rawQuery.getString(0).replace("’", "'"));
                }
                rawQuery.close();
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }
}
