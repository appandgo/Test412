package brian.com.test412;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {

    private Button btnOk;
    private TextView textViewAccueil;
    private EditText input_login;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOk = (Button) findViewById(R.id.ok_button);
        textViewAccueil = (TextView) findViewById(R.id.text_Accueil);
        input_login = (EditText) findViewById(R.id.login_input);
        info = (TextView) findViewById(R.id.info);

        this.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent Rss = new Intent(Main.this, Rss.class);
                Rss.putExtra("login", input_login.getText().toString());
                startActivity(Rss);
            }
        });

        this.input_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                input_login.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings) ? true : super.onOptionsItemSelected(item);
    }
}