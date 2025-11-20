package icu.one.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.LabResult;

@Mapper
public interface LabResultMapper {

    /**
     * 查询血气分析数据
     * @param patientId 患者ID
     * @return 血气分析结果列表
     */
    List<LabResult> getBloodGasResults(@Param("patientId") Long patientId);

    /**
     * 查询血糖数据
     * @param patientId 患者ID
     * @return 血糖结果列表
     */
    List<LabResult> getBloodGlucoseResults(@Param("patientId") Long patientId);

    /**
     * 查询检验数据动态变化
     * @param patientId 患者ID
     * @param testType 检验类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 检验趋势结果列表
     */
    List<LabResult> getLabTrend(@Param("patientId") Long patientId, 
                               @Param("testType") String testType, 
                               @Param("startTime") Date startTime, 
                               @Param("endTime") Date endTime);

    /**
     * 查询最新检验结果
     * @param patientId 患者ID
     * @return 最新检验结果列表（最多10条）
     */
    List<LabResult> getLatestLabResults(@Param("patientId") Long patientId);

    /**
     * 添加检验结果
     * @param labResult 检验结果对象
     * @return 影响的行数
     */
    int addLabResult(LabResult labResult);

    /**
     * 批量添加检验结果
     * @param labResults 检验结果对象列表
     * @return 影响的行数
     */
    int batchAddLabResults(@Param("list") List<LabResult> labResults);
}
