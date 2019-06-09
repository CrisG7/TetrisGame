package control;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.*;

/**
 * 游戏音乐类
 * @author TR
 *
 */
public class MusicPlayer {

	private String endBottomMusicpath = "music/EndRows.wav";// 触底音效文件(完成)
	private String eraseMusicpath = "music/delete.wav";// 消行音效文件(完成 )
	private String lenveUppath = "music/win.wav";// 升级(完成)
	private String gameOverpath = "music/lost.wav";// 游戏结束(完成)
	private String shapeMovepath = "music/keydown.wav";// 移动(完成)
	private String gamestartpath = "music/ReadyGo.WAV";// 开始(完成)
	private String autougrowpath = "music/fixup.wav";// 自动上涨(完成)
	Player p;
	
	public void playMusic(){
		play("music/back1.mid");
	}
	/**
	 * 播放自动上涨
	 */
	public void playAutouGrow() {

		play(autougrowpath);

	}

	/**
	 * 播放开始音效
	 */
	public void playStart() {
		play(gamestartpath);

	}

	/**
	 * 播放触底音效
	 */
	public void playEndBottomSound() {

		play(endBottomMusicpath);

	}

	/**
	 * 播放消行音效
	 */
	public void playEraseSound() {

		play(eraseMusicpath);

	}

	/**
	 * 游戏结束
	 */
	public void playGameOver() {

		play(gameOverpath);
	}

	/**
	 * 移动
	 */
	public void playShapeMove() {
		play(shapeMovepath);
	}

	/**
	 * 播放升级音效
	 */
	public void playLenveUp() {

		play(lenveUppath);

	}

	private void play(String soundFilePath) {

		try {
			File audioFile = new File(soundFilePath);// 创建声音文件对象
			URL url = audioFile.toURI().toURL();
			final Player audioPlayer = Manager.createRealizedPlayer(url);// 创建播放器

			audioPlayer.start();

			audioPlayer.addControllerListener(new ControllerListener() {
				@Override
				public void controllerUpdate(ControllerEvent arg0) {
					// TODO 自动生成的方法存根
					if (arg0 instanceof EndOfMediaEvent) {
						audioPlayer.close();
					}
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoPlayerException e) {
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}