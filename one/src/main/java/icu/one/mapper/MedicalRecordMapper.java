package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.MedicalRecord;

@Mapper
public interface MedicalRecordMapper {

    /**
     * 查询患者所有病历记录
     * @param patientId 患者ID
     * @return 病历记录列表
     */
    List<MedicalRecord> getRecordsByPatientId(@Param("patientId") Long patientId);

    /**
     * 查询检验检查报告
     * @param patientId 患者ID
     * @return 检验检查报告列表
     */
    List<MedicalRecord> getLabReports(@Param("patientId") Long patientId);

    /**
     * 添加病历记录（用于记录检验检查结果）
     * @param medicalRecord 病历记录对象
     * @return 影响的行数
     */
    int addMedicalRecord(MedicalRecord medicalRecord);

    /**
     * 查询患者在科期间的记录
     * @param patientId 患者ID
     * @return 在科期间的病历记录列表
     */
    List<MedicalRecord> getRecordsDuringStay(@Param("patientId") Long patientId);
}
