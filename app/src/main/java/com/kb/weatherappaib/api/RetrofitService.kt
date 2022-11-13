import com.kb.weatherappaib.models.CityDataModel
import com.kb.weatherappaib.models.FiveDayForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/geo/1.0/direct")
    suspend fun getCity(
        @Query("q") userTypedCity: String,
        @Query("appid") appId: String
    ): Response<CityDataModel>

    @GET("/data/2.5/forecast")
    suspend fun getFiveDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): Response<FiveDayForecast>
}