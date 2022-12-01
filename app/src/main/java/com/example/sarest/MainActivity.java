package com.example.sarest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.sarest.API.models.*;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SAREST_MAIN_ACTIVITY";

    private RecyclerView mRecyclerView;
    private CardsAdapter mCardsAdapter;
    private RequestQueue mRequestQueue;

    private List<Criterion> mCriterionList;
    private List<Grade> mGradeList;
    private List<Group> mGroupList;
    private List<Subject> mSubjectList;
    private List<Subject_Group> mSubjectGroupList;
    private List<Teacher> mTeacherList;
    private List<Teacher_Subject> mTeacherSubjectList;
    private List<User> mUserList;

    private List<Card> mCardList;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.idPB);

        parseJSON();
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        mCardsAdapter = new CardsAdapter(MainActivity.this, mCardList);

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mCardsAdapter);
    }

    private void parseJSON() {
        String[] urls = {
                "user_list",
                "teacher_list",
                "subject_list",
                "teacher_subj_list",
                "subject_group_list",
                "criterion_list",
                "grade_list",
                "group_list"
        };

        String IP = "10.0.2.2";
        String port = "8000";

        for (String url : urls) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://" + IP + ":" + port + "/api/v1/" + url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if (mUserList == null)
                                    mUserList = new ArrayList<>();
                                if (mTeacherList == null)
                                    mTeacherList = new ArrayList<>();
                                if (mSubjectList == null)
                                    mSubjectList = new ArrayList<>();
                                if (mTeacherSubjectList == null)
                                    mTeacherSubjectList = new ArrayList<>();
                                if (mSubjectGroupList == null)
                                    mSubjectGroupList = new ArrayList<>();
                                if (mCriterionList == null)
                                    mCriterionList = new ArrayList<>();
                                if (mGradeList == null)
                                    mGradeList = new ArrayList<>();
                                if (mGroupList == null)
                                    mGroupList = new ArrayList<>();
                                if (mCardList == null)
                                    mCardList = new ArrayList<>();

                                switch (url) {
                                    case "user_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int userId = json.getInt("id");
                                            String email = json.getString("email");
                                            String surname = json.getString("surname");
                                            String name = json.getString("name");
                                            String lastname = json.getString("lastname");

                                            mUserList.add(new User(userId, email, surname, name, lastname));

                                            String msg = "email: " + email + " surname: " + surname + " name: " + name + " lastname: " + lastname;
                                            Log.i(LOG_TAG, "[user_list]: " + msg);
                                        }
                                        break;
                                    case "teacher_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int user = json.getInt("user");

                                            mTeacherList.add(new Teacher(user));

                                            String msg = "user: " + user;
                                            Log.i(LOG_TAG, "[teacher_list]: " + msg);
                                        }
                                        break;
                                    case "subject_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int subjId = json.getInt("id");
                                            String subjName = json.getString("name");

                                            mSubjectList.add(new Subject(subjId, subjName));

                                            String msg = "subjId: " + subjId + " subjName: " + subjName;
                                            Log.i(LOG_TAG, "[subject_list]: " + msg);
                                        }
                                        break;
                                    case "teacher_subj_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int teacherSubjId = json.getInt("id");
                                            int TSteacherId = json.getInt("teacher");
                                            int TSsubjectId = json.getInt("subject");

                                            mTeacherSubjectList.add(new Teacher_Subject(teacherSubjId, TSteacherId, TSsubjectId));

                                            String msg = "teacherSubjId: " + teacherSubjId + " TSteacherId: " + TSteacherId + " TSsubjectId: " + TSsubjectId;
                                            Log.i(LOG_TAG, "[teacher_subj_list]: " + msg);
                                        }
                                        break;
                                    case "subject_group_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);
                                            int subjGroupId = json.getInt("id");
                                            int SGsubjectId = json.getInt("subject");
                                            int SGgroupId = json.getInt("group");

                                            mSubjectGroupList.add(new Subject_Group(subjGroupId, SGsubjectId, SGgroupId));

                                            String msg = "subjGroupId: " + subjGroupId + " SGsubjectId: " + SGsubjectId + " SGgroupId: " + SGgroupId;
                                            Log.i(LOG_TAG, "[subject_group_list]: " + msg);
                                        }
                                        break;
                                    case "criterion_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int critId = json.getInt("id");
                                            String critName = json.getString("name");

                                            mCriterionList.add(new Criterion(critId, critName));

                                            String msg = "critId: " + critId + " critName: " + critName;
                                            Log.i(LOG_TAG, "[criterion_list]: " + msg);
                                        }
                                        break;
                                    case "grade_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int gradeId = json.getInt("id");
                                            int gradeTSId = json.getInt("teacher_subject");
                                            int gradeCriterionId = json.getInt("criterion");
                                            int grade = json.getInt("grade");

                                            mGradeList.add(new Grade(gradeId, gradeTSId, gradeCriterionId, grade));

                                            String msg = "gradeId: " + gradeId + " gradeTSId: " + gradeTSId + " gradeCriterionId: " + gradeCriterionId + " grade: " + grade;
                                            Log.i(LOG_TAG, "[grade_list]: " + msg);
                                        }
                                        break;
                                    case "group_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int groupId = json.getInt("id");
                                            String groupName = json.getString("name");

                                            mGroupList.add(new Group(groupId, groupName));

                                            String msg = "groupId: " + groupId + " groupName: " + groupName;
                                            Log.i(LOG_TAG, "[group_list]: " + msg);
                                        }

//                                        // это последний запрос
////                                        do {
//                                            getCards();
//                                            buildRecyclerView();
////                                        } while (mCardList.size() != 0 && mCardsAdapter.getItemCount() == 0);
//
//                                        mProgressBar.setVisibility(View.GONE);
//                                        mRecyclerView.setVisibility(View.VISIBLE);
//
//                                        Toast.makeText(getApplicationContext(), "Success get JSON data!", Toast.LENGTH_SHORT).show();

                                        Handler handler = new Handler();
                                        handler.postDelayed(() -> {
                                            getCards();

                                            buildRecyclerView();

                                            mProgressBar.setVisibility(View.GONE);
                                            mRecyclerView.setVisibility(View.VISIBLE);

                                            Toast.makeText(getApplicationContext(), "Success get JSON data!", Toast.LENGTH_SHORT).show();
                                        }, 1000);
                                        break;
                                    default:
                                        // error
                                        throw new Exception("Invalid API request");
                                }
                            } catch (Exception e) {
                                Log.e(LOG_TAG, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(LOG_TAG, error.getMessage());
                        }
                    });
            mRequestQueue.add(jsonArrayRequest);
        }
    }

    protected void getCards() {
        // ФИО препода, дисциплина и рейтинг

        for (Subject_Group sg : mSubjectGroupList) {
            for (Teacher_Subject ts : mTeacherSubjectList)
            {
                if (ts.subjectId == sg.subjectId)
                {
                    for (Criterion crit : mCriterionList)
                    {
                        for (Grade grade : mGradeList)
                        {
                            if (grade.teacher_subjectId == ts.id && grade.criterionId == crit.id)
                            {
                                for (User user : mUserList)
                                {
                                    if (ts.teacherId == user.id)
                                    {
                                        for(Subject s : mSubjectList)
                                        {
                                            if (s.id == ts.subjectId)
                                            {
                                                String FIO;
                                                if (!user.lastname.equals(""))
                                                    FIO = user.surname + user.name + user.lastname;
                                                else
                                                    FIO = user.surname + user.name;

                                                mCardList.add(new Card(FIO, s.name, grade.grade));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}