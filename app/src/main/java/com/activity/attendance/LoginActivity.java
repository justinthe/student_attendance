package com.activity.attendance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.bean.FacultyBean;
import com.db.DBAdapter;

public class LoginActivity extends Activity {

    Button login;
    EditText userName, password;
    Spinner spinnerLoginAs;
    String userRole;
    private String[] userRoleString = new String[] {"admin", "faculty"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button)findViewById(R.id.buttonLogin);
        userName = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        spinnerLoginAs = (Spinner)findViewById(R.id.spinnerLoginAs);

        spinnerLoginAs.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                userRole = (String) spinnerLoginAs.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userRoleString);
        adapter_role.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoginAs.setAdapter(adapter_role);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userRole.equals("admin")) {
                    String user_name = userName.getText().toString();
                    String pass_word = password.getText().toString();

                    if (TextUtils.isEmpty(user_name)) {
                        userName.setError("Invalid username");
                    }
                    else if(TextUtils.isEmpty(pass_word)) {
                        password.setError("Enter Password");
                    }
                    else {
                        if (user_name.equals("admin") & pass_word.equals("admin123")) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    String user_name = userName.getText().toString();
                    String pass_word = password.getText().toString();

                    if (TextUtils.isEmpty(user_name)) {
                        userName.setError("Invalid username");
                    }
                    else if(TextUtils.isEmpty(pass_word)) {
                        password.setError("Enter password");
                    }

                    DBAdapter dbAdapter = new DBAdapter(LoginActivity.this);
                    FacultyBean facultyBean = dbAdapter.validateFaculty(user_name, pass_word);

                    if(facultyBean != null) {
                        Intent intent = new Intent(LoginActivity.this, AddAttendanceSessionActivity.class);
                        startActivity(intent);
                        ((ApplicationContext)LoginActivity.this.getApplicationContext()).setFacultyBean(facultyBean);
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
