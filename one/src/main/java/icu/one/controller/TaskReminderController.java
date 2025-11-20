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

import icu.one.entity.TaskReminder;
import icu.one.mapper.TaskReminderMapper;

@RestController
@RequestMapping("/api/task-reminders")
public class TaskReminderController {

    @Autowired
    private TaskReminderMapper taskReminderMapper;

    /**
     * 查询所有提醒
     * @return 提醒列表（包含任务和用户信息）
     */
    @GetMapping
    public ResponseEntity<List<TaskReminder>> selectAllReminders() {
        List<TaskReminder> reminders = taskReminderMapper.selectAllReminders();
        return ResponseEntity.ok(reminders);
    }

    /**
     * 根据ID查询提醒
     * @param reminderId 提醒ID
     * @return 提醒详情
     */
    @GetMapping("/{reminderId}")
    public ResponseEntity<TaskReminder> getReminderById(@PathVariable Long reminderId) {
        TaskReminder reminder = taskReminderMapper.getReminderById(reminderId);
        return reminder != null ? 
            ResponseEntity.ok(reminder) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据任务ID查询提醒
     * @param taskId 任务ID
     * @return 提醒列表
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskReminder>> getRemindersByTaskId(@PathVariable Long taskId) {
        List<TaskReminder> reminders = taskReminderMapper.getRemindersByTaskId(taskId);
        return ResponseEntity.ok(reminders);
    }

    /**
     * 根据用户ID查询提醒
     * @param userId 用户ID
     * @return 提醒列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskReminder>> getRemindersByUserId(@PathVariable Long userId) {
        List<TaskReminder> reminders = taskReminderMapper.getRemindersByUserId(userId);
        return ResponseEntity.ok(reminders);
    }

    /**
     * 查询待发送的提醒
     * @return 待发送提醒列表
     */
    @GetMapping("/pending")
    public ResponseEntity<List<TaskReminder>> getPendingReminders() {
        List<TaskReminder> reminders = taskReminderMapper.getPendingReminders();
        return ResponseEntity.ok(reminders);
    }

    /**
     * 查询今日提醒
     * @return 今日提醒列表
     */
    @GetMapping("/today")
    public ResponseEntity<List<TaskReminder>> getTodayReminders() {
        List<TaskReminder> reminders = taskReminderMapper.getTodayReminders();
        return ResponseEntity.ok(reminders);
    }

    /**
     * 根据状态查询提醒
     * @param status 提醒状态
     * @return 提醒列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskReminder>> getRemindersByStatus(@PathVariable String status) {
        List<TaskReminder> reminders = taskReminderMapper.getRemindersByStatus(status);
        return ResponseEntity.ok(reminders);
    }

    /**
     * 添加任务提醒
     * @param taskReminder 提醒对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addTaskReminder(@RequestBody TaskReminder taskReminder) {
        try {
            int result = taskReminderMapper.addTaskReminder(taskReminder);
            return result > 0 ? 
                ResponseEntity.ok("任务提醒添加成功") : 
                ResponseEntity.badRequest().body("任务提醒添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务提醒添加失败: " + e.getMessage());
        }
    }

    /**
     * 批量添加任务提醒
     * @param reminders 提醒对象列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchAddTaskReminders(@RequestBody List<TaskReminder> reminders) {
        try {
            int result = taskReminderMapper.batchAddTaskReminders(reminders);
            return result > 0 ? 
                ResponseEntity.ok("批量添加任务提醒成功，共添加 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量添加任务提醒失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量添加任务提醒失败: " + e.getMessage());
        }
    }

    /**
     * 更新提醒状态
     * @param reminderId 提醒ID
     * @param status 状态
     * @param sentTime 发送时间
     * @return 操作结果
     */
    @PutMapping("/{reminderId}/status")
    public ResponseEntity<String> updateReminderStatus(
            @PathVariable Long reminderId,
            @RequestParam String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date sentTime) {
        try {
            int result = taskReminderMapper.updateReminderStatus(reminderId, status, sentTime);
            return result > 0 ? 
                ResponseEntity.ok("提醒状态更新成功") : 
                ResponseEntity.badRequest().body("提醒状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("提醒状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 批量更新提醒状态
     * @param reminderIds 提醒ID列表
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/batch-status")
    public ResponseEntity<String> batchUpdateReminderStatus(
            @RequestParam List<Long> reminderIds,
            @RequestParam String status) {
        try {
            int result = taskReminderMapper.batchUpdateReminderStatus(reminderIds, status);
            return result > 0 ? 
                ResponseEntity.ok("批量更新提醒状态成功，共更新 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量更新提醒状态失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量更新提醒状态失败: " + e.getMessage());
        }
    }

    /**
     * 标记提醒为已读
     * @param reminderId 提醒ID
     * @param userId 用户ID
     * @return 操作结果
     */
    @PutMapping("/{reminderId}/read")
    public ResponseEntity<String> markReminderAsRead(
            @PathVariable Long reminderId,
            @RequestParam Long userId) {
        try {
            int result = taskReminderMapper.markReminderAsRead(reminderId, userId);
            return result > 0 ? 
                ResponseEntity.ok("提醒标记为已读成功") : 
                ResponseEntity.badRequest().body("提醒标记为已读失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("提醒标记为已读失败: " + e.getMessage());
        }
    }

    /**
     * 删除任务提醒
     * @param reminderId 提醒ID
     * @return 操作结果
     */
    @DeleteMapping("/{reminderId}")
    public ResponseEntity<String> deleteTaskReminder(@PathVariable Long reminderId) {
        try {
            int result = taskReminderMapper.deleteTaskReminder(reminderId);
            return result > 0 ? 
                ResponseEntity.ok("任务提醒删除成功") : 
                ResponseEntity.badRequest().body("任务提醒删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务提醒删除失败: " + e.getMessage());
        }
    }

    /**
     * 根据任务ID删除所有提醒
     * @param taskId 任务ID
     * @return 操作结果
     */
    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<String> deleteRemindersByTaskId(@PathVariable Long taskId) {
        try {
            int result = taskReminderMapper.deleteRemindersByTaskId(taskId);
            return result > 0 ? 
                ResponseEntity.ok("根据任务ID删除提醒成功，共删除 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("根据任务ID删除提醒失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("根据任务ID删除提醒失败: " + e.getMessage());
        }
    }

    /**
     * 统计提醒发送情况
     * @return 提醒统计列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> getReminderStatistics() {
        List<Map<String, Object>> statistics = taskReminderMapper.getReminderStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * 获取用户未读提醒数量
     * @param userId 用户ID
     * @return 未读提醒数量
     */
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<Integer> getUnreadReminderCount(@PathVariable Long userId) {
        int count = taskReminderMapper.getUnreadReminderCount(userId);
        return ResponseEntity.ok(count);
    }
}
