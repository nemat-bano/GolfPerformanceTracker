package com.sample.ui.xml.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class PerformanceMetricView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var progress = 0f
    private var label = ""
    private var valueText = ""

    private val density = resources.displayMetrics.density

    private val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(31, 31, 31)
    }

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(90, 90, 90)
        strokeWidth = 10f * density
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(46, 125, 50)
        strokeWidth = 10f * density
        strokeCap = Paint.Cap.ROUND
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 15f * density
        typeface = Typeface.DEFAULT_BOLD
    }

    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        textSize = 14f * density
    }

    fun setMetric(
        label: String,
        valueText: String,
        progress: Float
    ) {
        this.label = label
        this.valueText = valueText
        this.progress = progress.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = 16f * density
        val cardRect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        canvas.drawRoundRect(cardRect, radius, radius, cardPaint)

        val padding = 18f * density
        val topTextY = 32f * density

        canvas.drawText(label, padding, topTextY, labelPaint)

        val valueWidth = valuePaint.measureText(valueText)
        canvas.drawText(
            valueText,
            width - padding - valueWidth,
            topTextY,
            valuePaint
        )

        val lineY = 70f * density
        val startX = padding
        val endX = width - padding

        canvas.drawLine(startX, lineY, endX, lineY, trackPaint)

        canvas.drawLine(
            startX,
            lineY,
            startX + (endX - startX) * progress,
            lineY,
            progressPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = (96 * density).toInt()
        val width = MeasureSpec.getSize(widthMeasureSpec)

        setMeasuredDimension(
            width,
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }
}