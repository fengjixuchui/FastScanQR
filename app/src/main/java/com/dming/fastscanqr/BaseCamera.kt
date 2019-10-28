package com.dming.fastscanqr

import com.dming.fastscanqr.utils.DLog
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

open class BaseCamera {

    protected val mPreviewSizes: MutableList<CameraSize> = ArrayList()
    protected var viewWidth: Int = 0
    protected var viewHeight: Int = 0
    protected var mCameraSize: CameraSize? = null

    protected fun getDealCameraSize(width: Int, height: Int, rotation: Int): CameraSize {
        val lessThanView = ArrayList<CameraSize>()
        DLog.i("getDealCameraSize width>  $width height>> $height")
        for (size in mPreviewSizes) {
            if (rotation == 90 || rotation == 270) { // width > height normal
                lessThanView.add(CameraSize(size.height, size.width, size)) // ?
            } else { // width < height normal  0 180
                lessThanView.add(CameraSize(size.width, size.height, size))
            }
        }
        var cSize: CameraSize? = null
        var diffMinValue = Float.MAX_VALUE
        for (size in lessThanView) {
            val diffWidth = abs(width - size.width)
            val diffHeight = abs(height - size.height)
            val diffValue = sqrt(0.0f + diffWidth * diffWidth + diffHeight * diffHeight)
            if (diffValue < diffMinValue) {  // 找出差值最小的数
                diffMinValue = diffValue
                cSize = size
            }
        }
        if (cSize == null) {
            cSize = lessThanView[0]
        }
        DLog.i("suitableSize>" + cSize!!.toString())
        return cSize
    }

}