package icu.one.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.Assessment;

@Mapper
public interface AssessmentMapper {

    /**
     * 查询患者评估记录
     * @param patientId 患者ID
     * @return 评估记录列表
     */
    List<Assessment> getAssessmentsByPatientId(@Param("patientId") Long patientId);

    /**
     * 添加评估记录
     * @param assessment 评估对象
     * @return 影响的行数
     */
    int addAssessment(Assessment assessment);

    /**
     * 查询患者重要评分（APACHE、SOFA、GCS）
     * @param patientId 患者ID
     * @return 重要评分记录列表
     */
    List<Assessment> getImportantAssessments(@Param("patientId") Long patientId);

    /**
     * 查询24小时神志变化（GCS评分）
     * @param patientId 患者ID
     * @return GCS评分记录列表
     */
    List<Assessment> getConsciousnessChanges(@Param("patientId") Long patientId);

    /**
     * 查询评分趋势
     * @param patientId 患者ID
     * @param assessmentType 评分类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 评分趋势记录列表
     */
    List<Assessment> getAssessmentTrend(@Param("patientId") Long patientId, 
                                      @Param("assessmentType") String assessmentType, 
                                      @Param("startTime") Date startTime, 
                                      @Param("endTime") Date endTime);

    /**
     * 查询最新评分
     * @param patientId 患者ID
     * @param assessmentType 评分类型
     * @return 最新评分记录
     */
    Assessment getLatestAssessment(@Param("patientId") Long patientId, 
                                 @Param("assessmentType") String assessmentType);
}
