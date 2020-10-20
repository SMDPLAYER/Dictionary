package uz.smd.dictionary;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.NativeFliteTTS;
import uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.NativeFliteTTS.SynthReadyCallback;

@TargetApi(14)
public class FliteTtsService extends TextToSpeechService {
    private static final String DEFAULT_COUNTRY = "USA";
    private static final String DEFAULT_LANGUAGE = "eng";
    private static final String DEFAULT_VARIANT = "male,rms";
    private static final String LOG_TAG;
    private Object mAvailableVoices;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            FliteTtsService.this.initializeFliteEngine();
        }
    };
    private SynthesisCallback mCallback;
    private String mCountry = DEFAULT_COUNTRY;
    private NativeFliteTTS mEngine;
    private String mLanguage = DEFAULT_LANGUAGE;
    private final SynthReadyCallback mSynthCallback = new SynthReadyCallback() {
        public void onSynthDataReady(byte[] bArr) {
            if (bArr == null || bArr.length == 0) {
                onSynthDataComplete();
                return;
            }
            int maxBufferSize = FliteTtsService.this.mCallback.getMaxBufferSize();
            int i = 0;
            while (i < bArr.length) {
                int min = Math.min(maxBufferSize, bArr.length - i);
                FliteTtsService.this.mCallback.audioAvailable(bArr, i, min);
                i += min;
            }
        }

        public void onSynthDataComplete() {
            FliteTtsService.this.mCallback.done();
        }
    };
    private String mVariant = DEFAULT_VARIANT;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flite_Java_");
        stringBuilder.append(FliteTtsService.class.getSimpleName());
        LOG_TAG = stringBuilder.toString();
    }

    public void onCreate() {
        initializeFliteEngine();
        super.onCreate();
    }

    private void initializeFliteEngine() {
        if (this.mEngine != null) {
            this.mEngine.stop();
            this.mEngine = null;
        }
        this.mEngine = new NativeFliteTTS(this, this.mSynthCallback);
    }

    /* Access modifiers changed, original: protected */
    public String[] onGetLanguage() {
        Log.v(LOG_TAG, "onGetLanguage");
        return new String[]{this.mLanguage, this.mCountry, this.mVariant};
    }

    /* Access modifiers changed, original: protected */
    public int onIsLanguageAvailable(String str, String str2, String str3) {
        Log.v(LOG_TAG, "onIsLanguageAvailable");
        return this.mEngine.isLanguageAvailable(str, str2, str3);
    }

    /* Access modifiers changed, original: protected */
    public int onLoadLanguage(String str, String str2, String str3) {
        Log.v(LOG_TAG, "onLoadLanguage");
        return this.mEngine.isLanguageAvailable(str, str2, str3);
    }

    /* Access modifiers changed, original: protected */
    public void onStop() {
        Log.v(LOG_TAG, "onStop");
        this.mEngine.stop();
    }

    /* Access modifiers changed, original: protected|declared_synchronized */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003f A:{SYNTHETIC, Splitter:B:15:0x003f} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0036  */
    public synchronized void onSynthesizeText(android.speech.tts.SynthesisRequest r6, SynthesisCallback r7) {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = LOG_TAG;	 Catch:{ all -> 0x0050 }
        r1 = "onSynthesize";
        android.util.Log.v(r0, r1);	 Catch:{ all -> 0x0050 }
        r0 = r6.getLanguage();	 Catch:{ all -> 0x0050 }
        r1 = r6.getCountry();	 Catch:{ all -> 0x0050 }
        r2 = r6.getVariant();	 Catch:{ all -> 0x0050 }
        r6 = r6.getText();	 Catch:{ all -> 0x0050 }
        r3 = r5.mLanguage;	 Catch:{ all -> 0x0050 }
        r4 = 1;
        if (r3 != r0) goto L_0x0028;
    L_0x001d:
        r3 = r5.mCountry;	 Catch:{ all -> 0x0050 }
        if (r3 != r1) goto L_0x0028;
    L_0x0021:
        r3 = r5.mVariant;	 Catch:{ all -> 0x0050 }
        if (r3 == r2) goto L_0x0026;
    L_0x0025:
        goto L_0x0028;
    L_0x0026:
        r3 = 1;
        goto L_0x0034;
    L_0x0028:
        r3 = r5.mEngine;	 Catch:{ all -> 0x0050 }
        r3 = r3.setLanguage(r0, r1, r2);	 Catch:{ all -> 0x0050 }
        r5.mLanguage = r0;	 Catch:{ all -> 0x0050 }
        r5.mCountry = r1;	 Catch:{ all -> 0x0050 }
        r5.mVariant = r2;	 Catch:{ all -> 0x0050 }
    L_0x0034:
        if (r3 != 0) goto L_0x003f;
    L_0x0036:
        r6 = LOG_TAG;	 Catch:{ all -> 0x0050 }
        r7 = "Could not set language for synthesis";
        android.util.Log.e(r6, r7);	 Catch:{ all -> 0x0050 }
        monitor-exit(r5);
        return;
    L_0x003f:
        r5.mCallback = r7;	 Catch:{ all -> 0x0050 }
        r7 = r5.mCallback;	 Catch:{ all -> 0x0050 }
        r0 = 16000; // 0x3e80 float:2.2421E-41 double:7.905E-320;
        r1 = 2;
        r7.start(r0, r1, r4);	 Catch:{ all -> 0x0050 }
        r7 = r5.mEngine;	 Catch:{ all -> 0x0050 }
        r7.synthesize(r6);	 Catch:{ all -> 0x0050 }
        monitor-exit(r5);
        return;
    L_0x0050:
        r6 = move-exception;
        monitor-exit(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hy.enguzb.dictionary.FliteTtsService.onSynthesizeText(android.speech.tts.SynthesisRequest, android.speech.tts.SynthesisCallback):void");
    }
}
