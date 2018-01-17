package com.wondertek.core.util;

	import   java.awt.*;  
	import   java.awt.event.*;  
	import   java.awt.geom.*;  
	import   java.util.*;  
	import   javax.swing.*;  

	public   class   TransformTest  
	{     public   static   void   main(String[]   args)  
	      {     JFrame   frame   =   new   TransformTestFrame();  
	            frame.show();  
	      }  
	}  

	class   TransformTestFrame   extends   JFrame  
	      implements   ActionListener  
	{     public   TransformTestFrame()  
	      {     setTitle( "TransformTest ");  
	            setSize(400,   400);  
	            addWindowListener(new   WindowAdapter()  
	                  {     public   void   windowClosing(WindowEvent   e)  
	                        {     System.exit(0);  
	                        }  
	                  }   );  

	            Container   contentPane   =   getContentPane();  
	            canvas   =   new   TransformPanel();  
	            contentPane.add(canvas,   "Center");  

	            JPanel   buttonPanel   =   new   JPanel();  
	            ButtonGroup   group   =   new   ButtonGroup();  

	            rotateButton   =   new   JRadioButton( "Rotate",   true);  
	            buttonPanel.add(rotateButton);  
	            group.add(rotateButton);  
	            rotateButton.addActionListener(this);  

	            translateButton   =   new   JRadioButton( "Translate",   false);  
	            buttonPanel.add(translateButton);  
	            group.add(translateButton);  
	            translateButton.addActionListener(this);  

	            scaleButton   =   new   JRadioButton( "Scale",   false);  
	            buttonPanel.add(scaleButton);  
	            group.add(scaleButton);  
	            scaleButton.addActionListener(this);  

	            shearButton   =   new   JRadioButton( "Shear",   false);  
	            buttonPanel.add(shearButton);  
	            group.add(shearButton);  
	            shearButton.addActionListener(this);  

	            contentPane.add(buttonPanel,   "North");  
	      }  

	      public   void   actionPerformed(ActionEvent   event)  
	      {     Object   source   =   event.getSource();  
	            if   (source   ==   rotateButton)   canvas.setRotate();  
	            else   if   (source   ==   translateButton)   canvas.setTranslate();  
	            else   if   (source   ==   scaleButton)   canvas.setScale();  
	            else   if   (source   ==   shearButton)   canvas.setShear();  
	      }  

	      private   TransformPanel   canvas;  
	      private   JRadioButton   rotateButton;  
	      private   JRadioButton   translateButton;  
	      private   JRadioButton   scaleButton;  
	      private   JRadioButton   shearButton;  
	}  

	class   TransformPanel   extends   JPanel  
	{     public   TransformPanel()  
	      {     img=new   ImageIcon( "D:\\workspace\\mobilevideo\\mobilevideo\\core\\core-util\\src\\test\\java\\123456_130_ud_QVGA.png").getImage();  
	            t   =   new   AffineTransform();  
	            setRotate();  
	      }  

	      public   void   paintComponent(Graphics   g)  
	      {     super.paintComponent(g);  
	            Graphics2D   g2   =   (Graphics2D)g;  
	            g2.drawImage(img,0,0,null);//画原图  
	            g2.translate(getWidth()   /   2,   getHeight()   /   2);//平移坐标轴，将原点移到组件中心  
	            g2.transform(t);//设置仿射变换  
	                  /*   we   don 't   use   setTransform   because   we   want  
	                        to   compose   with   the   current   translation  
	                  */  
	            g2.drawImage(img,-50,-50,null);//在新坐标系中画图像  
	      }  

	      public   void   setRotate()  
	      {     t.setToRotation(Math.toRadians(90));//旋转  
	            repaint();  
	      }  

	      public   void   setTranslate()  
	      {     t.setToTranslation(60,   15);//平移  
	            repaint();  
	      }  

	      public   void   setScale()  
	      {     t.setToScale(-1,   1);//翻转图像  
	            repaint();  
	      }  

	      public   void   setShear()  
	      {     t.setToShear(-0.2,   2);//剪切  
	            repaint();  
	      }  
	         
	      private   Image   img;  
	      private   AffineTransform   t;  
	}   
