package eu.tutorials.grafikkotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import eu.tutorials.grafikkotlin.api.Api
import eu.tutorials.grafikkotlin.api.Service
import eu.tutorials.grafikkotlin.data.Login
import eu.tutorials.grafikkotlin.data.ResultLogin
import eu.tutorials.grafikkotlin.data.getOrders
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    var lineChart: LineChart?= null
    private var scoreList = ArrayList<Score>()
    private var scoreL = ArrayList<Score>()
    var token : String = ""


    data class Score(
        val name:String,
        val score: Int,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()

        lineChart = findViewById(R.id.lineChart)

        initLineChart()

        setDataToLineChart()

    }

    private fun login(){
        val body = Login(
            CompanyDB = "SBODEMOAU",
            UserName = "manager",
            Password = "P@ssw0rd"
        )
        Api.service(Service::class.java).login(body).enqueue(
            object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    response.body().let {
                        Log.d("Login", "success")
                        //token = it?.sessionId.toString()
                        getOrder(token)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("Gagal",t.message.toString())
                }
            }
        )
//        Api.service(Service::class.java).login(body).enqueue(
//            object : Callback<ResultLogin> {
//                override fun onResponse(
//                    call: Call<ResultLogin>,
//                    response: Response<ResultLogin>
//                ) {
//                    Log.d("Login", "success")
//                    token = response.body()?.sessionId.toString()
//                    getOrder(token)
//                }
//                override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
//                    Log.e("Login", "failed", t)
//                }
//            }
//        )
    }

    private fun getOrder(token: String){
        Api.service(Service::class.java).getOrders(token).enqueue(
            object : Callback<getOrders> {
                override fun onResponse(
                    call: Call<getOrders>,
                    response: Response<getOrders>
                ) {
                    Log.d("Login", "success")
                }

                override fun onFailure(call: Call<getOrders>, t: Throwable) {
                    Log.e("Login", "failed", t)
                }
            }
        )
    }

    private fun initLineChart() {

        lineChart?.axisLeft?.setDrawGridLines(false)
        val xAxis: XAxis = lineChart?.xAxis!!
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart?.axisRight?.isEnabled = false

        lineChart?.legend?.isEnabled = false

        lineChart?.description?.isEnabled = false

        lineChart?.animateX(1000, Easing.EaseInSine)

        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        val entries: ArrayList<Entry> = ArrayList()
        val entries2: ArrayList<Entry> = ArrayList()

        scoreList = getScoreList()
        scoreL = getScoreL()

        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.score.toFloat()))
        }
        for (i in scoreL.indices) {
            val score = scoreL[i]
            entries2.add(Entry(i.toFloat(), score.score.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")
        val lineDataS = LineDataSet(entries2, "")
        lineDataSet.setColor(Color.rgb(178, 34, 33))
        lineDataS.setColor(Color.rgb(10, 99, 99))


        val dataSet: ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(lineDataSet)
        dataSet.add(lineDataS)

        val data = LineData(dataSet)
        lineChart?.data = data

        lineChart?.invalidate()
    }

    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("A", 56))
        scoreList.add(Score("B", 75))
        scoreList.add(Score("C", 85))
        scoreList.add(Score("D", 45))
        scoreList.add(Score("E", 63))
        scoreList.add(Score("F", 120))
        scoreList.add(Score("G", 80))

        return scoreList
    }

    private fun getScoreL(): ArrayList<Score> {
        scoreL.add(Score("A", 59))
        scoreL.add(Score("B", 85))
        scoreL.add(Score("C", 65))
        scoreL.add(Score("D", 55))
        scoreL.add(Score("E", 33))
        scoreL.add(Score("F", 50))
        scoreL.add(Score("G", 75))

        return scoreL
    }
}

