package com.example.gitapi.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gitapi.Pojo.ItemsModel;
import com.example.gitapi.Pojo.RootModel;
import com.example.gitapi.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;



public class MainActivity extends AppCompatActivity {


    ImageView errorState;
    Button retryBtn;
    RepositoryViewModel repositoryViewModel;
    SwipeRefreshLayout swipe_refresh;
    RecyclerView recyclerView;
    boolean isRecyclerVisible = false;
    ShimmerFrameLayout shimmerFrameLayout;
   // RootModel rootModel;

    FloatingActionButton datePicker;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker=findViewById(R.id.datePicker);
       // rootModel =new RootModel();
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(() -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            shimmerOn();
            Toast.makeText(this, "Refreshed", Toast.LENGTH_LONG).show();
            swipe_refresh.setRefreshing(false);

        });
        errorState = findViewById(R.id.fail_Image);
        retryBtn = findViewById(R.id.retry_Btn);
        retryBtn.setOnClickListener(v -> {
            Intent i=new Intent(this, MainActivity.class);
            startActivity(i);
            shimmerOn();
        });
        shimmerOn();
        reloadApp();

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(v -> {

                    DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day);

                  datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;

                Log.e("Setlistner","Called???????");
                String date=year+"-"+month+"-"+day;
                Log.d("New Date",date);
                reloadAppWithDate(date);
            }
        };

    }


   /* @RequiresApi(api = Build.VERSION_CODES.N)
    public String SelectedDate(){
         String date = null;
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

        setListener= (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            return String.valueOf(year1 + "-" + month1 + "-" + dayOfMonth);
        };

    }*/



    public void reloadApp(){
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            // Show the connected screen
            hideFailureState();
            isRecyclerVisible=true;
            repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
            repositoryViewModel.getRepositories();
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter();
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(repositoryListAdapter);
            repositoryViewModel.repositoryMutableLiveData.observe(this, rootModel ->
                    repositoryListAdapter.setList((rootModel.getItems())));

            shimmerOff();

        } else {
            // Show disconnected screen

            if (isRecyclerVisible) {
                recyclerView.setVisibility(View.GONE);
                shimmerOff();
            }

            unHideFailureState();
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }
    public void reloadAppWithSize(int size){
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            // Show the connected screen
            ArrayList<ItemsModel> Data=new ArrayList<>();


            ArrayList<ItemsModel> showedData=new ArrayList<>();
            hideFailureState();
            isRecyclerVisible=true;
            repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
            //repositoryViewModel.getRepositories();
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter();
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(repositoryListAdapter);

           // Log.d("Size",rootModel.getItems().size()+"");
            Log.d("Size",Data.size()+"");

            repositoryViewModel.repositoryMutableLiveData.observe(this, rootModel -> {
                Data.addAll(rootModel.getItems());
                Log.d("all Size",rootModel.getItems().size()+"");
                for (int i=0; i<size;i++){
                    showedData.add(Data.get(i));
                }
                Log.d("Size",showedData.size()+"");
            });

                    repositoryListAdapter.setList((showedData));


            shimmerOff();

        } else {
            // Show disconnected screen

            if (isRecyclerVisible) {
                recyclerView.setVisibility(View.GONE);
                shimmerOff();
            }

            unHideFailureState();
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void reloadAppWithDate(String date){
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            // Show the connected screen
            hideFailureState();
            isRecyclerVisible=true;
            repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
            repositoryViewModel.getRepositoryWithDate(date);
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter();
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(repositoryListAdapter);
            repositoryViewModel.repositoryMutableLiveData.observe(this, rootModel ->
                    repositoryListAdapter.setList((rootModel.getItems())));
            shimmerOff();

        } else {
            // Show disconnected screen

            if (isRecyclerVisible) {
                recyclerView.setVisibility(View.GONE);
                shimmerOff();
            }

            unHideFailureState();

            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

   public void reloadAppWithLanguage(String lang){
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            // Show the connected screen
            hideFailureState();
            ArrayList<ItemsModel> filteredLanguage=new ArrayList<>();
            isRecyclerVisible=true;
            repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
            repositoryViewModel.getRepositoryWithLanguage(lang);
            RepositoryListAdapter repositoryListAdapter = new RepositoryListAdapter();
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(repositoryListAdapter);
            repositoryViewModel.repositoryMutableLiveData.observe(this, rootModel -> {
                for(ItemsModel item:rootModel.getItems()){
                    if(item.getLanguage().equals(lang))
                    filteredLanguage.add(item);
                }
                if(filteredLanguage.size()==0){
                    Toast.makeText(this,"No Data Found",Toast.LENGTH_LONG).show();
                     reloadApp();
                }else {
                    repositoryListAdapter.setList(filteredLanguage);
                }

            });

            shimmerOff();

        } else {
            // Show disconnected screen

            if (isRecyclerVisible) {
                recyclerView.setVisibility(View.GONE);
                shimmerOff();
            }

            unHideFailureState();
            Toast.makeText(this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        shimmerOn();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerOff();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Top10:
                reloadAppWithSize(10);

               // reloadAppWithDate("2019-02-13");
                return true;

            case R.id.Top20:

                reloadAppWithSize(20);
                return true;

            case R.id.Top30:
                reloadAppWithSize(30);
                return true;

            case R.id.jupyterNotebook:
                reloadAppWithLanguage("Jupyter Notebook");
                return true;
            case R.id.go:
                reloadAppWithLanguage("Go");

                return true;

            case R.id.java:
                reloadAppWithLanguage("Java");
                return true;
            case R.id.shell:
                reloadAppWithLanguage("Shell");

                return true;
            case R.id.javaScript:
                reloadAppWithLanguage("JavaScript");
                return true;

            case R.id.objective_c:
                reloadAppWithLanguage("Objective-C");

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void hideFailureState() {
        errorState.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
    }
public void shimmerOff(){
    shimmerFrameLayout.setVisibility(View.GONE);
    shimmerFrameLayout.stopShimmerAnimation();

}
    public void shimmerOn(){
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setDuration(5);
        shimmerFrameLayout.startShimmerAnimation();


    }
    public void unHideFailureState() {
        errorState.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);
        shimmerOn();

    }
}