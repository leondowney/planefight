package com.qfedu.planefight.core;

import com.qfedu.planefight.start.PlaneFightStart;

import java.awt.*;

/**
 * @author Leon Downey
 * @date 2023/2/8 09:42
 * @describe： 创建飞机大战总父类
 */
public abstract class PlaneFightObject implements Drawable,Movable{
    //建立关系（大关系）
    //调停者设计模式
    public PlaneFightStart pfs;
    public int x;
    public int y;
    public Image img;
    public int width;
    public int height;
    public boolean good;

    //所有飞机大战中统一的画的方法
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
    }

    //区分敌我飞机
    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }
    //让子类自己实现自己的move方法
    @Override
    public abstract void move();
    //获取子弹对应的矩形
    public Rectangle getRectangle(){
        return new Rectangle(x, y, width, height);
    }
}
