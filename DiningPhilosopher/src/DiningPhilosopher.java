public class DiningPhilosopher {
    public static void main(String[] args) throws InterruptedException {
        Philosopher[] philosophers = new Philosopher[5];
        //generate 5 forks
        Object[] forks = new Object[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new Object();
        }
        //generate 5 threads representing philosophers
        for (int i = 0; i < 5; i++) {
            Object leftFork = forks[i];
            //in case that i is larger than 4
            Object rightFork = forks[(i + 1) % 5];
            if (i != 4) {
                philosophers[i] = new Philosopher(leftFork, rightFork, i);
                //if i == 4, switch leftfork and rightfork to avoid deadlock
            } else {
                philosophers[i] = new Philosopher(rightFork, leftFork, i);
            }
            Thread t = new Thread(philosophers[i], "Philosopher " + i);
            t.start();
        }
    }
}
