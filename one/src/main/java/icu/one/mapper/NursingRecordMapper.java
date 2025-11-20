package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.NursingRecord;

@Mapper
public interface NursingRecordMapper {

    /**
     * 查询神志相关护理记录
     * @param patientId 患者ID
     * @return 神志相关护理记录列表（最近24小时）
     */
    List<NursingRecord> getConsciousnessRecords(@Param("patientId") Long patientId);

    /**
     * 添加护理记录
     * @param nursingRecord 护理记录对象
     * @return 影响的行数
     */
    int addNursingRecord(NursingRecord nursingRecord);
}
