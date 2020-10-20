package uz.smd.dictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.providers.BuildConfig;
//import com.google.android.gms.ads.AdRequest.Builder;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import uz.smd.dictionary.edu.cmu.cs.speech.tts.flite.Voice;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener, OnInitListener {
    private static final String ENG_UZB_DICTIONARY = "eng";
    private static final String FLITE_DATA_PATH = Voice.getDataStorageBasePath();
    private static final String UZB_ENG_DICTIONARY = "uzb";
    private Button btnDicOption;
    private ImageButton btnErase;
    private ImageButton btnSearch;
    private ImageButton btnSound;
    private AutoCompleteTextView edtSearch;
//    private AdView mAdView;
    private String mCurDic = ENG_UZB_DICTIONARY;
//    InterstitialAd mInterstitialAd;
    private ArrayList<Voice> mVoices;
    private TextToSpeech tts;
    private TextView tvFirstWord;
    private TextView tvPron;
    private TextView tvPron_1;
    private TextView tvPron_2;
    private TextView tvSecond;
    private TextView tvSecond_1;
    private TextView tvSecond_2;
    private TextView tvTitle;
    private TextView tvTitle_1;
    private TextView tvTitle_2;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        initLayout();
        InitializeAds();
        this.tts = new TextToSpeech(this, this);
        this.tts.setPitch(0.6f);
        this.tts.setSpeechRate(0.8f);
