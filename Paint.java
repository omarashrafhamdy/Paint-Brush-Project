import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class Paint extends Applet{
	public boolean isFill=false;
	Button rectangleBt,ovalBt,lineBt,eraserBt,pencilBt,redBt,blueBt,greenBt,clearAllBt,undoBt;
	Checkbox fillCb;
	int mode=0;
	Color color = null;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<Shape> tempShapes = new ArrayList<Shape>();
	int x,y,x1,x2,y1,y2,width,height;
	boolean drawing = false;
	
	
	//Final Modes
	public static final int RECTANGLE = 1;
	public static final int OVAL = 2;
	public static final int LINE = 3;
	public static final int ERASER = 4;
	public static final int PENCIL = 5;
	
	
	//Classes for MouseAdapter and MouseMotionAdapter
		class PressMouse extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				if (mode != 0 && color != null) {
					x1 = e.getX();
					y1 = e.getY();
				}
			}
			// add object in shapes arraylist and the condition in rectangle and oval are to get the points for each side upward or downward
			public void mouseReleased(MouseEvent e) {
				if (mode != 0 && color != null) {
					if(drawing)	{
						switch (mode) {
							case RECTANGLE:
								x2 = e.getX();
								y2 = e.getY();
								width = Math.abs(x1-x2);
								height = Math.abs(y1-y2);
								if(x2<x1&& y2<y1){
									x=x2;
									y=y2;
								}
								else if(x2>x1 && y2<y1){
									x=x1;
									y=y2;
								}
								else if(x2<x1 && y2 > y1){
									x=x2;
									y=y1;
								}
								else{
									x=x1;
									y=y1;
								}
								shapes.add(new Rectangle(x, y, width, height, color, isFill));
								break;
								
							case OVAL:
								x2 = e.getX();
								y2 = e.getY();
								width = Math.abs(x1-x2);
								height = Math.abs(y1-y2);
								if(x2<x1&& y2<y1){
									x=x2;
									y=y2;
								}
								else if(x2>x1 && y2<y1){
									x=x1;
									y=y2;
								}
								else if(x2<x1 && y2 > y1){
									x=x2;
									y=y1;
								}
								else{
									x=x1;
									y=y1;
								}
								shapes.add(new Oval(x, y, width, height, color, isFill));
								break;
								
							case LINE:
								x2 = e.getX();
								y2 = e.getY();
								shapes.add(new Line(x1, y1, x2, y2, color, isFill));
								break;
						}
						drawing = false;
						repaint();
					}
				}
			}
		}
		//getting values of x2 , y2 , width , height while dragging
		class DragMouse extends MouseMotionAdapter {
			public void mouseDragged(MouseEvent e) {
				if (mode != 0 && color != null) {
					switch (mode) {
						case RECTANGLE:
						case OVAL:
							drawing = true;
							x2 = e.getX();
							y2 = e.getY();
							width = Math.abs(x2 - x1);
							height = Math.abs(y2 - y1);
							break;		
							
						case LINE:
							drawing = true;
							x2 = e.getX();
							y2 = e.getY();
							break;
							
						case ERASER:
							x2 = e.getX();
							y2 = e.getY();
							shapes.add(new Rectangle(x2,y2,5,10,Color.white,true));
							break;
							
						case PENCIL:
							x2 = e.getX();
							y2 = e.getY();
							shapes.add(new Rectangle(x2,y2,2,3,color,true));
							break;
					}
					repaint();
				}
			}
		}
	
	public void init(){
		rectangleBt = new Button("Rectangle");
		add(rectangleBt);
		ovalBt = new Button("Oval");
		add(ovalBt);
		lineBt = new Button("Line");
		add(lineBt);
		eraserBt = new Button("Eraser");
		add(eraserBt);
		clearAllBt = new Button("Clear All");
		add(clearAllBt);
		undoBt = new Button("Undo");
		add(undoBt);
		pencilBt = new Button("Pencil");
		add(pencilBt);
		redBt = new Button("Red");
		add(redBt);
		blueBt = new Button("Blue");
		add(blueBt);
		greenBt = new Button("Green");
		add(greenBt);
		fillCb = new Checkbox("Fill",false);
		add(fillCb);
		addMouseListener(new PressMouse());
		addMouseMotionListener(new DragMouse());
		rectangleBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = RECTANGLE;
			}
		});
		ovalBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = OVAL;
			}
		});
		lineBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = LINE;
			}
		});
		eraserBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = ERASER;
			}
		});
		clearAllBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i = 0; i<shapes.size();i++){
					tempShapes.add(shapes.get(i));
				}
				shapes.clear();
				//shapes.add(new Rectangle(0,0,1920,1080,Color.white,true)); another way to clear all
				x=y=x1=x2=y1=y2=width=height=0;
				repaint();
			}
		});
		undoBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(shapes.size()>0){
					x=y=x1=x2=y1=y2=width=height=0;
					shapes.remove(shapes.size()-1);
					repaint();
				}
				else{
					for(int i = 0; i<tempShapes.size();i++){
						shapes.add(tempShapes.get(i));
					}
					tempShapes.clear();
					repaint();
				}
			}
		});
		pencilBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = PENCIL;
			}
		});
		redBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				color = Color.RED;
			}
		});
		blueBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				color=Color.BLUE;
			}
		});
		greenBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				color=Color.GREEN;
			}
		});
		fillCb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange()==1)
					isFill = true;
				else
					isFill = false;
			}
		});
		
	}
		
	public void paint(Graphics g){
		if(mode==0 && color == null)
			g.drawString("Choose shape and color first and start drawing",getWidth()/2-150,getHeight()/2);
		
		
		if(mode!=0 && color != null){
			for (int i=0;i<shapes.size();i++){
				g.setColor(shapes.get(i).color);
				shapes.get(i).draw(g);
			}
			
			switch(mode){
				case RECTANGLE:
					
					if(x2<x1&& y2<y1){
						x=x2;
						y=y2;
					}
					else if(x2>x1 && y2<y1){
						x=x1;
						y=y2;
					}
					else if(x2<x1 && y2 > y1){
						x=x2;
						y=y1;
					}
					else{
						x=x1;
						y=y1;
					}
					
					if (isFill)
					{
						g.setColor(color);
						g.fillRect(x, y, width, height);
					}
					else{
						g.setColor(color);
						g.drawRect(x, y, width, height);
					}
					break;
					
				case OVAL:
					if(x2<x1&& y2<y1){
						x=x2;
						y=y2;
					}
					else if(x2>x1 && y2<y1){
						x=x1;
						y=y2;
					}
					else if(x2<x1 && y2 > y1){
						x=x2;
						y=y1;
					}
					else{
						x=x1;
						y=y1;
					}
					
					if (isFill)
					{
						g.setColor(color);
						g.fillOval(x, y, width, height);

					}
					else{
						g.setColor(color);
						g.drawOval(x, y, width, height);
					}
					break;
					
				case LINE:
					g.setColor(color);
					g.drawLine(x1, y1, x2, y2);
					break;
						
			}
			
				
			
			
		}
		
	}
}

abstract class Shape {
    protected int x, y, width, height;
    protected Color color;
    protected boolean isFill;

    public Shape(int x, int y, int width, int height, Color color, boolean isFill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isFill = isFill;
    }
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void setIsFill(boolean isFill){
		this.isFill = isFill;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean getIsFill(){
		return isFill;
	}

    public abstract void draw(Graphics g);
}

class Rectangle extends Shape {
    public Rectangle(int x, int y, int width, int height, Color color, boolean isFill) {
        super(x, y, width, height, color, isFill);
    }

    public void draw(Graphics g) {
        if (isFill)
            g.fillRect(x, y, width, height);
        else
            g.drawRect(x, y, width, height);
    }
}

class Oval extends Shape {
    public Oval(int x, int y, int width, int height, Color color, boolean isFill) {
        super(x, y, width, height, color, isFill);
    }

    public void draw(Graphics g) {
        if (isFill)
            g.fillOval(x, y, width, height);
        else
            g.drawOval(x, y, width, height);
    }
}

class Line extends Shape {
    public Line(int x, int y, int width, int height, Color color, boolean isFill) {
        super(x, y, width, height, color, isFill);
    }

    public void draw(Graphics g) {
        g.drawLine(x, y, width, height);
    }
}



