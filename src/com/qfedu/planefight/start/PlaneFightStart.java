package com.qfedu.planefight.start;

import com.qfedu.planefight.constant.Constant;
import com.qfedu.planefight.core.*;
import com.qfedu.planefight.util.ImageUtil;
import com.qfedu.planefight.util.MusicUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Leon Downey
 * @date 2023/2/7 16:11
 * @describe： 程序启动的主方法
 */
public class PlaneFightStart extends MyFrame {
    Point center = new Point((Constant.GAME_WIDTH) / 2, (Constant.GAME_HEIGHT) / 2);
    public Plane myPlane = new Plane(this, true);
    public java.util.List<EnemyPlane> enemyPlanes = new CopyOnWriteArrayList<>();
    public Background background = new Background(0, 4, "background_08");
    public java.util.List<Explode> explodes = new CopyOnWriteArrayList<>();
    public java.util.List<Missile> missiles = new CopyOnWriteArrayList<>();
    public java.util.List<Item> items = new CopyOnWriteArrayList<>();
    public List<MusicUtil> musics = new CopyOnWriteArrayList<>();


    public Random random = new Random();

    @Override
    public void launchFrame() {
        super.launchFrame();
        // 添加键盘监听器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                myPlane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                myPlane.keyReleased(e);
            }
        });
        PlaneFightStart pfs = this;
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    int r = random.nextInt(7);
                    EnemyPlane enemyPlane = null;
                    switch (r) {
                        case 0://boss
                            enemyPlane = new EnemyPlane(pfs, 1, 100, 100, false);
                            enemyPlanes.add(enemyPlane);
                            try {
                                Thread.sleep(30000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 1:
                            enemyPlane = new EnemyPlane(pfs, (int) (-400 + 100 * Math.random() * 6), 300, 1, false);
                            enemyPlanes.add(enemyPlane);
                            break;
                        case 2:
                            enemyPlane = new EnemyPlane(pfs, (int) (50 + 80 * Math.random() * 6), -100, 2, false);
                            enemyPlanes.add(enemyPlane);
                            break;
                        default:
                            enemyPlane = new EnemyPlane(pfs, 0, 0, r, false);
                            enemyPlanes.add(enemyPlane);
                            break;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                while(true){
                    int r = random.nextInt(7)+1;
                    System.out.println(r);
                    background=new Background(0, 4, "background_0"+r);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
        new MusicUtil("bgm").start();
    }

    final long start = System.currentTimeMillis();

    @Override
    public void paint(Graphics g) {
        background.draw(g);
        myPlane.draw(g);
        if (!myPlane.live) {
            Image img1 = ImageUtil.images.get("fail");
            g.drawImage(img1, (Constant.GAME_WIDTH - img1.getWidth(null)) / 2,
                    (Constant.GAME_HEIGHT - img1.getHeight(null)) / 2, null);
        }
        for (EnemyPlane enemyPlane : enemyPlanes) {
            enemyPlane.draw(g);
        }
        for (Missile missile : missiles) {
            missile.hitPlanes(enemyPlanes);
            missile.hitPlane(myPlane);
            missile.draw(g);
        }
        for (Explode explode : explodes) {
            explode.draw(g);
        }
        for (Item item : items) {
            item.draw(g);
            item.hitMyPlane(myPlane);
        }

    }

    public static void main(String[] args) {
        PlaneFightStart c = new PlaneFightStart();
        c.launchFrame();
    }
}
