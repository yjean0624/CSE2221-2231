public class Philosopher implements Runnable {

    private Object leftFork;
    private Object rightFork;
    private int forkIndex;

    public Philosopher(Object leftFork, Object rightFork, int i) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.forkIndex = i;
    }

    //get the index of another fork
    private int anotherForkIndex(int i) {
        int another;
        if (i == 4) {
            another = i - 4;
        } else {
            another = i + 1;
        }
        return another;
    }

    private void print(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + action);
    }

    //get a random number between 1000 to 3000(milisecond) for sleep
    private int random() {
        return (int) (Math.random() * 3000 + 1000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                //thinking
                this.print(" is Thinking");
                Thread.sleep(this.random());
                //pick left fork
                synchronized (this.leftFork) {
                    this.print(" takes left fork " + this.forkIndex);
                    //pick right fork and eat
                    synchronized (this.rightFork) {
                        this.print(" takes right fork "
                                + this.anotherForkIndex(this.forkIndex));
                        this.print(" is Eating");
                        Thread.sleep(this.random());
                        //drop both forks
                        this.print(" return forks " + this.forkIndex + " and "
                                + this.anotherForkIndex(this.forkIndex));
                    }
                    //return to thinking
                    this.print(" is Thinking");
                    Thread.sleep(this.random());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
