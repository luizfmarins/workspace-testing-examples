import org.gradle.api.Project

public class ProjectTester {

	public static boolean isTestable(Project project) {
		project.plugins.hasPlugin("com.liferay.osgi.plugin") && 
			!project.name.endsWith("test")	
	}

}