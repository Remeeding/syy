package os4;

import java.util.*;


class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int runTime = 0;
    String status = "R"; // R:就绪, E:结束
    
    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class FCFSScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 输入进程数量
        System.out.println("请输入选择个数：");
        int processCount = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符
        
        List<Process> processes = new ArrayList<>();
        
        // 输入每个进程的信息
        for (int i = 0; i < processCount; i++) {
            System.out.printf("请输入第%d个选择信息：名称 到达时间 任务所需时间：\n", i+1);
            String[] input = scanner.nextLine().trim().split("\\s+");
            String name = input[0];
            int arrivalTime = Integer.parseInt(input[1]);
            int burstTime = Integer.parseInt(input[2]);
            processes.add(new Process(name, arrivalTime, burstTime));
        }
        
        // 设置时间片大小为5（根据图片中的运行时间推断）
        int timeQuantum = 5;
        
        // 执行时间片轮转调度
        roundRobinScheduling(processes, timeQuantum);
        
        scanner.close();
    }
    
    public static void roundRobinScheduling(List<Process> processes, int timeQuantum) {
        Queue<Process> queue = new LinkedList<>();
        List<Process> allProcesses = new ArrayList<>(processes);
        int currentTime = 0;
        Process currentProcess = null;
        int timeInQuantum = 0;
        
        while (true) {
            // 添加到达的进程到队列
            for (Process p : processes) {
                if (p.arrivalTime == currentTime) {
                    queue.add(p);
                }
            }
            
            // 检查当前进程是否完成或时间片用完
            if (currentProcess != null) {
                if (currentProcess.remainingTime == 0) {
                    currentProcess.status = "E";
                    currentProcess = null;
                } else if (timeInQuantum >= timeQuantum) {
                    queue.add(currentProcess);
                    currentProcess = null;
                }
            }
            
            // 如果当前没有运行进程，从队列中取出一个
            if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.poll();
                timeInQuantum = 0;
            }
            
            // 打印状态
            System.out.printf("CPU时刻：%d\n", currentTime);
            System.out.println("正在运行的进程：" + (currentProcess != null ? currentProcess.name : "无"));
            
            System.out.println("Name\tarrive\trun\treq\tstatus");
            for (Process p : allProcesses) {
                System.out.printf("%s\t%d\t%d\t%d\t%s\n",
                    p.name, p.arrivalTime, p.runTime, p.burstTime, p.status);
            }
            
            // 打印下一个要执行的进程索引或-1
            if (queue.isEmpty()) {
                System.out.println("-1");
            } else {
                System.out.println(allProcesses.indexOf(queue.peek()));
            }
            
            System.out.println();
            
            // 如果所有进程都完成，退出循环
            boolean allDone = true;
            for (Process p : allProcesses) {
                if (p.status.equals("R")) {
                    allDone = false;
                    break;
                }
            }
            if (allDone) break;
            
            // 执行一个时间单位
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                currentProcess.runTime++;
                timeInQuantum++;
            }
            currentTime++;
        }
    }
}