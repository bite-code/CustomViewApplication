package com.example.customviewapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.pow

/**
 * Created by Mahdi.Tajik on 2020/03/05.
 **/
class CustomView : View {


    private lateinit var mRectanle: Rect
    private lateinit var mPaintSquar: Paint
    private lateinit var mPaintCircle: Paint
    var squareColor = Color.BLUE
    var cyrcleRadius = 80f
    private var RECANGEL_SIZE = 200
    private val MARGIN = 50


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    constructor(context: Context?) : super(context) {
        init(null)
    }

    private fun init(attrs: AttributeSet?) {

        mRectanle = Rect()
        mPaintSquar = Paint(Paint.ANTI_ALIAS_FLAG)

        mPaintCircle = Paint(Paint.ANTI_ALIAS_FLAG)

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
            squareColor = typedArray.getColor(R.styleable.CustomView_square_color, Color.BLUE)
            RECANGEL_SIZE =
                typedArray.getDimensionPixelSize(R.styleable.CustomView_square_size, 100)
            typedArray.recycle()
        }

        mPaintSquar.color = squareColor

    }

    fun switchColor() {

        squareColor
        mPaintSquar.color = if (mPaintSquar.color == squareColor) Color.GREEN else squareColor
        postInvalidate()

    }


    override fun onDraw(canvas: Canvas?) {
        mRectanle.left = MARGIN
        mRectanle.top = MARGIN

        with(mRectanle) {
            right = left + RECANGEL_SIZE
            bottom = top + RECANGEL_SIZE
        }
        canvas?.drawRect(mRectanle, mPaintSquar)

        if (mCircleX == 0f || mCircleY == 0f) {
            mCircleX = width - cyrcleRadius - MARGIN
            mCircleY = (mRectanle.top + (mRectanle.height() / 2)).toFloat()
        }


        canvas?.drawCircle(mCircleX, mCircleY, cyrcleRadius, mPaintCircle)


    }

    var mCircleX = 0f
    var mCircleY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val defaultValue = super.onTouchEvent(event)

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y

                val dx = (x - mCircleX).pow(2)
                val dy = (y - mCircleY).pow(2)

                if (dx + dy < (cyrcleRadius).pow(2)) {
                    //Touched

                    mCircleY = y
                    mCircleX = x

                    postInvalidate()
                    return true
                }

            }

        }

        return defaultValue
    }

}