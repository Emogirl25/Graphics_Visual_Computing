/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.airhockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.airhockey.android.util.LoggerConfig;
import com.airhockey.android.util.ShaderHelper;
import com.airhockey.android.util.TextResourceReader;
import com.example.airhockey.R;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import static android.opengl.GLES20.*;


public class AirHockeyRenderer implements Renderer {
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	private int program;
	private final Context context;
	private static final int POSITION_COMPONENT_COUNT =2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	
	
	public AirHockeyRenderer(Context context){
		this.context = context;
		float[] tableVerticesWithTriangles = {		
			
						
		
							//3rd star
							//Upper outer left side of the star 1
									-0.7f, 0.6f,
									-0.5f, 0.0f,
									0.0f, 0.5f,
							//Bottom outer left side of the star 1
									-0.7f, -0.6f,
									-0.5f, 0.0f,
									0.0f, -0.5f,
							//Bottom outer right side of the star 1
									0.7f, -0.6f,
									0.5f, 0.0f,
									0.0f, -0.5f,
							//Upper outer right side of the star 1
									0.7f, 0.6f,
									0.5f, 0.0f,
									0.0f, 0.5f,
							//center outer right side of the star 1
									0.0f, -0.5f,
									0.5f, 0.0f,
									0.0f, 0.5f,
							//Bottom outer left side of the star 1
									0.0f, 0.5f,
									-0.5f, 0.0f,
									0.0f, -0.5f,
				
									//2nd star
									//upper side of the star
											0.0f, 0.9f,
											-0.3f, 0.2f,
											0.3f, 0.2f,
									//left side of the star
											-0.9f, 0.0f,
											-0.3f, 0.2f,
											-0.3f, -0.2f,
									//bottom side of the star
											0.0f, -0.9f,
											-0.3f, -0.2f,
											0.3f, -0.2f,
									//right side of the star
											0.9f, 0.0f,
											0.3f, 0.2f,
											0.3f, -0.2f,
											
											
	//3rd star
	//Upper outer left side of the star 2
											-0.65f, 0.55f,
											-0.45f, 0.0f,
											0.0f, 0.45f,
									//Bottom outer left side of the star 2
											-0.65f, -0.55f,
											-0.45f, 0.0f,
											0.0f, -0.45f,
									//Bottom outer right side of the star 2
											0.65f, -0.55f,
											0.45f, 0.0f,
											0.0f, -0.45f,
									//Upper outer right side of the star 2
											0.65f, 0.55f,
											0.45f, 0.0f,
											0.0f, 0.45f,
									//center outer right side of the star 2
											0.0f, -0.45f,
											0.45f, 0.0f,
											0.0f, 0.45f,
									//Bottom outer left side of the star 2
											0.0f, 0.45f,
											-0.45f, 0.0f,
											0.0f, -0.45f,
											
				//4rth star
									//upper side of the star
											0.0f, 0.85f,
											-0.28f, 0.18f,
											0.28f, 0.18f,
									//left side of the star
											-0.85f, 0.0f,
											-0.28f, 0.18f,
											-0.28f, -0.18f,
									//bottom side of the star
											0.0f, -0.85f,
											-0.28f, -0.18f,
											0.28f, -0.18f,
									//right side of the star
											0.85f, 0.0f,
											0.28f, 0.18f,
											0.28f, -0.18f,
											
		//5th star

											//Upper outer left side of the star 3
																					-0.59f, 0.49f,
																					-0.39f, 0.0f,
																					0.0f, 0.39f,
																			//Bottom outer left side of the star 3
																					-0.59f, -0.49f,
																					-0.39f, 0.0f,
																					0.0f, -0.39f,
																			//Bottom outer right side of the star 3
																					0.59f, -0.49f,
																					0.39f, 0.0f,
																					0.0f, -0.39f,
																			//Upper outer right side of the star 3
																					0.59f, 0.49f,
																					0.39f, 0.0f,
																					0.0f, 0.39f,
																			//center outer right side of the star 3
																					0.0f, -0.39f,
																					0.39f, 0.0f,
																					0.0f, 0.39f,
																			//Bottom outer left side of the star 3
																					0.0f, 0.39f,
																					-0.39f, 0.0f,
																					0.0f, -0.39f,
																					
															//6th star
																					
																					//upper side of the star
																							0.0f, 0.78f,
																							-0.26f, 0.16f,
																							0.26f, 0.16f,
																					//left side of the star
																							-0.78f, 0.0f,
																							-0.26f, 0.16f,
																							-0.26f, -0.16f,
																					//bottom side of the star
																							0.0f, -0.78f,
																							-0.26f, -0.16f,
																							0.26f, -0.18f,
																					//right side of the star
																							0.78f, 0.0f,
																							0.26f, 0.16f,
																							0.26f, -0.16f,
																							
																							
			//7th star
																							//Upper outer left side of the star 3
																							-0.54f, 0.44f,
																							-0.34f, 0.0f,
																							0.0f, 0.34f,
																					//Bottom outer left side of the star 3
																							-0.54f, -0.44f,
																							-0.34f, 0.0f,
																							0.0f, -0.34f,
																					//Bottom outer right side of the star 3
																							0.54f, -0.44f,
																							0.34f, 0.0f,
																							0.0f, -0.34f,
																					//Upper outer right side of the star 3
																							0.54f, 0.44f,
																							0.34f, 0.0f,
																							0.0f, 0.34f,
																					//center outer right side of the star 3
																							0.0f, -0.39f,
																							0.34f, 0.0f,
																							0.0f, 0.34f,
																					//Bottom outer left side of the star 3
																							0.0f, 0.34f,
																							-0.34f, 0.0f,
																							0.0f, -0.34f,
																							
																							
																							
					// 8th Star
																					//upper side of the star
																							0.0f, 0.71f,
																							-0.24f, 0.14f,
																							0.24f, 0.14f,
																					//left side of the star
																							-0.71f, 0.0f,
																							-0.24f, 0.14f,
																							-0.26f, -0.14f,
																					//bottom side of the star
																							0.0f, -0.71f,
																							-0.24f, -0.14f,
																							0.24f, -0.14f,
																					//right side of the star
																							0.71f, 0.0f,
																							0.24f, 0.14f,
																							0.24f, -0.14f,
																							
																							
					//9th Star
																					//Upper outer left side of the star 3
																							-0.48f, 0.38f,
																							-0.28f, 0.0f,
																							0.0f, 0.28f,
																					//Bottom outer left side of the star 3
																							-0.48f, -0.38f,
																							-0.28f, 0.0f,
																							0.0f, -0.28f,
																					//Bottom outer right side of the star 3
																							0.48f, -0.38f,
																							0.28f, 0.0f,
																							0.0f, -0.28f,
																					//Upper outer right side of the star 3
																							0.48f, 0.38f,
																							0.28f, 0.0f,
																							0.0f, 0.28f,
																					//center outer right side of the star 3
																							0.0f, -0.28f,
																							0.28f, 0.0f,
																							0.0f, 0.28f,
																					//Bottom outer left side of the star 3
																							0.0f, 0.28f,
																							-0.28f, 0.0f,
																							0.0f, -0.28f,
																							
																							
		//10th Star
																					//upper side of the star
																							0.0f, 0.64f,
																							-0.22f, 0.12f,
																							0.22f, 0.12f,
																					//left side of the star
																							-0.64f, 0.0f,
																							-0.22f, 0.12f,
																							-0.22f, -0.12f,
																					//bottom side of the star
																							0.0f, -0.64f,
																							-0.22f, -0.12f,
																							0.22f, -0.14f,
																					//right side of the star
																							0.64f, 0.0f,
																							0.22f, 0.12f,
																							0.22f, -0.12f,
		};
		vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexData.put(tableVerticesWithTriangles);
	}
	
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to red. The first component is
        // red, the second is green, the third is blue, and the last
        // component is alpha, which we don't use in this lesson.
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        
        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        
        program = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        
        if(LoggerConfig.ON){
        	ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);        
    }

	/**
     * onSurfaceChanged is called whenever the surface has changed. This is
     * called at least once when the surface is initialized. Keep in mind that
     * Android normally restarts an Activity on rotation, and in that case, the
     * renderer will be destroyed and a new one created.
     * 
     * @param width
     *            The new width, in pixels.
     * @param height
     *            The new height, in pixels.
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
    }

    /**
     * OnDrawFrame is called whenever a new frame needs to be drawn. Normally,
     * this is done at the refresh rate of the screen.
     */
    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);
        
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 18);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 18, 12);
       
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 30, 18);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 48, 12);
        
        glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 60, 18);
        
        glUniform4f(uColorLocation, 1.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 78, 12);
        
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 90, 18);
        
        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 108, 12);
        
        glUniform4f(uColorLocation, 0.5f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 120, 18);
        
        glUniform4f(uColorLocation, 0.0f, 0.5f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 138, 12);
    }
}
