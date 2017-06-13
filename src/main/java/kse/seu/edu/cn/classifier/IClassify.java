package kse.seu.edu.cn.classifier;

import java.io.File;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public interface IClassify {

    String classify(String sentence) throws Exception, IllegalAccessException, ClassNotFoundException;
    
    String classify(File file);
}
