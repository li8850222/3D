//package ch7;import gmaths.*;
//import ch7.SGNode;
//import ch7.TransformNode;
//
//import java.lang.reflect.Modifier;
//import java.nio.*;
//import com.jogamp.common.nio.*;
//import com.jogamp.opengl.*;
//import com.jogamp.opengl.util.*;
//import com.jogamp.opengl.util.awt.*;
//import com.jogamp.opengl.util.glsl.*;
//  
//public class M01_GLEventListener implements GLEventListener {
//	
//  
//  private static final boolean DISPLAY_SHADERS = false;
//    
//  public M01_GLEventListener(Camera camera) {
//    this.camera = camera;
//    this.camera.setPosition(new Vec3(4f,6f,15f));
//    this.camera.setTarget(new Vec3(0f,5f,0f));
//  }
//  
//  // ***************************************************
//  /*
//   * METHODS DEFINED BY GLEventListener
//   */
//
//  /* Initialisation */
//  public void init(GLAutoDrawable drawable) {   
//    GL3 gl = drawable.getGL().getGL3();
//    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
//    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
//    gl.glClearDepth(1.0f);
//    gl.glEnable(GL.GL_DEPTH_TEST);
//    gl.glDepthFunc(GL.GL_LESS);
//    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
//    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
//    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
//    initialise(gl);
//    startTime = getSeconds();
//  }
//  
//  /* Called to indicate the drawing surface has been moved and/or resized  */
//  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//    GL3 gl = drawable.getGL().getGL3();
//    gl.glViewport(x, y, width, height);
//    float aspect = (float)width/(float)height;
//    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
//  }
//
//  /* Draw */
//  public void display(GLAutoDrawable drawable) {
//    GL3 gl = drawable.getGL().getGL3();
//    render(gl);
//  }
//
//  /* Clean up memory, if necessary */
//  public void dispose(GLAutoDrawable drawable) {
//    GL3 gl = drawable.getGL().getGL3();
//    disposeModels(gl);
//  }
//
//  
//  
//  
//  
//  
//  // ***************************************************
//  /* INTERACTION
//   *
//   *
//   */
//   
//   public void incXPosition() {
//     xPosition += 0.5f;
//     if (xPosition>5f) xPosition = 5f;
//     updateX();
//   }
//   
//   public void decXPosition() {
//     xPosition -= 0.5f;
//     if (xPosition<-5f) xPosition = -5f;
//     updateX();
//   }
//   
//   private void updateX() {
//     translateX.setTransform(Mat4Transform.translate(xPosition,0,0));
//     translateX.update(); // IMPORTANT – the scene graph has changed
//   }
//  
//  
//  
//  
//  
//  
//  
//  // ***************************************************
//  /* THE SCENE
//   * Now define all the methods to handle the scene.
//   * This will be added to in later examples.
//   */
//   
//  private Camera camera;
//  private Model tt1, cube, sphere, cube1,tui1,tui2,tui3,tui4,wall,window,floor,dengzuo1,dengzuo2,lianjie1,lianjie2,lianjie3,dengzhao,zhijia1,zhijia2;
////  private Model dengzuo;
//  private Light light;
//  private Mat4 perspective;
//  private SGNode twoBranchRoot;
//  
//  private TransformNode translateX, rotateAll, rotateUpper,rotateUpper1,rotateUpper2,rotateUpper3,rotateUpper4,rotateUpper5,rotateUpper6,rotateUpper7;
//  private float xPosition = 0;
//  private float rotateAllAngleStart = 25, rotateAllAngle = rotateAllAngleStart;
//  private float rotateUpperAngleStart = -60, rotateUpperAngle = rotateUpperAngleStart;
//  private float rotateUpperAngleStart1 = -60, rotateUpperAngle1 = rotateUpperAngleStart1;
//  private float rotateUpperAngleStart2 = -60, rotateUpperAngle2 = rotateUpperAngleStart2;
//  private float rotateUpperAngleStart3 = -60, rotateUpperAngle3 = rotateUpperAngleStart3;
//  private float rotateUpperAngleStart4 = -60, rotateUpperAngle4 = rotateUpperAngleStart4;
//  private float rotateUpperAngleStart5 = -60, rotateUpperAngle5 = rotateUpperAngleStart5;
//  private float rotateUpperAngleStart6 = -60, rotateUpperAngle6 = rotateUpperAngleStart6;
//  private float rotateUpperAngleStart7 = -60, rotateUpperAngle7 = rotateUpperAngleStart7;
//  
//  
//  private void disposeModels(GL3 gl) {
//    tt1.dispose(gl);
//    cube.dispose(gl);
//    sphere.dispose(gl);
//    light.dispose(gl);
////    cube1.dispose(gl);
////    tui1.dispose(gl);
////    tui2.dispose(gl);
////    tui3.dispose(gl);
////    tui4.dispose(gl);
////    dengzuo.dispose(gl);
//  }
//  
//  public void initialise(GL3 gl) {
//    createRandomNumbers();
//    //这是设置纹理图片 你在这里设置好 在下面创建的时候就可以调用
//    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/chequerboard.jpg");
//    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
//    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
//    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
//    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
//    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/1216521904.jpg");
//    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/qiangmian.jpg");
//    int[] textureId7 = TextureLibrary.loadTexture(gl, "textures/diban.jpg");
//    int[] textureId8 = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
//    int[] textureId9 = TextureLibrary.loadTexture(gl, "textures/cloud.jpg");
//    int[] textureId10 = TextureLibrary.loadTexture(gl, "textures/zhuozi.jpg");
//    int[] textureId11 = TextureLibrary.loadTexture(gl, "textures/qiangmian2.jpg");
//
//
//    light = new Light(gl);
//    light.setCamera(camera);
//    //下面的这五条 是创建了地板 
//    Mesh m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    Shader shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,0f,20f), Mat4Transform.translate(0f,0f,0f));
//    //地板的纹理
//    tt1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId7, textureId7);
//    
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,0f,20f), Mat4Transform.translate(0f,0f,0.2f));
//    floor = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId8, textureId8);
//    
//    
//    //下面四条是正方体
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
//   
//    //下面这条管纹理 调用 也是在创建正方体 如果没有这个 程序会报错 这个会将正方体的各个参数穿进去
//    //两个纹理 第二个管的是反光的时候显示的图案
//    cube = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId1, textureId2);
//
//    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//    //下面这条管的是纹理 也就是表面图案 是吗？？？ 不是
//    shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04.txt");
////     no texture version
////     shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04_notex.txt");
//    //下面这条管的是三种漫反射的值 第一个是背光时候的亮度 第二个是磨砂反光 第三个是镜面反光  如果去掉纹理  将会显示出来 最后一个数字没搞明白
//    material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 32.0f);
//   // 下面这条管大小 第一组的第一个管x的长度 第二个y（上下） 第三个z（前后），后面一组管位置 xyz
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(3,3,3), Mat4Transform.translate(0f,0.5f,0));
//    //下面这条管球的世界坐标系起点 和上面的其实有些类似 但是下面的变化之后 上面的就会基于下面的变化继续变化
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,4,0), modelMatrix);
//    
//    sphere = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId3, textureId4);
//    
//    // no texture version
////     sphere = new Model(gl, camera, light, shader, material, modelMatrix, m); 
//    
//    
//    
//    // 桌子腿 1，2，3，4
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(10,0.2f,6), Mat4Transform.translate(0f,19f,0));
//    cube1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId10, textureId10);
////第一条腿
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(-8,0.1f,4));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
//    tui1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId10, textureId10);
//  //第二条腿
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(8,0.1f,4));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
//    tui2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId10, textureId10);
//  //第三条腿
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(8,0.1f,-4));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
//    tui3 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId10, textureId10);
//  //第四条腿
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,4f,0.5f), Mat4Transform.translate(-8,0.1f,-4));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,1.5f,0), modelMatrix);
//    tui4 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId10, textureId10);
//    
//    
//    //墙壁
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(20f,15f,0.5f), Mat4Transform.translate(0,0,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,7.3f,-6f), modelMatrix);
//    wall = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId11, textureId11);
//    
//    //窗户
//    m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(8f,6f,0.1f), Mat4Transform.translate(0,0,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,7.3f,-5.7f), modelMatrix);
//    window = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId9, textureId10);
//    
//    
//    
//    //灯座
//    
//    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(1.1f,0.2f,1.1f), Mat4Transform.translate(0,0,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
//    dengzuo1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5, textureId5);
////    
////    m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
////    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
////    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
////    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,0.3f,0.5f), Mat4Transform.translate(0,0.5f,0));
////    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
////    dengzuo2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5, textureId5);
//    
//    
//    
//    //连接点1，2，3
//    
////    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
////    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
////    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
////    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(0,1f,0));
////    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
////    lianjie1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//    
////
////    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
////    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
////    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
////    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(-4.5f,5.5f,0));
////    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
////    lianjie2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//    
//    
//    m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(0f,10f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//    lianjie3 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//    
////    //支架 1
////    
////    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
////    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
////    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
////
////    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
////    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(45f),modelMatrix);
////    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,5f,0), modelMatrix);
////    zhijia1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//    
//    
//    //支架 2
//    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
//    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-45f),modelMatrix);
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,6.3f,0), modelMatrix);
//
//    zhijia2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//// 顺序 平移 旋转 缩放
//    
//    
//    
//    //灯罩
//    m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.6f,0.4f,0.6f), Mat4Transform.translate(0f,0f,0f));
//    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(45f),modelMatrix);
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0.3f,6.8f,0), modelMatrix);
//    dengzhao = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//    
//
//    
//    //靠上的灯座
//    
//     m = new Mesh(gl, Cone.vertices.clone(), Cone.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.5f,0.3f,0.5f), Mat4Transform.translate(0,0.5f,0));
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(0,3.9f,0), modelMatrix);
//    dengzuo2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5, textureId5);
//    
//    
//    //支架 1
//    
//    m = new Mesh(gl, Cylinder.vertices.clone(), Cylinder.indices.clone());
//    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//    modelMatrix = Mat4.multiply(Mat4Transform.scale(0.1f,2f,0.1f), Mat4Transform.translate(0f,0f,0f));
//    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(45f),modelMatrix);
//    modelMatrix = Mat4.multiply(Mat4Transform.translate(-0.7f,5f,0), modelMatrix);
//    zhijia1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//  
//  
// //连接点1
//  m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//  shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//  material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(0,1f,0));
//  modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//  lianjie1 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//  
//// 连接点2
//  m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
//  shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
//  material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
//  modelMatrix = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), Mat4Transform.translate(-4.5f,5.5f,0));
//  modelMatrix = Mat4.multiply(Mat4Transform.translate(0f,4f,0), modelMatrix);
//  lianjie2 = new Model(gl, camera, light, shader, material, modelMatrix, m, textureId5);
//  
//  
//  
//  
//  
//  twoBranchRoot = new NameNode("two-branch structure");
//  translateX = new TransformNode("translate("+xPosition+",0,0)", Mat4Transform.translate(xPosition,0,0));
//  rotateAll = new TransformNode("rotateAroundZ("+rotateAllAngle+")", Mat4Transform.rotateAroundZ(rotateAllAngle));
//  NameNode lowerBranch = new NameNode("lower branch");
//  Mat4 ms = Mat4Transform.scale(0.5f,0.3f,0.5f);
//  ms = Mat4.multiply(ms, Mat4Transform.translate(0f,13.5f,0f));
//  TransformNode makeLowerBranch = new TransformNode("scale(2.5,4,2.5); translate(0,5,0)", ms);
//  ModelNode cube0Node = new ModelNode("Sphere(0)", dengzuo2);
//  
//  TransformNode translateToTop = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0,4.3f,0));
//  rotateUpper = new TransformNode("rotateAroundY("+rotateUpperAngle+")",Mat4Transform.rotateAroundZ(rotateUpperAngle));
//  NameNode upperBranch = new NameNode("upper branch");
//  ms = Mat4Transform.scale(0.1f,1.8f,0.1f);
//  //下面这条是改变物体的原点（沿着哪点摆动）
//  ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
//  TransformNode makeUpperBranch = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
//  ModelNode cube1Node = new ModelNode("Sphere(1)", zhijia1);
//    
//  
////  
////  TransformNode translateToTop1 = new TransformNode("translate(0,4,0)",Mat4Transform.translate(0,4.3f,0));
////  rotateUpper1 = new TransformNode("rotateAroundY("+rotateUpperAngle+")",Mat4Transform.rotateAroundZ(rotateUpperAngle));
////  NameNode upperBranch1 = new NameNode("upper branch");
////  ms = Mat4Transform.scale(0.1f,1.8f,0.1f);
////  //下面这条是改变物体的原点（沿着哪点摆动）
////  ms = Mat4.multiply(ms, Mat4Transform.translate(0,0.5f,0));
////  TransformNode makeUpperBranch1 = new TransformNode("scale(1.4f,3.1f,1.4f);translate(0,0.5,0)", ms);
////  ModelNode cube1Node1 = new ModelNode("Sphere(1)", lianjie1);
//  
//twoBranchRoot.addChild(translateX);
//translateX.addChild(rotateAll);
//  rotateAll.addChild(lowerBranch);
//    lowerBranch.addChild(makeLowerBranch);
//      makeLowerBranch.addChild(cube0Node);
//      
//    lowerBranch.addChild(translateToTop);
//      translateToTop.addChild(rotateUpper);
//        rotateUpper.addChild(upperBranch);
//          upperBranch.addChild(makeUpperBranch);
//            makeUpperBranch.addChild(cube1Node);
//            
////            lowerBranch.addChild(translateToTop1);
////            translateToTop1.addChild(rotateUpper1);
////              rotateUpper1.addChild(upperBranch1);
////                upperBranch1.addChild(makeUpperBranch1);
////                  makeUpperBranch1.addChild(cube1Node1);
//    
//        twoBranchRoot.update();
//    
//    
//    
//    
//  }
// 
//  private void render(GL3 gl) {
//    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//    //下面是集中创造物体 你在上面写好的在这里调用就能创造出来 注销掉就不会创造
////    light.setPosition(getLightPosition());  // changing light position each frame
////    light.render(gl);
//    light.setPosition(getLightPosition1());  // changing light position each frame
//    light.render(gl);
////    tt1.render(gl);
////    cube.render(gl);  //这个是箱子
////    sphere.render(gl);//这个是球体
//    cube1.render(gl);
//    tui1.render(gl);
//    tui2.render(gl);
//    tui3.render(gl);
//    tui4.render(gl);
//    wall.render(gl);
//    dengzuo1.render(gl);
////    dengzuo2.render(gl);
//    lianjie1.render(gl);
//    lianjie2.render(gl);
//    lianjie3.render(gl);
////    zhijia1.render(gl);
//    zhijia2.render(gl);
//    dengzhao.render(gl);
//    floor.render(gl);
//    window.render(gl);
//    updateBranches();
//    twoBranchRoot.draw(gl);
//  }
//
////  
//  private void updateBranches() {
//	    double elapsedTime = getSeconds()-startTime;
//	    rotateAllAngle = rotateAllAngleStart*0;
//	    rotateUpperAngle = rotateUpperAngleStart*-Math.abs((float)Math.sin(elapsedTime*0.3f));
////	    rotateUpperAngle1 = rotateUpperAngleStart1*(float)Math.sin(elapsedTime*0.3f);
//	    rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
//	    rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
////	    rotateUpper1.setTransform(Mat4Transform.rotateAroundY(rotateUpperAngle1));
////	    twoBranchRoot.update(); // IMPORTANT – the scene graph has changed
//	  }
//  
////  
//  
//  
//  
//  
//  // The light's postion is continually being changed, so needs to be calculated for each frame.
//  private Vec3 getLightPosition() {
//    double elapsedTime = getSeconds()-startTime;
////    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
//    float x = 0f;
//    float y = 13f;
////    float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
//    float z = 3f;
//    return new Vec3(x,y,z);
//  }
//  
//  
//  //灯泡的坐标
//    private Vec3 getLightPosition1() {
//        double elapsedTime = getSeconds()-startTime;
////        float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
//        float x = 0.3f;
//        float y = 6.8f;
////        float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
//        float z = 0f;
//        return new Vec3(x,y,z);
//    
//    //return new Vec3(5f,3.4f,5f);  // use to set in a specific position for testing
//  }
//  
//    // ***************************************************
//  /* TIME
//   */ 
//  
//  private double startTime;
//  
//  private double getSeconds() {
//    return System.currentTimeMillis()/1000.0;
//  }
//
//  // ***************************************************
//  /* An array of random numbers
//   */ 
//  
//  private int NUM_RANDOMS = 1000;
//  private float[] randoms;
//  
//  private void createRandomNumbers() {
//    randoms = new float[NUM_RANDOMS];
//    for (int i=0; i<NUM_RANDOMS; ++i) {
//      randoms[i] = (float)Math.random();
//    }
//  }
//  
//  
//}