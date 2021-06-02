package name.xu.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * 提取项目中的 md 文件
 * 归档到一个目录中
 *
 * @author Created by Xu
 */
@Mojo(name = "md-doc", requiresProject = false, defaultPhase = LifecyclePhase.NONE)
public class XuMarkdownMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;
    @Override
    public void execute() {
        // 项目根目录
        String projectPath = mavenProject.getBasedir().toString() + File.separator;
        // 输出目录
        String outPutRootPath = mavenProject.getBuild().getDirectory() + File.separator + "md-doc" + File.separator;

        XuMarkdownUtil xuMarkdownUtil = new XuMarkdownUtil(projectPath, outPutRootPath);
        xuMarkdownUtil.execute();
    }
}
