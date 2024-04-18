package org.mycode.mykotlinapplication

import android.opengl.GLSurfaceView
import android.opengl.GLU
import javax.microedition.khronos.opengles.GL10


class OpenGLRenderer : GLSurfaceView.Renderer {
    private val mCube = Cube()
    private var mCubeRotation = 0f
    private var mCubeRotationDirection = 1f

    override fun onSurfaceCreated(gl: GL10, p1: javax.microedition.khronos.egl.EGLConfig?) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f)
        gl.glClearDepthf(1.0f)
        gl.glEnable(GL10.GL_DEPTH_TEST)
        gl.glDepthFunc(GL10.GL_LEQUAL)
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST)
    }
    fun changeRotationDirection(distanceX: Float) {
        mCubeRotationDirection = if (distanceX > 0) 1f else -1f
    }
    override fun onDrawFrame(gl: GL10) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl.glLoadIdentity()

        gl.glTranslatef(0.0f, 0.0f, -10.0f)
        gl.glRotatef(mCubeRotation, 1.0f, 1.0f, 1.0f)

        mCube.draw(gl)

        gl.glLoadIdentity()

        mCubeRotation -= 0.55f
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        gl.glViewport(0, 0, width, height)
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadIdentity()
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / height.toFloat(), 0.1f, 100.0f)
        gl.glViewport(0, 0, width, height)

        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadIdentity()
    }
}
