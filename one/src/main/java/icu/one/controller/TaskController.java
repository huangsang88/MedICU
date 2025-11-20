package icu.one.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.Task;
import icu.one.mapper.TaskMapper;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 查询所有任务
     * @return 任务列表（包含患者和用户信息）
     */
    @GetMapping
    public ResponseEntity<List<Task>> selectAllTasks() {
        List<Task> tasks = taskMapper.selectAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * 根据ID查询任务
     * @param taskId 任务ID
     * @return 任务详情（包含患者和用户信息）
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskMapper.getTaskById(taskId);
        return task != null ? 
            ResponseEntity.ok(task) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据状态查询任务
     * @param status 任务状态
     * @return 任务列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        List<Task> tasks = taskMapper.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 根据分配人查询任务
     * @param userId 用户ID
     * @return 任务列表
     */
    @GetMapping("/assignee/{userId}")
    public ResponseEntity<List<Task>> getTasksByAssignee(@PathVariable Long userId) {
        List<Task> tasks = taskMapper.getTasksByAssignee(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 根据患者查询任务
     * @param patientId 患者ID
     * @return 任务列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Task>> getTasksByPatient(@PathVariable Long patientId) {
        List<Task> tasks = taskMapper.getTasksByPatient(patientId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 日历视图查询 - 按日期范围查询任务
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 任务列表
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Task>> getTasksByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Task> tasks = taskMapper.getTasksByDateRange(startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    /**
     * 查询今日任务
     * @return 今日任务列表
     */
    @GetMapping("/today")
    public ResponseEntity<List<Task>> getTodayTasks() {
        List<Task> tasks = taskMapper.getTodayTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * 查询过期任务
     * @return 过期任务列表
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskMapper.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * 添加任务
     * @param task 任务对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            int result = taskMapper.addTask(task);
            return result > 0 ? 
                ResponseEntity.ok("任务添加成功") : 
                ResponseEntity.badRequest().body("任务添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新任务
     * @param task 任务对象
     * @return 操作结果
     */
    @PutMapping
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        try {
            int result = taskMapper.updateTask(task);
            return result > 0 ? 
                ResponseEntity.ok("任务更新成功") : 
                ResponseEntity.badRequest().body("任务更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务更新失败: " + e.getMessage());
        }
    }

    /**
     * 更新任务状态
     * @param taskId 任务ID
     * @param status 状态
     * @param completionTime 完成时间
     * @param completionNotes 完成说明
     * @return 操作结果
     */
    @PutMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date completionTime,
            @RequestParam(required = false) String completionNotes) {
        try {
            int result = taskMapper.updateTaskStatus(taskId, status, completionTime, completionNotes);
            return result > 0 ? 
                ResponseEntity.ok("任务状态更新成功") : 
                ResponseEntity.badRequest().body("任务状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 重新分配任务
     * @param taskId 任务ID
     * @param assignedTo 分配给的用户ID
     * @return 操作结果
     */
    @PutMapping("/{taskId}/reassign")
    public ResponseEntity<String> reassignTask(
            @PathVariable Long taskId,
            @RequestParam Long assignedTo) {
        try {
            int result = taskMapper.reassignTask(taskId, assignedTo);
            return result > 0 ? 
                ResponseEntity.ok("任务重新分配成功") : 
                ResponseEntity.badRequest().body("任务重新分配失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务重新分配失败: " + e.getMessage());
        }
    }

    /**
     * 批量更新任务状态
     * @param taskIds 任务ID列表
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/batch-status")
    public ResponseEntity<String> batchUpdateTaskStatus(
            @RequestParam List<Long> taskIds,
            @RequestParam String status) {
        try {
            int result = taskMapper.batchUpdateTaskStatus(taskIds, status);
            return result > 0 ? 
                ResponseEntity.ok("批量更新任务状态成功，共更新 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量更新任务状态失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量更新任务状态失败: " + e.getMessage());
        }
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return 操作结果
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        try {
            int result = taskMapper.deleteTask(taskId);
            return result > 0 ? 
                ResponseEntity.ok("任务删除成功") : 
                ResponseEntity.badRequest().body("任务删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务删除失败: " + e.getMessage());
        }
    }

    /**
     * 统计任务完成情况
     * @return 任务统计列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> getTaskStatistics() {
        List<Map<String, Object>> statistics = taskMapper.getTaskStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * 按优先级统计任务
     * @return 优先级统计列表
     */
    @GetMapping("/statistics/priority")
    public ResponseEntity<List<Map<String, Object>>> getTaskStatisticsByPriority() {
        List<Map<String, Object>> statistics = taskMapper.getTaskStatisticsByPriority();
        return ResponseEntity.ok(statistics);
    }
}
