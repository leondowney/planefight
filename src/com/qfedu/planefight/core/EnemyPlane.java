package com.qfedu.planefight.core;

import com.qfedu.planefight.constant.Constant;
import com.qfedu.planefight.start.PlaneFightStart;
import com.qfedu.planefight.util.ImageUtil;

import java.awt.*;
import java.util.Random;

/**
 * @author Leon Downey
 * @date 2023/2/8 11:09
 * @describe： 敌方飞机类
 */
public class EnemyPlane extends Plane {
	public int type;// 类型
	public int exp;// 经验

	/**
	 * 无参构造
	 */
	public EnemyPlane() {
		super();
	}

	/**
	 * 带参构造
	 */
	public EnemyPlane(int x, int y, String imageName) {
		super(x, y, imageName);
		this.speed = 100;
	}

	public EnemyPlane(PlaneFightStart pfs, int x, int y, int type, boolean good) {
		this.pfs = pfs;
		this.x = x;
		this.y = y;
		if (type == 100) {
			img = ImageUtil.images.get("enemyPlane_boss_0"+(int)(Math.random()*5+1));
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
			this.blood = Constant.ENEMYPLANE_BOSS_MAX_BOOLD;
		} else {
			this.width = images[type * 4 - 4].getWidth(null);
			this.height = images[type * 4 - 4].getHeight(null);
			this.blood = Constant.ENEMYPLANE_MAX_BOOLD;
		}
		this.speed = 0.3 + 0.1 * type;
		this.type = type;
		this.good = good;
		live = true;
	}

	/**
	 * 发子弹
	 */
	@Override
	public void fire() {
		if (type == 100) {
			int num = (int) (Math.random()*24)+24;
			for (int i = 1; i <= num; i++) {
				Missile missile = new Missile( pfs, this.x, this.y, "enemyPlane_missile_0" + type, type+new Random().nextInt(1), good);
				int r = (int) (Math.sqrt(width * width + height * height) / 2);
				int theta = 360 * i / num;
				missile.setTheta(theta);
				missile.x = (int) (missile.x + (width / 2 + r * Math.sin(Math.toRadians(theta)) - missile.width / 2));
				missile.y = (int) (missile.y
						- ((r * Math.cos(Math.toRadians(theta)) - height / 2 + missile.height / 2)));
				pfs.missiles.add(missile);
			}
		} else {
			Missile missile = new Missile(pfs, this.x, this.y, "enemyPlane_missile_0" + type, type, good);
			missile.x += (this.width - missile.width) / 2;
			missile.y += height;
			pfs.missiles.add(missile);
		}
	}

	Point center = new Point(Constant.GAME_WIDTH / 2, Constant.GAME_HEIGHT / 2);
	double theta;
	int r = 100;
	public static Image images[] = new Image[24];
	static {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				images[4 * i + j] = ImageUtil.images.get("enemyPlane_0" + (i + 1) + "_0" + (j + 1));
			}
		}
	}

	@Override
	public String toString() {
		return "EnemyPlane [type=" + type + ", exp=" + exp + ", center=" + center + ", theta=" + theta + ", r=" + r
				+ "]";
	}

	@Override
	public void move() {
		
		switch (type) {
		case 100:// 大boss水平平移
			if (x >= (Constant.GAME_WIDTH) || x <= 0 - width) {
				speed = -speed;
			}
			x += speed;
			break;
		case 1:// 水平平移
			x += speed * 3;
			if (x >= (Constant.GAME_WIDTH - width)) {
				speed = -speed;
			}
			break;
		case 2:// 竖直平移
			y += speed * 3;
			break;
		case 3:// 正弦线
			x = (int) (center.x - width + (center.x - width) * Math.sin(theta));
			theta += speed / 10;
			y += speed * 10;
			break;
		case 4:// 余弦线
			x = (int) (center.x - width + (center.x - width) * Math.cos(theta));
			theta += speed / 10;
			y += speed * 10;
			break;
		case 5:// 双曲线
			x = (int) (center.x - width + 50 * 1 / Math.cos(theta));
			y = (int) (center.y - height + 50 * Math.sin(theta) / Math.cos(theta));
			theta += speed / 20;
			break;
		case 6:// 星型线
			x = (int) (center.x - width / 2 + 200 * Math.pow(Math.cos(theta), 3));
			y = (int) (center.y + 200 * Math.pow(Math.sin(theta), 3)) - 200;
			theta += speed / 20;
			break;
		case 7:// 心形线
			x = (int) (center.x + r * (2 * Math.cos(theta + Math.PI / 2) + Math.cos(2 * theta + Math.PI / 2)));
			y = (int) (center.y + r * (2 * Math.sin(theta + Math.PI / 2) + Math.sin(2 * theta + Math.PI / 2)));
			theta += speed;
			break;
		}

		//敌方飞机发子弹的速率
		if (type!=100&&random.nextInt(1000) > 966 && live) {
			fire();
		}
		if(type==100&&random.nextInt(1000)>985&&live){
			fire();
		}
	}

	static Random random = new Random();
	static int count[] = new int[6];

	//敌机被击败的效果
	@Override
	public void draw(Graphics g) {

		if (blood <= 0 && live) {
			live = false;
			pfs.myPlane.score += 100 * type;
			// 爆炸
			Explode ex = new Explode(pfs, x, y);
			ex.x += (width - ex.width) / 2;
			ex.y += (height - ex.height) / 2;
			pfs.explodes.add(ex);

			// 掉落道具
			Item item = new Item(pfs, x, y);
			item.x += (width - item.width) / 2;
			item.y += height;
			pfs.items.add(item);

			//boss被打败 界面清空
			if(type==100){
				pfs.enemyPlanes.clear();
				pfs.missiles.clear();
				pfs.items.clear();
			}
		}

		//画敌机
		if (type == 100 && live) {
			g.drawImage(img, x, y, null);
			drawBlood(g);// boss血条
		}
		if (type != 100 && count[type - 1] >= 4 * type) {
			count[type - 1] = 4 * (type - 1);
		}
		if (type != 100 && live) {
			g.drawImage(images[count[type - 1]], x, y, null);
			drawBlood(g);// 小兵血条
			count[type - 1]++;
		}
		move();
	}

	/**
	 * 画血条
	 */
	private void drawBlood(Graphics g) {
		Image bloodImg = ImageUtil.images.get("blood");
		Image blood_blank = ImageUtil.images.get("blood_blank");
		int i = 0;
		for (; i < width / bloodImg.getWidth(null); i++) {
			g.drawImage(bloodImg, x + bloodImg.getWidth(null) * i, y-10, null);
		}
		if(type==100){
			int num = (int) (((double) (bloodImg.getWidth(null) * i) / (Constant.ENEMYPLANE_BOSS_MAX_BOOLD))
					* (Constant.ENEMYPLANE_BOSS_MAX_BOOLD - blood) / blood_blank.getWidth(null));
			for (int j = 0; j < num; j++) {
				g.drawImage(blood_blank, x + bloodImg.getWidth(null) * i - blood_blank.getWidth(null) * (j + 1), y-10 ,
						null);
			}
		}else{
			int num = (int) (((double) (bloodImg.getWidth(null) * i) / (Constant.ENEMYPLANE_MAX_BOOLD))
					* (Constant.ENEMYPLANE_MAX_BOOLD - blood) / blood_blank.getWidth(null));
			for (int j = 0; j < num; j++) {
				g.drawImage(blood_blank, x + bloodImg.getWidth(null) * i - blood_blank.getWidth(null) * (j + 1), y-10 ,
						null);
			}
		}
	}
}