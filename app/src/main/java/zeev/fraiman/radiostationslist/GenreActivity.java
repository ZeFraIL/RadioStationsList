package zeev.fraiman.radiostationslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class GenreActivity extends AppCompatActivity {

    Context context;
    RadioButton rbAll, rbJazz, rbNews;

    Button bReady;

    String genre = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        initComponents();

        genre = "all";


        bReady.setOnClickListener(v -> {
            genre = "all";
            if (rbJazz.isChecked())
                genre = "jazz";
            if (rbNews.isChecked())
                genre = "news";
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("genre", genre);
            startActivity(intent);
        });
    }

    private void initComponents() {
        context = this;
        rbAll = findViewById(R.id.rbAll);
        rbJazz = findViewById(R.id.rbJazz);
        rbNews = findViewById(R.id.rbNews);
        bReady = findViewById(R.id.bReady);
    }
}