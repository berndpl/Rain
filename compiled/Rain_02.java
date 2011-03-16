import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 

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

public class Rain_02 extends PApplet {

  

Minim minim;
AudioSample beep;
AudioInput input;
float audioLevel = 0;  
int audioGain = 50;
float audioThreshhold = 0;

PFont font;  
PShape dropShape;                  

int bpm = 60; 
int tapBpm = 0;
            
ArrayList drops;
Cloud[] clouds = new Cloud[3];  

public void setup() { 
  size(800,600,P3D);
  frameRate(30);
  //Font
	font = createFont("Arial",48);
  textFont(font,12);
	//Sound
	minim = new Minim(this);
  /*beep = minim.loadSample("tick.wav");*/
  input = minim.getLineIn(Minim.STEREO, 512);
	//Objects
	drops = new ArrayList();
	clouds[0] = new Cloud(); 
	dropShape = loadShape("drop_white.svg"); 
	dropShape.scale(0.04f);
}

public void draw() {
  background(0);                      
  fill(255);                          
//smooth();
  //HUD
  text("FPS "+PApplet.parseInt(frameRate),20,40);  
	audioLevel = input.mix.level ();  
  text("Level: " + (float)audioLevel,20,60);
	audioLevel = audioLevel * audioGain;
  text("Gained: " + (float)audioLevel,20,80); 
  text("Gain [\u00fc,+]: " + (int)audioGain,20,100); 
  text("Threshhold [\u00e4,#]: " + (int)audioThreshhold,20,120); 
  text("BPM [j,k]: " + bpm,20,140); 

	//Make clouds rain    
	if (audioLevel >  audioThreshhold) {
	clouds[0].createDrops(PApplet.parseInt(audioLevel));
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
}
class Cloud {    
	int cloudWidth = 90;
	int dropsAmount = 10;  
	int cloudStart;
	int cloudHeight;	
  int cloudEnd;

	Cloud () {		
		this.cloudStart = width/2 - cloudWidth/2;
    this.cloudEnd = cloudStart + cloudWidth;  
		this.cloudHeight = 20;
		//Draw Cloud
    line(cloudStart, 1, cloudEnd, 1);   

	}

	public void createDrops(int dropsAmount){
		this.dropsAmount = dropsAmount; 
		//Create Drops		
		for (int i = 0; i <  this.dropsAmount; i++){                    
			int dropSlotX = PApplet.parseInt(random(cloudStart, cloudEnd));			
			println("dropsAmount: " + dropsAmount);
			int dropProbability = PApplet.parseInt(random(0, 100)); 
			if (dropProbability == 0)  {		
			drops.add(new Drop(dropSlotX));
			}
	  }		       		

	}
	
	public void rain(){        
		//Draw Cloud
		noStroke();
		rect(this.cloudStart+62,this.cloudHeight+19,39,39);
		rect(this.cloudStart+43,this.cloudHeight+7,39,39); 	
		rect(this.cloudStart+43,this.cloudHeight+28,39,39); 	
		rect(this.cloudStart+5,this.cloudHeight+28,39,39); 	
		rect(this.cloudStart+5,this.cloudHeight+0,39,39); 	
		rect(this.cloudStart+(-15),this.cloudHeight+19,39,39);
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
		int posY;    
		int bpm;
		int speed;
		
		Drop (int dropSlotX) {
    this.dropSlotX = dropSlotX;
		this.posY = 80; //cloud (60) + margin (20)
		this.bpm = bpm;
    setSpeed(); //sets speed
		}                      
		
		public void fall(){
				setSpeed();         		 			
		  	posY +=speed;
			//Draw
				strokeWeight(3);
				stroke(255);
				point(dropSlotX,posY);
//				shape(dropShape,dropSlotX,posY); 
				smooth();				
				shape(dropShape,dropSlotX-10,posY-20); 				
								
 

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
			int getSpeed = PApplet.parseInt((height/frameRate * (bpm/60))/20);
			if (getSpeed >  0) {
			 speed = PApplet.parseInt(getSpeed);
			}
		}

}


  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Rain_02" });
  }
}
