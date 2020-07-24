package com.arbelkilani.bingetv.presentation.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.utils.px
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PieChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val DEFAULT_UNSPECIFIED_SIZE = 180
        private const val DEFAULT_BORDER_THICKNESS = 4f
    }

    private var paint: Paint = Paint()
    private var size = 0
    private var center = 0f

    // border
    private var borderThickness = DEFAULT_BORDER_THICKNESS

    // slice
    private var sweepAngle = 0f
    private var startAngle: Float = 0f

    data class Test(
        var percentage: Int = 0,
        var color: Int = Color.BLACK,
        var name: String = ""
    )

    private lateinit var datas: MutableList<Test>

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)

        datas = mutableListOf()

        datas.add(Test(20, Color.WHITE, "name 1"))
        datas.add(Test(20, Color.CYAN, "name 2"))
        datas.add(Test(30, Color.YELLOW, "name 3"))
        datas.add(Test(30, Color.LTGRAY, "name 4"))

    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.apply {
            val typedArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> size = DEFAULT_UNSPECIFIED_SIZE.px
            MeasureSpec.EXACTLY -> size = min(measuredWidth, measuredHeight)
        }
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        center = size / 2f


        drawSlice(canvas)
        //drawSeparator(canvas)
        //drawBorder(canvas)
    }

    private fun drawBorder(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.DKGRAY
        paint.strokeWidth = borderThickness

        canvas?.apply {
            val radius = center - paint.strokeWidth
            drawCircle(center, center, radius, paint)
        }
    }

    private fun blendColors(from: Int, to: Int, ratio: Float): Int {
        val inverseRatio = 1f - ratio
        val r: Float = Color.red(to) * ratio + Color.red(from) * inverseRatio
        val g: Float = Color.green(to) * ratio + Color.green(from) * inverseRatio
        val b: Float = Color.blue(to) * ratio + Color.blue(from) * inverseRatio
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }

    private fun drawSlice(
        canvas: Canvas?
    ) {
        val rnd = java.util.Random()

        paint.style = Paint.Style.FILL

        for ((index, data) in datas.withIndex()) {
            paint.color =
                blendColors(
                    ContextCompat.getColor(context, R.color.colorPrimaryDark),
                    ContextCompat.getColor(context, R.color.colorAccent),
                    (1f - index * 0.1f)
                )
            sweepAngle = data.percentage * 360f / 100

            canvas?.apply {
                val radius = center - borderThickness
                val rectF =
                    RectF(center - radius, center - radius, center + radius, center + radius)

                drawArc(rectF, startAngle, sweepAngle, true, paint)
            }

            startAngle += sweepAngle
        }


    }

    private fun drawSeparator(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.strokeWidth = borderThickness

        for (data in datas) {
            sweepAngle = data.percentage * 360f / 100

            canvas?.apply {
                val radius = center - borderThickness
                val stopX =
                    (center + (radius * cos(Math.toRadians(startAngle.toDouble())))).toFloat()
                val stopY =
                    (center + (radius * sin(Math.toRadians(startAngle.toDouble())))).toFloat()

                drawLine(center, center, stopX, stopY, paint)
            }

            startAngle += sweepAngle
        }


    }
}