package name.xu.plugin;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提取项目中的 md 文件
 * 归档到一个目录中
 *
 * @author Created by Xu
 */
public class XuMarkdownUtil {

    private static final String SUFFIX = ".md";
    /**
     * 排除的目录 下面列出常见的要跳过的
     */
    private static final List<String> EXCLUDE_DIR = Arrays.asList("target", "log");
    /**
     * 输出目录
     */
    private final String outPutRootPath;
    /**
     * 输入目录
     */
    private final String inPutRootPath;

    private final List<String> sidebarList = new ArrayList<>();

    public XuMarkdownUtil(String inPutRootPath, String outPutRootPath) {
        if (outPutRootPath == null || inPutRootPath == null) {
            throw new RuntimeException("输入输出目录不可为空");
        }
        this.inPutRootPath = inPutRootPath;
        this.outPutRootPath = outPutRootPath;
    }

    public void execute() {
        // 尝试创建输出目录
        mkdir(new File(outPutRootPath));
        try {
            // 复制 html 文件
            copyFileByStream(XuMarkdownUtil.class.getResourceAsStream("/md-doc/index.html"),
                    new FileOutputStream(outPutRootPath + "index.html"));
            scanMdFile(inPutRootPath, outPutRootPath, 0);
            // 写 _sidebar.md 文件
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    outPutRootPath + "_sidebar.md"
            ), StandardCharsets.UTF_8));
            for (String arr : sidebarList) {
                writer.write(arr + "\r\n");
            }
            writer.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描文件
     *
     * @param baseSourceRootPath 原始文件根目录 末尾带斜杠
     * @param baseDescRootPath   目标文件根目录 末尾带斜杠
     * @param level              目录级别,根目录为 0
     */
    private void scanMdFile(String baseSourceRootPath, String baseDescRootPath, int level) throws IOException {
        File[] allFiles = new File(baseSourceRootPath).listFiles();
        if (allFiles == null) {
            return;
        }
        List<File> dirs = new ArrayList<>();
        List<File> files = new ArrayList<>();
        // 文件/文件夹分类
        for (File file : allFiles) {
            if (file.isDirectory()) {
                // 文件夹
                //判断是不是排除的文件夹/ 排除 . 开头的 文件夹
                if (EXCLUDE_DIR.contains(file.getName()) || file.getName().startsWith(".")) {
                    continue;
                }
                dirs.add(file);
            } else {
                // 文件
                if (file.getName().endsWith(SUFFIX)) {
                    files.add(file);
                }
            }
        }
        // 处理文件
        for (File file : files) {
            // 复制文件
            StringBuilder sideLine = new StringBuilder();
            for (int i = 0; i < level; i++) {
                sideLine.append("  ");
            }
            sideLine.append("- [");
            // 侧边栏目录名
            String sidebarName;
            // 优先取文件的第一行
            String fileStr = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
            sidebarName = getFirstLine(fileStr);
            if (sidebarName == null) {
                // 使用原始的名字
                sidebarName = file.getName().replace(SUFFIX, "");
            }
            // 侧边栏目录名
            sideLine.append(sidebarName);
            sideLine.append("](");
            sideLine.append((baseDescRootPath + file.getName()).replace(outPutRootPath, ""));
            sideLine.append(")");
            sidebarList.add(sideLine.toString());

            IOUtils.copy(new FileInputStream(file),
                    new FileOutputStream(createNewFile(new File(baseDescRootPath + file.getName()))));

        }
        // 处理文件夹
        int nextLevel;
        if (files.size() == 0) {
            nextLevel = level;
        } else {
            nextLevel = level + 1;
        }
        for (File dir : dirs) {
            scanMdFile(
                    baseSourceRootPath + dir.getName() + File.separator,
                    baseDescRootPath + dir.getName() + File.separator, nextLevel);
        }
    }


    public static File createNewFile(File file) throws IOException {
        if (!file.exists()) {
            mkdir(file.getParentFile());
            if (!file.createNewFile()) {
                throw new RuntimeException("创建文件失败");
            }
        }
        return file;
    }

    /**
     * 创建一个文件夹
     *
     * @param dir 文件夹
     */
    private static void mkdir(File dir) {
        if (dir.exists()) {
            // 已存在,不用创建
            return;
        }
        // 判断父目录是否存在
        if (!dir.getParentFile().exists()) {
            mkdir(dir.getParentFile());
        }
        if (!dir.mkdir()) {
            throw new RuntimeException("创建文件夹失败");
        }
    }

    /**
     * 流的方式复制文件
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    static void copyFileByStream(InputStream inputStream, OutputStream outputStream) {
        //获取源文件的名称
        try {
            //创建输出流对象
            byte[] bytes = new byte[1024 * 8];//创建搬运工具
            //创建长度
            int len;
            //循环读取数据
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            //释放资源
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取一个字符串的第一行
     *
     * @param str 字符串
     */
    static String getFirstLine(String str) {
        if (str == null) {
            return null;
        }
        int index = 0;
        int rIndex = str.indexOf("\r");
        int nIndex = str.indexOf("\n");
        if (rIndex == -1) {
            if (nIndex == -1) {
                return null;
            } else {
                index = nIndex;
            }
        } else {
            if (nIndex == -1) {
                index = rIndex;
            } else {
                // 取小的
                index = Math.min(rIndex, nIndex);
            }
        }
        String substring = str.substring(0, index);
        return substring.trim().replaceAll("#", "");
    }
}
