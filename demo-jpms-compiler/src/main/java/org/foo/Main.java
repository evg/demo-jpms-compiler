package org.foo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;



public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		Iterable<? extends JavaFileObject> compilationUnits = List.of(new JavaSourceFromString("org.foo.generated.MyGreeter", getJavaSource()));
		JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnosticCollector , compilerOptions, null, compilationUnits);
		boolean allFilesCompiledWithoutErrors = task.call();
		log("All files compiled without errors: " + allFilesCompiledWithoutErrors);
		log(diagnosticCollector.getDiagnostics().size() + " diagnostics reported by compiler task");
		diagnosticCollector.getDiagnostics().stream().forEach(diagnostic->{
			log("DIAGNOSTIC " + diagnostic);
		});
	}

	private void log(String text) {
		System.out.println(text);
	}

	private String getJavaSource() {
		String[] lines = new String[] {
				"package org.foo.generated;",
				"",
				"import org.foo.*;",
				"",
				"public class MyGreeter implements IGreeter {",
				"  @Override",
				"  public String getGreeting() {",
				"    return \"Hello, generated world\";",
				"  }",
				"",
				"}"
		};
		return Stream.of(lines).collect(Collectors.joining(System.lineSeparator()));
	}

	private DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
	private List<String> compilerOptions = Arrays.asList("-Xlint:deprecation");
}
