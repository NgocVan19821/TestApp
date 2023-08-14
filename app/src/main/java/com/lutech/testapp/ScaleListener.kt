package com.lutech.testapp

import android.view.ScaleGestureDetector
import android.widget.ImageView

class ScaleListener internal constructor(private var mImageView: ImageView) :
    ScaleGestureDetector.SimpleOnScaleGestureListener() {
    private var mScaleFactor = 1.0f

    override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
        mScaleFactor *= scaleGestureDetector.scaleFactor
        mScaleFactor = Math.max(
            0.1f,
            Math.min(mScaleFactor, 10.0f)
        )
        mImageView.scaleX = mScaleFactor
        mImageView.scaleY = mScaleFactor
        return true
    }

}