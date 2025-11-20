package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.TreatmentRecord;

@Mapper
public interface TreatmentRecordMapper {

    /**
     * 查询患者治疗记录
     * @param patientId 患者ID
     * @return 治疗记录列表
     */
    List<TreatmentRecord> getTreatmentRecordsByPatientId(@Param("patientId") Long patientId);

    /**
     * 添加治疗记录
     * @param treatmentRecord 治疗记录对象
     * @return 影响的行数
     */
    int addTreatmentRecord(TreatmentRecord treatmentRecord);
}
