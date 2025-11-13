package icu.one.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.Bed;

@Mapper
public interface BedMapper {

    /**
     * 查询所有床位
     * @return 床位列表
     */
    List<Bed> selectAllBeds();

    /**
     * 根据床位ID查询床位
     * @param bedId 床位ID
     * @return 床位对象
     */
    Bed getBedById(@Param("bedId") Long bedId);

    /**
     * 根据床位号查询床位
     * @param bedNo 床位号
     * @return 床位对象
     */
    Bed getBedByNo(@Param("bedNo") String bedNo);

    /**
     * 根据科室查询床位
     * @param department 科室名称
     * @return 床位列表
     */
    List<Bed> getBedsByDepartment(@Param("department") String department);

    /**
     * 根据状态查询床位
     * @param status 床位状态
     * @return 床位列表
     */
    List<Bed> getBedsByStatus(@Param("status") String status);

    /**
     * 查询空闲床位
     * @return 空闲床位列表
     */
    List<Bed> getAvailableBeds();

    /**
     * 查询占用床位
     * @return 占用床位列表
     */
    List<Bed> getOccupiedBeds();

    /**
     * 查询所有床位及患者信息
     * @return 床位及患者信息列表
     */
    List<Map<String, Object>> getBedsWithPatients();

    /**
     * 添加床位
     * @param bed 床位对象
     * @return 影响的行数
     */
    int addBed(Bed bed);

    /**
     * 更新床位信息
     * @param bed 床位对象
     * @return 影响的行数
     */
    int updateBed(Bed bed);

    /**
     * 更新床位状态
     * @param bedId 床位ID
     * @param status 床位状态
     * @return 影响的行数
     */
    int updateBedStatus(@Param("bedId") Long bedId, @Param("status") String status);

    /**
     * 分配患者到床位
     * @param bedId 床位ID
     * @param patientId 患者ID
     * @return 影响的行数
     */
    int assignPatientToBed(@Param("bedId") Long bedId, @Param("patientId") Long patientId);

    /**
     * 解除床位患者绑定
     * @param bedId 床位ID
     * @return 影响的行数
     */
    int unassignPatientFromBed(@Param("bedId") Long bedId);

    /**
     * 换床操作
     * @param fromBedId 原床位ID
     * @param toBedId 目标床位ID
     * @param patientId 患者ID
     * @return 影响的行数
     */
    int swapBeds(@Param("fromBedId") Long fromBedId, @Param("toBedId") Long toBedId, @Param("patientId") Long patientId);

    /**
     * 预约锁定床位
     * @param bedId 床位ID
     * @return 影响的行数
     */
    int reserveBed(@Param("bedId") Long bedId);

    /**
     * 取消床位预约
     * @param bedId 床位ID
     * @return 影响的行数
     */
    int cancelBedReservation(@Param("bedId") Long bedId);

    /**
     * 删除床位
     * @param bedId 床位ID
     * @return 影响的行数
     */
    int deleteBed(@Param("bedId") Long bedId);

    /**
     * 统计科室床位使用情况
     * @param department 科室名称
     * @return 床位统计信息
     */
    Map<String, Object> getBedStatisticsByDepartment(@Param("department") String department);

    /**
     * 获取所有科室床位统计
     * @return 所有科室床位统计列表
     */
    List<Map<String, Object>> getAllBedStatistics();

    /**
     * 根据患者ID查询床位
     * @param patientId 患者ID
     * @return 床位对象
     */
    Bed getBedByPatientId(@Param("patientId") Long patientId);

    /**
     * 批量更新床位状态
     * @param bedIds 床位ID列表
     * @param status 床位状态
     * @return 影响的行数
     */
    int batchUpdateBedStatus(@Param("bedIds") List<Long> bedIds, @Param("status") String status);
}
