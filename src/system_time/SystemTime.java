package system_time;

// Inspiration for Singleton https://www.geeksforgeeks.org/singleton-class-java/

public class SystemTime {
    private static SystemTime INSTANCE = null;

    private final long realTime;
    private long programTime;

    private SystemTime() {
        // during whole working of program it doesn't change realTime
        // because we won't be able to calculate minutes, which program is running
        this.realTime = System.currentTimeMillis();
        this.programTime = System.currentTimeMillis();
    }

    // this method makes sure, that there is only one instance of this class in whole program
    public static SystemTime getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SystemTime();
        }
        return INSTANCE;
    }

    public void setProgramTime(long programTime) {
        this.programTime = programTime;
    }

    public long getCurrentProgramTime() {
        // get number of milliseconds, which is program running
        long programRunning = System.currentTimeMillis() - this.realTime;
        return this.programTime + programRunning;
    }
}
