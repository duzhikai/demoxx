package util.login.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class ImageVerifyCodeGenerator {

    // 随机字符词典(去掉0和1,因为它们和字母O,l在图片展示容易混淆)
    public static final String RANDOM_CHARS = "23456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 字体数组
    public static final String[] FONT_NAMES = { "Chandas" };

    /**
     * 生成4位随机图片验证码
     *
     * @return
     */
    public static synchronized Map<String, Object> generate() {

        // 1. 初始化图片缓冲器
        BufferedImage image = initImage();
        // 2. 生成随机验证码
        String[] codes = createVerifyCodes();
        // 3. 验证码写入图片中
        draw(image, codes);
        // 4. 返回图片和验证码
        Map<String, Object> imageInfo = new HashMap<String, Object>();
        imageInfo.put("image", image);
        StringBuffer verfyCode = new StringBuffer();
        for (String c : codes) {
            verfyCode.append(c);
        }
        imageInfo.put("code", verfyCode.toString());
        return imageInfo;
    }

    /**
     * 生成图片验证码
     *
     * @param width           图片验证码宽度
     * @param height          图片验证码高度
     * @param verfyCodeNumber 验证码字符个数
     * @return 图片验证码和验证码
     */
    public static synchronized Map<String, Object> generate(int width, int height, int verfyCodeNumber) {

        // 1. 初始化图片缓冲器
        BufferedImage image = initImage(100, 40, BufferedImage.TYPE_INT_RGB);
        // 2. 生成随机验证码
        String[] codes = createVerifyCodes(verfyCodeNumber);
        // 3. 验证码写入图片中
        draw(image, codes);
        // 4. 返回图片和验证码
        Map<String, Object> imageInfo = new HashMap<String, Object>();
        imageInfo.put("image", image);
        StringBuffer verfyCode = new StringBuffer();
        for (String c : codes) {
            verfyCode.append(c);
        }
        imageInfo.put("code", verfyCode);
        return imageInfo;
    }

    public static BufferedImage initImage() {
        // 默认图片宽100,高 40 底色白色,颜色系统使用RGB
        return initImage(100, 40, BufferedImage.TYPE_INT_RGB);
    }

    public static BufferedImage initImage(int width, int height, int imageType) {
        return initImage(width, height, imageType, Color.white);
    }

    public static BufferedImage initImage(int width, int height, int imageType, Color color) {
        BufferedImage image = new BufferedImage(width, height, imageType);
        // 获取画笔
        Graphics graphics = image.getGraphics();
        // 设置底色
        graphics.setColor(color);
        // 画一个长方形
        graphics.fillRect(0, 0, width, height);
        return image;
    }

    public static synchronized String[] createVerifyCodes() {
        return createVerifyCodes(4);
    }

    public static String[] createVerifyCodes(int verfyCodeNumber) {
        return createVerifyCodes(RANDOM_CHARS.toCharArray(), verfyCodeNumber);
    }

    public static String[] createVerifyCodes(char[] chars, int verfyCodeNumber) {
        String[] codes = new String[verfyCodeNumber];
        for (int i = 0; i < codes.length; i++) {
            char c = randomChar(chars);
            codes[i] = String.valueOf(c);
        }
        return codes;
    }

    /**
     * 获取随机字符
     *
     * @return
     */
    public static synchronized char randomChar(char[] chars) {
        int index = ThreadLocalRandom.current().nextInt(chars.length);
        return RANDOM_CHARS.charAt(index);
    }

    public static void draw(BufferedImage image, String[] codes) {
        int width = image.getWidth();
        int height = image.getHeight();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        for (int i = 0; i < codes.length; i++) {
            // 计算字符所在图片中x的位置,一般按字符个数平均分
            float x = i * 1.0f * width / codes.length;
            float y = height - 5 / 1.0f;
            drawString(graphics, codes[i], x, y);
        }
        graphics.dispose();
    }

    public static void drawString(Graphics2D graphics, String code, float x, float y) {
        drawString(graphics, code, x, y, randomFont(), randomColor());
    }

    public static void drawString(Graphics2D graphics, String code, float x, float y, Font font, Color color) {
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(code, x, y);
    }

    /**
     * 获取随机字体
     *
     * @return
     */
    public static Font randomFont() {
        GraphicsEnvironment graphicsEnv =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = graphicsEnv.getAvailableFontFamilyNames();
        return randomFont(fontNames);
    }

    /**
     * 获取随机字体
     *
     * @return
     */
    public static Font randomFont(String[] fonts) {
        int index = ThreadLocalRandom.current().nextInt(fonts.length); // 获取随机的字体
        String fontName = fonts[index];
        int style = ThreadLocalRandom.current().nextInt(4); // 随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int size = ThreadLocalRandom.current().nextInt(10) + 24; // 随机获取字体的大小
        return new Font(fontName, style, size); // 返回一个随机的字体
    }

    /**
     * 随机颜色
     *
     * @return
     */
    public static Color randomColor() {
        int r = ThreadLocalRandom.current().nextInt(225); // 这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int g = ThreadLocalRandom.current().nextInt(225);
        int b = ThreadLocalRandom.current().nextInt(225);
        return new Color(r, g, b); // 返回一个随机颜色
    }
}
