package icu.one.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.Patient;

@Mapper
public interface PatientMapper {

    /**
     * 查询所有患者
     * @return 患者列表
     */
    List<Patient> selectAllPatients();

    /**
     * 根据ID查询患者
     * @param patientId 患者ID
     * @return 患者信息
     */
    Patient getPatientById(@Param("patientId") Long patientId);

    /**
     * 根据病历号查询患者
     * @param medicalRecordNo 病历号
     * @return 患者信息
     */
    Patient getPatientByMedicalRecordNo(@Param("medicalRecordNo") String medicalRecordNo);

    /**
     * 查询入科患者列表（状态为住院）
     * @return 住院患者列表
     */
    List<Patient> getAdmittedPatients();

    /**
     * 查询出院患者
     * @return 出院患者列表
     */
    List<Patient> getDischargedPatients();

    /**
     * 查询转科患者
     * @return 转科患者列表
     */
    List<Patient> getTransferredPatients();

    /**
     * 手动登记患者（紧急情况，支持部分字段）
     * @param patient 患者对象
     * @return 影响的行数
     */
    int registerEmergencyPatient(Patient patient);

    /**
     * 完善患者信息
     * @param patient 患者对象
     * @return 影响的行数
     */
    int updatePatientInfo(Patient patient);

    /**
     * 更新患者状态
     * @param patientId 患者ID
     * @param status 状态
     * @param dischargeTime 出院时间（当状态为出院时）
     * @return 影响的行数
     */
    int updatePatientStatus(@Param("patientId") Long patientId, 
                          @Param("status") String status, 
                          @Param("dischargeTime") Date dischargeTime);

    /**
     * 删除患者
     * @param patientId 患者ID
     * @return 影响的行数
     */
    int deletePatient(@Param("patientId") Long patientId);

    /**
     * 查询患者流转情况（包含关键时间线）
     * @param patientId 患者ID
     * @return 患者流转信息
     */
    Patient getPatientFlow(@Param("patientId") Long patientId);

    /**
     * 根据姓名模糊查询患者
     * @param name 患者姓名
     * @return 患者列表
     */
    List<Patient> searchPatientsByName(@Param("name") String name);

    /**
     * 统计患者数量
     * @return 患者状态统计列表
     */
    List<Map<String, Object>> countPatientsByStatus();

    /**
     * 批量更新患者状态
     * @param patientIds 患者ID列表
     * @param status 状态
     * @return 影响的行数
     */
    int batchUpdatePatientStatus(@Param("patientIds") List<Long> patientIds, 
                               @Param("status") String status);
}
