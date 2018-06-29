package jp.ac.titech.itpro.sdl.xcolorname;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import jp.ac.titech.itpro.sdl.xcolorname.color.ColorSimilarity;
import jp.ac.titech.itpro.sdl.xcolorname.color.HtmlNameSet;
import jp.ac.titech.itpro.sdl.xcolorname.color.JisNameSet;
import jp.ac.titech.itpro.sdl.xcolorname.color.MyFilterableColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.MyNameColor;
import jp.ac.titech.itpro.sdl.xcolorname.color.NameSet;
import jp.ac.titech.itpro.sdl.xcolorname.color.RgbColorSimilarity;
import jp.ac.titech.itpro.sdl.xcolorname.color.RgbrColorSimilarity;
import jp.ac.titech.itpro.sdl.xcolorname.color.WasNameSet;
import jp.ac.titech.itpro.sdl.xcolorname.color.YosNameSet;

public class EditActivity extends AppCompatActivity {
    private final static String TAG = "EditActivity";

    private final static int COLOR_SIMILARITY_RGB = 0;
    private final static int COLOR_SIMILARITY_RGBR = 1;
    private final static int COLOR_SIMILARITY_XYZ = 2; // not implemented yet
    private final static int COLOR_SIMILARITY_CIEDE = 3; // not implemented yet
    private final static int COLOR_NAMESET_JIS = 0;
    private final static int COLOR_NAMESET_WAS = 1;
    private final static int COLOR_NAMESET_YOS = 2;
    private final static int COLOR_NAMESET_HTML = 3;

    private View pickedColorView;
    private MyFilterableColor pickedColor;

    private int colorSimilarityType;
    private int colorNamesetType;

    private ListView resultView;
    ResultAdapter resultAdapter;
    List<ResultViewItem> resultItems;

