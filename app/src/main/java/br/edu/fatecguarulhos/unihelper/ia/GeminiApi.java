package br.edu.fatecguarulhos.unihelper.ia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeminiApi {
    @POST("v1beta/models/gemini-2.5-flash-lite:generateContent")
    Call<GeminiResponse> gerarResposta(
            @Query("key") String apiKey,
            @Body GeminiRequest request
    );
}
