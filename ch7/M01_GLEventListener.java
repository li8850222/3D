package ch7;import gmaths.*;
import ch7.SGNode;
import ch7.TransformNode;

import java.lang.reflect.Modifier;
import java.nio.*;
import java.util.Random;

import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
  
public class M01_GLEventListener implements GLEventListener {
	
	
	  private double startTime;
	  int nicai = 60;//下面那根
	  int nizaicai =240;
	  int turnlightl=1;
	  int turnlightr=1;
	  int turnlight=1;
	  int angle1 = 60;
	  int anglenew=60;
	   float vx =0.0f;
	   float vz =0.0f;
	   int mark = 0;
	  Vec3 shade_position;
	  ModelNode cube1Node5;
  
  private static final boolean DISPLAY_SHADERS = false;
    
  public M01_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(4f,6f,15f));
    this.camera.setTarget(new Vec3(0f,5f,0f));
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
//    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
//    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    disposeModels(gl);
  }

  
  
  
  
  
  // ***************************************************
  /* INTERACTION
   *
   *
   */

  public void turnlightl() {
	  turnlightl=turnlightl*-1;
	   }
  public void turnlightr() {
	  turnlightr=turnlightr*-1;
	   }
  public void turntablelamp() {
	  turnlight=turnlight*-1;
	   }
   public void incXPosition() {
     xPosition += 0.5f;
     if (xPosition>5f) xPosition = 5f;
     updateX();
   }
   
   public void decXPosition() {
     xPosition -= 0.5;
     if (xPosition<-5f) xPosition = -5f;
     updateX();
   }
   //改变灯的位置---------------------------------------------------------------------
   public void changposition() {
	   Random a = new Random();
	   Random b = new Random(2);
	   Random c = new Random();
	   Random d = new Random(4);
	   int a1 = a.nextInt(10);
	   int b1 = b.nextInt(5);
	   int c1 = c.nextInt(10);
	   int d1 = d.nextInt(3);


	   if(a1>5) {
		     xPosition += b1;
		     if (xPosition>5f) {
		    	 xPosition = 5f;
		     }
		    
	   }else {
		   xPosition -= b1;
		      if (xPosition<-5f) {
		    	 xPosition = -5f;
				
			}
		   
	   }
	   if(c1>5) {
	
		     zPosition += d1;
		     if (zPosition>3f) {
		    	 zPosition = 3f;
		     }
	   }else {
		   
		   zPosition -= d1;
		   if (zPosition<-3f) {
		    	 zPosition =-3f;
		    	 }
	   }
	     updateX();
	   }
   //跳动方法---------------------------------------------------------------------
   float o_vh = 0.05f;
   float g = 0.001f;
   float vh = o_vh;
   float h = 0;
   float x = 0;
   public void jump() {
	   if(angle1==anglenew) {
		   if (vh>0) {
		 	   nicai -=1;
		 	   nizaicai +=2;
		   }else if(vh<0) {
		 	   nicai +=1;
		 	   nizaicai -=2;
		   }
	    yPosition+=vh;
//	    float pp = yPosition;
	    vh-=g;
	    xPosition+=vx;
	    zPosition+=vz;
	    if (yPosition<=0) {
	     yPosition = 0;
	     jumpon=0;
	     vh = o_vh;
	    }
	    
	    if(xPosition>=5) {
	    	xPosition =5;
	    }
	    if(xPosition<=-5) {
	    	xPosition =-5;
	    }
	    if(zPosition>=3) {
	    	zPosition =3;
	    }
	    if(zPosition<=-3) {
	    	zPosition =-3;
	    }
	     updateX();
	   } else {
		   if(anglenew-angle1>=180) {
			   angle1-=5;
		   }else if(anglenew-angle1>0){
			   angle1+=5;
		   }else if(anglenew-angle1<-180) {
			   angle1+=5;
		   }else {
			   angle1-=5;
		   }
		   
		   if(angle1>=360) {
			   angle1=0;
		   }
		   else if(angle1==-5) {
			   angle1=355;
		   }
		   light2.xmove =  (float)Math.sqrt(2)*(float)Math.sin(Math.toRadians(angle1+90));
		   light2.zmove =  (float)Math.sqrt(2)*(float)Math.cos(Math.toRadians(angle1+90));
	   }
   }
   //跳动方法---------------------------------------------------------------------
   
   private void updateX() {
     translateX.setTransform(Mat4Transform.translate(xPosition,yPosition,zPosition));
     translateX.update(); // IMPORTANT – the scene graph has changed
   }
  
  
  
  
  
  
  
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */
  int jumpon =0;
  int vr=0,vf=0;
  private Camera camera;
  private Model tt1,brush,book,picture,holder,cube,pen1,pen2,pen3,chuang1,chuang2, sphere, cube1,tui1,tui2,tui3,tui4,wall,window,floor,wall2,dengzuo2,lianjie1,lianjie2,lianjie3,dengzhao,zhijia1,zhijia2,wall3,wall1;
