package com.qfedu.planefight.util;

import com.qfedu.planefight.constant.Constant;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leon Downey
 * @date 2023/2/7 16:39
 * @describe： 图片工具类
 */
public class ImageUtil {

    //用map集合存放图片对象（原因：map集合以键值对储存对象，便于查找）
    public static Map<String, Image> images = new HashMap<>();
    //静态代码块
    static {
        // 遍历背景图
        for (int i = 1; i <= 7; i++) {
            images.put("background_0" + i,
                    GameUtil.getImage(Constant.IMG_PRE + "background/background_0" + i + ".png"));
        }
        // 遍历我方飞机图
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                images.put("myPlane_0" + i + "_0" + j,
                        GameUtil.getImage(Constant.IMG_PRE + "plane/myPlane/myPlane_0" + i + "_0" + j + ".png"));
            }
        }
        // 遍历我方飞机导弹图
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                images.put("myPlane_missile_0" + i + "_0" + j, GameUtil
                        .getImage(Constant.IMG_PRE + "missile/myPlane/myPlane_missile_0" + i + "_0" + j + ".png"));
            }
        }
        images.put("myPlane_missile_super",
                GameUtil.getImage(Constant.IMG_PRE + "missile/myPlane/myPlane_missile_super.png"));

        // 遍历吃道具效果
        for (int i = 1; i <= 6; i++) {
            images.put("item_0" + i, GameUtil.getImage(Constant.IMG_PRE + "item/item_0" + i + ".png"));
        }
        images.put("effect_04", GameUtil.getImage(Constant.IMG_PRE + "item/effect/effect_04.png"));
        // 遍历吃道具之后的子弹
        images.put("myPlane_missile_05_01",
                GameUtil.getImage(Constant.IMG_PRE + "missile/myPlane/myPlane_missile_05_01.png"));
        images.put("myPlane_missile_05_02",
                GameUtil.getImage(Constant.IMG_PRE + "missile/myPlane/myPlane_missile_05_02.png"));
        images.put("myPlane_missile_05_03",
                GameUtil.getImage(Constant.IMG_PRE + "missile/myPlane/myPlane_missile_05_03.png"));

        // 遍历我方飞机血条
        images.put("myBlood_blank", GameUtil.getImage(Constant.IMG_PRE + "blood/myBlood_blank.png"));
        images.put("myBlood", GameUtil.getImage(Constant.IMG_PRE + "blood/myBlood.png"));
        // 遍历分数
        images.put("score", GameUtil.getImage(Constant.IMG_PRE + "score/score.png"));
        // 游戏结果
        images.put("warning", GameUtil.getImage(Constant.IMG_PRE + "warning.png"));
        images.put("success", GameUtil.getImage(Constant.IMG_PRE + "success.png"));
        images.put("fail", GameUtil.getImage(Constant.IMG_PRE + "fail.png"));

        // 遍历敌方飞机血条
        images.put("blood_blank", GameUtil.getImage(Constant.IMG_PRE + "blood/blood_blank.png"));
        images.put("blood", GameUtil.getImage(Constant.IMG_PRE + "blood/blood.png"));
        // 遍历敌方飞机导弹
        for (int i = 1; i <= 6; i++) {
            images.put("enemyPlane_missile_0" + i,
                    GameUtil.getImage(Constant.IMG_PRE + "missile/enemyPlane/enemyPlane_missile_0" + i + ".png"));
        }
        // 遍历敌方boss导弹
        images.put("enemyPlane_missile_0100",
                GameUtil.getImage(Constant.IMG_PRE + "missile/enemyPlane/enemyPlane_missile_0100.png"));

        // 遍历爆炸效果
        for (int i = 1; i <= 8; i++) {
            images.put("explode_0" + i, GameUtil.getImage(Constant.IMG_PRE + "explode/explode_0" + i + ".png"));
        }

        // 遍历敌方飞机图
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 4; j++) {
                images.put("enemyPlane_0" + i + "_0" + j,
                        GameUtil.getImage(Constant.IMG_PRE + "plane/emenyPlane/enemyPlane_0" + i + "_0" + j + ".png"));
            }
        }
        // 遍历敌方boss图
        for (int i = 1; i <= 5; i++) {
            images.put("enemyPlane_boss_0" + i,
                    GameUtil.getImage(Constant.IMG_PRE + "plane/boss/enemyPlane_boss_0" + i + ".png"));
        }
    }
}
