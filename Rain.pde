import ddf.minim.*;  

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

void setup() { 
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
	dropShape.scale(0.04);
}

void draw() {
  background(0);                      
  fill(255);                          
//smooth();
  //HUD
  text("FPS "+int(frameRate),20,40);  
	audioLevel = input.mix.level ();  
  text("Level: " + (float)audioLevel,20,60);
	audioLevel = audioLevel * audioGain;
  text("Gained: " + (float)audioLevel,20,80); 
  text("Gain [ü,+]: " + (int)audioGain,20,100); 
  text("Threshhold [ä,#]: " + (int)audioThreshhold,20,120); 
  text("BPM [j,k]: " + bpm,20,140); 

	//Make clouds rain    
	if (audioLevel > audioThreshhold) {
	clouds[0].createDrops(int(audioLevel));
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
}