//  private Model dengzuo;
  private Light light,light1,light2;
 
//  private Mat4 perspective;
  private SGNode twoBranchRoot;
  
  private TransformNode translateX,rotateAll1, rotateUpper,rotateUpper1,rotateUpper2,rotateUpper3,rotateUpper4,rotateUpper5;
  private float xPosition = 0;
  private float zPosition = 0;
  private float yPosition = 0;
  private float rotateAllAngleStart = 0, rotateAllAngle = rotateAllAngleStart;
  private float rotateAllAngleStart1 = 0, rotateAllAngle1 = rotateAllAngleStart1;
  private float rotateUpperAngleStart = -60, rotateUpperAngle = rotateUpperAngleStart;
  private float rotateUpperAngleStart1 = -90, rotateUpperAngle1 = rotateUpperAngleStart1;
//  private float rotateUpperAngleStart2 = -90, rotateUpperAngle2 = rotateUpperAngleStart2;
//  private float rotateUpperAngleStart3 = -90, rotateUpperAngle3 = rotateUpperAngleStart3;
//  private float rotateUpperAngleStart4 = -90, rotateUpperAngle4 = rotateUpperAngleStart4;
//  private float rotateUpperAngleStart5 = -90, rotateUpperAngle5 = rotateUpperAngleStart5;
  private float rotateUpperAngleStart6 = -90, rotateUpperAngle6 = rotateUpperAngleStart6;
//  private float rotateUpperAngleStart7 = -90, rotateUpperAngle7 = rotateUpperAngleStart7;
//  private float rotateUpperAngleStart8 = -90, rotateUpperAngle8 = rotateUpperAngleStart8;
  
  
  private void disposeModels(GL3 gl) {
    tt1.dispose(gl);
    cube.dispose(gl);
    sphere.dispose(gl);
    light.dispose(gl);
    light1.dispose(gl);
    light2.dispose(gl);
    
//    cube1.dispose(gl);
//    tui1.dispose(gl);
//    tui2.dispose(gl);
//    tui3.dispose(gl);
//    tui4.dispose(gl);
//    dengzuo.dispose(gl);
  }
  
  public void initialise(GL3 gl) {
	 
    createRandomNumbers();
    //这是设置纹理图片 你在这里设置好 在下面创建的时候就可以调用
    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/zhao.jpg");
    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/picture.jpg");
    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/wall1.jpg");
    int[] textureId7 = TextureLibrary.loadTexture(gl, "textures/diban.jpg");
    int[] textureId8 = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
    int[] textureId9 = TextureLibrary.loadTexture(gl, "textures/book.jpg");
    int[] textureId10 = TextureLibrary.loadTexture(gl, "textures/zhuozi.jpg");
    int[] textureId11 = TextureLibrary.loadTexture(gl, "textures/mutou1.jpg");
    int[] textureId12 = TextureLibrary.loadTexture(gl, "textures/dengzuo.jpg");
    int[] textureId13 = TextureLibrary.loadTexture(gl, "textures/dengzhao.jpg");
    int[] textureId14 = TextureLibrary.loadTexture(gl, "textures/lianjiedian.jpg");
    int[] textureId15 = TextureLibrary.loadTexture(gl, "textures/pen.jpg");
    int[] textureId16 = TextureLibrary.loadTexture(gl, "textures/tower.jpg");
    int[] textureId17 = TextureLibrary.loadTexture(gl, "textures/iron.jpeg");


    light = new Light(gl);
    light1 = new Light(gl);
    light2 = new Light(gl);
    light.setCamera(camera);
    light1.setCamera(camera);
    light2.setCamera(camera);
    //下面的这五条 是创建了地板 
    Mesh m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    Shader shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,0f,20f), Mat4Transform.translate(0f,0f,0f));
    //地板的纹理
    tt1 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId7, textureId7);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,0f,20f), Mat4Transform.translate(0f,0f,0.2f));
    floor = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId8, textureId8);
    
    
    //下面四条是正方体
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
   
    //下面这条管纹理 调用 也是在创建正方体 如果没有这个 程序会报错 这个会将正方体的各个参数穿进去
    //两个纹理 第二个管的是反光的时候显示的图案
    cube = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId1, textureId2);

    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    //下面这条管的是纹理 也就是表面图案 是吗？？？ 不是
    shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04.txt");
