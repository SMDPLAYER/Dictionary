package uz.smd.dictionary.edu.cmu.cs.speech.tts.flite;

import android.content.Context;
import android.util.Log;
import java.io.File;

public class NativeFliteTTS {
    private static final String LOG_TAG;
    private final SynthReadyCallback mCallback;
    private final Context mContext;
    private final String mDatapath = new File(Voice.getDataStorageBasePath()).getParent();
    private boolean mInitialized = false;
    private int mNativeData;

    public interface SynthReadyCallback {
        void onSynthDataComplete();

        void onSynthDataReady(byte[] bArr);
    }

    private static final native boolean nativeClassInit();

    private final native boolean nativeCreate(String str);

    private final native boolean nativeDestroy();

    private final native float nativeGetBenchmark();

    private final native int nativeIsLanguageAvailable(String str, String str2, String str3);

    private final native boolean nativeSetLanguage(String str, String str2, String str3);

    private final native boolean nativeStop();

    private final native boolean nativeSynthesize(String str);

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flite_Java_");
        stringBuilder.append(NativeFliteTTS.class.getSimpleName());
        LOG_TAG = stringBuilder.toString();
        System.loadLibrary("ttsflite");
        nativeClassInit();
    }

    public NativeFliteTTS(Context context, SynthReadyCallback synthReadyCallback) {
        this.mContext = context;
        this.mCallback = synthReadyCallback;
        attemptInit();
    }

    /* Access modifiers changed, original: protected */
    public void finalize() {
        nativeDestroy();
    }

    public int isLanguageAvailable(String str, String str2, String str3) {
        return nativeIsLanguageAvailable(str, str2, str3);
    }

    public boolean setLanguage(String str, String str2, String str3) {
        attemptInit();
        return nativeSetLanguage(str, str2, str3);
    }

    public void synthesize(String str) {
        nativeSynthesize(str);
    }

    public void stop() {
        nativeStop();
    }

    public float getNativeBenchmark() {
        return nativeGetBenchmark();
    }

    private void nativeSynthCallback(byte[] bArr) {
        if (this.mCallback != null) {
            if (bArr == null) {
                this.mCallback.onSynthDataComplete();
            } else {
                this.mCallback.onSynthDataReady(bArr);
            }
        }
    }

    private void attemptInit() {
        if (!this.mInitialized) {
            if (nativeCreate(this.mDatapath)) {
                Log.i(LOG_TAG, "Initialized Flite");
                this.mInitialized = true;
                return;
            }
            Log.e(LOG_TAG, "Failed to initialize flite library");
        }
    }
}
