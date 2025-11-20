package icu.one.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.IntakeOutput;

@Mapper
public interface IntakeOutputMapper {

    /**
     * 查询24小时出入量平衡
     * @param patientId 患者ID
     * @return 出入量平衡信息，包含总摄入量、总排出量和平衡值
     */
    Map<String, Object> get24HourBalance(@Param("patientId") Long patientId);

    /**
     * 查询24小时出入量分类总结
     * @param patientId 患者ID
     * @return 出入量分类总结列表，每个元素包含类型、类别和总容量
     */
    List<Map<String, Object>> get24HourSummary(@Param("patientId") Long patientId);

    /**
     * 查询班次内出入量
     * @param patientId 患者ID
     * @param shiftType 班次类型
     * @return 班次出入量信息，包含总摄入量、总排出量和平衡值
     */
    Map<String, Object> getShiftBalance(@Param("patientId") Long patientId, @Param("shiftType") String shiftType);

    /**
     * 查询出入量趋势
     * @param patientId 患者ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 出入量记录列表
     */
    List<IntakeOutput> getIntakeOutputTrend(@Param("patientId") Long patientId, 
                                          @Param("startTime") Date startTime, 
                                          @Param("endTime") Date endTime);

    /**
     * 添加出入量记录
     * @param intakeOutput 出入量记录对象
     * @return 影响的行数
     */
    int addIntakeOutput(IntakeOutput intakeOutput);

    /**
     * 批量添加出入量记录
     * @param list 出入量记录对象列表
     * @return 影响的行数
     */
    int batchAddIntakeOutput(@Param("list") List<IntakeOutput> list);
}