//     no texture version
//     shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04_notex.txt");
    //下面这条管的是三种漫反射的值 第一个是背光时候的亮度 第二个是磨砂反光 第三个是镜面反光  如果去掉纹理  将会显示出来 最后一个数字没搞明白
    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
   // 下面这条管大小 第一组的第一个管x的长度 第二个y（上下） 第三个z（前后），后面一组管位置 xyz
    modelMatrix = Mat4.multiply(Mat4Transform.scale(3,3,3), Mat4Transform.translate(0f,0.5f,0));
    //下面这条管球的世界坐标系起点 和上面的其实有些类似 但是下面的变化之后 上面的就会基于下面的变化继续变化
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,4,0), modelMatrix);
    
    sphere = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId3, textureId4);
    
    // no texture version
//     sphere = new Model(gl, camera, light, shader, material, modelMatrix, m); 
    
    // brush pot
    
    m = new Mesh(gl, brushpot.vertices.clone(), brushpot.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.6f,0.7f,0.6f), Mat4Transform.translate(-9f,6.13f,-3f));
    brush = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId12, textureId12);
    
    //pen1
    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.05f,1f,0.05f), Mat4Transform.translate(0f,0f,0f));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-15f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-5.2f,4.5f,-1.8f), modelMatrix);
    pen1 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId15, textureId15);
    
    
    //pen2
    m = new Mesh(gl, brushpot.vertices.clone(), brushpot.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.05f,1.5f,0.05f), Mat4Transform.translate(0f,0f,0f));
  modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(15f),modelMatrix);
  modelMatrix = Mat4.multiply(Mat4Transform.translate(-5.7f,4.7f,-1.8f), modelMatrix);
    pen2 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId13, textureId13);
    
    //pen3
    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,1f,0.1f), Mat4Transform.translate(0f,0f,0f));
//    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(-45f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(-45f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(5.5f,4.15f,-1.5f), modelMatrix);
    pen3 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId17, textureId17);
    
    //book
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(1f,0.2f,1.8f), Mat4Transform.translate(5.5f,20f,-1f));
    book = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId9, textureId9);
    
    // 桌子腿 1，2，3，4
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(12,0.2f,8), Mat4Transform.translate(0f,19f,0));
    cube1 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId10, textureId10);
//第一条腿
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(-8,0.1f,4));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
    tui1 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId10, textureId10);
  //第二条腿
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(8,0.1f,4));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
    tui2 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId10, textureId10);
  //第三条腿
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(8,0.1f,-4));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
    tui3 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId10, textureId10);
  //第四条腿
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(-8,0.1f,-4));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
    tui4 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId10, textureId10);
    
    
    //墙壁
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,5f,0.5f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,12.3f,-6f), modelMatrix);
    wall = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId6, textureId6);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,5f,0.5f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,2.3f,-6f), modelMatrix);
    wall1 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId6, textureId6);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(7f,10f,0.5f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-6.5f,7.3f,-6f), modelMatrix);
    wall2 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId6, textureId6);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(7f,10f,0.5f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(6.5f,7.3f,-6f), modelMatrix);
    wall3 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId6, textureId6);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(7f,0.2f,0.2f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,7.3f,-6f), modelMatrix);
    chuang1 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId11, textureId11);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.2f,5f,0.2f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,7.3f,-6f), modelMatrix);
    chuang2 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId11, textureId11);
    
    
    //窗户
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,15f,0.5f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,7.3f,-20f), modelMatrix);
    window = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId16, textureId16);
    
    //picture
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(2f,1f,0.1f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(-25f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(3f,4.2f,-3.5f), modelMatrix);
    picture = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId5, textureId5);
    
    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(1f,0.5f,0.1f), Mat4Transform.translate(0,0,0));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(15f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(3f,4f,-3.6f), modelMatrix);
    holder = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId14, textureId14);
    
    
    
    
