package uz.smd.dictionary;

import android.app.Application;
import android.content.Context;
import android.database.SQLException;
import android.graphics.Typeface;
import android.util.Log;
import uz.smd.dictionary.db.MyDBHelper;
import uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.Voice;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainApplication extends Application {
    public static int FILE_COUNT = 4;
    private static final String FLITE_DATA_PATH = Voice.getDataStorageBasePath();
    public static String FONT_NAME = "fonet_tm.ttf";
    public static MyDBHelper dbHelper;
    public Typeface customFont;

    public void onCreate() {
        super.onCreate();
        this.customFont = Typeface.createFromAsset(getApplicationContext().getAssets(), FONT_NAME);
        dbHelper = new MyDBHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException unused) {
            Log.e("BilingualDictionary", "Unable to copy database");
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException unused2) {
            Log.e("BilingualDictionary", "Unable to open database");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FLITE_DATA_PATH);
        stringBuilder.append("cg/eng/usa/male_rms.cg.flitevox");
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(FLITE_DATA_PATH);
        stringBuilder3.append("cg/voices.list");
        String stringBuilder4 = stringBuilder3.toString();
        File file = new File(stringBuilder2);
        File file2 = new File(stringBuilder4);
        if (!file.exists() || !file2.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(FLITE_DATA_PATH);
            stringBuilder.append("cg/");
            copyFileListOfAssertsToDirectory(this, stringBuilder.toString(), "voices.list");
            stringBuilder = new StringBuilder();
            stringBuilder.append(FLITE_DATA_PATH);
            stringBuilder.append("cg/eng/usa/");
            copyAssertsToDirectory(this, stringBuilder.toString(), "male_rms.cg.flitevox");
        }
    }

    public void onTerminate() {
        super.onTerminate();
        if (dbHelper != null) {
            dbHelper.closeDatabase();
        }
    }

    public void copyAssertsToDirectory(Context context, String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            InputStream[] inputStreamArr = new InputStream[FILE_COUNT];
            inputStreamArr[0] = context.getResources().openRawResource(R.raw.male_rms_cg_flitevox_001);
            inputStreamArr[1] = context.getResources().openRawResource(R.raw.male_rms_cg_flitevox_002);
            inputStreamArr[2] = context.getResources().openRawResource(R.raw.male_rms_cg_flitevox_003);
            inputStreamArr[3] = context.getResources().openRawResource(R.raw.male_rms_cg_flitevox_004);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
            byte[] bArr = new byte[1024];
            for (int i = 0; i < FILE_COUNT; i++) {
                while (true) {
                    int read = inputStreamArr[i].read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                inputStreamArr[i].close();
                fileOutputStream.flush();
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFileListOfAssertsToDirectory(Context context, String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            InputStream openRawResource = context.getResources().openRawResource(R.raw.voices_list);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
