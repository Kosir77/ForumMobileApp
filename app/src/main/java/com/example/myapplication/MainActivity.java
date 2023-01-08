package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView objave;
    private String url = "https://tilenkelc.eu/Forum/api/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        objave = (TextView) findViewById(R.id.objave);
    }

    public  void prikaziObjave(View view){
        if (view != null){
            JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
            requestQueue.add(request);
        }
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response){
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject object =response.getJSONObject(i);
                    String userId = object.getString("user_id");
                    String title = object.getString("title");
                    String content = object.getString("content");
                    String createdAt = object.getString("created_at");

                    data.add("Created at: " + createdAt + "\n" +
                            "user: " + userId + "\n" +
                            "title: " + title + "\n" +
                            "content: " + content);


                } catch (JSONException e){
                    e.printStackTrace();
                    return;

                }
            }

            objave.setText("");


            for (String row: data){
                String currentText = objave.getText().toString();
                objave.setText(currentText + "\n\n" + row);
            }

        }

    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    public static final String EXTRA_MESSAGE = "com.example.universityapp.MESSAGE";

    public void addUserActivity (View view) {
        Intent intent = new Intent(this,AddUserActivity.class);
        String message = "Dodaj uporabnika v seznam.";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}