package com.qfedu.planefight.core;

import com.qfedu.planefight.start.PlaneFightStart;
import com.qfedu.planefight.util.ImageUtil;

import java.awt.*;

/**
 * @author Leon Downey
 * @date 2023/2/8 11:54
 * @describe： 爆炸类
 */
public class Explode extends PlaneFightObject {
	
	public boolean live;
	public Explode() {
		super();
		//无参构造函数
	}

	public Explode(PlaneFightStart pfs, int x, int y) {
		this.pfs=pfs;
		this.x=x;
		this.y=y;
		this.height=images[0].getHeight(null);
		this.width=images[0].getWidth(null);
		live=true;
	}

	public static Image images[] = new Image[8];
	static {
		for (int i = 0; i < 8; i++) {
			images[i]= ImageUtil.images.get("explode_0"+(i+1));
		}
	}


	@Override
	public void move() {

	}

	static int count = 0;

	@Override
	public void draw(Graphics g) {
		if (count >= 8) {
			count = 0;
			live=false;
			pfs.explodes.remove(this);
		}
		if(live){			
			g.drawImage(images[count], x, y, null);
			count++;
		}
	}

}
