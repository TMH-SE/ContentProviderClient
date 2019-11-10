package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String URL = "content://com.example.mycontentprovider.MyContentProvider/students";
    private ListView listStudent;
    private EditText txtId, txtName, txtClassName;
    private Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listStudent = findViewById(R.id.listStudent);
        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtClassName = findViewById(R.id.txtClassName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

//        Student student1 = new Student("Nguyen Van F", "DHKTPM12A");
//        insert(student1);
//        Student student2 = new Student("Nguyen Van G", "DHKTPM12B");
//        insert(student2);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtId.getText().toString();
                String name = txtName.getText().toString();
                String class_name = txtClassName.getText().toString();
                if (name.equalsIgnoreCase("")) {
                    notification("Please enter student's name");
                } else if (class_name.equalsIgnoreCase("")) {
                    notification("Please enter student's class name");
                } else if (id.equalsIgnoreCase("")) {
                    insert(new Student(name, class_name));
                    clear();
                    loadList();
                } else {
                    update(new Student(Integer.parseInt(id), name, class_name));
                    clear();
                    loadList();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
        loadList();
    }

    public void clear() {
        txtId.setText("");
        txtName.setText("");
        txtClassName.setText("");
    }

    public void loadList() {
        final StudentAdapter adapter = new StudentAdapter(this, R.layout.student_layer, queryAll());
        listStudent.setAdapter(adapter);
    }

    public List<Student> queryAll() {
        Cursor cursorStudent = getContentResolver().query(Uri.parse(URL), null, null, null, null);
        List<Student> list = new ArrayList<>();
        if (cursorStudent.moveToFirst()) {
            do {
                Student sv = new Student();
                sv.setId(Integer.parseInt(cursorStudent.getString(0)));
                sv.setName(cursorStudent.getString(1));
                sv.setClass_name(cursorStudent.getString(2));
                list.add(sv);
            } while (cursorStudent.moveToNext());
        }
        return list;
    }

    public void insert(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("class_name", student.getClass_name());
        getContentResolver().insert(Uri.parse(URL), values);
    }

    public void update(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("class_name", student.getClass_name());
        getContentResolver().update(Uri.parse(URL), values, "id = ?", new String[] {Integer.toString(student.getId())});
    }

    public void notification(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}
