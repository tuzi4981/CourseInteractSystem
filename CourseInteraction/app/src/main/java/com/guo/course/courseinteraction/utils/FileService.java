package com.guo.course.courseinteraction.utils;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guo on 2016/3/11 0011.
 */
public class FileService {
    private Context context;

    //生成构造器
    public FileService(Context context){
        this.context = context;
    }

    /**
     * 保存文件信息
     * @param fileName 文件名
     * @param fileContent 文件内容
     * @throws Throwable
     */
    public void saveFilePrivate(String fileName,String fileContent) throws Exception{
        //采用输出流对象，输出我们所需要的数据。
        //指出需要导出的位置openFileOutput(只接受文件名称，不接受文件路径,
        //用于指定文件的操作模式，行为【追加方式】，【覆盖形式】
        //文件有一个访问权限的控制，针对拥有者，针对其他用户的访问权限
        //私有操作模式：创建出来文件的只能被本应用访问，其他应用无法访问该文件。
        //             其次私有模式创建出来的文件会覆盖原有的文件内容。
        // 默认保存在，当前应用包所在的下的files文件夹下。
//        FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        //追加方式
        FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
        write(fileContent, outputStream);
    }
    private void write(String fileContent, FileOutputStream outputStream)
            throws IOException {
        outputStream.write(fileContent.getBytes());
        outputStream.close();
    }
}
