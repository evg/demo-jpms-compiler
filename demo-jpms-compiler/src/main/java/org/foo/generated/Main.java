package org.foo.generated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.DiagnosticCollector;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.foo.JavaSourceFromString;



public class Main {

	public static void main(String[] args) throws IOException {
		new Main().run();
	}

	private void run() throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
		log(diagnosticCollector.getDiagnostics().size() + " diagnostics reported after creating fileMgr");
		JavaFileManager fileManager = getFileManager(stdFileManager);
		Iterable<? extends JavaFileObject> compilationUnits = List.of(new JavaSourceFromString("org.foo.generated.MyGreeter", getJavaSource()));
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector , compilerOptions, null, compilationUnits);
		boolean allFilesCompiledWithoutErrors = task.call();
		log("All files compiled without errors: " + allFilesCompiledWithoutErrors);
		log(diagnosticCollector.getDiagnostics().size() + " diagnostics reported by compiler task");
		diagnosticCollector.getDiagnostics().stream().forEach(diagnostic->{
			log("DIAGNOSTIC " + diagnostic);
		});
		fileManager.close();

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
	
	private JavaFileManager getFileManager(StandardJavaFileManager stdFileManager)
	{
		return new ForwardingJavaFileManager<JavaFileManager>(stdFileManager)
		{
//			@Override
//			public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException
//			{
//				if (kind == Kind.CLASS)
//				{
//					ByteArrayClass outfile = new ByteArrayClass(className);
//					classMap.put(className, outfile);
//					return outfile;
//				} 
//				else
//				{
//					problemCollector.addError(className, "No class file present");
//					return super.getJavaFileForOutput(location, className, kind, sibling);
//				}
//			}

			// see https://stackoverflow.com/questions/53936466/compile-java-class-in-runtime-with-dependencies-to-nested-jar/53987381#53987381
			
//			@Override
//			public Location getLocationForModule(Location location, String moduleName) throws IOException {
//				return stdFileManager.getLocationForModule(location, moduleName);
//			}
//
//			@Override
//			public Location getLocationForModule(Location location, JavaFileObject fo) throws IOException {
//				return stdFileManager.getLocationForModule(location, fo);
//			}
//
//			@Override
//			public String inferModuleName(Location location) throws IOException {
//				return stdFileManager.inferModuleName(location);
//			}
//
//			@Override
//			public Iterable<Set<Location>> listLocationsForModules(Location location) throws IOException {
//				// TODO Auto-generated method stub
//				return stdFileManager.listLocationsForModules(location);
//			}
			
			
		};
	}

	private DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
	private List<JavaSourceFromString> jsList = new ArrayList<>();
	private List<String> compilerOptions = Arrays.asList("-Xlint:deprecation");
}
