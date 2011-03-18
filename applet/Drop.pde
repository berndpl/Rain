class Drop {
		
    int dropSlotX;
    int dropSlotZ;
		int posY;    
		int bpmDrop;
		int speed; 
		String lyric;
		
		Drop (int dropSlotX) {
    this.dropSlotX = dropSlotX;
		this.posY = 80; //cloud (60) + margin (20)
		if (lyricSwitch == true){ 
			this.lyric = lyrics[int(random(0,lyrics.length))];
		} else{
			this.lyric = "";
		}
		if (dynamicTempo == true){ 
			this.bpmDrop = int(random(bpm, bpm*4));
		} else{
			this.bpmDrop = bpm;
		}   
		this.dropSlotZ = int(random(0,80));
    setSpeed(); //sets speed
		}                      
		
		void fall(){
				setSpeed();         		 			
		  	posY +=speed + posY*0.04;           
//		  	posY +=speed;           
		
			//Draw
				strokeWeight(3);
				stroke(255);
				shapeMode(CENTER);            
				text(this.lyric,dropSlotX,posY,dropSlotZ);
				smooth();		
				shapeMode(CENTER);       
				//translate(300,0);                          		
				shape(dropShape,dropSlotX,posY); 		 
				//translate(-300,0);                          		
				println("X "+dropSlotX+"Y "+posY); 	

								
 

		}                             
		
		boolean hitGround(){
			if (posY >= height){
				rect(this.dropSlotX-20,height-10,40,10);
				posY = 0;
				return true;
			} else {
				return false;
			}

		}		          
		
		void setSpeed(){
			int getSpeed = int((height/frameRate * (bpmDrop/60))/20);
			if (getSpeed > 0) {
			 speed = int(getSpeed);
			}
		}

}


