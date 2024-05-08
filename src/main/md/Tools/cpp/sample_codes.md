## Handle Signal

```
#include <iostream>
#include <csignal>
#include <atomic>
#include <thread>
#include <mutex>

using namespace std;

// Atomic flag to indicate whether the signal was received
atomic<bool> signalReceived(false);

// Mutex to protect shared resources
mutex mtx;

// Signal handler function
void signalHandler(int signum) {
    // Set the atomic flag to true
    signalReceived.store(true, memory_order_relaxed);
}

// Worker thread function
void workerThread() {
    while (true) {
        // Check if the signal was received
        if (signalReceived.load(memory_order_relaxed)) {
            // Lock the mutex before accessing shared resources
            unique_lock<mutex> lock(mtx);

            // Perform any necessary operations here
            cout << "Signal received! Performing cleanup...\n";

            // Reset the signal flag
            signalReceived.store(false, memory_order_relaxed);

            // Release the lock
            lock.unlock();
        }

        // Simulate some work being done
        this_thread::sleep_for(chrono::seconds(1));
    }
}

int main() {
    // Register signal SIGINT and signal handler
    signal(SIGINT, signalHandler);

    cout << "Signal handling example. Press Ctrl+C to trigger SIGINT.\n";

    // Start the worker thread
    thread worker(workerThread);

    // Wait for the worker thread to finish (which it won't)
    worker.join();

    return 0;
}
```
