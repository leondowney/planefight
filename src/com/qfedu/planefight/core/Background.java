package com.qfedu.planefight.core;

import com.qfedu.planefight.constant.Constant;
import com.qfedu.planefight.util.ImageUtil;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Leon Downey
 * @date 2023/2/7 16:21
 * @describe： 游戏背景
 */
public class Background implements Drawable,Movable{
    //定义背景的滚动属性——以实现滚动和背景更换效果
    public int x;
    public int y;
    public double xSpeed ;
    public double ySpeed ;
    public Image backImg ;

    //重写move方法
    @Override
    public void move() {
    }
    public Background() {
            super();
        }

	public Background(double xSpeed, double ySpeed, Image backImg) {
            super();
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.backImg = backImg;
        }

    //构造方法初始化属性
    public Background(double xSpeed, double ySpeed, String imageName) {
        super();
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.backImg = ImageUtil.images.get(imageName);
    }

    //重写绘画方法，实现背景滚动
    //现成的画图方法
    @Override
    public void draw(Graphics g) {
        //先画一张背景铺满窗口
        g.drawImage(backImg, x, y, null);
        //再画一张在窗口正上方
        g.drawImage(backImg, x, y- Constant.GAME_HEIGHT,null);
        //图片往下滚（x坐标在这里没有用，但是为了代码规范，还是写出来一起看）
        y+=ySpeed;
        x+=xSpeed;
        //如果图片完全滚出窗口，那么再叫他回上头排队
        if(y>Constant.GAME_HEIGHT){
            y = 0;
        }
    }
}