    private TextView hueTextView, saturationTextView, valueTextView;
    private SeekBar hueSeekBar, saturationSeekBar, valueSeekBar;
    private final int biasHueSeekBar = 15;
    private final int biasSaturationSeekBar = 15;
    private final int biasValueSeekBar = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_edit);

        updateTitleWithCurrentTypes();

        Intent intent = getIntent();

        pickedColor = new MyFilterableColor(intent.getIntExtra(getString(R.string.key_picked_color), -1));
        pickedColorView = findViewById(R.id.picked_color_view);
        pickedColorView.setBackgroundColor(pickedColor.getIntColor());

        colorSimilarityType = COLOR_SIMILARITY_RGBR; // default is rgbr
        colorNamesetType = COLOR_NAMESET_JIS; // default is JIS

        resultView = findViewById(R.id.result_view);
        resultItems = new ArrayList<>();
        resultAdapter = new ResultAdapter(this, R.layout.resultview_item, resultItems);
        resultView.setAdapter(resultAdapter);
        updateResultView(); // update here!!

        hueTextView = findViewById(R.id.hue_textview);
        saturationTextView = findViewById(R.id.saturation_textview);
        valueTextView = findViewById(R.id.value_textview);
        hueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hueTextview.onClick");
                hueSeekBar.setProgress(biasHueSeekBar); // reset Hue
            }
        });
        saturationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saturationTextview.onClick");
                saturationSeekBar.setProgress(biasSaturationSeekBar); // reset Saturation
            }
        });
        valueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "valueTextview.onClick");
                valueSeekBar.setProgress(biasValueSeekBar); // reset Value
            }
        });

        hueSeekBar = findViewById(R.id.hue_seekbar);
        saturationSeekBar = findViewById(R.id.saturation_seekbar);
        valueSeekBar = findViewById(R.id.value_seekbar);
        hueSeekBar.setMax(biasHueSeekBar * 2);
        saturationSeekBar.setMax(biasSaturationSeekBar * 2);
        valueSeekBar.setMax(biasValueSeekBar * 2);
        hueSeekBar.setProgress(biasHueSeekBar);
        saturationSeekBar.setProgress(biasSaturationSeekBar);
        valueSeekBar.setProgress(biasValueSeekBar);
        hueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "hueSeekBar.onProgressChanged");
                pickedColor.filterHue(progress - biasHueSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        saturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "saturationSeekBar.onProgressChanged");
                pickedColor.filterSaturation(progress - biasSaturationSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "valueSeekBar.onProgressChanged");
                pickedColor.filterValue(progress - biasValueSeekBar);
                pickedColorView.setBackgroundColor(pickedColor.getIntColor());
                updateResultView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch(item.getItemId()){
            case R.id.item_nameset_jis:
                colorNamesetType = COLOR_NAMESET_JIS;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_nameset_was:
                colorNamesetType = COLOR_NAMESET_WAS;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_nameset_yos:
                colorNamesetType = COLOR_NAMESET_YOS;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_nameset_html:
                colorNamesetType = COLOR_NAMESET_HTML;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_diffmethod_rgb:
                colorSimilarityType = COLOR_SIMILARITY_RGB;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_diffmethod_rgbr:
                colorSimilarityType = COLOR_SIMILARITY_RGBR;
                updateResultView();
                updateTitleWithCurrentTypes();
                break;
            case R.id.item_diffmethod_xyz:
                // after create XyzColorSimilarity class TODO
                break;
            case R.id.item_diffmethod_ciede:
                // after create CiedeColorSimilarity class TODO
                break;
        }
        return true;
    }


    private void updateResultView(){
        NameSet nameSet = null;
        switch(colorNamesetType){
            case COLOR_NAMESET_JIS: nameSet = new JisNameSet(); break;
            case COLOR_NAMESET_WAS: nameSet = new WasNameSet(); break;
            case COLOR_NAMESET_YOS: nameSet = new YosNameSet(); break;
            case COLOR_NAMESET_HTML: nameSet = new HtmlNameSet(); break;
        }

        ColorSimilarity colorSimilarity = null;
        switch(colorSimilarityType){
            case COLOR_SIMILARITY_RGB: colorSimilarity = new RgbColorSimilarity(); break;
            case COLOR_SIMILARITY_RGBR: colorSimilarity = new RgbrColorSimilarity(); break;
        }

        List<MyNameColor> similarColors = colorSimilarity.getSimilarColor(pickedColor, nameSet);

        resultItems.clear();
        for(MyNameColor similarColor: similarColors){
            int intColor = similarColor.getIntColor();
            String colorName = similarColor.getColorName();
            String colorRgb = similarColor.getColorRgb();
            resultItems.add(new ResultViewItem(intColor, colorName, colorRgb));
        }

        resultAdapter.notifyDataSetChanged();
    }


    private void updateTitleWithCurrentTypes() {
        String baseTitle = getString(R.string.app_name);
        String namesetLabel = getString(R.string.item_nameset_label);
        String diffmethodLabel = getString(R.string.item_diffmethod_label);

        String nameset = "";
        switch(colorNamesetType){
            case COLOR_NAMESET_JIS: nameset = getString(R.string.item_nameset_jis_label); break;
            case COLOR_NAMESET_WAS: nameset = getString(R.string.item_nameset_was_label); break;
            case COLOR_NAMESET_YOS: nameset = getString(R.string.item_nameset_yos_label); break;
            case COLOR_NAMESET_HTML: nameset = getString(R.string.item_nameset_html_label); break;
        }

        String similarity = "";
        switch(colorSimilarityType){
            case COLOR_SIMILARITY_RGB:
                similarity = getString(R.string.item_diffmethod_rgb_label); break;
            case COLOR_SIMILARITY_RGBR:
                similarity = getString(R.string.item_diffmethod_rgbr_label); break;
            case COLOR_SIMILARITY_XYZ:
                similarity = getString(R.string.item_diffmethod_xyz_label); break;
            case COLOR_SIMILARITY_CIEDE:
                similarity = getString(R.string.item_diffmethod_ciede_label); break;
        }

        setTitle(baseTitle + " (" + namesetLabel + ": " + nameset + " / " + diffmethodLabel + ": " + similarity + ")");
    }
}
