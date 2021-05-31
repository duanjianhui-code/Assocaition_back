package com.assocaition.management.module.foreground.controller.verification;

import com.assocaition.management.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Duanjianhui
 * @date 2021-04-30 2:51 下午
 * @describe
 */
@Controller
@RequestMapping("verification")
public class Verification {

    private static char[] chs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            .toCharArray();
    private static final int NUMBER_OF_CHS = 4;
    private static final int IMG_WIDTH = 65;
    private static final int IMG_HEIGHT = 30;

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/getVerificationCode")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response)throws IOException {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
                BufferedImage.TYPE_INT_RGB); // 实例化BufferedImage
        Graphics g = image.getGraphics();
        Color c = new Color(200, 200, 255); // 验证码图片的背景颜色
        g.setColor(c);
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT); // 图片的边框

        StringBuffer sb = new StringBuffer(); // 用于保存验证码字符串
        int index; // 数组的下标
        Random random = new Random();
        Font font = new Font("Fixedsys", Font.BOLD, 20);
        // 随机产生30条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(IMG_WIDTH);
            int y = random.nextInt(IMG_HEIGHT);
            int xl = random.nextInt(15);
            int yl = random.nextInt(15);
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < NUMBER_OF_CHS; i++) {
            index = random.nextInt(chs.length); // 随机一个下标
            g.setFont(font);
            g.setColor(new Color(random.nextInt(88), random.nextInt(210), random.nextInt(150))); // 随机一个颜色
            g.drawString(chs[index] + "", 15 * i + 3, 18); // 画出字符
            sb.append(chs[index]); // 验证码字符串
        }

        // 将验证码字符串保存到redis中
        redisUtil.set("verification",sb.toString());
        ImageIO.write(image, "jpg", response.getOutputStream()); // 向页面输出图像
    }

    @RequestMapping("/getVerificationCodeNew")
    public void getVerificationCodeNew(HttpServletRequest request, HttpServletResponse response,String id)throws IOException {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
                BufferedImage.TYPE_INT_RGB); // 实例化BufferedImage
        Graphics g = image.getGraphics();
        Color c = new Color(200, 200, 255); // 验证码图片的背景颜色
        g.setColor(c);
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT); // 图片的边框

        StringBuffer sb = new StringBuffer(); // 用于保存验证码字符串
        int index; // 数组的下标
        Random random = new Random();
        Font font = new Font("Fixedsys", Font.BOLD, 20);
        // 随机产生30条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(IMG_WIDTH);
            int y = random.nextInt(IMG_HEIGHT);
            int xl = random.nextInt(15);
            int yl = random.nextInt(15);
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < NUMBER_OF_CHS; i++) {
            index = random.nextInt(chs.length); // 随机一个下标
            g.setFont(font);
            g.setColor(new Color(random.nextInt(88), random.nextInt(210), random.nextInt(150))); // 随机一个颜色
            g.drawString(chs[index] + "", 15 * i + 3, 18); // 画出字符
            sb.append(chs[index]); // 验证码字符串
        }

        // 将验证码字符串保存到redis中
        redisUtil.set("verification",sb.toString());
        ImageIO.write(image, "jpg", response.getOutputStream()); // 向页面输出图像

    }

}
