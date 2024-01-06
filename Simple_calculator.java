
import java.util.List;
import java.util.stream.Stream;

public class Simple_calculator {

    public static void main(String[] args) {
        Count sharedCount = new Count();
        int threadCount = 100;
        List<Counter> counters = Stream.generate(() -> new Counter(sharedCount)).limit(threadCount).toList();
        counters.forEach(c -> c.start());
        counters.forEach(c -> {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
         
        System.out.printf("%d", sharedCount.getCount(), threadCount);
    }
}

class Count {
    private volatile int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class Counter extends Thread {

    Count count;

    public Counter(Count c) {
        this.count = c;
    }

    @Override
    public void run() {

        //increases the value of the shared count by one
    	synchronized (count) {
            int oldCount = count.getCount();
            count.setCount(oldCount + 1);
        }
		
    }
}
