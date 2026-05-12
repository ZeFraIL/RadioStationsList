package zeev.fraiman.radiostationslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        if ("close".equals(getIntent().getStringExtra("action"))) {
            finish();
            return;
        }

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                runOnUiThread(this::finish);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        if ("close".equals(intent.getStringExtra("action"))) {
            finish();
        }
    }
}
