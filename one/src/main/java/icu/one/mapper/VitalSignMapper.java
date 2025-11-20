package icu.one.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.VitalSign;

@Mapper
public interface VitalSignMapper {

    /**
     * 查询患者生命体征趋势
     * @param patientId 患者ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生命体征记录列表
     */
    List<VitalSign> getVitalSignsTrend(@Param("patientId") Long patientId, 
                                      @Param("startTime") Date startTime, 
                                      @Param("endTime") Date endTime);

    /**
     * 查询单参数生命体征数据
     * @param patientId 患者ID
     * @param parameter 参数名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 单参数数据列表
     */
    List<Map<String, Object>> getSingleParameterTrend(@Param("patientId") Long patientId, 
                                                    @Param("parameter") String parameter, 
                                                    @Param("startTime") Date startTime, 
                                                    @Param("endTime") Date endTime);

    /**
     * 查询最新生命体征
     * @param patientId 患者ID
     * @return 最新生命体征记录
     */
    VitalSign getLatestVitalSigns(@Param("patientId") Long patientId);

    /**
     * 添加生命体征记录
     * @param vitalSigns 生命体征记录对象
     * @return 影响的行数
     */
    int addVitalSigns(VitalSign vitalSigns);

    /**
     * 批量添加生命体征记录
     * @param vitalSignsList 生命体征记录对象列表
     * @return 影响的行数
     */
    int batchAddVitalSigns(@Param("list") List<VitalSign> vitalSignsList);
}
