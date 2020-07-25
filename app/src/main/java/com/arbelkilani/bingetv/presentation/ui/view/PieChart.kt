package com.arbelkilani.bingetv.presentation.ui.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.databinding.BindingAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class PieChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val DEFAULT_UNSPECIFIED_SIZE = 180
        private const val DEFAULT_BORDER_THICKNESS = 4f

        @JvmStatic
        @BindingAdapter("custom:pie_chart")
        fun PieChart.populatePieChart(list: List<GenreEntity>?) {
            if (list.isNullOrEmpty())
                return

            datas.clear()
            datas.addAll(list.filter { it.percentage > 0 })
            invalidate()
        }
    }

    private var paint: Paint = Paint()
    private var center = 0f
    private var size: Int = 0

    // border
    private var borderThickness = DEFAULT_BORDER_THICKNESS

    // slice
    private var sweepAngle = 0f
    private var startAngle = 0f
    private var middleAngle = 0f

    private var datas: MutableList<GenreEntity>

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)

        datas = mutableListOf()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.apply {
            val typedArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        center = size / 2f

        drawSlice(canvas)
        //drawSeparator(canvas)
        //drawBorder(canvas)
        drawTitles(canvas)


    }

    private fun drawTitles(canvas: Canvas?) {

        for (data in datas) {

            val rect = Rect()

            val textPaint = TextPaint()
            textPaint.color = Color.BLACK
            textPaint.isAntiAlias = true

            val path = Path()

            canvas?.apply {


                val text = data.name

                val radius = center - borderThickness
                sweepAngle = data.percentage * 360f / 100

                textPaint.getTextBounds(text, 0, text.length, rect)
                textPaint.textSize = (radius - rect.width()) * .13f

                val offset = (radius / 2) - (rect.width() * 2)
                middleAngle = startAngle + (sweepAngle / 2)

                val startX =
                    center + offset * cos(Math.toRadians(middleAngle.toDouble())).toFloat()
                val startY =
                    center + offset * sin(Math.toRadians(middleAngle.toDouble())).toFloat()

                val endX = center + radius * cos(Math.toRadians(middleAngle.toDouble())).toFloat()
                val endY = center + radius * sin(Math.toRadians(middleAngle.toDouble())).toFloat()

                path.moveTo(startX, startY)
                path.lineTo(endX, endY)
                path.close()

                drawTextOnPath(
                    text.toCharArray(),
                    0,
                    text.length,
                    path,
                    rect.width().toFloat(),
                    rect.height().toFloat(),
                    textPaint
                )

            }

            startAngle += sweepAngle
        }
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

    private fun drawSlice(
        canvas: Canvas?
    ) {
        paint.style = Paint.Style.FILL
        paint.color = Color.RED

        for ((index, data) in datas.withIndex()) {

            paint.alpha = 255 / (index + 1)

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
        paint.color = Color.BLACK
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