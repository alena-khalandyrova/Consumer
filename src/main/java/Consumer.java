import java.util.Deque;
import java.util.LinkedList;

public class Consumer {
    private static class Entry {
        long timestamp;
        int value;

        Entry(long timestamp, int value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private static final long FIVE_MINUTES = 5 * 60 * 1000;

    private final Deque<Entry> window = new LinkedList<>();

    private long sum = 0;

    public void accept(int number) {
        long now = System.currentTimeMillis();

        while (!window.isEmpty() && now - window.peekFirst().timestamp > FIVE_MINUTES) {
            Entry old = window.pollFirst();
            sum -= old.value;
        }

        window.addLast(new Entry(now, number));
        sum += number;
    }

    public double mean() {
        long now = System.currentTimeMillis();

        while (!window.isEmpty() && now - window.peekFirst().timestamp > FIVE_MINUTES) {
            Entry old = window.pollFirst();
            sum -= old.value;
        }

        if (window.isEmpty()) {
            return 0.0;
        }

        return sum / (double) window.size();
    }
}
