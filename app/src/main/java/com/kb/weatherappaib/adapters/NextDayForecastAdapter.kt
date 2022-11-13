package com.kb.weatherappaib.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kb.weatherappaib.R
import com.kb.weatherappaib.models.MyList
import com.kb.weatherappaib.utils.DateUtil
import kotlin.math.roundToInt

/**
 * This is an array adapter class which helps for display next five days weather forecast
 */
class NextDayForecastAdapter(private val context: Context, private val forecasts: List<MyList>) :
    RecyclerView.Adapter<NextDayForecastAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.next_day_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val forecast = forecasts[position]
        holder.textViewMaxTemperature.text =
            context.getString(R.string.high_temp).plus(roundedValue(forecast.main.temp_max))
        holder.textViewMinTemperature.text =
            context.getString(R.string.low_temp).plus(roundedValue(forecast.main.temp_min))
        if (forecast.weather[CONSTANT_ZERO].main == context.getString(R.string.cloud)) {
            holder.imageViewCloudType.setImageResource(R.drawable.ic_cloud)
        } else {
            holder.imageViewCloudType.setImageResource(R.drawable.ic_sun)
        }
        holder.textViewClimateType.text = forecast.weather[CONSTANT_ZERO].main
        holder.textViewDay.text = DateUtil.getDayFromDate(forecast.dt_txt)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Future Enhancement", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDay: TextView = itemView.findViewById(R.id.textViewDay)
        var textViewClimateType: TextView = itemView.findViewById(R.id.textViewClimateType)
        var textViewMaxTemperature: TextView = itemView.findViewById(R.id.textViewMaxTemperature)
        var textViewMinTemperature: TextView = itemView.findViewById(R.id.textViewMinTemperature)
        var imageViewCloudType: ImageView = itemView.findViewById(R.id.imageViewCloudType)
    }

    private fun roundedValue(givenAmount: Double): String {
        return ((givenAmount * 100.0).roundToInt() / 100.0).toString() + context.getString(R.string.celsius)
    }

    companion object {
        const val CONSTANT_ZERO = 0
    }
}