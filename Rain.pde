import ddf.minim.*;   
import fullscreen.*;     

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
Cloud[] clouds = new Cloud[3]; 
String[] lyrics;


void setup() { 
  size(800,600,P3D);
  frameRate(30); 
	//Fullscreen
  fs = new FullScreen(this);
  fs.setResolution(1200, 760);  
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
	dropShape.scale(0.04);  
	//Text 
  lyrics = loadStrings("lyrics.txt"); 
}

void draw() {
  background(0);                      
  fill(255);                          
//smooth();
  //HUD                                            
	audioLevel = input.mix.level () * audioGain;
	if (hudSwitch == true){    	
	  text("FPS "+int(frameRate),20,40);  
	  text("Level: " + (float)input.mix.level(),20,60);
	  text("Gained: " + (float)audioLevel,20,80); 
	  text("Gain [ü,+]: " + (int)audioGain,20,100); 
	  text("Threshhold [ä,#]: " + (int)audioThreshhold,20,120); 
	  text("BPM [j,k]: " + bpm,20,140); 
	  text("Dynamic [d]: " + dynamicTempo,20,160); 
	  text("Rain [r]: " + rainSwitch,20,180); 
	}
  



	//Make clouds rain
	if (rainSwitch == true){    
		if (audioLevel > audioThreshhold) {
		clouds[0].createDrops(int(audioLevel));
		}                             
	}
	
	clouds[0].rain();
	    

}

void keyPressed(){
  if (key == ' '){
		 if (tapBpm != 0){
			println("BPM Millis "+millis());
			println("BPM TapBpm "+tapBpm);
			bpm = int(((millis() - tapBpm)/1000)*60);
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
	if (key == 'ü'){
		audioGain += 5;
	}
	if (key == '+'){
		audioGain -= 5;
	}
	if (key == 'ä'){
		audioThreshhold += 0.5;
	}                     
 	if (key == '#'){
		audioThreshhold -= 0.5;
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
}
