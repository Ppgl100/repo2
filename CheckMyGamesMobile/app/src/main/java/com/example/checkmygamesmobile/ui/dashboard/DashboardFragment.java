package com.example.checkmygamesmobile.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.checkmygamesmobile.Activity2;
import com.example.checkmygamesmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private View myRoot;
    private TableRow myTableRow;
    private EditText myTitle;
    private Button serachButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        myRoot =root;
        createEntry();

        myTitle = (EditText) root.findViewById(R.id.searchTitle);
        serachButton = (Button) root.findViewById(R.id.search);
        serachButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                findMyTitle(""+myTitle.getText());
                //penAddGameActivity("STEAM");
            }
        });

        return root;
    }

    public void createEntry()
    {
        FileInputStream is;
        BufferedReader reader;
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");

        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();

                TableLayout l = (TableLayout) myRoot.findViewById(R.id.myTableLayout);
                //l.setBackgroundColor(Color.GREEN);
                int i=0;
                while (line != null) {
                    String myLine = line;
                    String plat = myLine.substring(0,myLine.indexOf(";"));
                    String gameT = myLine.substring(myLine.indexOf(";")+1,myLine.length());

                    TableRow tr = new TableRow(this.getActivity());

                    TextView platform = new TextView(this.getActivity());
                    platform.setText(plat+"");
                    platform.setTextSize(20);
                    platform.setWidth(250);
                    platform.setMaxLines(3);
                    platform.setPadding(0,0,0,0);
                    platform.setSingleLine(false);
                    //platform.setTranslationY(platform.getTranslationY()+140);
                    tr.addView(platform,0);

                    TextView gameTitle = new TextView(this.getActivity());
                    gameTitle.setText(gameT+"");
                    gameTitle.setTextSize(20);
                    gameTitle.setWidth(350);
                    gameTitle.setMaxLines(3);
                    gameTitle.setPadding(50,0,0,0);
                    gameTitle.setSingleLine(false);
                    //gameTitle.setTranslationY(gameTitle.getTranslationY()+140);
                    tr.addView(gameTitle,1);

                    Button edit = new Button(this.getActivity());
                    edit.setText("EDIT");
                    edit.setBackgroundColor(Color.WHITE);
                    edit.setTranslationY(edit.getTranslationY()+27);
                    edit.setTranslationX(130);
                    final Activity myActivity = this.getActivity();
                    edit.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(myActivity, Activity2.class);
                            myTableRow = (TableRow)view.getParent();
                            TableRow tmp = (TableRow)view.getParent();
                            TextView tmpPlat = (TextView)tmp.getChildAt(0);
                            intent.putExtra("Plat",tmpPlat.getText());
                            TextView tmpGame = (TextView)tmp.getChildAt(1);
                            intent.putExtra("Game",tmpGame.getText());
                            startActivityForResult(intent,1);
                        }
                    });
                    tr.addView(edit,2);

                    Button remove = new Button(this.getActivity());
                    remove.setText("REMOVE");
                    remove.setBackgroundColor(Color.WHITE);
                    remove.setTranslationX(edit.getTranslationX()-264);
                    remove.setTranslationY(edit.getTranslationY()+207);
                    remove.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            myTableRow = (TableRow)view.getParent();
                            TableLayout l = (TableLayout) myRoot.findViewById(R.id.myTableLayout);
                            l.removeView(myTableRow);

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
                            file.delete();

                            TableLayout layout = (TableLayout) myRoot.findViewById(R.id.myTableLayout);

                            for (int i = 0; i < layout.getChildCount(); i++) {
                                View child = layout.getChildAt(i);

                                if (child instanceof TableRow) {
                                    TableRow row = (TableRow) child;
                                    TextView tmpPlatF = (TextView)row.getChildAt(0);
                                    TextView tmpGameT = (TextView)row.getChildAt(1);
                                    writeToSD(tmpPlatF.getText()+";"+tmpGameT.getText());
                                }
                            }
                        }
                    });
                    tr.addView(remove,3);

                    tr.setMinimumHeight(430);

                    if(i==0) {
                        tr.setBackgroundColor(0XFF1BA4DA);
                        i++;
                    }
                    else
                    {
                        tr.setBackgroundColor(0XFF4CC6FF);
                        i--;
                    }

                    tr.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    tr.setPadding(20,0,0,0);
                    l.addView(tr);

                    line = reader.readLine();
                    //l.addView(t,0);

                    //String plat =myLine.substring(0,myLine.indexOf(";"));

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*TableLayout t = myRoot.findViewById(R.id.myTableLayout);
        TableRow tr = new TableRow(this.getContext());
        TextView platForm = new TextView(this.getContext());
        TextView gameTitl2 = new TextView(this.getContext());
        tr.addView(platForm);
        tr.addView(gameTitl2);
        t.addView(tr);*/
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
                if(myT.equals((title.toUpperCase())))
                {
                    row.setBackgroundColor(Color.YELLOW);
                }
            }
        }
    }

    // This method is called when the second activity finishes
    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (resultCode == 1)
        {
            List<String> returnString = data.getStringArrayListExtra("myData");

            TextView tmpPlat = (TextView)myTableRow.getChildAt(0);
            tmpPlat.setText(returnString.get(0));
            TextView tmpGame = (TextView)myTableRow.getChildAt(1);
            tmpGame.setText(returnString.get(1));

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/samplefile.txt");
            file.delete();

            TableLayout layout = (TableLayout) myRoot.findViewById(R.id.myTableLayout);

            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);

                if (child instanceof TableRow) {
                    TableRow row = (TableRow) child;
                    TextView tmpPlatF = (TextView)row.getChildAt(0);
                    TextView tmpGameT = (TextView)row.getChildAt(1);
                    writeToSD(tmpPlatF.getText()+";"+tmpGameT.getText());
                }
            }


        }
        else
        {

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