package com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bean.FacultyBean;
import com.bean.StudentBean;

import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {

    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "Attendance";

    // Contacts table name
    private static final String FACULTY_INFO_TABLE = "faculty_table";
    private static final String STUDENT_INFO_TABLE = "student_table";
    private static final String ATTENDANCE_SESSION_TABLE = "attendance_session_table";
    private static final String ATTENDANCE_TABLE = "attendance_table";

    // Contacts Table Columns names
    private static final String KEY_FACULTY_ID = "faculty_id";
    private static final String KEY_FACULTY_FIRSTNAME = "faculty_firstname";
    private static final String KEY_FACULTY_LASTNAME = "faculty_lastname";
    private static final String KEY_FACULTY_MO_NO = "faculty_mobilenumber";
    private static final String KEY_FACULTY_ADDRESS = "faculty_address";
    private static final String KEY_FACULTY_USERNAME = "faculty_username";
    private static final String KEY_FACULTY_PASSWORD = "faculty_password";

    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_FIRSTNAME = "student_firstname";
    private static final String KEY_STUDENT_LASTNAME = "student_lastname";
    private static final String KEY_STUDENT_MO_NO = "student_mobilenumber";
    private static final String KEY_STUDENT_ADDRESS = "student_address";
    private static final String KEY_STUDENT_DEPARTMENT = "student_department";
    private static final String KEY_STUDENT_CLASS = "student_class";

    private static final String KEY_ATTENDANCE_SESSION_ID = "attendance_session_id";
    private static final String KEY_ATTENDANCE_SESSION_FACULTY_ID = "attendance_session_faculty_id";
    private static final String KEY_ATTENDANCE_SESSION_DEPARTMENT = "attendance_session_department";
    private static final String KEY_ATTENDANCE_SESSION_CLASS = "attendance_session_class";
    private static final String KEY_ATTENDANCE_SESSION_DATE = "attendance_session_date";
    private static final String KEY_ATTENDANCE_SESSION_SUBJECT = "attendance_session_subject";

    private static final String KEY_SESSION_ID = "attendance_session_id";
    private static final String KEY_ATTENDANCE_STUDENT_ID = "attendance_student_id";
    private static final String KEY_ATTENDANCE_STATUS = "attendance_status";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_FIRSTNAME + " TEXT, " +
                KEY_FACULTY_LASTNAME + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT, " +
                KEY_FACULTY_USERNAME + " TEXT, " +
                KEY_FACULTY_PASSWORD + " TEXT " + ")";
        Log.d("queryFaculty", queryFaculty);

        String queryStudent="CREATE TABLE "+ STUDENT_INFO_TABLE +" (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_FIRSTNAME + " TEXT, " +
                KEY_STUDENT_LASTNAME + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DEPARTMENT + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")";
        Log.d("queryStudent",queryStudent );


        String queryAttendanceSession="CREATE TABLE "+ ATTENDANCE_SESSION_TABLE +" (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER, " +
                KEY_ATTENDANCE_SESSION_DEPARTMENT + " TEXT, " +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " DATE," +
                KEY_ATTENDANCE_SESSION_SUBJECT + " TEXT" + ")";
        Log.d("queryAttendanceSession",queryAttendanceSession );


        String queryAttendance="CREATE TABLE "+ ATTENDANCE_TABLE +" (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT " + ")";
        Log.d("queryAttendance",queryAttendance );

        try {
            db.execSQL(queryFaculty);
            db.execSQL(queryStudent);
            db.execSQL(queryAttendanceSession);
            db.execSQL(queryAttendance);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFaculty(FacultyBean facultyBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO " +
                            FACULTY_INFO_TABLE +
                            " (" +
                                KEY_FACULTY_FIRSTNAME + ", " +
                                KEY_FACULTY_LASTNAME + ", " +
                                KEY_FACULTY_MO_NO + ", " +
                                KEY_FACULTY_ADDRESS + ", " +
                                KEY_FACULTY_USERNAME + ", " +
                                KEY_FACULTY_PASSWORD +
                            ") VALUES ('" +
                                facultyBean.getFaculty_firstname() + "', '" +
                                facultyBean.getFaculty_lastname() + "', '" +
                                facultyBean.getFaculty_mobilenumber() + "', '" +
                                facultyBean.getFaculty_address() + "', '" +
                                facultyBean.getFaculty_username() + "', '" +
                                facultyBean.getFaculty_password() + "')";
        Log.d("Query", query);
        db.execSQL(query);
        db.close();

    }

    public FacultyBean validateFaculty(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                                KEY_FACULTY_ID + ", " +
                                KEY_FACULTY_FIRSTNAME + ", " +
                                KEY_FACULTY_LASTNAME + ", " +
                                KEY_FACULTY_MO_NO + ", " +
                                KEY_FACULTY_ADDRESS + ", " +
                                KEY_FACULTY_USERNAME + ", " +
                                KEY_FACULTY_PASSWORD + " " +
                        "FROM " +
                                FACULTY_INFO_TABLE + " " +
                        "WHERE " +
                                KEY_FACULTY_USERNAME + " = '" + username + "' AND " +
                                KEY_FACULTY_PASSWORD + " = '" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            FacultyBean facultyBean = new FacultyBean();
            facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
            facultyBean.setFaculty_firstname(cursor.getString(1));
            facultyBean.setFaculty_lastname((cursor.getString(2)));
            facultyBean.setFaculty_mobilenumber(cursor.getString(3));
            facultyBean.setFaculty_address(cursor.getString(4));
            facultyBean.setFaculty_username(cursor.getString(5));
            facultyBean.setFaculty_password(cursor.getString(6));
            return facultyBean;
        }
        return null;
    }

    public ArrayList<FacultyBean> getAllFaculty() {
        ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                            KEY_FACULTY_ID + ", " +
                            KEY_FACULTY_FIRSTNAME + ", " +
                            KEY_FACULTY_LASTNAME + ", " +
                            KEY_FACULTY_MO_NO + ", " +
                            KEY_FACULTY_ADDRESS + ", " +
                            KEY_FACULTY_USERNAME + ", " +
                            KEY_FACULTY_PASSWORD + " " +
                        "FROM " +
                            FACULTY_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                FacultyBean facultyBean = new FacultyBean();
                facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
                facultyBean.setFaculty_firstname(cursor.getString(1));
                facultyBean.setFaculty_lastname((cursor.getString(2)));
                facultyBean.setFaculty_mobilenumber(cursor.getString(3));
                facultyBean.setFaculty_address(cursor.getString(4));
                facultyBean.setFaculty_username(cursor.getString(5));
                facultyBean.setFaculty_password(cursor.getString(6));
                list.add(facultyBean);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public void deleteFaculty(int facultyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + FACULTY_INFO_TABLE +
                        " WHERE " +
                            KEY_FACULTY_ID + " = " + facultyId;
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public void addStudent(StudentBean studentBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + STUDENT_INFO_TABLE + " (" +
                            KEY_STUDENT_FIRSTNAME + ", " +
                            KEY_STUDENT_LASTNAME + ", " +
                            KEY_STUDENT_MO_NO + ", " +
                            KEY_STUDENT_ADDRESS + ", " +
                            KEY_STUDENT_DEPARTMENT + ", " +
                            KEY_STUDENT_CLASS + ") values ('" +
                        studentBean.getStudent_firstname() + "', '" +
                        studentBean.getStudent_lastname() + "', '" +
                        studentBean.getStudent_mobilenumber() + "', '" +
                        studentBean.getStudent_address() + "', '" +
                        studentBean.getStudent_department() + "', '" +
                        studentBean.getStudent_class() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public ArrayList<StudentBean> getAllStudent() {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                            KEY_STUDENT_ID + ", " +
                            KEY_STUDENT_FIRSTNAME + ", " +
                            KEY_STUDENT_LASTNAME + ", " +
                            KEY_STUDENT_MO_NO + ", " +
                            KEY_STUDENT_ADDRESS + ", " +
                            KEY_STUDENT_DEPARTMENT + ", " +
                            KEY_STUDENT_CLASS + " " +
                        "FROM " +
                            STUDENT_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_firstname(cursor.getString(1));
                studentBean.setStudent_lastname(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_department(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<StudentBean> getAllStudentByBranchYear(String branch, String year) {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                            KEY_STUDENT_ID + ", " +
                            KEY_STUDENT_FIRSTNAME + ", " +
                            KEY_STUDENT_LASTNAME + ", " +
                            KEY_STUDENT_MO_NO + ", " +
                            KEY_STUDENT_ADDRESS + ", " +
                            KEY_STUDENT_DEPARTMENT + ", " +
                            KEY_STUDENT_CLASS + " " +
                        "FROM " +
                            STUDENT_INFO_TABLE + " " +
                        "WHERE " +
                            KEY_STUDENT_DEPARTMENT + " = '" + branch + "' " +
                            "AND " + KEY_STUDENT_CLASS + " = '" + year + "'";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_firstname(cursor.getString(1));
                studentBean.setStudent_lastname(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_department(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while(cursor.moveToNext());
        }
        return list;
    }

    public StudentBean getStudentById(int studentId) {
        StudentBean studentBean = new StudentBean();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                            KEY_STUDENT_ID + ", " +
                            KEY_STUDENT_FIRSTNAME + ", " +
                            KEY_STUDENT_LASTNAME + ", " +
                            KEY_STUDENT_MO_NO + ", " +
                            KEY_STUDENT_ADDRESS + ", " +
                            KEY_STUDENT_DEPARTMENT + ", " +
                            KEY_STUDENT_CLASS + " " +
                        "FROM " +
                            STUDENT_INFO_TABLE + " " +
                        "WHERE " +
                            KEY_STUDENT_ID + " = '" + studentId + "' ";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                    studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                    studentBean.setStudent_firstname(cursor.getString(1));
                    studentBean.setStudent_lastname(cursor.getString(2));
                    studentBean.setStudent_mobilenumber(cursor.getString(3));
                    studentBean.setStudent_address(cursor.getString(4));
                    studentBean.setStudent_department(cursor.getString(5));
                    studentBean.setStudent_class(cursor.getString(6));
            } while(cursor.moveToNext());
        }
        return studentBean;
    }

    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + STUDENT_INFO_TABLE + " " +
                        "WHERE " +
                            KEY_STUDENT_ID + " = " + studentId;
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }
}
