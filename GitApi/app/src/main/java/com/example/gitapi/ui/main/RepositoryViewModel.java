package com.example.gitapi.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gitapi.Data.RepositoryClient;
import com.example.gitapi.Pojo.RootModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryViewModel extends ViewModel {

    MutableLiveData<RootModel> repositoryMutableLiveData=new MutableLiveData<>();

    public void getRepositories(){

        RepositoryClient.getInstance().getRepositories()
                .enqueue(new Callback<RootModel>() {
                    @Override
                    public void onResponse(Call<RootModel> call, Response<RootModel> response) {
                        repositoryMutableLiveData.postValue(response.body());

                        Log.i("suc !!!!!!","suc ");

                    }

                    @Override
                    public void onFailure(Call<RootModel> call, Throwable t) {
                        Log.i("fail !!!!!!","Fail "+t);
                    }
                });

    }

    public void getRepositoryWithDate(String date){

        RepositoryClient.getInstance().getRepositoriesWithDate(date)
                .enqueue(new Callback<RootModel>() {
                    @Override
                    public void onResponse(Call<RootModel> call, Response<RootModel> response) {
                        repositoryMutableLiveData.postValue(response.body());

                        Log.i("suc with Date!!!!!!","suc ");
                    }

                    @Override
                    public void onFailure(Call<RootModel> call, Throwable t) {
                        Log.i("Fail with date !!!!!!","faiiiil ");
                    }
                });
    }


    public void getRepositoryWithLanguage(String lang){

        RepositoryClient.getInstance().getRepositoriesWithLanguage(lang)
                .enqueue(new Callback<RootModel>() {
                    @Override
                    public void onResponse(Call<RootModel> call, Response<RootModel> response) {
                        repositoryMutableLiveData.postValue(response.body());

                        Log.i("suc with lang!!!!!!","suc ");
                    }

                    @Override
                    public void onFailure(Call<RootModel> call, Throwable t) {
                        Log.i("Fail with lang !!!!!!","faiiiil ");
                    }
                });
    }
}
