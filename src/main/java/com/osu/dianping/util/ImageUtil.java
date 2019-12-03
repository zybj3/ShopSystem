package com.osu.dianping.util;

import com.osu.dianping.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {

    private static String  basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final Random random = new Random();

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMultipartFiletoFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;

    }
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current Relative addr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200).outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }

        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String fileName ) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public  static String getRandomFileName() {
        int randnum = random.nextInt(89999) + 100000;
        String nowTimeStr = sDataFormat.format(new Date());
        return nowTimeStr+randnum;
    }

    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                for (int i=0; i<files.length; i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static void main(String[] args){
        System.out.print(basePath);
        try {
            Thumbnails.of(new File("/Users/yuanzhao/desktop/fengwenjuan.jpg")).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/melon.jpeg")), 0.25f)
                    .outputQuality(0.8f).toFile("/Users/yuanzhao/desktop/fengwenjuannew.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current Relative addr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }

        return relativeAddr;
    }
}
