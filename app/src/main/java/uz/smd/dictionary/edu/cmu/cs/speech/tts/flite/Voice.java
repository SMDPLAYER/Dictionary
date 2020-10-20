package uz.smd.dictionary.edu.cmu.cs.speech.tts.flite;

import android.os.Environment;
import java.util.Locale;

public class Voice {
    private static final String FLITE_DATA_PATH;
    private static final String LOG_TAG;
    private static final String VOICE_BASE_URL = "http://tts.speech.cs.cmu.edu/android/vox-flite-1.5.6/";
    private boolean mIsValidVoice;
    private boolean mIsVoiceAvailable;
    private String mVoiceCountry;
    private String mVoiceLanguage;
    private String mVoiceMD5;
    private String mVoiceName;
    private String mVoicePath;
    private String mVoiceVariant;

    public static String getDownloadURLBasePath() {
        return VOICE_BASE_URL;
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flite_Java_");
        stringBuilder.append(Voice.class.getSimpleName());
        LOG_TAG = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/flite-data/");
        FLITE_DATA_PATH = stringBuilder.toString();
    }

    public static String getDataStorageBasePath() {
        return FLITE_DATA_PATH;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0063  */
    public Voice(String r6) {
        /*
        r5 = this;
        r5.<init>();
        r0 = "\t";
        r0 = r6.split(r0);
        r1 = r0.length;
        r2 = 2;
        r3 = 1;
        r4 = 0;
        if (r1 == r2) goto L_0x0026;
    L_0x000f:
        r0 = LOG_TAG;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Voice line could not be read: ";
        r1.append(r2);
        r1.append(r6);
        r6 = r1.toString();
        android.util.Log.e(r0, r6);
        goto L_0x0052;
    L_0x0026:
        r6 = r0[r4];
        r5.mVoiceName = r6;
        r6 = r0[r3];
        r5.mVoiceMD5 = r6;
        r6 = r5.mVoiceName;
        r0 = "-";
        r6 = r6.split(r0);
        r0 = r6.length;
        r1 = 3;
        if (r0 == r1) goto L_0x0054;
    L_0x003a:
        r6 = LOG_TAG;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "Incorrect voicename:";
        r0.append(r1);
        r1 = r5.mVoiceName;
        r0.append(r1);
        r0 = r0.toString();
        android.util.Log.e(r6, r0);
    L_0x0052:
        r6 = 0;
        goto L_0x0061;
    L_0x0054:
        r0 = r6[r4];
        r5.mVoiceLanguage = r0;
        r0 = r6[r3];
        r5.mVoiceCountry = r0;
        r6 = r6[r2];
        r5.mVoiceVariant = r6;
        r6 = 1;
    L_0x0061:
        if (r6 == 0) goto L_0x009e;
    L_0x0063:
        r5.mIsValidVoice = r3;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r0 = getDataStorageBasePath();
        r6.append(r0);
        r0 = "cg/";
        r6.append(r0);
        r0 = r5.mVoiceLanguage;
        r6.append(r0);
        r0 = "/";
        r6.append(r0);
        r0 = r5.mVoiceCountry;
        r6.append(r0);
        r0 = "/";
        r6.append(r0);
        r0 = r5.mVoiceVariant;
        r6.append(r0);
        r0 = ".cg.flitevox";
        r6.append(r0);
        r6 = r6.toString();
        r5.mVoicePath = r6;
        r5.checkVoiceAvailability();
        goto L_0x00a0;
    L_0x009e:
        r5.mIsValidVoice = r4;
    L_0x00a0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.cmu.cs.speech.tts.flite.Voice.<init>(java.lang.String):void");
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0093 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:26:?, code skipped:
            r0 = LOG_TAG;
            r1 = new java.lang.StringBuilder();
            r1.append("Could not read voice file: ");
            r1.append(r6.mVoicePath);
            android.util.Log.e(r0, r1.toString());
     */
    /* JADX WARNING: Missing block: B:28:0x00ae, code skipped:
            return;
     */
    /* JADX WARNING: Missing block: B:30:?, code skipped:
            r2.close();
     */
    private void checkVoiceAvailability() {
        /*
        r6 = this;
        r0 = LOG_TAG;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Checking for Voice Available: ";
        r1.append(r2);
        r2 = r6.mVoiceName;
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
        r0 = 0;
        r6.mIsVoiceAvailable = r0;
        r1 = "MD5";
        r1 = java.security.MessageDigest.getInstance(r1);	 Catch:{ NoSuchAlgorithmException -> 0x00cc }
        r2 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x00b3 }
        r3 = r6.mVoicePath;	 Catch:{ FileNotFoundException -> 0x00b3 }
        r2.<init>(r3);	 Catch:{ FileNotFoundException -> 0x00b3 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r3 = new byte[r3];
    L_0x002c:
        r4 = r2.read(r3);	 Catch:{ IOException -> 0x0093 }
        r5 = -1;
        if (r4 == r5) goto L_0x0037;
    L_0x0033:
        r1.update(r3, r0, r4);	 Catch:{ IOException -> 0x0093 }
        goto L_0x002c;
    L_0x0037:
        r2.close();	 Catch:{ IOException -> 0x003a }
    L_0x003a:
        r1 = r1.digest();
        r2 = new java.lang.StringBuffer;
        r2.<init>();
    L_0x0043:
        r3 = r1.length;
        r4 = 1;
        if (r0 >= r3) goto L_0x005d;
    L_0x0047:
        r3 = r1[r0];
        r3 = r3 & 255;
        r3 = r3 + 256;
        r5 = 16;
        r3 = java.lang.Integer.toString(r3, r5);
        r3 = r3.substring(r4);
        r2.append(r3);
        r0 = r0 + 1;
        goto L_0x0043;
    L_0x005d:
        r0 = r2.toString();
        r1 = r6.mVoiceMD5;
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x006c;
    L_0x0069:
        r6.mIsVoiceAvailable = r4;
        return;
    L_0x006c:
        r0 = LOG_TAG;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "Voice file found, but MD5 sum incorrect. Found";
        r1.append(r3);
        r2 = r2.toString();
        r1.append(r2);
        r2 = ". Expected: ";
        r1.append(r2);
        r2 = r6.mVoiceMD5;
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.e(r0, r1);
        return;
    L_0x0091:
        r0 = move-exception;
        goto L_0x00af;
    L_0x0093:
        r0 = LOG_TAG;	 Catch:{ all -> 0x0091 }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0091 }
        r1.<init>();	 Catch:{ all -> 0x0091 }
        r3 = "Could not read voice file: ";
        r1.append(r3);	 Catch:{ all -> 0x0091 }
        r3 = r6.mVoicePath;	 Catch:{ all -> 0x0091 }
        r1.append(r3);	 Catch:{ all -> 0x0091 }
        r1 = r1.toString();	 Catch:{ all -> 0x0091 }
        android.util.Log.e(r0, r1);	 Catch:{ all -> 0x0091 }
        r2.close();	 Catch:{ IOException -> 0x00ae }
    L_0x00ae:
        return;
    L_0x00af:
        r2.close();	 Catch:{ IOException -> 0x00b2 }
    L_0x00b2:
        throw r0;
    L_0x00b3:
        r0 = LOG_TAG;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Voice File not found: ";
        r1.append(r2);
        r2 = r6.mVoicePath;
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.e(r0, r1);
        return;
    L_0x00cc:
        r0 = LOG_TAG;
        r1 = "MD5 could not be computed";
        android.util.Log.e(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.cmu.cs.speech.tts.flite.Voice.checkVoiceAvailability():void");
    }

    public boolean isValid() {
        return this.mIsValidVoice;
    }

    public boolean isAvailable() {
        return this.mIsVoiceAvailable;
    }

    public String getName() {
        return this.mVoiceName;
    }

    public String getDisplayName() {
        Locale locale = new Locale(this.mVoiceLanguage, this.mVoiceCountry, this.mVoiceVariant);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getDisplayLanguage());
        stringBuilder.append("(");
        stringBuilder.append(locale.getDisplayCountry());
        stringBuilder.append(",");
        stringBuilder.append(locale.getVariant());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String getVariant() {
        return this.mVoiceVariant;
    }

    public String getDisplayLanguage() {
        Locale locale = new Locale(this.mVoiceLanguage, this.mVoiceCountry, this.mVoiceVariant);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getDisplayLanguage());
        stringBuilder.append(" (");
        stringBuilder.append(locale.getDisplayCountry());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String getPath() {
        return this.mVoicePath;
    }

    public Locale getLocale() {
        return new Locale(this.mVoiceLanguage, this.mVoiceCountry, this.mVoiceVariant);
    }
}
