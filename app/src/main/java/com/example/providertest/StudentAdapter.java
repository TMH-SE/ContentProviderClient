package com.example.providertest;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    static final String URL = "content://com.example.mycontentprovider.MyContentProvider/students";
    static final Uri uri = Uri.parse(URL);
    private Context ctx;
    private int layout;
    private List<Student> list;
    private EditText txtId, txtName, txtClassName;

    public StudentAdapter(Context ctx, int layout, List<Student> list) {
        this.ctx = ctx;
        this.layout = layout;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Student student = list.get(i);
        final int pos = i;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        view = inflater.inflate(R.layout.student_layer, null);
        TextView txtStudent = view.findViewById(R.id.txtStudent);
        txtStudent.setText(student.toString());
        Button btnUpdate, btnDelete;
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtId = view.getRootView().findViewById(R.id.txtId);
                txtName = view.getRootView().findViewById(R.id.txtName);
                txtClassName = view.getRootView().findViewById(R.id.txtClassName);
                txtId.setText(Integer.toString(student.getId()));
                txtName.setText(student.getName());
                txtClassName.setText(student.getClass_name());
            }
        });
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ContentResolver resolver = view.getContext().getContentResolver();
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete item?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                resolver.delete(uri, "id = ?", new String[] {Integer.toString(student.getId())});
                                list.remove(pos);
                                notifyDataSetChanged();
                            }
                        }
                );
                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;
    }


}
