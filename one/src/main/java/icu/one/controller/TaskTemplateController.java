package icu.one.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import icu.one.entity.TaskTemplate;
import icu.one.mapper.TaskTemplateMapper;

@RestController
@RequestMapping("/api/task-templates")
public class TaskTemplateController {

    @Autowired
    private TaskTemplateMapper taskTemplateMapper;

    /**
     * 查询所有任务模板
     * @return 任务模板列表
     */
    @GetMapping
    public ResponseEntity<List<TaskTemplate>> selectAllTemplates() {
        List<TaskTemplate> templates = taskTemplateMapper.selectAllTemplates();
        return ResponseEntity.ok(templates);
    }

    /**
     * 根据ID查询任务模板
     * @param templateId 模板ID
     * @return 任务模板
     */
    @GetMapping("/{templateId}")
    public ResponseEntity<TaskTemplate> getTemplateById(@PathVariable Long templateId) {
        TaskTemplate template = taskTemplateMapper.getTemplateById(templateId);
        return template != null ? 
            ResponseEntity.ok(template) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据科室查询任务模板
     * @param department 科室
     * @return 任务模板列表
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<TaskTemplate>> getTemplatesByDepartment(@PathVariable String department) {
        List<TaskTemplate> templates = taskTemplateMapper.getTemplatesByDepartment(department);
        return ResponseEntity.ok(templates);
    }

    /**
     * 根据任务类型查询模板
     * @param taskType 任务类型
     * @return 任务模板列表
     */
    @GetMapping("/type/{taskType}")
    public ResponseEntity<List<TaskTemplate>> getTemplatesByType(@PathVariable String taskType) {
        List<TaskTemplate> templates = taskTemplateMapper.getTemplatesByType(taskType);
        return ResponseEntity.ok(templates);
    }

    /**
     * 根据频率查询模板
     * @param frequency 频率
     * @return 任务模板列表
     */
    @GetMapping("/frequency/{frequency}")
    public ResponseEntity<List<TaskTemplate>> getTemplatesByFrequency(@PathVariable String frequency) {
        List<TaskTemplate> templates = taskTemplateMapper.getTemplatesByFrequency(frequency);
        return ResponseEntity.ok(templates);
    }

    /**
     * 添加任务模板
     * @param taskTemplate 任务模板对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addTaskTemplate(@RequestBody TaskTemplate taskTemplate) {
        try {
            int result = taskTemplateMapper.addTaskTemplate(taskTemplate);
            return result > 0 ? 
                ResponseEntity.ok("任务模板添加成功") : 
                ResponseEntity.badRequest().body("任务模板添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务模板添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新任务模板
     * @param taskTemplate 任务模板对象
     * @return 操作结果
     */
    @PutMapping
    public ResponseEntity<String> updateTaskTemplate(@RequestBody TaskTemplate taskTemplate) {
        try {
            int result = taskTemplateMapper.updateTaskTemplate(taskTemplate);
            return result > 0 ? 
                ResponseEntity.ok("任务模板更新成功") : 
                ResponseEntity.badRequest().body("任务模板更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务模板更新失败: " + e.getMessage());
        }
    }

    /**
     * 启用/禁用任务模板
     * @param templateId 模板ID
     * @param isActive 是否激活
     * @return 操作结果
     */
    @PutMapping("/{templateId}/status")
    public ResponseEntity<String> toggleTemplateStatus(
            @PathVariable Long templateId,
            @RequestParam Boolean isActive) {
        try {
            int result = taskTemplateMapper.toggleTemplateStatus(templateId, isActive);
            return result > 0 ? 
                ResponseEntity.ok("任务模板状态更新成功") : 
                ResponseEntity.badRequest().body("任务模板状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务模板状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除任务模板
     * @param templateId 模板ID
     * @return 操作结果
     */
    @DeleteMapping("/{templateId}")
    public ResponseEntity<String> deleteTaskTemplate(@PathVariable Long templateId) {
        try {
            int result = taskTemplateMapper.deleteTaskTemplate(templateId);
            return result > 0 ? 
                ResponseEntity.ok("任务模板删除成功") : 
                ResponseEntity.badRequest().body("任务模板删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("任务模板删除失败: " + e.getMessage());
        }
    }

    /**
     * 统计各类型任务模板数量
     * @return 模板统计列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> getTemplateStatistics() {
        List<Map<String, Object>> statistics = taskTemplateMapper.getTemplateStatistics();
        return ResponseEntity.ok(statistics);
    }
}
