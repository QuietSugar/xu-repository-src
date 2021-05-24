package name.xu.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * 获取项目信息
 *
 * @author Created by Xu
 */
@Mojo(name = "projectInfo", defaultPhase = LifecyclePhase.PACKAGE)
public class XuProjectInfoMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;
    /**
     * 调用的时候传入的参数
     */
    @Parameter(property = "msg", defaultValue = "defaultMsg")
    private String msg;


    @Override
    public void execute() {
        System.out.println("Project Info : ↓ ↓ ↓");
        System.out.println("msg: " + msg);
        // 项目路径
        System.out.println("Basedir : " + mavenProject.getBasedir().toString());
        System.out.println("Build Dir (target) : " + mavenProject.getBuild().getDirectory());
        System.out.println("mavenSession.getExecutionRootDirectory()  : " + mavenSession.getExecutionRootDirectory());
    }
}
