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
		stroke(255,transparency*1.2);		
		fill(255,255,255,transparency);
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