package com.qfedu.planefight.util;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * @author Leon Downey
 * @date 2023/2/8 09:15
 * @describe： 音乐播放工具类
 */
public class MusicUtil extends Thread {
    private boolean loop;
    private static String fileName;

    public MusicUtil(String fileName,boolean loop) {
        this.loop = loop;
        this.fileName = fileName;
    }

    public MusicUtil(String fileName) {
        this.fileName = fileName;
    }
    //循环播放音乐
    @Override
    public void run() {
        try{
            if(loop){
                while(true){
                    play();
                }
            }else{
                play();
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    //根据的相对路径获取音乐
    private void play(){
        Player p=null;
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("src/com/qfedu/planefight/music"+fileName+".mp3"));
            p=new Player(buffer);
            p.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
