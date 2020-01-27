/**
 * CpuTimer - class to compute CPU time used by a block of code.
 */
import java.lang.management.*;

/**
 * @author Lucas Igel-Dunn
 *
 */
public class CpuTimer {

  public CpuTimer() {
    bean = ManagementFactory.getThreadMXBean();
    assert bean.isCurrentThreadCpuTimeSupported() : 
      "getCurrentThreadCpuTime not supported by this JVM, use a different JVM";
    startTimeSeconds = 1.0e-9 * (double) bean.getCurrentThreadCpuTime();
  }

  public double getElapsedCpuTime() {
    return 1.0e-9 * (double) bean.getCurrentThreadCpuTime() - startTimeSeconds;
  }

  // Thread management bean to access CPU time
  private final ThreadMXBean bean;

  // Amount of CPU time used before creation of this class
  private final double startTimeSeconds;
}
