package com.qfedu.planefight.core;

import com.qfedu.planefight.constant.Constant;
import com.qfedu.planefight.start.PlaneFightStart;
import com.qfedu.planefight.util.ImageUtil;
import com.qfedu.planefight.util.MusicUtil;

import java.awt.*;
import java.util.Random;

/**
 * @author Leon Downey
 * @date 2023/2/8 10:53
 * @describe： 道具类
 */
public class Item extends PlaneFightObject {

	private int type;
	public boolean live;
	private Plane myPlane;

	public Item(PlaneFightStart pfs, int x, int y) {
		live = true;
		this.pfs = pfs;
		this.x = x;
		this.y = y;
		this.width = imgs[0].getWidth(null);
		this.height = imgs[0].getHeight(null);
		this.myPlane = pfs.myPlane;
	}

	static Image imgs[] = new Image[6];
	static {
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = ImageUtil.images.get("item_0" + (i + 1));
		}
	}
	int changeY = 2;

	@Override
	public void move() {
		// changeY+=1;
		y += changeY;
	}

	//用数字来控制随机道具
	private Random random = new Random();
	int num = random.nextInt(15);
	long effectStart;

	private int preBlood;
	private int preLevel;
	private int preScore;
	private int preType;
	@Override
	public void draw(Graphics g) {
		if (live) {
			if (num <= 2) {
				type = 1;
			} else if (num <= 4) {
				type = 2;
			} else if (num <= 6) {
				type = 3;
			} else if (num <= 8) {
				type = 4;
			} else if (num <= 10) {
				type = 5;
			} else if (num <= 12) {
				type = 6;
				flag=true;
			}
			if(type!=0){
				preBlood=myPlane.blood;
				preLevel=myPlane.level;
				preScore=myPlane.score;
				preType=myPlane.type;
				effectStart = System.currentTimeMillis();
				g.drawImage(imgs[type-1], x, y, null);
			}
			move();
			outOfBounds();
		} else {
			// 效果
			generateEffect(g);
			long end = System.currentTimeMillis();
			if(end-effectStart>=50&&end-effectStart<100)
				new MusicUtil("getItem").start();
			if(end-effectStart>=5000&&end-effectStart<=5100){
				pfs.items.remove(this);
			}
		}
	}
	boolean flag = false;
	private void generateEffect(Graphics g) {
		switch (type) {
		case 1://升级
			if(myPlane.level<3&&preLevel==myPlane.level){
				myPlane.level+=1;
			}
			break;
		case 2://换子弹
			if(myPlane.type==preType&&type<=3){
				myPlane.type= new Random().nextInt(3)+1;
			}
			break;
		case 3:// 加血
			if (myPlane.blood >= (Constant.MYPLANE_MAX_BOOLD - 20)) {
				myPlane.blood = Constant.MYPLANE_MAX_BOOLD;
			} else {
				myPlane.blood += 20;
				preBlood+=20;
			}
			break;
		case 4:// 防护罩
			Image img = ImageUtil.images.get("effect_0" + (type));
			g.drawImage(img, myPlane.x + (myPlane.width - img.getWidth(null)) / 2,
					myPlane.y + (myPlane.height - img.getHeight(null)) / 2, null);
			myPlane.blood=preBlood;
			break;
		case 5://星星加积分
			if(preScore==myPlane.score){
				myPlane.score+=200;
			}
			break;
		case 6://给大招
			if(flag){
				myPlane.superFireCount+=10;
			}
			flag=false;
			break;
		}
	}

	long time=0;
	/**
	 * 道具遇到我方飞机的办法
	 */
	public boolean hitMyPlane(Plane p) {
		if (this.getRectangle().intersects(p.getRectangle()) && p.live) {
			this.live = false;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 道具边界处理
	 */
	private void outOfBounds() {
		if ((x >= (Constant.GAME_WIDTH - width) || x <= 0) || (y >= (Constant.GAME_HEIGHT - height) || y <= 0)) {
			this.live = false;
			this.pfs.items.remove(this);
		}
	}
}
