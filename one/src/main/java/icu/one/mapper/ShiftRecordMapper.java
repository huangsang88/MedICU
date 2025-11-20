package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.ShiftRecord;

@Mapper
public interface ShiftRecordMapper {

    /**
     * 查询患者交班记录
     * @param patientId 患者ID
     * @return 交班记录列表
     */
    List<ShiftRecord> getShiftRecordsByPatientId(@Param("patientId") Long patientId);

    /**
     * 添加交班记录
     * @param shiftRecord 交班记录对象
     * @return 影响的行数
     */
    int addShiftRecord(ShiftRecord shiftRecord);

    /**
     * 查询最近交班记录
     * @param patientId 患者ID
     * @return 最新交班记录
     */
    ShiftRecord getLatestShiftRecord(@Param("patientId") Long patientId);
}
