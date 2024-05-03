#include <iostream>
#include <vector>
#include <string>

// Function to display the menu
void displayMenu() {
    std::cout << "\n1. Add task\n";
    std::cout << "2. View tasks\n";
    std::cout << "3. Mark task as done\n";
    std::cout << "4. Exit\n";
    std::cout << "Choose an option: ";
}

// Function to add a task
void addTask(std::vector<std::string> &tasks) {
    std::string task;
    std::cout << "Enter the task: ";
    std::cin.ignore(); // Clear the input buffer
    std::getline(std::cin, task);
    tasks.push_back(task);
    std::cout << "Task added.\n";
}

// Function to view all tasks
void viewTasks(const std::vector<std::string> &tasks) {
    if (tasks.empty()) {
        std::cout << "No tasks available.\n";
        return;
    }
    std::cout << "\nTasks:\n";
    for (size_t i = 0; i < tasks.size(); ++i) {
        std::cout << i + 1 << ". " << tasks[i] << "\n";
    }
}

// Function to mark a task as done
void markTaskDone(std::vector<std::string> &tasks) {
    if (tasks.empty()) {
        std::cout << "No tasks to mark as done.\n";
        return;
    }
    int index;
    std::cout << "Enter the task number to mark as done: ";
    std::cin >> index;
    if (index < 1 || index > tasks.size()) {
        std::cout << "Invalid task number.\n";
    } else {
        tasks.erase(tasks.begin() + index - 1);
        std::cout << "Task marked as done.\n";
    }
}

int main() {
    std::vector<std::string> tasks;
    int choice;
    bool running = true;

    while (running) {
        displayMenu();
        std::cin >> choice;

        switch (choice) {
            case 1:
                addTask(tasks);
                break;
            case 2:
                viewTasks(tasks);
                break;
            case 3:
                markTaskDone(tasks);
                break;
            case 4:
                running = false;
                break;
            default:
                std::cout << "Invalid choice. Try again.\n";
        }
    }

    std::cout << "Exiting...\n";
    return 0;
}
