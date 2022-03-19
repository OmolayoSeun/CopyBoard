package com.omolayoseun.copyboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditTextFragment extends Fragment {

    EditText et_text;
    Button save, clear;
    boolean openedFile = false;
    String fileName = null;
    boolean checkSaves = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSaves = Tools.thereIsSaved();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_frame, container, false);

        et_text = view.findViewById(R.id.et_text);
        save = view.findViewById(R.id.save);
        clear = view.findViewById(R.id.clear);

        if (checkSaves){
            fileName = Tools.getUnsavedTextName();
            et_text.setText(Tools.getUnsavedTextString());
            checkSaves = false;

        }
        else if (Tools.fileToView != null){
            File file = new File(Tools.dirPath + Tools.fileToView);
            StringBuilder str = new StringBuilder();
            try {
                Scanner read = new Scanner(file);
                while (read.hasNext()){
                    str.append(read.nextLine()).append("\n");
                    openedFile = true;
                    fileName = Tools.fileToView.replace(".txt", "");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            et_text.setText(str.toString());
            Tools.fileToView = null;
        }

        et_text.setSelection(et_text.length());

        save.setOnClickListener(view1 -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inf = getLayoutInflater();
            View v = inf.inflate(R.layout.alert_box, null);
            builder.setView(v);

            EditText et = v.findViewById(R.id.alertEdit);
            if (openedFile || fileName != null) et.setText(fileName);

            et.setSelection(et.length());
            builder.setCancelable(true);
            builder.setTitle("hello");
            builder.setPositiveButton("Save", (dialogInterface, i) -> {
                fileName = et.getText().toString();
                File textFile = new File(Tools.dirPath + fileName + ".txt");
                if (!textFile.exists()){

                    try {
                        if(textFile.createNewFile()) {
                            FileWriter writer = new FileWriter(textFile);
                            writer.write(et_text.getText().toString());
                            ToastClass.makeText(getActivity(), "Text was successfully saved");
                            writer.close();
                        }
                        else ToastClass.makeText(getActivity(), "Can't create file due to unknown error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    ToastClass.makeText(getActivity(), "Text already exit");

                dialogInterface.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                ToastClass.makeText(getActivity(), "Text not save");
                dialogInterface.dismiss();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

        clear.setOnClickListener(view12 -> et_text.setText(""));

        return view;
    }


    @Override
    public void onPause() {
        String text = et_text.getText().toString();
            Tools.saveUnfinishedWork(fileName, text);
        super.onPause();
    }

}
