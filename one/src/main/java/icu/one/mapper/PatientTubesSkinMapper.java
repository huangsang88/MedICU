package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.PatientTubesSkin;

@Mapper
public interface PatientTubesSkinMapper {

    /**
     * 查询患者管路信息
     * @param patientId 患者ID
     * @return 管路信息列表
     */
    List<PatientTubesSkin> getPatientTubes(@Param("patientId") Long patientId);

    /**
     * 查询患者皮肤信息
     * @param patientId 患者ID
     * @return 皮肤信息列表
     */
    List<PatientTubesSkin> getPatientSkin(@Param("patientId") Long patientId);

    /**
     * 查询3D模型所需的管路皮肤信息
     * @param patientId 患者ID
     * @return 3D模型数据列表
     */
    List<PatientTubesSkin> get3DModelData(@Param("patientId") Long patientId);

    /**
     * 添加管路皮肤记录
     * @param patientTubeSkin 管路皮肤记录对象
     * @return 影响的行数
     */
    int addPatientTubesSkin(PatientTubesSkin patientTubeSkin);

    /**
     * 更新管路皮肤状态
     * @param recordId 记录ID
     * @param status 状态
     * @param description 描述
     * @return 影响的行数
     */
    int updateTubesSkinStatus(@Param("recordId") Long recordId, 
                            @Param("status") String status, 
                            @Param("description") String description);
}