//   灯座
    
//    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(1.1f,0.2f,1.1f), Mat4Transform.translate(0,0,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
//    dengzuo1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5, textureId5);
//    
//    m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,0.3f,0.5f), Mat4Transform.translate(0,0.5f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
//    dengzuo2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5, textureId5);
    
    
    
    //连接点1，2，3
    
//    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(0,1f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//    lianjie1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
    
//
//    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(-4.5f,5.5f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//    lianjie2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
    
//    
//    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(0f,10f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//    lianjie3 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
    
//    //支架 1
//    
//    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
//    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(45f),modelMatrix);
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,5f,0), modelMatrix);
//    zhijia1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
    
    
    
    

//    light2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
    
    
    
    
    
    
    
    
    
    //支架 2
    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-45f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,6.3f,0), modelMatrix);

    zhijia2 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId13);
    
    
    
    //灯罩
    m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    dengzhao = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId0);
    

    
    //靠上的灯座
    
     m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.7f,0.5f,0.7f), Mat4Transform.translate(0,0.5f,0));
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
    dengzuo2 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId6, textureId6);
    
    
    //支架 1
    
    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(45f),modelMatrix);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,5f,0), modelMatrix);
    zhijia1 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId13);
  

  
  
 //连接点1
  m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
  shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
  material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.2f,0.2f,0.2f), Mat4Transform.translate(0,1f,0));
  modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
  lianjie1 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId14);
  
// 连接点2
  m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
  shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
  material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.2f,0.2f,0.2f), Mat4Transform.translate(-4.5f,5.5f,0));
  modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
  lianjie2 = new Model(gl, camera, light, light1,light2,shader, material, modelMatrix, m, textureId14);
  
  // 连接点3
  m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
  shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
  material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.2f,0.2f,0.2f), Mat4Transform.translate(0f,10f,0));
  modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
  lianjie3 = new Model(gl, camera, light,light1,light2, shader, material, modelMatrix, m, textureId14);

  
  // 灯座2的连接代码
  twoBranchRoot = new NameNode("two-branch structure");
  translateX = new TransformNode("translate("+xPosition+"),0,translate("+zPosition+"))", Mat4Transform.translate(xPosition,yPosition,zPosition));
  rotateUpper4 = new TransformNode("rotateAroundY("+rotateAllAngle1+")", Mat4Transform.rotateAroundZ(rotateAllAngle1));
  NameNode lowerBranch = new NameNode("lower branch");
  Mat4 ms = Mat4Transform.scale(0.7f,0.5f,0.7f);
  ms = Mat4.multiply(ms, Mat4Transform.translate(0f,8.1f,0f));
  TransformNode makeLowerBranch = new TransformNode("scale(2.5,4,2.5); translate(0,5,0)", ms);
  ModelNode cube0Node = new ModelNode("Sphere(0)", dengzuo2);
  
  // 支架1的连接代码
  TransformNode translateToTop = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0,0.1f,0));
  rotateUpper = new TransformNode("rotateAroundY("+rotateUpperAngle+")",Mat4Transform.rotateAroundZ(rotateUpperAngle));
  NameNode upperBranch = new NameNode("upper branch");
  ms = Mat4Transform.scale(0.1f,1.8f,0.1f);
  //下面这条是改变物体的原点（沿着哪点摆动）
  ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
  TransformNode makeUpperBranch = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
  ModelNode cube1Node = new ModelNode("Sphere(1)", zhijia1);
    
  
  // 支架2的连接代码
  TransformNode translateToTop1 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0f,1.9f,0));
  rotateUpper1 = new TransformNode("rotateAroundY("+rotateUpperAngle1+")",Mat4Transform.rotateAroundZ(rotateUpperAngle1));
  NameNode upperBranch1 = new NameNode("upper branch");
  ms = Mat4Transform.scale(0.1f,1.8f,0.1f);
  ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
  TransformNode makeUpperBranch1 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
  ModelNode cube1Node1 = new ModelNode("Sphere(2)", zhijia2);
  
  // 连接点1的连接代码
  TransformNode translateToTop4 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0f,4.2f,0));
  rotateAll1 = new TransformNode("rotateAroundY("+rotateUpperAngle1+")",Mat4Transform.rotateAroundZ(rotateUpperAngle1));
  NameNode upperBranch4 = new NameNode("upper branch");
  ms = Mat4Transform.scale(0.3f,0.3f,0.3f);
  ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
  TransformNode makeUpperBranch4 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
  ModelNode cube1Node4 = new ModelNode("Sphere(5)", lianjie1);
  
  // 连接点2的连接代码
