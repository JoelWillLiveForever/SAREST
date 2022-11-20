package com.example.sarest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Debug;
import android.os.RecoverySystem;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SAREST_MAIN_ACTIVITY";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        parseJSON();
    }

    private void parseJSON() {
        final String urlTeacher = "http://10.0.2.2:8000/api/v1/teacher_list";
        final String urlSubject = "http://10.0.2.2:8000/api/v1/subject_list";
        final String urlTeacherSubj = "http://10.0.2.2:8000/api/v1/teacher_subj_list";
        final String urlCriterion = "http://10.0.2.2:8000/api/v1/criterion_list";
        final String urlSubjectGroup = "http://10.0.2.2:8000/api/v1/subject_group_list";
        final String urlGrade = "http://10.0.2.2:8000/api/v1/grade_list";
        final String urlGroup = "http://10.0.2.2:8000/api/v1/group_list";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlTeacher, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject json = response.getJSONObject(i);

                                int id = json.getInt("id");
                                Log.i(LOG_TAG, json.toString());
                                mExampleList.add(new ExampleItem(id));
                            }

                            Log.d(LOG_TAG, "SIZE = " + String.valueOf(mExampleList.size()));

                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            Log.d(LOG_TAG, "ADAPTER = " + (mExampleAdapter == null ? "NULL" : "NOT NULL"));

                            mRecyclerView.setAdapter(mExampleAdapter);
                            Log.d(LOG_TAG, "RECYCLER ADAPTER = " + (mRecyclerView.getAdapter() == null ? "NULL" : "NOT NULL"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mRequestQueue.add(request);
    }
}