import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Monster implements Runnable{
	// �넫�슦紐�:320,100
	private Thread th;
	private static int count;
	public static int BOSS_MOVE=50;
	
	// 占쎄텢占쎌뒠占쎈막 占쎌뵠沃섎챷占�
	private Image bossBasicImage = new ImageIcon(JavaGameServer.class.getResource("/assets/monster/boss.png"))
		.getImage();
	private Image bossTrapImage = new ImageIcon(JavaGameServer.class.getResource("/assets/monster/trap_boss.png"))
		.getImage();
	private Image bossAngryImage = new ImageIcon(JavaGameServer.class.getResource("/assets/monster/angry_boss.png"))
		.getImage();
	
	private int x, y;
	
	// 占쎌겱占쎌삺 占쎄맒占쎄묶
	private Image currentState = bossBasicImage;
	
	// 占쎈립�⑨옙 占쎌맄燁삼옙
	private static final int GROUND_START_X=18;
	private static final int GROUND_START_Y=45;
	private static final int GROUND_END_X=720;
	private static final int GROUND_END_Y=800;
	
	// 獄쎻뫚堉�
	private String direction = "stop";
	private int directionInt = 0;
	
	// 占쎄문占쎄쉐占쎌쁽
	public Monster(int x, int y) {
		this.x = x;
		this.y = y;
		start();
	}
	
	public Image getCurrentState() {
		return currentState;
	}
	public void setCurrentState(Image state) {
		this.currentState = state;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	// 野껊슣�뿫 占쎈뻻占쎌삂 占쎌뜎 0.1�룯占� 揶쏄쑨爰쏙옙�몵嚥∽옙 7甕곤옙 �⑤벀爰쏙옙釉�
	public void initBossAttack() {
		setCurrentState(bossAngryImage);
		
		for(int i=0;i<7;i++) {
//			System.out.println("癰귣똻�뮞揶쏉옙 �룯�뜃由� �⑤벀爰�");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 野껊슣�뿫 占쎈즲餓ο옙 1�룯占� 揶쏄쑨爰쏙옙�몵嚥∽옙 3甕곤옙 �⑤벀爰쏙옙釉�
	public void bossAttack() {
		setCurrentState(bossAngryImage);
		
		for(int i=0;i<3;i++) {
//			System.out.println("癰귣똻�뮞揶쏉옙 �⑤벀爰�");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changeBasic() {
		setCurrentState(bossBasicImage);
		directionInt = (int)(Math.random() * 3)+1; // 1:left, 2:right, 3:top, 4:down
		for(int i=0;i<8;i++) {
			switch(directionInt) {
			case 0:
				direction = "stop";
				break;
			case 1:
				direction = "left";
				
				if((x-BOSS_MOVE) <= GROUND_START_X+BOSS_MOVE) {
					direction = "right";
					directionInt=2;
//					System.out.println("占쎌겱占쎌삺 �넫�슦紐�:"+x+", "+y);
				}else
					x-=BOSS_MOVE;
					
				break;
			case 2:
				direction = "right";
				
				if((x+BOSS_MOVE) >= GROUND_END_X-BOSS_MOVE) {
					direction = "left";
					directionInt=1;
//					System.out.println("占쎌겱占쎌삺 �넫�슦紐�:"+x+", "+y);
				}else
					x+=BOSS_MOVE;
					
				break;
			case 3:
				direction = "top";
				
				if((y-BOSS_MOVE) <= GROUND_START_Y+BOSS_MOVE) {
					direction = "down";
					directionInt=4;
//					System.out.println("占쎌겱占쎌삺 �넫�슦紐�:"+x+", "+y);
				}else
					y-=BOSS_MOVE;
				break;
			case 4:
				direction = "down";
				
				if((y+BOSS_MOVE) >= GROUND_END_Y-BOSS_MOVE) {
					direction = "top";
					directionInt=3;
//					System.out.println("占쎌겱占쎌삺 �넫�슦紐�:"+x+", "+y);
				} else
					y+=BOSS_MOVE;
				break;
			}
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for�눧占� 占쎄국
	}
	
	public void start() {
		th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		initBossAttack();
		while(true) {
			changeBasic();
			changeBasic();
			bossAttack();
		}
	}
}