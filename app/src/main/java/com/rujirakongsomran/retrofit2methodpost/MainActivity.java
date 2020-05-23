package com.rujirakongsomran.retrofit2methodpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rujirakongsomran.retrofit2methodpost.Model.Request;
import com.rujirakongsomran.retrofit2methodpost.Model.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {
        tvResult = (TextView) findViewById(R.id.tvResult);

        // Retrofit2
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Request request = new Request(
                "Hamory",
                "60000",
                "26"
        );

        Call<Response> call = jsonPlaceHolderApi.postCreate(request);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()) {
                    tvResult.setText("Code: " + response.code());
                    return;
                }

                Response res = response.body();

                Gson gson = new Gson();
                String json = gson.toJson(res);
                String jsonFormat = formatString(json);
                tvResult.setText(jsonFormat);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });

    }

    public static String formatString(String text) {

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
