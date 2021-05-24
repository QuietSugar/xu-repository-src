package name.xu.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 提取项目中的 md 文件
 * 归档到一个目录中
 *
 * @author Created by Xu
 */
@Mojo(name = "md-doc", defaultPhase = LifecyclePhase.PACKAGE)
public class XuMarkdownMojo extends AbstractMojo {

    private static final String SUFFIX = ".md";
    // 排除的目录
    private static final List<String> excludeDir = Arrays.asList("target", "log");

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    private static String projectRootPath;
    private static String target;

    @Override
    public void execute() {
        //项目根路径
        projectRootPath = mavenProject.getBasedir().toString();
        target = mavenProject.getBuild().getDirectory() + File.separator + "md-doc";
        try {
            scanMdFile(projectRootPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 扫描文件
     */
    private void scanMdFile(String filePath) throws IOException {
        File dir = new File(filePath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory() && !excludeDir.contains(file.getName())) {
                scanMdFile(file.getAbsolutePath());
            } else {
                if (file.getName().endsWith(SUFFIX)) {
                    // 复制文件
                    copyFileByFileChannel(file, createNewFile(new File(file.getAbsolutePath().replace(projectRootPath, target))));
                }
            }
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

    private static void mkdir(File dir) {
        if (!dir.getParentFile().exists()) {
            mkdir(dir.getParentFile());
        }
        if (!dir.mkdir()) {
            throw new RuntimeException("创建文件夹失败");
        }
    }


    /**
     * channel方式复制文件
     */
    public static void copyFileByFileChannel(File sourceFile, File targetFile) {

        RandomAccessFile randomAccessSourceFile;
        RandomAccessFile randomAccessTargetFile;
        try {
            // 构造RandomAccessFile，用于获取FileChannel
            randomAccessSourceFile = new RandomAccessFile(sourceFile, "r");
            randomAccessTargetFile = new RandomAccessFile(targetFile, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        FileChannel sourceFileChannel = randomAccessSourceFile.getChannel();
        FileChannel targetFileChannel = randomAccessTargetFile.getChannel();

        // 分配1MB的缓存空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        try {
            while (sourceFileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                targetFileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sourceFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                targetFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
