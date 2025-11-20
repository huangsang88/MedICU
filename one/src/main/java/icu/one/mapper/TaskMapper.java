package icu.one.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.Task;

@Mapper
public interface TaskMapper {

    /**
     * 查询所有任务
     * @return 任务列表（包含患者和用户信息）
     */
    List<Task> selectAllTasks();

    /**
     * 根据ID查询任务
     * @param taskId 任务ID
     * @return 任务详情（包含患者和用户信息）
     */
    Task getTaskById(@Param("taskId") Long taskId);

    /**
     * 根据状态查询任务
     * @param status 任务状态
     * @return 任务列表
     */
    List<Task> getTasksByStatus(@Param("status") String status);

    /**
     * 根据分配人查询任务
     * @param userId 用户ID
     * @return 任务列表
     */
    List<Task> getTasksByAssignee(@Param("userId") Long userId);

    /**
     * 根据患者查询任务
     * @param patientId 患者ID
     * @return 任务列表
     */
    List<Task> getTasksByPatient(@Param("patientId") Long patientId);

    /**
     * 日历视图查询 - 按日期范围查询任务
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 任务列表
     */
    List<Task> getTasksByDateRange(@Param("startDate") Date startDate, 
                                 @Param("endDate") Date endDate);

    /**
     * 查询今日任务
     * @return 今日任务列表
     */
    List<Task> getTodayTasks();

    /**
     * 查询过期任务
     * @return 过期任务列表
     */
    List<Task> getOverdueTasks();

    /**
     * 添加任务
     * @param task 任务对象
     * @return 影响的行数
     */
    int addTask(Task task);

    /**
     * 更新任务
     * @param task 任务对象
     * @return 影响的行数
     */
    int updateTask(Task task);

    /**
     * 更新任务状态
     * @param taskId 任务ID
     * @param status 状态
     * @param completionTime 完成时间
     * @param completionNotes 完成说明
     * @return 影响的行数
     */
    int updateTaskStatus(@Param("taskId") Long taskId, 
                       @Param("status") String status, 
                       @Param("completionTime") Date completionTime, 
                       @Param("completionNotes") String completionNotes);

    /**
     * 重新分配任务
     * @param taskId 任务ID
     * @param assignedTo 分配给的用户ID
     * @return 影响的行数
     */
    int reassignTask(@Param("taskId") Long taskId, 
                   @Param("assignedTo") Long assignedTo);

    /**
     * 批量更新任务状态
     * @param taskIds 任务ID列表
     * @param status 状态
     * @return 影响的行数
     */
    int batchUpdateTaskStatus(@Param("taskIds") List<Long> taskIds, 
                            @Param("status") String status);

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return 影响的行数
     */
    int deleteTask(@Param("taskId") Long taskId);

    /**
     * 统计任务完成情况
     * @return 任务统计列表
     */
    List<Map<String, Object>> getTaskStatistics();

    /**
     * 按优先级统计任务
     * @return 优先级统计列表
     */
    List<Map<String, Object>> getTaskStatisticsByPriority();
}
