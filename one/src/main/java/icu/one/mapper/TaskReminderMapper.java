package icu.one.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.TaskReminder;

@Mapper
public interface TaskReminderMapper {

    /**
     * 查询所有提醒
     * @return 提醒列表（包含任务和用户信息）
     */
    List<TaskReminder> selectAllReminders();

    /**
     * 根据ID查询提醒
     * @param reminderId 提醒ID
     * @return 提醒详情
     */
    TaskReminder getReminderById(@Param("reminderId") Long reminderId);

    /**
     * 根据任务ID查询提醒
     * @param taskId 任务ID
     * @return 提醒列表
     */
    List<TaskReminder> getRemindersByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据用户ID查询提醒
     * @param userId 用户ID
     * @return 提醒列表
     */
    List<TaskReminder> getRemindersByUserId(@Param("userId") Long userId);

    /**
     * 查询待发送的提醒
     * @return 待发送提醒列表
     */
    List<TaskReminder> getPendingReminders();

    /**
     * 查询今日提醒
     * @return 今日提醒列表
     */
    List<TaskReminder> getTodayReminders();

    /**
     * 根据状态查询提醒
     * @param status 提醒状态
     * @return 提醒列表
     */
    List<TaskReminder> getRemindersByStatus(@Param("status") String status);

    /**
     * 添加任务提醒
     * @param taskReminder 提醒对象
     * @return 影响的行数
     */
    int addTaskReminder(TaskReminder taskReminder);

    /**
     * 批量添加任务提醒
     * @param reminders 提醒对象列表
     * @return 影响的行数
     */
    int batchAddTaskReminders(@Param("reminders") List<TaskReminder> reminders);

    /**
     * 更新提醒状态
     * @param reminderId 提醒ID
     * @param status 状态
     * @param sentTime 发送时间
     * @return 影响的行数
     */
    int updateReminderStatus(@Param("reminderId") Long reminderId, 
                           @Param("status") String status, 
                           @Param("sentTime") Date sentTime);

    /**
     * 批量更新提醒状态
     * @param reminderIds 提醒ID列表
     * @param status 状态
     * @return 影响的行数
     */
    int batchUpdateReminderStatus(@Param("reminderIds") List<Long> reminderIds, 
                                @Param("status") String status);

    /**
     * 标记提醒为已读
     * @param reminderId 提醒ID
     * @param userId 用户ID
     * @return 影响的行数
     */
    int markReminderAsRead(@Param("reminderId") Long reminderId, 
                         @Param("userId") Long userId);

    /**
     * 删除任务提醒
     * @param reminderId 提醒ID
     * @return 影响的行数
     */
    int deleteTaskReminder(@Param("reminderId") Long reminderId);

    /**
     * 根据任务ID删除所有提醒
     * @param taskId 任务ID
     * @return 影响的行数
     */
    int deleteRemindersByTaskId(@Param("taskId") Long taskId);

    /**
     * 统计提醒发送情况
     * @return 提醒统计列表
     */
    List<Map<String, Object>> getReminderStatistics();

    /**
     * 获取用户未读提醒数量
     * @param userId 用户ID
     * @return 未读提醒数量
     */
    int getUnreadReminderCount(@Param("userId") Long userId);
}