TransformNode translateToTop2 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0f,1.7f,0));
rotateUpper2 = new TransformNode("rotateAroundY("+rotateAllAngle1+")",Mat4Transform.rotateAroundZ(rotateAllAngle1));
NameNode upperBranch2 = new NameNode("upper branch");
ms = Mat4Transform.scale(0.3f,0.3f,0.3f);
ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
TransformNode makeUpperBranch2 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
ModelNode cube1Node2 = new ModelNode("Sphere(3)", lianjie2);


// 连接点3的连接代码
TransformNode translateToTop3 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0f,1.7f,0));
rotateUpper3 = new TransformNode("rotateAroundY("+rotateAllAngle1+")",Mat4Transform.rotateAroundZ(rotateAllAngle1));
NameNode upperBranch3 = new NameNode("upper branch");
ms = Mat4Transform.scale(0.3f,0.3f,0.3f);
ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
TransformNode makeUpperBranch3 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
ModelNode cube1Node3 = new ModelNode("Sphere(4)", lianjie3);

//灯罩的连接代码
TransformNode translateToTop5 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0.35f,1.8f,0));
rotateUpper5 = new TransformNode("rotateAroundY("+rotateAllAngle1+")",Mat4Transform.rotateAroundZ(rotateAllAngle1));
NameNode upperBranch5 = new NameNode("upper branch");
ms = Mat4Transform.scale(0.6f,0.4f,0.6f);
ms = Mat4.multiply(Mat4Transform.rotateAroundZ(90f),ms);
TransformNode makeUpperBranch5 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
cube1Node5 = new ModelNode("Sphere(6)", dengzhao);



  //底座
