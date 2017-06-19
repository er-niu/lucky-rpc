package lucky.test.file;

import java.io.File;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:39 2017/6/19
 */
public class FileTest {

    public static void main(String[] args) {

        File f = new File(FileTest.class.getResource("/").getPath());
        System.out.println(f);
        System.out.println(FileTest.class.getResource("/").getPath());
        System.out.println(f.getPath());
    }
}
