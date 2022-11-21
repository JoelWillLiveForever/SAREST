package com.example.sarest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.sarest.API.models.*;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SAREST_MAIN_ACTIVITY";

//     private RecyclerView mRecyclerView;
//     private ExampleAdapter mExampleAdapter;
//     private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private ArrayList<Criterion> mCriterionList;
    private ArrayList<Grade> mGradeList;
    private ArrayList<Group> mGroupList;
    private ArrayList<Subject> mSubjeёctList;
    private ArrayList<Subject_Group> mSubjectGroupList;
    private ArrayList<Teacher> mTeacherList;
    private ArrayList<Teacher_Subject> mTeacherSubjectList;
    private ArrayList<User> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//         mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

//         mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        mTeacherList = new ArrayList<>();
        parseJSON();

        for (Teacher t: mTeacherList)
        {
            Log.d(LOG_TAG, "TEACHER: user = " + t.user);
        }
    }

    private void parseJSON() {
        String[] urls = {
                "teacher_list"
//             "subject_list",
//             "teacher_subj_list",
//             "criterion_list",
//             "subject_group_list",
//             "grade_list",
//             "group_list"
        };

        for (String url : urls) {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:8000/api/v1/" + url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int j = 0; j < response.length(); j++) {
                                    JSONObject json = response.getJSONObject(j);

                                    switch (url) {
                                        case "teacher_list":
                                            int user = json.getInt("user");

                                            mTeacherList.add(new Teacher(user));
                                            break;
                                        case "subject_list":

                                            break;
                                        case "teacher_subj_list":

                                            break;
                                        case "criterion_list":

                                            break;
                                        case "subject_group_list":

                                            break;
                                        case "grade_list":

                                            break;
                                        case "group_list":

                                            break;
                                        default:
                                            // error
                                            throw new Exception("Invalid API request");
                                    }

                                    Log.i(LOG_TAG, json.toString());
                                }

                                //                             mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                                //                             Log.d(LOG_TAG, "ADAPTER = " + (mExampleAdapter == null ? "NULL" : "NOT NULL"));
                                //
                                //                             mRecyclerView.setAdapter(mExampleAdapter);
                                //                             Log.d(LOG_TAG, "RECYCLER ADAPTER = " + (mRecyclerView.getAdapter() == null ? "NULL" : "NOT NULL"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
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
}