package com.example.gitapi.Data;

import com.example.gitapi.Pojo.RootModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RepositoryClient {

    private static final String Base_Url="https://api.github.com/search/";
    private final RepositoryInterface repositoryInterface;
    private static RepositoryClient Instance;

    public RepositoryClient() {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                . build();

        repositoryInterface= retrofit.create(RepositoryInterface.class);

    }

    public static RepositoryClient getInstance() {
        if(null== Instance){
            Instance=new RepositoryClient();
        }
        return Instance;
    }

    public Call<RootModel> getRepositories(){
        return repositoryInterface.getRepositoriesDefault();

    }

    public Call<RootModel> getRepositoriesWithDate(String date){
        return repositoryInterface.getRepositoryWithDate(date);

    }


    public Call<RootModel> getRepositoriesWithLanguage(String language){
        return repositoryInterface.getRepositoryWithLanguage(language);

    }
}
