package com.qfedu.planefight.core;

import com.qfedu.planefight.constant.Constant;
import com.qfedu.planefight.start.PlaneFightStart;
import com.qfedu.planefight.util.ImageUtil;

import java.util.List;

/**
 * @author Leon Downey
 * @date 2023/2/8 10:09
 * @describe： 导弹类
 */
public class Missile extends PlaneFightObject {
    boolean live;
    int speed;
    int type;
    public Missile() {
        super();
    }

    public Missile(PlaneFightStart pfs, int x, int y, String imageName,boolean good) {
        this.live = true;
        this.x = x;
        this.y = y;
        this.img = ImageUtil.images.get(imageName);
        this.width = img.getWidth(null);
        this.height = img.getWidth(null);
        this.pfs = pfs;
        this.speed=10;
        this.good=good;
    }
    public Missile(PlaneFightStart pfs, int x, int y, String imageName,int type,boolean good) {
        this(pfs, x, y, imageName,good);
        this.type=type;
    }


    public void setTheta(int theta) {
        this.theta = theta;
    }

    private int theta;
    /**
     * 子弹打到飞机的效果
     */
    public boolean hitPlane(Plane p){
        if(this.getRectangle().intersects(p.getRectangle())&&this.good!=p.isGood()&&p.live){
            this.live=false;
            if(p.level>=1){
                p.blood-= 10*p.level;
            }else{
                p.blood-= 20;
            }
            pfs.missiles.remove(this);
            return true;
        }else{
            return false;
        }
    }
    /**
     * 子弹打到敌机的效果
     */
    public boolean hitPlanes(List<EnemyPlane> enemyPlanes){
        for (EnemyPlane enemyPlane : enemyPlanes) {
            if(this.hitPlane(enemyPlane)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void move() {
        switch (type) {
            case 100://boss导弹1
                x+=Math.sin(Math.toRadians(theta))*speed;
                y-=Math.cos(Math.toRadians(theta))*speed;
                break;
            case 101://boss导弹2
                x-=Math.sin(Math.toRadians(theta))*speed;
                y-=Math.cos(Math.toRadians(theta))*speed;
                break;
            case 102://boss导弹3
                x+=Math.sin(Math.toRadians(theta))*speed;
                y+=Math.cos(Math.toRadians(theta))*speed;
                break;
            case 104://boss导弹4
                x-=Math.sin(Math.toRadians(theta))*speed;
                y+=Math.cos(Math.toRadians(theta))*speed;
                break;
            case 1:
                y += speed;
                break;
            case 2:
                y += speed;
                break;
            case 3:
                y += speed;
                break;
            case 4:
                y += speed;
                break;
            case 5:
                y += speed;
                break;
            case 6://super
                x+=Math.sin(Math.toRadians(theta))*speed;
                y+=Math.cos(Math.toRadians(theta))*speed;
                break;
            default:
                y -= height;
                break;
        }
        outOfBounds();
    }

    /**
     * 子弹出界的方法
     */
    private void outOfBounds() {
        if ((x >= (Constant.GAME_WIDTH-width) || x <= 0) || (y >= (Constant.GAME_HEIGHT - height) || y <= 0)) {
            this.live = false;
            this.pfs.missiles.remove(this);
        }
    }

}
