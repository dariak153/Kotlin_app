package org.mycode.mykotlinapplication

import android.opengl.GLSurfaceView
import android.opengl.GLU
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class OpenGLActivity : AppCompatActivity() {
    private lateinit var mGestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val renderer = OpenGLRenderer()
        val view = GLSurfaceView(this)
        view.setRenderer(OpenGLRenderer())
        setContentView(view)
        mGestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                p0: MotionEvent,
                p1: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                renderer.changeRotationDirection(distanceX)
                return true
            }
        })
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}
