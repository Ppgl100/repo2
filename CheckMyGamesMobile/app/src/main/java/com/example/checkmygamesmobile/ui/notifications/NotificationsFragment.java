package com.example.checkmygamesmobile.ui.notifications;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.checkmygamesmobile.Activity2;
import com.example.checkmygamesmobile.MainActivity;
import com.example.checkmygamesmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button serachButton;
    private EditText myTitle;
    private View myRoot;
    private View mainRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        View root2 = inflater.inflate(R.layout.fragment_dashboard, container, false);
        View root3 = inflater.inflate(R.layout.activity_main, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        one = (Button) root.findViewById(R.id.sortby1);
        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sort("one");
                //penAddGameActivity("STEAM");
            }
        });

        two = (Button) root.findViewById(R.id.sortby2);
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sort("two");
                //penAddGameActivity("STEAM");
            }
        });

        three = (Button) root.findViewById(R.id.sortby3);
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sort("three");
                //penAddGameActivity("STEAM");
            }
        });

        four = (Button) root.findViewById(R.id.sortby4);
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sort("four");
                //penAddGameActivity("STEAM");
            }
        });

        myTitle = (EditText) root.findViewById(R.id.searchTitle);
        serachButton = (Button) root.findViewById(R.id.search);
        serachButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                findMyTitle(""+myTitle.getText());
                //penAddGameActivity("STEAM");
            }
        });

        myRoot = root2;
        mainRoot = root3;
        return root;
    }

    private void findMyTitle(String title)
    {
        //MainActivity.getInstance().switchToDashboard();

        TableLayout layout = (TableLayout) myRoot.findViewById(R.id.myTableLayout);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;
                TextView tmpPlatF = (TextView)row.getChildAt(0);
                TextView tmpGameT = (TextView)row.getChildAt(1);
                String myT = tmpGameT.getText().toString().toUpperCase();
                if(myT.contains((title.toUpperCase())))
                {
                    row.setBackgroundColor(Color.YELLOW);
                }
            }
        }
    }

    private void sort(String sort)
    {
        FileInputStream is;
        BufferedReader reader;
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");

        List<String> myReadList = new ArrayList<String>();
        List<String> myReadList2 = new ArrayList<String>();
        try
        {
            if (file.exists()) {

                is = new FileInputStream(file);

                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();


                //l.setBackgroundColor(Color.GREEN);
                int i = 0;
                while (line != null) {
                    String myLine = line;
                    String plat = myLine.substring(0,myLine.indexOf(";"));
                    String gameT = myLine.substring(myLine.indexOf(";")+1,myLine.length());
                    myReadList.add(myLine);
                    myReadList2.add(gameT+";"+plat);
                    line = reader.readLine();
                }
            }
        }
        catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }

        if(sort.equals("one"))
        {
            Collections.sort(myReadList);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
            f.delete();
            for(int i=0;i<myReadList.size();i++) {
                writeToSD(myReadList.get(i));
            }
        }
        else if(sort.equals("two"))
        {
            Collections.sort(myReadList);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
            f.delete();
            for(int i=myReadList.size()-1;i>=0;i--) {
                writeToSD(myReadList.get(i));
            }
        }
        else if(sort.equals("three"))
        {
            Collections.sort(myReadList2);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
            f.delete();

            List<String> myNewList = new ArrayList<String>();


            for(int i=0;i<myReadList2.size();i++)
            {
                String myLine = myReadList2.get(i);
                String gameT = myLine.substring(0,myLine.indexOf(";"));
                String plat = myLine.substring(myLine.indexOf(";")+1,myLine.length());

                myNewList.add(plat+";"+gameT);
            }

            for(int i=0;i<myNewList.size();i++) {
                writeToSD(myNewList.get(i));
            }
        }
        else if(sort.equals("four"))
        {
            Collections.sort(myReadList2);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
            f.delete();

            List<String> myNewList = new ArrayList<String>();


            for(int i=0;i<myReadList2.size();i++)
            {
                String myLine = myReadList2.get(i);
                String gameT = myLine.substring(0,myLine.indexOf(";"));
                String plat = myLine.substring(myLine.indexOf(";")+1,myLine.length());

                myNewList.add(plat+";"+gameT);
            }

            for(int i=myNewList.size()-1;i>=0;i--) {
                writeToSD(myNewList.get(i));
            }
        }
    }

    public Boolean writeToSD(String text){
        Boolean write_successful = false;
        File root=null;
        try {
            // <span id="IL_AD8" class="IL_AD">check for</span> SDcard
            root = Environment.getExternalStorageDirectory();
            Log.i(TAG, root.getAbsolutePath());

            //check sdcard permission
            if (root.canWrite()){
                File fileDir = new File(""+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
                fileDir.mkdirs();

                BufferedWriter bw = new BufferedWriter(new FileWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt", true));
                bw.write(text);
                bw.newLine();
                bw.flush();

                /*File file= new File(fileDir, "samplefile.txt");
                FileWriter filewriter = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(filewriter);
                out.write(text);
                out.close();*/
                write_successful = true;
            }
        } catch (IOException e) {
            Log.e("ERROR:---", "Could not write file to SDCard" + e.getMessage());
            write_successful = false;
        }
        return write_successful;
    }
}