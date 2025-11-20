package icu.one.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.TaskTemplate;

@Mapper
public interface TaskTemplateMapper {

    /**
     * 查询所有任务模板
     * @return 任务模板列表
     */
    List<TaskTemplate> selectAllTemplates();

    /**
     * 根据ID查询任务模板
     * @param templateId 模板ID
     * @return 任务模板
     */
    TaskTemplate getTemplateById(@Param("templateId") Long templateId);

    /**
     * 根据科室查询任务模板
     * @param department 科室
     * @return 任务模板列表
     */
    List<TaskTemplate> getTemplatesByDepartment(@Param("department") String department);

    /**
     * 根据任务类型查询模板
     * @param taskType 任务类型
     * @return 任务模板列表
     */
    List<TaskTemplate> getTemplatesByType(@Param("taskType") String taskType);

    /**
     * 根据频率查询模板
     * @param frequency 频率
     * @return 任务模板列表
     */
    List<TaskTemplate> getTemplatesByFrequency(@Param("frequency") String frequency);

    /**
     * 添加任务模板
     * @param taskTemplate 任务模板对象
     * @return 影响的行数
     */
    int addTaskTemplate(TaskTemplate taskTemplate);

    /**
     * 更新任务模板
     * @param taskTemplate 任务模板对象
     * @return 影响的行数
     */
    int updateTaskTemplate(TaskTemplate taskTemplate);

    /**
     * 启用/禁用任务模板
     * @param templateId 模板ID
     * @param isActive 是否激活
     * @return 影响的行数
     */
    int toggleTemplateStatus(@Param("templateId") Long templateId, 
                           @Param("isActive") Boolean isActive);

    /**
     * 删除任务模板
     * @param templateId 模板ID
     * @return 影响的行数
     */
    int deleteTaskTemplate(@Param("templateId") Long templateId);

    /**
     * 统计各类型任务模板数量
     * @return 模板统计列表
     */
    List<Map<String, Object>> getTemplateStatistics();
}
