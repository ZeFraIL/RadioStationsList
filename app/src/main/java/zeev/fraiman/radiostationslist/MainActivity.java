package zeev.fraiman.radiostationslist;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String RADIO_BROWSER_API = "https://de1.api.radio-browser.info/json/stations/topclick/50";
    TextView tvGenre;
    private ListView listView;
    private Button stopButton, bPlayService, bStopService;
    private ArrayList<RadioStation> radioStations;
    private ArrayList<String> stationNames;
    private ArrayList<String> stationUrls;
    private ArrayAdapter<String> adapter;
    private MediaPlayer mediaPlayer;
    NotificationHelper notificationHelper;
    Intent takeit;
    String what_genre, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

    }

    private void initComponents() {
        takeit = getIntent();
        what_genre = takeit.getStringExtra("genre");

        notificationHelper = new NotificationHelper(this);
        listView = findViewById(R.id.listView);
        stopButton = findViewById(R.id.stopButton);
        stopButton.setClickable(false);
        bPlayService = findViewById(R.id.bPlayService);
        bPlayService.setClickable(false);
        bStopService = findViewById(R.id.bStopService);
        bStopService.setClickable(false);
        tvGenre = findViewById(R.id.tvGenre);
        if (what_genre == null) {
            what_genre = "all";
        }
        tvGenre.setText(what_genre);
        stationNames = new ArrayList<>();
        stationUrls = new ArrayList<>();
        radioStations = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationNames);
        listView.setAdapter(adapter);

        mediaPlayer = new MediaPlayer();

        fetchRadioStations();

        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            url = stationUrls.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to play " + stationNames.get(position) + "?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                stopButton.setClickable(true);
                playRadio(url, stationNames.get(position));
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        stopButton.setOnClickListener(v -> {
            stopRadio();
            bPlayService.setClickable(true);
        });

        bPlayService.setOnClickListener(v -> {
            bStopService.setClickable(true);
            bPlayService.setClickable(false);
            Intent intent = new Intent(this, RadioService.class);
            intent.putExtra("STREAM_URL", url);
            startService(intent);
        });

        bStopService.setOnClickListener(v -> {
            Intent intent = new Intent(this, RadioService.class);
            stopService(intent);
        });
    }

    private void openLoadingActivity(String stationName) {
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("station_name", stationName);
        startActivity(intent);
    }

    private void closeLoadingActivity() {
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("action", "close");
        startActivity(intent);
    }

    private void fetchRadioStations() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(RADIO_BROWSER_API).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "Error loading list", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject station = jsonArray.getJSONObject(i);
                            String name = station.getString("name");
                            String country = station.getString("country");
                            String bitrate = station.getString("bitrate");
                            String url = station.getString("url");
                            String tags = station.getString("tags");
                            String language = station.getString("language");
                            String homepage = station.getString("homepage");
                            String geo_long = station.getString("geo_long");
                            String geo_lat = station.getString("geo_lat");
                            RadioStation radioStation = new RadioStation(name,  url,  bitrate,
                                     language,  homepage,  geo_long,  geo_lat);
                            radioStations.add(radioStation);
                            if ("all".equals(what_genre)) {
                                stationNames.add(name+" ("+country+")");
                                stationUrls.add(url);
                            }
                            else
                                if (tags.contains(what_genre)) {
                                    stationNames.add(name+" ("+country+")");
                                    stationUrls.add(url);
                            }
                        }
                        runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(MainActivity.this,
                                "Data processing error", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this,
                            "Server error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void playRadio(String streamUrl, String stationName) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }

        notificationHelper.showLoadingNotification(stationName);
        openLoadingActivity(stationName);

        try {
            mediaPlayer.setDataSource(streamUrl);
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                notificationHelper.dismissLoadingNotification();
                closeLoadingActivity();
                Toast.makeText(this, "Playing: " + stationName, Toast.LENGTH_LONG).show();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                notificationHelper.dismissLoadingNotification();
                closeLoadingActivity();
                Toast.makeText(this, "Playback error", Toast.LENGTH_SHORT).show();
                return true;
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            notificationHelper.dismissLoadingNotification();
            closeLoadingActivity();
            e.printStackTrace();
            Toast.makeText(this, "Failed to connect to radio station", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRadio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            Toast.makeText(this, "Playback stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
