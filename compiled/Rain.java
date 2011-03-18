import processing.core.*; 
import processing.xml.*; 

import processing.opengl.*; 
import ddf.minim.*; 
import fullscreen.*; 
import peasy.*; 

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


   
 
 

PeasyCam cam;   

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
Cloud[] clouds = new Cloud[1]; 
String[] lyrics;

int cloudWidth;
int cloudHeight;

public void setup() { 
  size(1024,600,OPENGL);
  frameRate(30);  
// cam = new PeasyCam(this, 1000);
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
  background(0);                      
  fill(255);  

//println("Count "+frameCount%2);

/*
if (frameCount%2 == 0){
	camera(width/2, height/2, width/2, width/2, height/2, 0,
		       0.0, 1.0, 0.0);
	} else {
		camera(width/2+50, height/2, width/2, width/2, height/2, 0,
			       0.0, 1.0, 0.0);		
	}
*/

//rotateY(mouseX);    

	pushMatrix();
//	translate(100, 100); 
//	rotateY(90);    
//	stroke(1);
//	fill(50);
//	box(10,10,10);     
	popMatrix();
                     
//smooth();
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

//	translate(width/2, height/2);
//	rotateY(radians(mouseX));


	//Make clouds rain
	if (rainSwitch == true){    
		if (audioLevel >  audioThreshhold) {
		clouds[0].createDrops(PApplet.parseInt(audioLevel));
		}                             
	}
	
	clouds[0].rain();
	    

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
		for (int i = 0; i <  this.dropsAmount; i++){                    
			int dropSlotX = PApplet.parseInt(random(cloudStart, cloudEnd));			
//			println("dropsAmount: " + dropsAmount);
//			int dropProbability = int(random(0, 100)); 
//			if (dropProbability == 0)  {		
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
	
	public void rain(){        
    //Draw Cloud
		drawCloud(dropsAmount);
		//Draw Drops      
		for (int i = 0; i <  drops.size()-1; i++){
			Drop drop = (Drop) drops.get(i);
			drop.fall();                   
			if (drop.hitGround()){
				drops.remove(i); 
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
    setSpeed(); //sets speed
		}                      
		
		public void fall(){
				setSpeed();         		 			
		  	posY +=(speed * 0.04f);           
		
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
				return true;
			} else {
				return false;
			}

		}		          
		
		public void setSpeed(){
			int getSpeed = PApplet.parseInt((height/frameRate * (bpmDrop/60))/20);
			if (getSpeed >  0) {
			 speed = PApplet.parseInt(getSpeed);
			}
		}

}


  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Rain" });
  }
}
