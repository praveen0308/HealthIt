package com.jmm.healthit.ui.dashboard;

import static com.jmm.healthit.network.NewsApiClient.API_KEY;
import static com.jmm.healthit.network.WeatherApiClient.WEATHER_API_KEY;
import static com.jmm.healthit.utils.Extensions.fromListToSet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.adapters.DiseaseWatchListAdapter;
import com.jmm.healthit.adapters.FoodTipsAdapter;
import com.jmm.healthit.databinding.FragmentHomeBinding;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.model.Disease;
import com.jmm.healthit.model.FoodModel;
import com.jmm.healthit.model.UserModel;
import com.jmm.healthit.model.news.ArticlesItem;
import com.jmm.healthit.model.news.ResponseModel;
import com.jmm.healthit.model.weather.WeatherResponse;
import com.jmm.healthit.network.NewApiService;
import com.jmm.healthit.network.NewsApiClient;
import com.jmm.healthit.network.WeatherApiClient;
import com.jmm.healthit.network.WeatherApiService;
import com.jmm.healthit.ui.disease.DiseaseDetail;
import com.jmm.healthit.ui.welcome.SignIn;
import com.jmm.healthit.utils.AlarmReceiver;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements DiseaseWatchListAdapter.DiseaseAdapterInterface {


    private FragmentHomeBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = Home.class.getSimpleName();
    private ProgressBarHandler progressBar;
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private DiseaseWatchListAdapter rvAdapter;
    private NavController navController;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
//        stopRepeatingTask();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        startRepeatingTask();
        setAqiLayout();
        setReminderLayout();
        setupRvData();
        getCurrentWeather();
        setQuote();
        binding.tvGreeting.setText("Hello "+ PreferenceUtils.getUsername(getContext()));

        /*alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, alarmIntent); */
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){

                    String diseaseName = v.getText().toString().trim();

                    if (diseaseName.length()>2){
                        binding.etSearch.setText("");
                        Intent intent = new Intent(requireActivity(),DiseaseDetail.class);
                        intent.putExtra("diseaseName",diseaseName.substring(0, 1).toUpperCase() + diseaseName.substring(1));
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(), "Enter valid disease name..", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });

        binding.layoutReminder.getRoot().setOnClickListener(view1 ->{
            startActivity(new Intent(getActivity(),SetReminder.class));
                }

        );
    }

    private void getCurrentWeather(){
        progressBar.show();
        final WeatherApiService apiService = WeatherApiClient.getClient().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getCurrentWeather(WEATHER_API_KEY,PreferenceUtils.getCity(getContext()), "yes");
        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                progressBar.hide();


                if (response.body()!=null){
                    Log.d(TAG, "onResponse: "+response.body().getLocation().getRegion());
                    binding.layoutAqi.tvTitle.setText(String.valueOf(response.body().getCurrent().getAirQuality().getPm10()));
                    binding.layoutAqi.tvDescription.setText(String.valueOf(response.body().getCurrent().getCondition().getText()));
                    binding.layoutAqi.tvCityName.setText(PreferenceUtils.getCity(getContext()));
                    binding.layoutAqi.tvCityName.setVisibility(View.VISIBLE);
                }

                getUserWatchlist();
                /*RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_insert_photo_24)
                        .error(R.drawable.ic_baseline_error_24);

                Glide.with(binding.getRoot().getContext())
                        .load(response.body().getCurrent().getCondition().getIcon())
                        .apply(options)
                        .into(binding.layoutAqi.icImage);*/
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressBar.hide();
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                Log.e("out", t.toString());
            }
        });
    }

    private void setAqiLayout(){
        binding.layoutAqi.tvContext.setText("AQI");
        binding.layoutAqi.tvTitle.setText("000");
        binding.layoutAqi.tvTitle.setVisibility(View.VISIBLE);
        binding.layoutAqi.tvDescription.setText("Cloudy Air");
    }


    private void setReminderLayout(){
        binding.layoutReminder.tvContext.setText("Timer");
        binding.layoutReminder.getRoot().setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.color2));
        binding.layoutReminder.icImage.setImageResource(R.drawable.ic_baseline_timer_24);
        binding.layoutReminder.tvDescription.setText("Set Reminder");
    }

    private void setupRvData(){
        rvAdapter = new DiseaseWatchListAdapter(this);
        binding.rvDisease.setHasFixedSize(true);
        binding.rvDisease.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDisease.setAdapter(rvAdapter);
    }

    private void setQuote(){
        String[] quotes = getResources().getStringArray(R.array.quotes);
        Random r = new Random();
        int low = 0;
        int high = quotes.length-1;
        int result = r.nextInt(high-low) + low;

        binding.layoutFact.tvMessage.setText(quotes[result]);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
               setQuote();
            } finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };


    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }


    private void getUserWatchlist(){
        progressBar.show();
        db.collection("users").document(PreferenceUtils.getUserID(getContext())).get().addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()) {
                Log.d(TAG, "fetched user: " + task.getResult());

                DocumentSnapshot userSnap =task.getResult();
                UserModel userModel = userSnap.toObject(UserModel.class);

                PreferenceUtils.saveUserWatchList(fromListToSet(userModel.getWatchList()),getContext());
                Set<String> items = PreferenceUtils.getUserWatchList(getContext());
                Log.d(TAG, "getUserWatchlist: "+items);
                if (!items.isEmpty()) getUserWatchListedDiseaseList();

            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error : " + e.toString());
        });

    }


    private void getUserWatchListedDiseaseList(){
        progressBar.show();
        db.collection("disease").get().addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()) {
                Log.d(TAG, "fetched documents: " + task.getResult().getDocuments().size());
                int noOfResults = task.getResult().getDocuments().size();
                if (noOfResults == 0) {
                    Toast.makeText(getContext(), "Unknown disease", Toast.LENGTH_SHORT).show();
                } else {
                    List<DocumentSnapshot> results =task.getResult().getDocuments();
                    List<Disease> items = new ArrayList<>();
                    for (int i=0;i<results.size();i++){
                        items.add(results.get(i).toObject(Disease.class));
                    }
                    for (int i=0;i<results.size();i++){
                        items.get(i).setDiseaseId(results.get(i).getId());
                    }
                    List<Disease> filteredItems = new ArrayList<>();
                    Set<String> watchListItems = PreferenceUtils.getUserWatchList(getContext());
                    for(Disease item:items){
                        if (watchListItems.contains(item.getDiseaseId())){
                            filteredItems.add(item);
                        }
                    }

                    rvAdapter.setDiseaseItems(filteredItems);

                }


            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error : " + e.toString());
        });


    }
    @Override
    public void onItemClick(Disease item) {
        Intent intent = new Intent(requireActivity(),DiseaseDetail.class);
        intent.putExtra("diseaseName",item.getDiseaseName());
        startActivity(intent);
    }
}