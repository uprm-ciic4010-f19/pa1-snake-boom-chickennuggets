package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.Entities.Static.Apple;
import Game.GameStates.GameOverState;
import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;
	public Tail tail= null;
	public int xCoord;
	public int yCoord;
	public double Score = 0;
	public boolean GameOverMusic;
	public int speed = 6;
	public int moveCounter;
	public static int step = 0;
	

	public String direction;//is your first name one?



	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;


	}

	public void tick(){
		moveCounter++;
		if(moveCounter>=speed) {
			checkCollisionAndMove();
			moveCounter=0;
			step++;
			
			
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			handler.getWorld().body.addFirst(new Tail(this.xCoord, this.yCoord,handler));
			lenght++;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
			if (direction == "Down") backTracking();
			else 
			direction="Up";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
			if (direction == "Up") backTracking();
			else 
			direction="Down";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
			if (direction == "Right") backTracking();
			else 
			direction="Left";

		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
			if (direction == "Left") backTracking();
			else 
			direction="Right";
			
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			State.setState(handler.getGame().pauseState);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
			speed--;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
			speed++;
		}
		if(Apple.isGood(step) == false) {
			//System.out.print(false);
		}
	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord== 0) {
				xCoord = handler.getWorld().GridWidthHeightPixelCount - 1; // Check Collision with left walls
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = handler.getWorld().GridWidthHeightPixelCount - 60; // Check Collision with right walls
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = handler.getWorld().GridWidthHeightPixelCount -1; // Check Collision with upper wall
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = handler.getWorld().GridWidthHeightPixelCount - 60; // Check Collision with lower wall
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;
		


		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));

		}

		for(int i=0; i < handler.getWorld().body.size(); i++) {
			if(this.xCoord == handler.getWorld().body.get(i).x && this.yCoord == handler.getWorld().body.get(i).y) {
				State.setState (handler.getGame().GameOverState); // GameOverState
			
			}
		}
		
	}





	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		int x = r.nextInt(50);
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				g.setColor(Color.green);


				/*if (x>0 && x <=25) Cambia de color simultaneamente. 
					g.setColor(Color.red);
				else if (x > 25 && x <=50)
					g.setColor(Color.blue);
				else if (x > 50 && x <=75)
					g.setColor(Color.GREEN);
				else if (x>75 && x <= 100)
					g.setColor(Color.YELLOW);*/


				if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

			}
		}

	}

	public void Eat(){
		lenght++;

		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;
		Score();
	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
	
	public void backTracking() {  
		if (lenght >= 2)
		{		switch (direction){
		case "Right":
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
				direction = "Right";
				break;
			}
		case "Left":
			
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
			direction = "Left";
			break;
		}
		
		case "Up":
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
				direction = "Up";
				break;
			}
			
		case"Down":
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
				direction = "Down";
				break;
			}
		}
	}
	}
		 
	
		
		public void Score() {
			/*if (handler.getWorld().playerLocation[tail.x][tail.y] == true && Apple.isGood(step) == false) {
				Score = Score - Math.sqrt(2*Score + 1);
		    	System.out.println(Score);
			}*/
			if(handler.getWorld().playerLocation[tail.x][tail.y] == true);
			if (Apple.isGood(step) == false) {
				if (Score == 0) Score = 0;
				
				else if (Score >= (Score - Math.sqrt(2*Score + 1))){
				Score = Score - Math.sqrt(2*Score + 1);
				if (Score < 0) Score = 0;
		    	System.out.println(Score);
				}
				
			}
			
			else if (Apple.isGood(step) == true) {
					Score = Score + Math.sqrt(2*Score + 1);
					System.out.println(Score);
					
			}
			//System.out.print(Apple.isGood(step));
			//System.out.print(step);
			step = 0;
		
		}
		public void renderScore(Graphics g) {
			String ScoreString = String.valueOf(Score);
			int fontSize = 20;
			g.setColor(Color.CYAN);
			g.setFont(new Font("BroadWay", Font.PLAIN, fontSize));
			g.drawString("Score:"+ScoreString, handler.getWorld().GridWidthHeightPixelCount / 2,15);
		}
}
	

