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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private ArrayList<Card> mCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        parseJSON();
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

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        for (String url: urls) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:8000/api/v1/" + url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
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

                            try {
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
                                        }
                                        break;
                                    case "teacher_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int user = json.getInt("user");

                                            mTeacherList.add(new Teacher(user));
                                        }
                                        break;
                                    case "subject_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int subjId = json.getInt("id");
                                            String subjName = json.getString("name");

                                            mSubjectList.add(new Subject(subjId, subjName));
                                        }
                                        break;
                                    case "teacher_subj_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int teacherSubjId = json.getInt("id");
                                            int TSteacherId = json.getInt("teacher");
                                            int TSsubjectId = json.getInt("subject");

                                            mTeacherSubjectList.add(new Teacher_Subject(teacherSubjId, TSteacherId, TSsubjectId));
                                        }
                                        break;
                                    case "subject_group_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);
                                            int subjGroupId = json.getInt("id");
                                            int SGsubjectId = json.getInt("subject");
                                            int SGgroupId = json.getInt("group");

                                            mSubjectGroupList.add(new Subject_Group(subjGroupId, SGsubjectId, SGgroupId));
                                        }
                                        break;
                                    case "criterion_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int critId = json.getInt("id");
                                            String critName = json.getString("name");

                                            mCriterionList.add(new Criterion(critId, critName));
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
                                        }
                                        break;
                                    case "group_list":
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject json = response.getJSONObject(i);

                                            int groupId = json.getInt("id");
                                            String groupName = json.getString("name");

                                            mGroupList.add(new Group(groupId, groupName));
                                        }

                                        // это последний запрос
                                        getCards();

                                        mCardsAdapter = new CardsAdapter(MainActivity.this, mCardList);
                                        mRecyclerView.setAdapter(mCardsAdapter);

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
                                                if (!Objects.equals(user.lastname, ""))
                                                {
                                                    FIO = user.surname + user.name + user.lastname;
                                                }
                                                else
                                                {
                                                    FIO = user.surname + user.name;
                                                }
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