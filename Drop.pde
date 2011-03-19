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
		  	posY += speed + (posY/8) * 0.2;           			
				//Draw
				strokeWeight(3);
				stroke(255);
				shapeMode(CENTER);            
				shape(dropShape,dropSlotX,posY); 		    		
				text(this.lyric,dropSlotX,posY,dropSlotZ);    		
		}                             
		
		boolean hitGround(){
			if (posY >= height){
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