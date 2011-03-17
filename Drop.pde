class Drop {
		
    int dropSlotX;
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
    setSpeed(); //sets speed
		}                      
		
		void fall(){
				setSpeed();         		 			
		  	posY +=speed;
			//Draw
				strokeWeight(3);
				stroke(255);
				//point(dropSlotX,posY);
				/*smooth();				                              
				pushMatrix();         
			  rotate(radians(30 * 10));  // 30 * 12 = 360 degrees
				translate(200,400);
				text(this.lyric,dropSlotX+posY,posY-10);
			  popMatrix();  */        
				text(this.lyric,dropSlotX-20,posY-10);
				smooth();				
				shape(dropShape,dropSlotX-10,posY-20); 				
								
 

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


