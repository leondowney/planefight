package com.qfedu.planefight.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Leon Downey
 * @date 2023/2/7 17:01
 * @describe： 游戏工具类
 */
public class GameUtil {

    /**
     * class.getClassLoader().getResource：从类中导入相对路径
     * 将字符串保存到对象当中，返回Image类的img对象
     * @param imagePath
     * @return img
     */
    public static Image getImage(String imagePath) {
        URL url = GameUtil.class.getClassLoader().getResource(imagePath);
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(url);
        }
        return img;
    }
}
