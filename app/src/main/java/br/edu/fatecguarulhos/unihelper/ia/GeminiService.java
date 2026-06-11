package br.edu.fatecguarulhos.unihelper.ia;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import br.edu.fatecguarulhos.unihelper.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class GeminiService {

    private final GeminiApi api;

    public GeminiService() {

        OkHttpClient client =
                new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://generativelanguage.googleapis.com/")
                        .client(client)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        )
                        .build();

        api = retrofit.create(GeminiApi.class);
    }

    public void enviarPergunta(
            String pergunta,
            GeminiCallBack callback) {

        GeminiRequest.Part part =
                new GeminiRequest.Part(pergunta);

        GeminiRequest.Content content =
                new GeminiRequest.Content(
                        Arrays.asList(part)
                );

        GeminiRequest request =
                new GeminiRequest(
                        Arrays.asList(content)
                );

        api.gerarResposta(
                BuildConfig.GEMINI_API_KEY,
                request
        ).enqueue(new Callback<GeminiResponse>() {

            @Override
            public void onResponse(
                    Call<GeminiResponse> call,
                    Response<GeminiResponse> response) {

                if (!response.isSuccessful()) {
                    try {
                        String erro = response.errorBody().string();

                        callback.onErro("HTTP " + response.code() + "\n\n" + erro);

                    } catch (Exception e) {

                        callback.onErro("HTTP " + response.code());
                    }

                    return;
                }

                GeminiResponse body = response.body();

                if (body == null || body.candidates == null || body.candidates.isEmpty()) {

                 callback.onErro("Resposta vazia");
                    return;
                }

            String texto = body.candidates.get(0).content.parts.get(0).text;

             callback.onResposta(texto);
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {

                callback.onErro("Falha na conexão: " + t.getMessage());
            }
        });
    }
}