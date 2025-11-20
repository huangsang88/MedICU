package icu.one.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.MedicalOrder;

@Mapper
public interface MedicalOrderMapper {

    /**
     * 查询患者检验检查医嘱
     * @param patientId 患者ID
     * @return 医嘱记录列表
     */
    List<MedicalOrder> getOrdersByPatientId(@Param("patientId") Long patientId);

    /**
     * 查询检验检查项目（根据内容关键词）
     * @param keyword 关键词
     * @param patientId 患者ID
     * @return 医嘱记录列表
     */
    List<MedicalOrder> getLabAndExamOrders(@Param("keyword") String keyword, 
                                          @Param("patientId") Long patientId);

    /**
     * 开立检验检查医嘱
     * @param medicalOrder 医嘱对象
     * @return 影响的行数
     */
    int addMedicalOrder(MedicalOrder medicalOrder);

    /**
     * 更新医嘱状态
     * @param orderId 医嘱ID
     * @param status 状态
     * @param executionTime 执行时间
     * @param executor 执行人
     * @return 影响的行数
     */
    int updateOrderStatus(@Param("orderId") Long orderId, 
                        @Param("status") String status, 
                        @Param("executionTime") Date executionTime, 
                        @Param("executor") String executor);

    /**
     * 查询待执行检验检查
     * @return 待执行医嘱列表
     */
    List<MedicalOrder> getPendingOrders();

    /**
     * 查询新开医嘱（用于提醒）
     * @param startTime 开始时间
     * @return 新开医嘱列表
     */
    List<MedicalOrder> getNewOrders(@Param("startTime") Date startTime);
}
