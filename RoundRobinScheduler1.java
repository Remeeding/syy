package os4;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduler1 {

    static class Process {
        String name;
        double arrivalTime;
        double burstTime;
        
        public Process(String name, double arrivalTime, double burstTime) {
            this.name = name;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }
    }

    public static void main(String[] args) {
        // 设置时间片大小为7（足够大，使每个进程能一次执行完）
        int timeQuantum = 7;
        
        // 创建进程列表，包含到达时间和运行时间
        Process[] processes = {
            new Process("A", 8.0, 2.0),
            new Process("B", 8.5, 0.5),
            new Process("C", 9.0, 0.2),
            new Process("D", 9.5, 1.0),
            new Process("E", 10.0, 1.5),  // 补充E-G的示例数据
            new Process("F", 10.5, 0.8),
            new Process("G", 11.0, 1.2)
        };
        
        // 创建执行队列
        Queue<Process> processQueue = new LinkedList<>();
        for (Process p : processes) {
            processQueue.add(p);
        }
        
        
        // 初始队列显示
        System.out.println("时间片大小：" + timeQuantum);
        printProcessQueue(processQueue);
        
        // 处理每个进程
        while (!processQueue.isEmpty()) {
            Process currentProcess = processQueue.poll();
            
            System.out.println(currentProcess.name + "正在运行！");
            System.out.println(currentProcess.name + "运行完毕！");
            
            // 显示更新后的队列
            if (!processQueue.isEmpty()) {
                printProcessQueue(processQueue);
            }
        }
        
        System.out.println("进程调度完成！");
        System.out.println();
        // 打印进程信息表
        System.out.println("作业号\t到达时间\t运行时间");
        for (Process p : processes) {
            System.out.printf("%s\t%.1f\t\t%.1f\n", p.name, p.arrivalTime, p.burstTime);
        }
    }
    
    private static void printProcessQueue(Queue<Process> queue) {
        System.out.print("进程队列：");
        for (Process process : queue) {
            System.out.print(process.name + " ");
        }
        System.out.println();
    }
}