twoBranchRoot.addChild(translateX);
translateX.addChild(rotateUpper4);
rotateUpper4.addChild(lowerBranch);
    lowerBranch.addChild(makeLowerBranch);
      makeLowerBranch.addChild(cube0Node);
    //灯架1 连接到底座2
      upperBranch4.addChild(translateToTop);
      translateToTop.addChild(rotateUpper);
        rotateUpper.addChild(upperBranch);
          upperBranch.addChild(makeUpperBranch);
            makeUpperBranch.addChild(cube1Node);
          //灯架2 连接到灯架1
            upperBranch.addChild(translateToTop1);
            translateToTop1.addChild(rotateUpper1);
              rotateUpper1.addChild(upperBranch1);
                upperBranch1.addChild(makeUpperBranch1);
                  makeUpperBranch1.addChild(cube1Node1);
       //连接点1 连接到底座2  
                  lowerBranch.addChild(translateToTop4);
        		  translateToTop4.addChild(rotateAll1);
        		  rotateAll1.addChild(upperBranch4);
        		  upperBranch4.addChild(makeUpperBranch4);
        		  makeUpperBranch4.addChild(cube1Node4);
        //连接点2 连接到灯架1
		  upperBranch.addChild(translateToTop2);
		  translateToTop2.addChild(rotateUpper2);
		  rotateUpper2.addChild(upperBranch2);
		  upperBranch2.addChild(makeUpperBranch2);
		  makeUpperBranch2.addChild(cube1Node2);
		  
		//连接点3 连接到灯架2
		  upperBranch1.addChild(translateToTop3);
		  translateToTop3.addChild(rotateUpper3);
		  rotateUpper3.addChild(upperBranch3);
		  upperBranch3.addChild(makeUpperBranch3);
		  makeUpperBranch3.addChild(cube1Node3);
		    
		  //灯罩连接到连接点3
		  upperBranch1.addChild(translateToTop5);
		  translateToTop5.addChild(rotateUpper5);
		  rotateUpper5.addChild(upperBranch5);
		  upperBranch5.addChild(makeUpperBranch5);
		  makeUpperBranch5.addChild(cube1Node5);
        twoBranchRoot.update();
    
    
    
    
  }
 
  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    //下面是集中创造物体 你在上面写好的在这里调用就能创造出来 注销掉就不会创造
    if (turnlightr == 1) {
        light.setPosition(getLightPosition());  // changing light position each frame
        light.render(gl);
    }else {
        light.setPosition(getLightPosition());  // changing light position each frame
        light.render1(gl);
    }
    
    if (turnlightl== 1) {
        light1.setPosition(getLightPosition1()); 
        light1.render(gl);
    }else {
    	 light1.setPosition(getLightPosition1()); 
         light1.render1(gl);
    }
    
    if(turnlight==1) {
        light2.setPosition(cube1Node5.getPosition());
        light2.render2(gl);
    }else {
        light2.setPosition(cube1Node5.getPosition());
        light2.render1(gl);
    }
//    tt1.render(gl);
//    cube.render(gl);  //这个是箱子
//    sphere.render(gl);//这个是球体
    cube1.render(gl);
    tui1.render(gl);
    tui2.render(gl);
    tui3.render(gl);
    tui4.render(gl);
    wall.render(gl);
    wall1.render(gl);
    wall2.render(gl);
    wall3.render(gl);
    chuang1.render(gl);
    chuang2.render(gl);
    brush.render(gl);
    pen1.render(gl);
    pen2.render(gl);
    pen3.render(gl);
    picture.render(gl);
    holder.render(gl);
    book.render(gl);
    if (jumpon==1) {
    	mark =1;
		jump();
	}
    floor.render(gl);
    window.render(gl);
    updateBranches();
    twoBranchRoot.draw(gl);

    
  }

//  
  private void updateBranches() {
	    double elapsedTime = getSeconds()-startTime;
	    rotateAllAngle = rotateUpperAngleStart*0;
	    rotateAllAngle1 = rotateUpperAngleStart;
	    rotateUpperAngle = nicai;
	    rotateUpperAngle1 = nizaicai;
	    rotateUpperAngle6 = angle1;
//	    rotateUpperAngle6 = rotateUpperAngleStart*(float)Math.sin(elapsedTime*0.1f)*4;
	    rotateAll1.setTransform(Mat4Transform.rotateAroundY(rotateUpperAngle6));
	    rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
	    rotateUpper1.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle1));
//	    rotateAll.setTransform(Mat4Transform.rotateAroundX(rotateUpperAngle));
	    twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
//	    cube1Node5.getPosition()
	  }
  
//  
  
  
  
  
  // The light's postion is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
//    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float x = 4f;
    float y = 13f;
    float z = -4f;
    return new Vec3(x,y,z);
  }
  
  
  //灯泡的坐标
    private Vec3 getLightPosition1() {
        double elapsedTime = getSeconds()-startTime;
        float x = -4f;
        float y = 13f;
        float z = -4f;
        return new Vec3(x,y,z);
  }
    
  
    // ***************************************************
  /* TIME
   */ 
  

  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  // ***************************************************
  /* An array of random numbers
   */ 
  
  private int NUM_RANDOMS = 1000;
  private float[] randoms;
  
  private void createRandomNumbers() {
    randoms = new float[NUM_RANDOMS];
    for (int i=0; i<NUM_RANDOMS; ++i) {
      randoms[i] = (float)Math.random();
    }
  }
  
  
}