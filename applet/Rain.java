import processing.core.*; 
import processing.xml.*; 

import processing.opengl.*; 
import ddf.minim.*; 
import fullscreen.*; 
import processing.opengl.*; 
import codeanticode.glgraphics.*; 
import deadpixel.keystone.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Rain extends PApplet {


   
  
//Projection



   
GLGraphicsOffScreen offscreenA; 
GLGraphicsOffScreen offscreenB; 
Keystone ks;
CornerPinSurface surfaceA;
CornerPinSurface surfaceB;

Minim minim;
AudioSample beep;
AudioInput input;
float audioLevel = 0;  
int audioGain = 50;
float audioThreshhold = 0;

FullScreen fs;  
PFont font;  
PShape dropShape;
PShape cloudShape;                  

int bpm = 60; 
int tapBpm = 0; 
boolean dynamicTempo = true;
boolean rainSwitch = true;
boolean lyricSwitch = true;
boolean hudSwitch = true; 

ArrayList drops;
ArrayList blobs;
Cloud[] clouds = new Cloud[1]; 
String[] lyrics;

int cloudWidth;
int cloudHeight;

public void setup() { 
	//  size(1024,600,OPENGL);
  size(1024,600,GLConstants.GLGRAPHICS); 
	hint(DISABLE_DEPTH_TEST);
	//Projection
  offscreenA = new GLGraphicsOffScreen(this, width-10, height-10);
  offscreenB = new GLGraphicsOffScreen(this, width-50, height-50);
  ks = new Keystone(this);
  surfaceA = ks.createCornerPinSurface(width, height, 20);
  surfaceB = ks.createCornerPinSurface(width, height, 20);

  frameRate(30);  
	//Fullscreen
  fs = new FullScreen(this);
  fs.setResolution(1024, 600);  
  fs.setShortcutsEnabled(true);
  //Font
	font = loadFont("JUICELight-48.vlw");
  textFont(font,18);
	//Sound
	minim = new Minim(this);
  /*beep = minim.loadSample("tick.wav");*/
  input = minim.getLineIn(Minim.STEREO, 512);
	//Objects
	drops = new ArrayList();
	blobs = new ArrayList();
	clouds[0] = new Cloud();             
	dropShape = loadShape("drop.svg"); 
	cloudShape = loadShape("cloud.svg"); 
	cloudHeight = PApplet.parseInt(cloudShape.height);
	cloudWidth = PApplet.parseInt(cloudShape.width);
	println("shape "+cloudShape.width);             
	//Text 
  lyrics = loadStrings("lyrics.txt"); 
}

public void draw() {

  PVector mouse = surfaceA.getTransformedMouse();
  offscreenA.beginDraw();

  background(0);                      
  fill(255);  

	// convert                      
  //HUD                                            
	audioLevel = input.mix.level () * audioGain;
	if (hudSwitch == true){    	
	  text("FPS "+PApplet.parseInt(frameRate),20,40);  
	  text("Level: " + (float)input.mix.level(),20,60);
	  text("Gained: " + (float)audioLevel,20,80); 
	  text("Gain [\u00fc,+]: " + (int)audioGain,20,100); 
	  text("Threshhold [\u00e4,#]: " + (int)audioThreshhold,20,120); 
	  text("BPM [j,k]: " + bpm,20,140); 
	  text("Dynamic [d]: " + dynamicTempo,20,160); 
	  text("Rain [r]: " + rainSwitch,20,180); 
	}

	//Make clouds rain
	if (rainSwitch == true){    
		if (audioLevel > audioThreshhold) {
		clouds[0].createDrops(PApplet.parseInt(audioLevel));
		}                             
	}
	
	clouds[0].rain("drops");

	    
  offscreenA.endDraw();   


  offscreenB.beginDraw();
  background(0);                             
	clouds[0].rain("blobs");   

  offscreenB.endDraw();   

  background(0);

  surfaceA.render(offscreenA.getTexture());                 
  surfaceB.render(offscreenB.getTexture());                 
}

public void keyPressed(){
  if (key == ' '){
		 if (tapBpm != 0){
			println("BPM Millis "+millis());
			println("BPM TapBpm "+tapBpm);
			bpm = PApplet.parseInt(((millis() - tapBpm)/1000)*60);
			tapBpm = millis();     
			println("BPM Tap "+bpm);
		 } else {
			tapBpm = millis();			
		}
  } 
	if (key == 'j'){
		bpm += 5;
	}
	if (key == 'k'){
		bpm -= 5;
	}
	if (key == '\u00fc'){
		audioGain += 5;
	}
	if (key == '+'){
		audioGain -= 5;
	}
	if (key == '\u00e4'){
		audioThreshhold += 0.5f;
	}                     
 	if (key == '#'){
		audioThreshhold -= 0.5f;
	}  	
	if (key == 'd'){
		if (dynamicTempo == true) {
			dynamicTempo = false;
		} else {
			dynamicTempo = true;
		}
	}  	
	if (key == 'r'){
		if (rainSwitch == true) {
			rainSwitch = false;
		} else {
			rainSwitch = true;
		}
	} 	
	if (key == 'l'){
		if (lyricSwitch == true) {
			lyricSwitch = false;
		} else {
			lyricSwitch = true;
		}
	}  
	if (key == 'h'){
		if (hudSwitch == true) {
			hudSwitch = false;
		} else {
			hudSwitch = true;
		}
	}  
	if (key == 'x'){
		clouds[0].createDrops(1);
	}  
  switch(key) {
  case 'c':
    // enter/leave calibration mode, where surfaces can be warped 
    // & moved
    ks.toggleCalibration();
    break;

  case 'a':
    // loads the saved layout
    ks.load();
    break;

  case 's':
    // saves the layout
    ks.save();
    break;
  }	                   
}
class Blob {                
	int blobX;
	int blobZ;
	int r;
	int speed;
	
	Blob (int dropSlotX, int dropSlotZ){
		this.blobX = dropSlotX;
		this.blobZ = dropSlotZ;
		this.r = 10;
	}         
	
	public void spread(){
		ellipse(blobX, blobZ, r, r);
		r += 1;      
	}     
	
	public boolean fullSpread(){
		if (r > 100){
			return true;
		} else {
			return false;
		}
	}
	
}
class Cloud {    
	int cloudWidth = 90;
	int dropsAmount = 10;  
	int cloudStart;
  int cloudEnd;  
	int dropSlotY;	

	Cloud () {		
		this.cloudStart = (width/2) - (cloudWidth/2);
    this.cloudEnd = this.cloudStart + cloudWidth;  
		this.dropSlotY = cloudHeight + 100; 
		//Draw Cloud
    line(cloudStart, 10, cloudEnd, 10);   

	}

	public void createDrops(int dropsAmount){
		this.dropsAmount = dropsAmount; 
		//Create Drops		
		for (int i = 0; i < this.dropsAmount; i++){                    
			int dropSlotX = PApplet.parseInt(random(cloudStart, cloudEnd));			
//			println("dropsAmount: " + dropsAmount);
//			int dropProbability = int(random(0, 100)); 
//			if (dropProbability == 0)  {		       
						println("drop.dropSlotX (Add) "+drop.dropSlotX);
						println("drop.dropSlotX (Add) "+drop.dropSlotZ);
			drops.add(new Drop(dropSlotX,dropSlotY));
//			}
	  }		       		

	}      
	
	public void drawCloud(int distort){
		//Draw Cloud    
		noStroke();
		shapeMode(CENTER);                                 
//		shape(cloudShape,cloudStart+(cloudWidth/2),dropSlotY);
		shape(cloudShape,cloudStart+(cloudWidth/2),dropSlotY);
	}
	
	public void rain(String type){        
		if (type == "drops"){
	    //Draw Cloud
			drawCloud(dropsAmount);
			//Draw Drops      
			for (int i = 0; i < drops.size()-1; i++){
				Drop drop = (Drop) drops.get(i);
				drop.fall();                   
					if (drop.hitGround()){ 
						blobs.add(new Blob(drop.dropSlotX,drop.dropSlotZ));
//						println("blobX "+drop.dropSlotX);
//						println("blobY "+drop.dropSlotZ);						
						drops.remove(i);
					} 			
				}                 
		} else if (type == "blobs") {
			for (int z = 0; z < blobs.size()-1; z++){
				Blob blob = (Blob) blobs.get(z);
				blob.spread(); 
				if (blob.fullSpread()){
					blobs.remove(z); 
				}
			}                           
		}
	}

}
class Drop {
		
    int dropSlotX;
    int dropSlotZ;
		int posY;    
		int bpmDrop;
		int speed; 
		String lyric;
		
		Drop (int dropSlotX, int dropSlotY) {
    this.dropSlotX = dropSlotX;
		this.posY = dropSlotY + (cloudHeight/2) - 10; //cloud (60) + margin (20)
		if (lyricSwitch == true){ 
			this.lyric = lyrics[PApplet.parseInt(random(0,lyrics.length))];
		} else{
			this.lyric = "";
		}
		if (dynamicTempo == true){ 
			this.bpmDrop = PApplet.parseInt(random(bpm, bpm*4));
		} else{
			this.bpmDrop = bpm;
		}   
		this.dropSlotZ = PApplet.parseInt(random(0,80));
		println("dropSlotX (Create) "+dropSlotX);
		println("dropSlotZ (Create) "+dropSlotZ);
    setSpeed(); //sets speed
		}                      
		
		public void fall(){
				//setSpeed();         		 			
		  	posY += speed + (posY/8) * 0.2f;           
//		  	posY += speed + (posY * (posY/500));           		       
			
			//Draw
				strokeWeight(3);
				stroke(255);
				shapeMode(CENTER);            
				shape(dropShape,dropSlotX,posY); 		    		
				text(this.lyric,dropSlotX,posY,dropSlotZ);    		
		}                             
		
		public boolean hitGround(){
			if (posY >= height){
				rect(this.dropSlotX-20,height-10,40,10);
				posY = 0;       
				println("blobX (HitGround)"+this.dropSlotX);
				println("blobY (HitGround)"+this.dropSlotZ);						
				
				return true;
			} else {
				return false;
			}

		}		          
		
		public void setSpeed(){
			int getSpeed = PApplet.parseInt((height/frameRate * (bpmDrop/60))/20);
			if (getSpeed > 0) {
			 speed = PApplet.parseInt(getSpeed);
			}
		}

}



  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Rain" });
  }
}
