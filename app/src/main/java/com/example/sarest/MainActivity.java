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

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private RequestQueue mRequestQueue;

    private ArrayList<Criterion> mCriterionList;
    private ArrayList<Grade> mGradeList;
    private ArrayList<Group> mGroupList;
    private ArrayList<Subject> mSubjectList;
    private ArrayList<Subject_Group> mSubjectGroupList;
    private ArrayList<Teacher> mTeacherList;
    private ArrayList<Teacher_Subject> mTeacherSubjectList;
    private ArrayList<User> mUserList;

    private ArrayList<Card> mCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        mUserList = new ArrayList<>();
        mTeacherList = new ArrayList<>();
        mSubjectList = new ArrayList<>();
        mTeacherSubjectList = new ArrayList<>();
        mSubjectGroupList = new ArrayList<>();
        mCriterionList = new ArrayList<>();
        mGradeList = new ArrayList<>();
        mGroupList = new ArrayList<>();
        parseJSON();

        // тут будем собирать карточки
        getCards();

        //Log.d(LOG_TAG, "First card: " + mCardList.get(0).getFIO());

        // Пихаем в recyclerView
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mExampleAdapter = new ExampleAdapter(MainActivity.this, mCardList);
        mRecyclerView.setAdapter(mExampleAdapter);

        // Output in a logcat //
        for (User u: mUserList)
        {
            Log.d(LOG_TAG, "USER: id = " + u.id +
                    "email = " + u.email +
                    "surname = " + u.surname +
                    "name = " + u.name +
                    "lastname = " + u.lastname);
        }
        for (Teacher t: mTeacherList)
        {
            Log.d(LOG_TAG, "TEACHER: user = " + t.user);
        }
        for (Subject s: mSubjectList)
        {
            Log.d(LOG_TAG, "SUBJECT: id = " + s.id +
                    "name = " + s.name);
        }
        for (Teacher_Subject ts: mTeacherSubjectList)
        {
            Log.d(LOG_TAG, "TEACHER_SUBJECT: id = " + ts.id +
                    "teacher = " + ts.teacherId +
                    "subject = " + ts.subjectId);
        }
        for (Subject_Group sg: mSubjectGroupList)
        {
            Log.d(LOG_TAG, "SUBJECT_GROUP: id = " + sg.id +
                    "subject = " + sg.subjectId +
                    "group = " + sg.groupId);
        }
        for (Criterion s: mCriterionList)
        {
            Log.d(LOG_TAG, "Criterion: id = " + s.id +
                    "name = " + s.name);
        }
        for (Grade g: mGradeList)
        {
            Log.d(LOG_TAG, "GRADE: id = " + g.id +
                    "teacher_subject = " + g.teacher_subjectId +
                    "criterion = " + g.criterionId +
                    "grade = " + g.grade);
        }
        for (Group gp: mGroupList)
        {
            Log.d(LOG_TAG, "Group: id = " + gp.id +
                    "name = " + gp.name);
        }
        // Output in a logcat //
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

        for (String url : urls) {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://10.0.2.2:8000/api/v1/" + url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int j = 0; j < response.length(); j++) {
                                    JSONObject json = response.getJSONObject(j);

                                    switch (url) {
                                        case "user_list":
                                            int userId = json.getInt("id");
                                            String email = json.getString("email");
                                            String surname = json.getString("surname");
                                            String name = json.getString("name");
                                            String lastname = json.getString("lastname");
                                            mUserList.add(new User(userId, email, surname, name, lastname));
                                            break;
                                        case "teacher_list":
                                            int user = json.getInt("user");

                                            mTeacherList.add(new Teacher(user));
                                            break;
                                        case "subject_list":
                                            int subjId = json.getInt("id");
                                            String subjName = json.getString("name");

                                            mSubjectList.add(new Subject(subjId, subjName));
                                            break;
                                        case "teacher_subj_list":
                                            int teacherSubjId = json.getInt("id");
                                            int TSteacherId = json.getInt("teacher");
                                            int TSsubjectId = json.getInt("subject");
                                            mTeacherSubjectList.add(new Teacher_Subject(teacherSubjId, TSteacherId, TSsubjectId));
                                            break;
                                        case "subject_group_list":
                                            int subjGroupId = json.getInt("id");
                                            int SGsubjectId = json.getInt("subject");
                                            int SGgroupId = json.getInt("group");
                                            mSubjectGroupList.add(new Subject_Group(subjGroupId, SGsubjectId, SGgroupId));
                                            break;
                                        case "criterion_list":
                                            int critId = json.getInt("id");
                                            String critName = json.getString("name");

                                            mCriterionList.add(new Criterion(critId, critName));
                                            break;
                                        case "grade_list":
                                            int gradeId = json.getInt("id");
                                            int gradeTSId = json.getInt("teacher_subject");
                                            int gradeCriterionId = json.getInt("criterion");
                                            int grade = json.getInt("grade");

                                            mGradeList.add(new Grade(gradeId, gradeTSId, gradeCriterionId, grade));
                                            break;
                                        case "group_list":
                                            int groupId = json.getInt("id");
                                            String groupName = json.getString("name");

                                            mGroupList.add(new Group(groupId, groupName));
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

    protected void getCards() {
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
                                                if (user.lastname != "")
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