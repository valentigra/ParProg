package ch.hsr.uebung04.aufgabe03;

public class UpgradeableReadWriteLock {
  private int readers = 0;
  private int writer = 0;
  private Thread updater = null;

	public synchronized void readLock() throws InterruptedException {
	  while (writer > 0) {
	    wait();
	  }
	  readers++;
	}

	public synchronized void readUnlock() {
	  readers--;
	  notifyAll();
	}

	public synchronized void upgradeableReadLock() throws InterruptedException {
	  while (updater != null || writer > 0) {
	    wait();
	  }
	  updater = Thread.currentThread();
	}

	public synchronized void upgradeableReadUnlock() {
	  updater = null;
	  notifyAll();
	}

	public synchronized void writeLock() throws InterruptedException {
	  while (!(updater == Thread.currentThread() || updater == null ) || readers > 0 || writer > 0) {
	    wait();
	  }
	  writer++;
	}

	public synchronized void writeUnlock() {
	  writer--;
	  notifyAll();
	}
}
