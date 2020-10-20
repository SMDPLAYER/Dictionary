package uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;



public class SettingsProvider extends ContentProvider {

    private class SettingsCursor extends MatrixCursor {
        private String settings;

        public int getCount() {
            return 1;
        }

        public SettingsCursor(String[] strArr) {
            super(strArr);
        }

        public void putSettings(String str) {
            this.settings = str;
        }

        public String getString(int i) {
            return this.settings;
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        SettingsCursor settingsCursor = new SettingsCursor(new String[]{BuildConfig.FLAVOR, BuildConfig.FLAVOR});
        settingsCursor.putSettings(externalStorageDirectory.getPath());
        return settingsCursor;
    }
}
