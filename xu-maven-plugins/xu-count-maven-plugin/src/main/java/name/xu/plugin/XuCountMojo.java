package name.xu.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 统计文件数
 * 统计代码行数
 *
 * @author Created by Xu
 */
@Mojo(name = "fileCountTotal",
        //该属性值表示，编写的插件所运行的目标项目是不必须为maven项目
        requiresProject = false,
        defaultPhase = LifecyclePhase.PACKAGE)
public class XuCountMojo extends AbstractMojo {

    private static List<String> fileList = new ArrayList<>();

    private static int allLines = 0;

    @Parameter(property = "currentBaseDir", defaultValue = "User/pathHome")
    private String currentBaseDir;

    @Parameter(property = "suffix", defaultValue = ".java")
    private String suffix;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        fileList = scanFile(currentBaseDir);
        System.out.println("文件路径: " + currentBaseDir);
        System.out.println("java文件数量: " + XuCountMojo.fileList.size());
        System.out.println("java代码总行数: " + allLines);
    }

    /**
     * 统计文件数量,将符合条件的文件存入集合中
     */
    private List<String> scanFile(String filePath) {
        File dir = new File(filePath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanFile(file.getAbsolutePath());
            } else {
                if (file.getName().endsWith(suffix)) {
                    fileList.add(file.getName());
                    allLines += countLine(file) + 1;
                }
            }
        }
        return fileList;
    }

    /**
     * 统计每个文件的行数
     */
    private int countLine(File file) {
        int lines = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                br.readLine();
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }
}
