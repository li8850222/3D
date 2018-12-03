package ch7;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;


import javax.swing.*;

public class M01 extends JFrame implements ActionListener {
  
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private GLCanvas canvas;
  private GLEventListener glEventListener;
  private final FPSAnimator animator; 
  private Camera camera;

  public static void main(String[] args) {
    M01 b1 = new M01("M01");
    b1.getContentPane().setPreferredSize(dimension);
    b1.pack();
    b1.setVisible(true);
  }

  public M01(String textForTitleBar) {
    super(textForTitleBar);
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new M01_GLEventListener(camera);
    canvas.addGLEventListener(glEventListener);
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    canvas.addKeyListener(new MyKeyboardInput(camera));
    getContentPane().add(canvas, BorderLayout.CENTER);
// 分界线---------------------------------------------------------------------------------
    JMenuBar menuBar=new JMenuBar();
    this.setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
    menuBar.add(fileMenu);
    
    JPanel p = new JPanel();
      JButton b = new JButton("left light");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("right light");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("turn table lamp");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("increase X position");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("decrease X position");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("change position");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("jump");
      b.addActionListener(this);
      p.add(b);
    this.add(p, BorderLayout.SOUTH);
    
// 分界线---------------------------------------------------------------------------------
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        remove(canvas);
        dispose();
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }
  public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equalsIgnoreCase("left light")) {
	    ((M01_GLEventListener) glEventListener).turnlightl();
	    }
	    
	    else if (e.getActionCommand().equalsIgnoreCase("right light")) {
		    ((M01_GLEventListener) glEventListener).turnlightr();
		    }
	    
	    else if (e.getActionCommand().equalsIgnoreCase("camera Z")) {
	      camera.setCamera(Camera.CameraType.Z);
	      canvas.requestFocusInWindow();
	    }
	    
	    else if (e.getActionCommand().equalsIgnoreCase("increase X position")) {
	      ((M01_GLEventListener) glEventListener).incXPosition();
	    }
	    
	    else if (e.getActionCommand().equalsIgnoreCase("decrease X position")) {
	      ((M01_GLEventListener) glEventListener).decXPosition();
	    }
	    
	    else if(e.getActionCommand().equalsIgnoreCase("turn table lamp")) {
	    	((M01_GLEventListener) glEventListener).turntablelamp();
	    }
	    
	    else if(e.getActionCommand().equalsIgnoreCase("change position")) {
	    	((M01_GLEventListener) glEventListener).changposition();
	    }
	    else if(e.getActionCommand().equalsIgnoreCase("jump")) {
	 	   Random a = new Random();
		   int a1 = a.nextInt(10);
		   int a2 = a.nextInt(10);
		   int a3 = a.nextInt(4);
		   if(a1>5) {
			   ((M01_GLEventListener) glEventListener).vx=(2+a3)*0.01f;
			   if(a2>5) {
				   ((M01_GLEventListener) glEventListener).anglenew=315;
			   }else {
				   ((M01_GLEventListener) glEventListener).anglenew=45;
			   }
		   }else {
			   ((M01_GLEventListener) glEventListener).vx=(2+a3)*-0.01f;
			   if(a2>5) {
				   ((M01_GLEventListener) glEventListener).anglenew=225;
			   }else {
				   ((M01_GLEventListener) glEventListener).anglenew=135;
			   }
		}
		   
		   if(a2>5) {
			   ((M01_GLEventListener) glEventListener).vz=(2+a3)*0.01f;
		   }else {
			   ((M01_GLEventListener) glEventListener).vz=(2+a3)*-0.01f;
		}
	    	((M01_GLEventListener) glEventListener).jumpon=1;
	    }
		  
	  }
  
}
 
class MyKeyboardInput extends KeyAdapter  {
  private Camera camera;
  
  public MyKeyboardInput(Camera camera) {
    this.camera = camera;
  }
  
  public void keyPressed(KeyEvent e) {
    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
    }
    camera.keyboardInput(m);
  }
}

class MyMouseInput extends MouseMotionAdapter {
  private Point lastpoint;
  private Camera camera;
  
  public MyMouseInput(Camera camera) {
    this.camera = camera;
  }
  
    /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */    
  public void mouseDragged(MouseEvent e) {
    Point ms = e.getPoint();
    float sensitivity = 0.001f;
    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
    //System.out.println("dy,dy: "+dx+","+dy);
    if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
      camera.updateYawPitch(dx, -dy);
    lastpoint = ms;
  }

  /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */  
  public void mouseMoved(MouseEvent e) {   
    lastpoint = e.getPoint(); 
  }
}