package control;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.*;

/**
 * ��Ϸ������
 * @author TR
 *
 */
public class MusicPlayer {

	private String endBottomMusicpath = "music/EndRows.wav";// ������Ч�ļ�(���)
	private String eraseMusicpath = "music/delete.wav";// ������Ч�ļ�(��� )
	private String lenveUppath = "music/win.wav";// ����(���)
	private String gameOverpath = "music/lost.wav";// ��Ϸ����(���)
	private String shapeMovepath = "music/keydown.wav";// �ƶ�(���)
	private String gamestartpath = "music/ReadyGo.WAV";// ��ʼ(���)
	private String autougrowpath = "music/fixup.wav";// �Զ�����(���)
	Player p;
	
	public void playMusic(){
		play("music/back1.mid");
	}
	/**
	 * �����Զ�����
	 */
	public void playAutouGrow() {

		play(autougrowpath);

	}

	/**
	 * ���ſ�ʼ��Ч
	 */
	public void playStart() {
		play(gamestartpath);

	}

	/**
	 * ���Ŵ�����Ч
	 */
	public void playEndBottomSound() {

		play(endBottomMusicpath);

	}

	/**
	 * ����������Ч
	 */
	public void playEraseSound() {

		play(eraseMusicpath);

	}

	/**
	 * ��Ϸ����
	 */
	public void playGameOver() {

		play(gameOverpath);
	}

	/**
	 * �ƶ�
	 */
	public void playShapeMove() {
		play(shapeMovepath);
	}

	/**
	 * ����������Ч
	 */
	public void playLenveUp() {

		play(lenveUppath);

	}

	private void play(String soundFilePath) {

		try {
			File audioFile = new File(soundFilePath);// ���������ļ�����
			URL url = audioFile.toURI().toURL();
			final Player audioPlayer = Manager.createRealizedPlayer(url);// ����������

			audioPlayer.start();

			audioPlayer.addControllerListener(new ControllerListener() {
				@Override
				public void controllerUpdate(ControllerEvent arg0) {
					// TODO �Զ����ɵķ������
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