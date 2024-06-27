#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <format>
#include <iostream>
#include <chrono>

using namespace std;

int doWork()
{
    std::cout << "Thread created" << std::endl;
    double pi = 3.14159;
    for (int i = 0; i < 10; i++)
    {
        std::string result = std::format(" :: Executing - {}, {:.2f}", i, pi);
        std::this_thread::sleep_for(std::chrono::microseconds(10));
        std::cout <<  std::this_thread::get_id()  << result << std::endl;
    }
    std::cout << "Done." << std::endl;
    return 1;
}

int main()
{
    std::cout <<  std::this_thread::get_id() << " :: Main...." << std::endl;
    std::thread t2(doWork);
    std::thread t(doWork);
    t.join();
    t2.detach();
    std::cout << "Main done." << std::endl;
    return 0;
}