package com.example.checkmygamesmobile.ui.home;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.checkmygamesmobile.Activity2;
import com.example.checkmygamesmobile.MainActivity;
import com.example.checkmygamesmobile.R;
import com.example.checkmygamesmobile.ui.dashboard.DashboardFragment;
import com.example.checkmygamesmobile.ui.dashboard.DashboardViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public Button ps1Button;
    public Button ps2Button;
    public Button ps3Button;
    public Button ps4Button;
    public Button xboxButton;
    public Button xbox360Button;
    public Button xboxOneButton;
    public Button nesButton;
    public Button snesButton;
    public Button n64Button;
    public Button gamecubeButton;
    public Button wiiButton;
    public Button wiiuButton;
    public Button switchButton;
    public Button steamButton;
    public Button epicgamesButton;
    public View myRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View root2 = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        ps1Button = (Button) root.findViewById(R.id.ps1);
        ps1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("PS1");
            }
        });
        ps2Button = (Button) root.findViewById(R.id.ps2);
        ps2Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("PS2");
            }
        });
        ps3Button = (Button) root.findViewById(R.id.ps3);
        ps3Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("PS3");
            }
        });
        ps4Button = (Button) root.findViewById(R.id.ps4);
        ps4Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("PS4");
            }
        });
        xboxButton = (Button) root.findViewById(R.id.xbox);
        xboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("XBOX");
            }
        });

        xbox360Button = (Button) root.findViewById(R.id.xbox360);
        xbox360Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("XBOX 360");
            }
        });

        xboxOneButton = (Button) root.findViewById(R.id.xboxOne);
        xboxOneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("XBOX ONE");
            }
        });

        nesButton = (Button) root.findViewById(R.id.nes);
        nesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("NES");
            }
        });

        snesButton = (Button) root.findViewById(R.id.snes);
        snesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("SNES");
            }
        });

        n64Button = (Button) root.findViewById(R.id.n64);
        n64Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("N64");
            }
        });

        gamecubeButton = (Button) root.findViewById(R.id.gamecube);
        gamecubeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("GAMECUBE");
            }
        });

        wiiButton = (Button) root.findViewById(R.id.wii);
        wiiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("Wii");
            }
        });

        wiiuButton = (Button) root.findViewById(R.id.wiiu);
        wiiuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("WiiU");
            }
        });

        switchButton = (Button) root.findViewById(R.id.nswitch);
        switchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("SWITCH");
            }
        });

        steamButton = (Button) root.findViewById(R.id.steam);
        steamButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("STEAM");
            }
        });

        epicgamesButton = (Button) root.findViewById(R.id.epicgames);
        epicgamesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openAddGameActivity("EPIC GAMES");
            }
        });


        myRoot = root2;
        return root;
    }

    private void openAddGameActivity(String Platform)
    {
        Intent intent = new Intent(this.getActivity(),Activity2.class);
        intent.putExtra("PLATFORM",Platform);
        startActivityForResult(intent,1);
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
            String myString = returnString.get(0)+";"+returnString.get(1);
            writeToSD(myString);
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