package com.omolayoseun.copyboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ListViewAdapterClass extends BaseAdapter {

    Context context;
    File directory = new File(Tools.dirPath);
    File[] files = directory.listFiles();
    int count = files.length;
    Caller caller;

    ListViewAdapterClass(Context context, Caller caller){
        this.context = context;
        this.caller = caller;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list, viewGroup, false);

        TextView txt = view.findViewById(R.id.name);

        txt.setText(files[i].getName());

        view.findViewById(R.id.view).setOnClickListener(view13 -> {
            Tools.fileToView = files[i].getName();
            caller.callView("");
        });

        view.findViewById(R.id.copy).setOnClickListener(view12 -> {
            File file = new File(Tools.dirPath + files[i].getName());
            StringBuilder str = new StringBuilder();
            try {
                Scanner read = new Scanner(file);
                while (read.hasNext()){
                    str.append(read.nextLine()).append("\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(files[i].getName(), str);
            clipboardManager.setPrimaryClip(clipData);
            ToastClass.makeText(context, "Text has been copied!");
        });

        view.findViewById(R.id.delete).setOnClickListener(view1 -> {
            File f = new File(Tools.dirPath + files[i].getName());

            if (f.delete()){
                ToastClass.makeText(context, "Delete successful");
                // interface
                caller.callList();
            }
        });

        return view;
    }
}
