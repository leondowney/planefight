package com.qfedu.planefight.core;

import com.qfedu.planefight.constant.Constant;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Leon Downey
 * @date 2023/2/8 09:44
 * @describe： 窗口类
 */
public class MyFrame extends Frame {
    /**
     * 自定义生成窗口的方法
     */
    public void launchFrame() {
        // 设置窗口大小
        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        //设置窗口位置
        // setLocation(0, 0);
        setLocationRelativeTo(null);// 相对居中，传入null，相对于显示器屏幕
        // 设置窗口标题
        setTitle("梁东宇在千锋编的飞机大战");
        // 设置可见
        setVisible(true);
        // 设置不能改变大小
        setResizable(false);
        // 设置关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);// 设置关闭
            }
        });
        enableInputMethods(false);
        setBackground(Color.BLACK);

        new MyThread().start();

    }

    /**
     * 双缓冲技术防止图片闪烁
     */
    Image backImg = null;

    @Override
    public void update(Graphics g) {
        if (backImg == null) {
            backImg = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        }
        Graphics backg = backImg.getGraphics();
        Color c = backg.getColor();
        backg.setColor(Color.BLACK);
        backg.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        backg.setColor(c);
        paint(backg);
        g.drawImage(backImg, 0, 0, null);
    }

    /**
     * 创建一个重新画的线程内部类
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

