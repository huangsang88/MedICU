package icu.one.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import icu.one.entity.MedicalOrder;
import icu.one.mapper.MedicalOrderMapper;

@RestController
@RequestMapping("/api/medical-orders")
public class MedicalOrderController {

    @Autowired
    private MedicalOrderMapper medicalOrderMapper;

    /**
     * 查询患者检验检查医嘱
     * @param patientId 患者ID
     * @return 医嘱记录列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalOrder>> getOrdersByPatientId(@PathVariable Long patientId) {
        List<MedicalOrder> orders = medicalOrderMapper.getOrdersByPatientId(patientId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 查询检验检查项目（根据内容关键词）
     * @param keyword 关键词
     * @param patientId 患者ID
     * @return 医嘱记录列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<MedicalOrder>> getLabAndExamOrders(
            @RequestParam String keyword,
            @RequestParam Long patientId) {
        List<MedicalOrder> orders = medicalOrderMapper.getLabAndExamOrders(keyword, patientId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 开立检验检查医嘱
     * @param medicalOrder 医嘱对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addMedicalOrder(@RequestBody MedicalOrder medicalOrder) {
        try {
            int result = medicalOrderMapper.addMedicalOrder(medicalOrder);
            return result > 0 ? 
                ResponseEntity.ok("医嘱开立成功") : 
                ResponseEntity.badRequest().body("医嘱开立失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("医嘱开立失败: " + e.getMessage());
        }
    }

    /**
     * 更新医嘱状态
     * @param orderId 医嘱ID
     * @param status 状态
     * @param executionTime 执行时间
     * @param executor 执行人
     * @return 操作结果
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date executionTime,
            @RequestParam String executor) {
        try {
            int result = medicalOrderMapper.updateOrderStatus(orderId, status, executionTime, executor);
            return result > 0 ? 
                ResponseEntity.ok("医嘱状态更新成功") : 
                ResponseEntity.badRequest().body("医嘱状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("医嘱状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 查询待执行检验检查
     * @return 待执行医嘱列表
     */
    @GetMapping("/pending")
    public ResponseEntity<List<MedicalOrder>> getPendingOrders() {
        List<MedicalOrder> orders = medicalOrderMapper.getPendingOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * 查询新开医嘱（用于提醒）
     * @param startTime 开始时间
     * @return 新开医嘱列表
     */
    @GetMapping("/new")
    public ResponseEntity<List<MedicalOrder>> getNewOrders(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime) {
        List<MedicalOrder> orders = medicalOrderMapper.getNewOrders(startTime);
        return ResponseEntity.ok(orders);
    }
}
