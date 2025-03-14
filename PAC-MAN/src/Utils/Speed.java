package Utils;

public class Speed {
    protected int speed = 2;
    protected static final int[] speedGrades = new int[]{2,3,4,6,8,12};
    protected int getSpeed() {
        return speedGrades[speed];
    }

    public void temporarilyIncreasedSpeed(int durationSeconds, int defaultSpeed) {
        increaseSpeed();
        Thread temporarilyIncreasedSpeed = new Thread(() -> {
         try {
             Thread.sleep(durationSeconds * 1000L);
             if (speed > defaultSpeed)
                 speed--;
         } catch (InterruptedException ignored) {

         }
        }
        );
        temporarilyIncreasedSpeed.start();
    }

    public void increaseSpeed() {
        if (speed < speedGrades.length-2) speed++;
    }
}
