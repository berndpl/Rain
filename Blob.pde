class Blob {                
	int blobX;
	int blobZ;
	int r;
	int rMax; 
	int transparency;
	int speed = 5;
	
	Blob (int dropSlotX, int dropSlotZ){
		this.blobX = dropSlotX;
		this.blobZ = dropSlotZ;   
		this.r = 1;
		this.rMax = 800;
		transparency = 150;		
	}         
	
	void spread(){         
		if (r > 200) {
			transparency = transparency - 30;
		}                    
//01		stroke(255,transparency*1.2);		
//02		stroke(255,255,255,transparency);		
//02		fill(255,255,255,transparency);
				stroke(255,255,255,transparency);		
		fill(0,0);
		ellipse(blobX, blobZ, r, r);
		r += speed;
	}     
	
	boolean fullSpread(){
		if (r > rMax){
			return true;
		} else {
			return false;
		}
	}
	
}