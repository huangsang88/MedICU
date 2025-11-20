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

import icu.one.entity.DepartmentRoutine;
import icu.one.mapper.DepartmentRoutineMapper;

@RestController
@RequestMapping("/api/department-routines")
public class DepartmentRoutineController {

    @Autowired
    private DepartmentRoutineMapper departmentRoutineMapper;

    /**
     * 查询所有科室常规配置
     * @return 科室常规配置列表
     */
    @GetMapping
    public ResponseEntity<List<DepartmentRoutine>> selectAllRoutines() {
        List<DepartmentRoutine> routines = departmentRoutineMapper.selectAllRoutines();
        return ResponseEntity.ok(routines);
    }

    /**
     * 根据ID查询科室常规配置
     * @param routineId 常规配置ID
     * @return 科室常规配置对象
     */
    @GetMapping("/{routineId}")
    public ResponseEntity<DepartmentRoutine> getRoutineById(@PathVariable Long routineId) {
        DepartmentRoutine routine = departmentRoutineMapper.getRoutineById(routineId);
        return routine != null ? 
            ResponseEntity.ok(routine) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据科室查询常规配置
     * @param department 科室名称
     * @return 科室常规配置列表
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<DepartmentRoutine>> getRoutinesByDepartment(@PathVariable String department) {
        List<DepartmentRoutine> routines = departmentRoutineMapper.getRoutinesByDepartment(department);
        return ResponseEntity.ok(routines);
    }

    /**
     * 根据触发事件查询配置
     * @param triggerEvent 触发事件
     * @return 科室常规配置列表
     */
    @GetMapping("/trigger-event")
    public ResponseEntity<List<DepartmentRoutine>> getRoutinesByTriggerEvent(@RequestParam String triggerEvent) {
        List<DepartmentRoutine> routines = departmentRoutineMapper.getRoutinesByTriggerEvent(triggerEvent);
        return ResponseEntity.ok(routines);
    }

    /**
     * 添加科室常规配置
     * @param departmentRoutine 科室常规配置对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addDepartmentRoutine(@RequestBody DepartmentRoutine departmentRoutine) {
        try {
            int result = departmentRoutineMapper.addDepartmentRoutine(departmentRoutine);
            return result > 0 ? 
                ResponseEntity.ok("科室常规配置添加成功") : 
                ResponseEntity.badRequest().body("科室常规配置添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("科室常规配置添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新科室常规配置
     * @param departmentRoutine 科室常规配置对象
     * @return 操作结果
     */
    @PutMapping
    public ResponseEntity<String> updateDepartmentRoutine(@RequestBody DepartmentRoutine departmentRoutine) {
        try {
            int result = departmentRoutineMapper.updateDepartmentRoutine(departmentRoutine);
            return result > 0 ? 
                ResponseEntity.ok("科室常规配置更新成功") : 
                ResponseEntity.badRequest().body("科室常规配置更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("科室常规配置更新失败: " + e.getMessage());
        }
    }

    /**
     * 启用/禁用常规配置
     * @param routineId 常规配置ID
     * @param isActive 是否激活
     * @return 操作结果
     */
    @PutMapping("/{routineId}/status")
    public ResponseEntity<String> toggleRoutineStatus(@PathVariable Long routineId, @RequestParam Boolean isActive) {
        try {
            int result = departmentRoutineMapper.toggleRoutineStatus(routineId, isActive);
            return result > 0 ? 
                ResponseEntity.ok("常规配置状态更新成功") : 
                ResponseEntity.badRequest().body("常规配置状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("常规配置状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除科室常规配置
     * @param routineId 常规配置ID
     * @return 操作结果
     */
    @DeleteMapping("/{routineId}")
    public ResponseEntity<String> deleteDepartmentRoutine(@PathVariable Long routineId) {
        try {
            int result = departmentRoutineMapper.deleteDepartmentRoutine(routineId);
            return result > 0 ? 
                ResponseEntity.ok("科室常规配置删除成功") : 
                ResponseEntity.badRequest().body("科室常规配置删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("科室常规配置删除失败: " + e.getMessage());
        }
    }

    /**
     * 根据患者入院事件生成任务
     * @param department 科室名称
     * @return 科室常规配置列表
     */
    @GetMapping("/admission-tasks/{department}")
    public ResponseEntity<List<DepartmentRoutine>> getRoutinesForPatientAdmission(@PathVariable String department) {
        List<DepartmentRoutine> routines = departmentRoutineMapper.getRoutinesForPatientAdmission(department);
        return ResponseEntity.ok(routines);
    }

    /**
     * 统计各科室常规配置数量
     * @return 统计信息列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> getRoutineStatistics() {
        List<Map<String, Object>> statistics = departmentRoutineMapper.getRoutineStatistics();
        return ResponseEntity.ok(statistics);
    }
}
