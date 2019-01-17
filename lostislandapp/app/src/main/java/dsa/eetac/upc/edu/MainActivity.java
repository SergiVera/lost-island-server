package dsa.eetac.upc.edu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private GameApi gameApi;
    private RecyclerView listObjects;
    private Button objectsBtn;
    private Button unityButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectsBtn = findViewById(R.id.objectsBtn);
        unityButton = findViewById(R.id.launchgamebtn);
        unityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ConfigurationActivity.class));
            }
        });
        listObjects = (RecyclerView) findViewById(R.id.recyclerView);
        listObjects.setHasFixedSize(true);
        listObjects.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        createGameApi();
    }
    private void createGameApi() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GameApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gameApi = retrofit.create(GameApi.class);
    }
    public void listObjectsClick(View v){
        objectsBtn.setVisibility(View.INVISIBLE);
        unityButton.setVisibility(View.INVISIBLE);
        listObjects.setVisibility(View.VISIBLE);
        gameApi.allObjects().enqueue(objectsCallBack);

    }
    Callback<List<GameObject>> objectsCallBack = new Callback<List<GameObject>>(){

        @Override
        public void onResponse(Call<List<GameObject>> call, Response<List<GameObject>> response) {
            if (response.isSuccessful()) {
                List<GameObject> data = new ArrayList<>();
                data.addAll(response.body());
                listObjects.setAdapter(new AdapterRecycler(data));
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<GameObject>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
