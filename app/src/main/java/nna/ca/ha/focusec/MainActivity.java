package nna.ca.ha.focusec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import nna.ca.ha.focusec.service.ScreenService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service_intent = new Intent(this, ScreenService.class);
        service_intent.putExtra("state", "on");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            startForegroundService(service_intent);
        }else{
            startService(service_intent);
        }
        Toast.makeText(this, "몇 초 집중함 서비스를 시작합니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

}
