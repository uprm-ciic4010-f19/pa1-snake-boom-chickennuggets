package Game.GameStates;


import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Game.Entities.Dynamic.Player;


public class GameOverState extends State {


	private UIManager uiManager;

	private InputStream audioFile;
	private AudioInputStream audioStream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip audioClip;
	

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);
		
		
		uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/2+104, 128, 64, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));
        
        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/2+164, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
	}

	
	
	
	/*public void Initmusic() {
		
		try {
			
			audioFile = getClass().getResourceAsStream("/music/SnakeGameOver.wav");
			audioStream = AudioSystem.getAudioInputStream(audioFile);
			format = audioStream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();

	}


	@Override
	public void render(Graphics g) {
		double GameScore = Player.Score;
		String ScoreString = String.valueOf(GameScore);
		int fontSize = 30;
		g.setColor(Color.RED);
		g.setFont(new Font("BroadWay", Font.PLAIN, fontSize));
		g.drawImage(Images.endGame, 0, 0, handler.getGame().getWidth(),handler.getGame().getHeight(), null);
		g.drawString("Final Score:"+ScoreString, (handler.getWidth()/2)-100,handler.getHeight() -320);
		g.setColor(Color.BLACK);
		//g.fillRect(0,0,handler.getWidth(),handler.getHeight());
		uiManager.Render(g);

	}
	
}

