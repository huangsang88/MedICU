package icu.one.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.DepartmentRoutine;

@Mapper
public interface DepartmentRoutineMapper {

    /**
     * 查询所有科室常规配置
     * @return 科室常规配置列表
     */
    List<DepartmentRoutine> selectAllRoutines();

    /**
     * 根据ID查询科室常规配置
     * @param routineId 常规配置ID
     * @return 科室常规配置对象
     */
    DepartmentRoutine getRoutineById(@Param("routineId") Long routineId);

    /**
     * 根据科室查询常规配置
     * @param department 科室名称
     * @return 科室常规配置列表
     */
    List<DepartmentRoutine> getRoutinesByDepartment(@Param("department") String department);

    /**
     * 根据触发事件查询配置
     * @param triggerEvent 触发事件
     * @return 科室常规配置列表
     */
    List<DepartmentRoutine> getRoutinesByTriggerEvent(@Param("triggerEvent") String triggerEvent);

    /**
     * 添加科室常规配置
     * @param departmentRoutine 科室常规配置对象
     * @return 影响的行数
     */
    int addDepartmentRoutine(DepartmentRoutine departmentRoutine);

    /**
     * 更新科室常规配置
     * @param departmentRoutine 科室常规配置对象
     * @return 影响的行数
     */
    int updateDepartmentRoutine(DepartmentRoutine departmentRoutine);

    /**
     * 启用/禁用常规配置
     * @param routineId 常规配置ID
     * @param isActive 是否激活
     * @return 影响的行数
     */
    int toggleRoutineStatus(@Param("routineId") Long routineId, @Param("isActive") Boolean isActive);

    /**
     * 删除科室常规配置
     * @param routineId 常规配置ID
     * @return 影响的行数
     */
    int deleteDepartmentRoutine(@Param("routineId") Long routineId);

    /**
     * 根据患者入院事件生成任务
     * @param department 科室名称
     * @return 科室常规配置列表
     */
    List<DepartmentRoutine> getRoutinesForPatientAdmission(@Param("department") String department);

    /**
     * 统计各科室常规配置数量
     * @return 统计信息列表，每个元素是一个Map，包含department, trigger_event, count, active_count
     */
    List<Map<String, Object>> getRoutineStatistics();
}
