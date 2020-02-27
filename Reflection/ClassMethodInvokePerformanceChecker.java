import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kommentar zur Klasse
 * 
 * <pre>
 * Nr.   Name            Datum     Auslöser (ggf. Release) / Beschreibung
 * ----  --------------  --------  ------------------------------------------------
 * *02*
 * *01*  ........        TT.MM.JJ  Neuanlage
 * </pre>
 */
public class ClassMethodInvokePerformanceChecker {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final String partOfName = "lieferPOLLFNR_";
		final Method[] methods = PollfnrsFuerTest.create().getClass().getDeclaredMethods();
		final List<Method> eleigibleMethods =
				Arrays.asList(methods).stream()
						.filter(method -> method.getName().toLowerCase().indexOf(partOfName.toLowerCase()) >= 0)
						.collect(Collectors.toList());
		System.out.println("Total number of eligible methods = " + eleigibleMethods.size());
		runTenMethods(eleigibleMethods);
		runFiftyMethods(eleigibleMethods);
		runHundredMethods(eleigibleMethods);
	}

	private static void runTenMethods(final List<Method> eleigibleMethods) {
		final Instant start = Instant.now();
		executeMethods(eleigibleMethods, 10);
		final Instant finish = Instant.now();
		final long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Time taken to run 10 methods = " + timeElapsed + "ms");
	}

	private static void runFiftyMethods(final List<Method> eleigibleMethods) {
		final Instant start = Instant.now();
		executeMethods(eleigibleMethods, 50);
		final Instant finish = Instant.now();
		final long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Time taken to run 50 methods = " + timeElapsed + "ms");
	}

	private static void runHundredMethods(final List<Method> eleigibleMethods) {
		final Instant start = Instant.now();
		executeMethods(eleigibleMethods, 100);
		final Instant finish = Instant.now();
		final long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Time taken to run 100 methods = " + timeElapsed + "ms");
	}

	private static void executeMethods(final List<Method> eleigibleMethods, final int methodCount) {
		int i = 0;
		for (final Method eleigibleMethod : eleigibleMethods) {
			try {
				eleigibleMethod.invoke(PollfnrsFuerTest.create(), (Object[]) null);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
				exception.printStackTrace();
			}
			i++;
			if (i >= methodCount) {
				break;
			}
		}
	}

}
