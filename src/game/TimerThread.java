package game;

import gui.NumberField;

public class TimerThread implements Runnable {

    private NumberField numField;
    private Thread t;
    private volatile boolean isRunning;

    public TimerThread(NumberField numField) {
        this.numField = numField;
        this.numField.setNumber(0);
        this.t = new Thread(this);
        this.isRunning = false;
    }

    public void start() {
        this.isRunning = true;
        this.t.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { System.out.println(e); }
            if (isRunning && this.numField.getValue() < 999)
                this.numField.setNumber(this.numField.getValue() + 1);
        }
    }

    public void stopRunning() {
      this.isRunning = false;
    }
}
