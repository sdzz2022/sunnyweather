package com.example.sunnyweather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather
import com.qweather.sdk.view.QWeather.OnResultWeatherNowListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HeConfig.init("HE2306011515341246","d2d1139fa8b74351a05db37cd9c297fe")
        HeConfig.switchToDevService()
        QWeather.getWeatherNow(
            this@MainActivity,
            "101280803",
            Lang.ZH_HANS,
            Unit.METRIC,
            object : OnResultWeatherNowListener {
                override fun onError(e: Throwable) {
                    Log.i("TAG", "getWeather onError: $e")
                }

                override fun onSuccess(weatherBean: WeatherNowBean) {
                    Log.i("TAG", "getWeather onSuccess: " + Gson().toJson(weatherBean))
                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK === weatherBean.code) {
                        val now = weatherBean.now
                        Log.d("weather",now.toString())
                    } else {
                        //在此查看返回数据失败的原因
                        val code: Code = weatherBean.code
                        Log.i("TAG", "failed code: $code")
                    }
                }
            })
    }
}