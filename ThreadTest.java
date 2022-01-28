package ThreadTest;

/**
 * 功能描述
 *
 * @since 2022-01-28
 */
public class Main {
    public volatile static int val = 1;
    public static final int MAX_VAL = 30;
    static Object readLock = new Object();
    static Object writeLock = new Object();
    static int BUFFER_SIZE = 26;
    static String[] buff = new String[BUFFER_SIZE];
    static int readIndex = 0, writeIndex = 0;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world!");
        for(int i=0;i<10;i++) {
            Thread thread = new Thread(new ReadThread(), "read - " + String.valueOf(i));
            thread.start();
        }
        for(int i=0;i<10;i++) {
            Thread thread = new Thread(new WriteThread(), "write - " + String.valueOf(i));
            thread.start();
        }
        Thread.sleep(1000);
    }
    static class WriteThread implements Runnable {

        public void run() {
            System.out.println(Thread.currentThread().getName() + "start!");
            while(true) {
                synchronized (writeLock) {
                    while((writeIndex+1) % BUFFER_SIZE == readIndex) {
                        try {
                            writeLock.wait();
                        } catch (Exception e) {

                        }
                    }
                    if(val >= MAX_VAL) return;
                    buff[writeIndex] = String.valueOf(val);
                    writeIndex = (writeIndex+1) % BUFFER_SIZE;
                    System.out.println("write success! write index: "+ writeIndex
                            + ",value: " + val);
                    val++;
                }
                synchronized (readLock) {
                    readLock.notifyAll();
                }
            }
        }
    }

    static class ReadThread implements Runnable {

        public void run() {
            System.out.println(Thread.currentThread().getName() + "start!");
            while(true) {
                synchronized (readLock) {
                    while(writeIndex == readIndex) {
                        try {
                            readLock.wait();
                        } catch (Exception e) {

                        }
                    }
                    System.out.println("read success! read index: "+ readIndex
                            + ",value: " + buff[readIndex]);
                    readIndex = (readIndex+1) % BUFFER_SIZE;

                }
                synchronized (writeLock) {
                    writeLock.notifyAll();
                }
            }
        }
    }

}
