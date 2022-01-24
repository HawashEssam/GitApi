package com.example.gitapi.Data;


import com.example.gitapi.Pojo.RootModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoryInterface {

    // url "repositories?q=created:%3E2019-01-10&sort=stars&order=desc"
    @GET("repositories?q=created:%3E2015-01-10&sort=stars&order=desc")
    Call<RootModel> getRepositoriesDefault();


    @GET("repositories?q=created")
    Call<RootModel> getRepositoryWithDate(@Query("created") String date);

    @GET("repositories?q=created:%3E2015-01-10&sort=stars&order=desc&language=")
    Call<RootModel> getRepositoryWithLanguage(@Query("language") String language);
}