//        this.mAdView = (AdView) findViewById(R.id.adView);
//        this.mAdView.loadAd(new Builder().build());
    }

    private void InitializeAds() {
//        this.mInterstitialAd = new InterstitialAd(this);
//        this.mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
//        this.mInterstitialAd.loadAd(new Builder().build());
    }

    public void showAds() {
//        if (this.mInterstitialAd.isLoaded()) {
//            this.mInterstitialAd.show();
//        }
    }

    public void onPause() {
//        if (this.mAdView != null) {
//            this.mAdView.pause();
//        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
//        if (this.mAdView != null) {
//            this.mAdView.resume();
//        }
    }

    public void onDestroy() {
//        if (this.mAdView != null) {
//            this.mAdView.destroy();
//        }
        if (this.tts != null) {
            this.tts.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    private void initLayout() {
        this.btnDicOption = (Button) findViewById(R.id.btnDicOption);
        this.btnDicOption.setTag(this.mCurDic);
        this.btnDicOption.setOnClickListener(this);
        this.edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch);
        this.edtSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    MainActivity.this.autoComplete(editable.toString());
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().length() > 0) {
                    MainActivity.this.autoComplete(charSequence.toString());
                }
            }
        });
        this.btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        this.btnSearch.setOnClickListener(this);
        this.btnErase = (ImageButton) findViewById(R.id.btnErase);
        this.btnErase.setOnClickListener(this);
        this.btnSound = (ImageButton) findViewById(R.id.btnSound);
        this.btnSound.setOnClickListener(this);
        this.tvFirstWord = (TextView) findViewById(R.id.tvFirstWord);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvPron = (TextView) findViewById(R.id.tvPron);
        this.tvSecond = (TextView) findViewById(R.id.tvSecond);
        this.tvTitle_1 = (TextView) findViewById(R.id.tvTitle_1);
        this.tvPron_1 = (TextView) findViewById(R.id.tvPron_1);
        this.tvSecond_1 = (TextView) findViewById(R.id.tvSecond_1);
        this.tvTitle_2 = (TextView) findViewById(R.id.tvTitle_2);
        this.tvPron_2 = (TextView) findViewById(R.id.tvPron_2);
        this.tvSecond_2 = (TextView) findViewById(R.id.tvSecond_2);
        @SuppressLint("WrongConstant") Typeface create = Typeface.create("Times New Roman", 0);
        this.tvFirstWord.setTypeface(create);
        this.tvTitle.setTypeface(create);
        this.tvSecond.setTypeface(create);
        this.tvTitle_1.setTypeface(create);
        this.tvSecond_1.setTypeface(create);
        this.tvTitle_2.setTypeface(create);
        this.tvSecond_2.setTypeface(create);
        create = ((MainApplication) getApplication()).customFont;
        this.tvPron.setTypeface(create);
        this.tvPron_1.setTypeface(create);
        this.tvPron_2.setTypeface(create);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        if (view.getId() == R.id.btnDicOption) {
            initValues();
            if (view.getTag().toString().equals(UZB_ENG_DICTIONARY)) {
                this.btnDicOption.setTag(ENG_UZB_DICTIONARY);
                this.btnDicOption.setText(R.string.str_eng_uzb);
                this.mCurDic = ENG_UZB_DICTIONARY;
            } else {
                this.btnDicOption.setTag(UZB_ENG_DICTIONARY);
                this.btnDicOption.setText(R.string.str_uzb_eng);
                this.mCurDic = UZB_ENG_DICTIONARY;
            }
            showAds();
        } else if (view.getId() == R.id.btnSearch) {
            String toLowerCase = this.edtSearch.getText().toString().trim().toLowerCase();
            if (toLowerCase.length() == 0) {
                Toast.makeText(this, "Please input search word!", 0).show();
            } else {
                showSearchResult(toLowerCase);
                showAds();
            }
        } else if (view.getId() == R.id.btnErase) {
            initValues();
        } else if (view.getId() == R.id.btnSound) {
            if (this.mCurDic.equals(ENG_UZB_DICTIONARY)) {
                speakOut(this.edtSearch.getText().toString().trim());
            } else {
                Toast.makeText(this, "No English!", 0).show();
            }
        }
    }

    private void initValues() {
        this.edtSearch.setText(BuildConfig.FLAVOR);
        this.tvFirstWord.setText(BuildConfig.FLAVOR);
        this.tvTitle.setVisibility(View.GONE);
        this.tvPron.setVisibility(View.GONE);
        this.tvSecond.setVisibility(View.GONE);
        this.tvPron.setText(BuildConfig.FLAVOR);
        this.tvSecond.setText(BuildConfig.FLAVOR);
        this.tvTitle_1.setVisibility(View.GONE);
        this.tvPron_1.setVisibility(View.GONE);
        this.tvSecond_1.setVisibility(View.GONE);
        this.tvPron_1.setText(BuildConfig.FLAVOR);
        this.tvSecond_1.setText(BuildConfig.FLAVOR);
        this.tvTitle_2.setVisibility(View.GONE);
        this.tvPron_2.setVisibility(View.GONE);
        this.tvSecond_2.setVisibility(View.GONE);
        this.tvPron_2.setText(BuildConfig.FLAVOR);
        this.tvSecond_2.setText(BuildConfig.FLAVOR);
    }

    private void autoComplete(String str) {
        ArrayList searchUzbEngList;
        ArrayList arrayList = new ArrayList();
        if (this.mCurDic.equals(UZB_ENG_DICTIONARY)) {
            searchUzbEngList = MainApplication.dbHelper.searchUzbEngList(str);
        } else {
            searchUzbEngList = MainApplication.dbHelper.searchEngUzbList(str);
        }
        if (searchUzbEngList.size() > 0) {
            this.edtSearch.setAdapter(new ArrayAdapter(this, R.layout.simple_dropdown_item_1line, searchUzbEngList));
            this.edtSearch.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    MainActivity.this.showSearchResult(MainActivity.this.edtSearch.getText().toString().trim());
                }
            });
            this.edtSearch.showDropDown();
        }
    }

    @SuppressLint("WrongConstant")
    private void showSearchResult(String str) {
        ArrayList searchEngUzb;
        String trim;
        String trim2;
        String trim3;
        if (this.mCurDic.equals(ENG_UZB_DICTIONARY)) {
            searchEngUzb = MainApplication.dbHelper.searchEngUzb(str.toLowerCase());
            if (searchEngUzb == null) {
                Toast.makeText(this, "No matching!", 0).show();
                return;
            }
            trim = ((String) searchEngUzb.get(1)).trim();
            trim2 = ((String) searchEngUzb.get(2)).trim();
            String trim4 = ((String) searchEngUzb.get(3)).trim();
            trim3 = ((String) searchEngUzb.get(4)).trim();
            String trim5 = ((String) searchEngUzb.get(5)).trim();
            String trim6 = ((String) searchEngUzb.get(6)).trim();
            TextView textView = this.tvFirstWord;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<b>");
            stringBuilder.append((String) searchEngUzb.get(0));
            stringBuilder.append("</b>");
            textView.setText(Html.fromHtml(stringBuilder.toString()));
            this.tvPron.setVisibility(View.VISIBLE);
            this.tvPron.setText(Html.fromHtml(trim));
            str = checkEnter(trim2);
            this.tvSecond.setVisibility(View.VISIBLE);
            this.tvSecond.setText(Html.fromHtml(str));
            if (trim3.equals(BuildConfig.FLAVOR)) {
                this.tvTitle.setVisibility(View.GONE);
                this.tvTitle_1.setVisibility(View.GONE);
                this.tvPron_1.setVisibility(View.GONE);
                this.tvSecond_1.setVisibility(View.GONE);
            } else {
                this.tvTitle.setVisibility(View.VISIBLE);
                this.tvTitle_1.setVisibility(View.VISIBLE);
                this.tvPron_1.setVisibility(View.VISIBLE);
                this.tvSecond_1.setVisibility(View.VISIBLE);
                this.tvPron_1.setText(Html.fromHtml(trim4));
                this.tvSecond_1.setText(Html.fromHtml(checkEnter(trim3)));
            }
            if (trim6.equals(BuildConfig.FLAVOR)) {
                this.tvTitle_2.setVisibility(View.GONE);
                this.tvPron_2.setVisibility(View.GONE);
                this.tvSecond_2.setVisibility(View.GONE);
                return;
            }
            this.tvTitle_2.setVisibility(View.VISIBLE);
            this.tvPron_2.setVisibility(View.VISIBLE);
            this.tvSecond_2.setVisibility(View.VISIBLE);
            this.tvPron_2.setText(Html.fromHtml(trim5));
            this.tvSecond_2.setText(Html.fromHtml(checkEnter(trim6)));
            return;
        }
        searchEngUzb = MainApplication.dbHelper.searchUzbEng(str.toLowerCase());
        if (searchEngUzb == null) {
            Toast.makeText(this, "No matching!", 0).show();
            return;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("<b>");
        stringBuilder2.append(((String) searchEngUzb.get(0)).trim());
        stringBuilder2.append("</b>");
        trim = stringBuilder2.toString();
        trim3 = ((String) searchEngUzb.get(1)).trim();
        trim2 = ((String) searchEngUzb.get(2)).trim();
        str = ((String) searchEngUzb.get(3)).trim();
        this.tvFirstWord.setText(Html.fromHtml(trim));
        this.tvPron.setVisibility(View.GONE);
        trim = checkEnter(trim3);
        this.tvSecond.setVisibility(View.VISIBLE);
        this.tvSecond.setText(Html.fromHtml(trim));
        if (trim2.equals(BuildConfig.FLAVOR)) {
            this.tvTitle.setVisibility(View.GONE);
            this.tvTitle_1.setVisibility(View.GONE);
            this.tvPron_1.setVisibility(View.GONE);
            this.tvSecond_1.setVisibility(View.GONE);
        } else {
            this.tvTitle.setVisibility(View.VISIBLE);
            this.tvTitle_1.setVisibility(View.VISIBLE);
            this.tvPron_1.setVisibility(View.GONE);
            this.tvSecond_1.setVisibility(View.VISIBLE);
            this.tvSecond_1.setText(Html.fromHtml(checkEnter(trim2)));
        }
        if (str.equals(BuildConfig.FLAVOR)) {
            this.tvTitle_2.setVisibility(View.GONE);
            this.tvPron_2.setVisibility(View.GONE);
            this.tvSecond_2.setVisibility(View.GONE);
            return;
        }
        this.tvTitle_2.setVisibility(View.VISIBLE);
        this.tvPron_2.setVisibility(View.GONE);
        this.tvSecond_2.setVisibility(View.VISIBLE);
        this.tvSecond_2.setText(Html.fromHtml(checkEnter(str)));
    }

    private String checkEnter(String str) {
        str = str.replace("<br><br>", "<br>");
        return str.startsWith("<br>") ? str.substring(4) : str;
    }

    @SuppressLint("WrongConstant")
    public void onInit(int i) {
        if (i == 0) {
            i = this.tts.setLanguage(Locale.US);
            if (i == -1 || i == -2) {
                Toast.makeText(this, "This Language is not supported", 0).show();
                return;
            } else {
                speakOut(BuildConfig.FLAVOR);
                return;
            }
        }
        Toast.makeText(this, "Initilization Failed!", 0).show();
    }

    private void speakOut(String str) {
        this.tts.speak(str, 0, null);
    }
}
