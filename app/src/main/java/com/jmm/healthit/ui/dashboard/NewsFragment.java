package com.jmm.healthit.ui.dashboard;

import static com.jmm.healthit.network.NewsApiClient.API_KEY;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jmm.healthit.adapters.NewsAdapter;
import com.jmm.healthit.databinding.FragmentNewsBinding;
import com.jmm.healthit.model.news.ArticlesItem;
import com.jmm.healthit.model.news.ResponseModel;
import com.jmm.healthit.network.NewsApiClient;
import com.jmm.healthit.network.NewApiService;
import com.jmm.healthit.utils.ProgressBarHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterInterface {


    private FragmentNewsBinding binding;
    private NewsAdapter newsAdapter;
    private ProgressBarHandler progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        showLoading(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvArticles();
        showLoading(true);
        final NewApiService apiService = NewsApiClient.getClient().create(NewApiService.class);
        Call<ResponseModel> call = apiService.getLatestNews("health", API_KEY);
        call.enqueue(new Callback<ResponseModel>() {

            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                showLoading(false);
                if (response.body().getStatus().equals("ok")) {
                    List<ArticlesItem> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        newsAdapter.setArticlesItems(articleList);
                        Log.d("articles",articleList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showLoading(false);
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                Log.e("out", t.toString());
            }
        });
    }

    private void setupRvArticles(){
        newsAdapter = new NewsAdapter(this);
        binding.rvNews.setHasFixedSize(true);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(newsAdapter);
    }

    @Override
    public void onItemClick(ArticlesItem item) {

    }
    private void showLoading(boolean visibility){
        if (visibility) progressBar.show();
        else progressBar.hide();
    }
}