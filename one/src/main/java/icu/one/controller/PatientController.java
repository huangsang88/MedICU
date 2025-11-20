package icu.one.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.Patient;
import icu.one.mapper.PatientMapper;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientMapper patientMapper;

    /**
     * 查询所有患者
     * @return 患者列表
     */
    @GetMapping
    public ResponseEntity<List<Patient>> selectAllPatients() {
        List<Patient> patients = patientMapper.selectAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * 根据ID查询患者
     * @param patientId 患者ID
     * @return 患者信息
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        Patient patient = patientMapper.getPatientById(patientId);
        return patient != null ? 
            ResponseEntity.ok(patient) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据病历号查询患者
     * @param medicalRecordNo 病历号
     * @return 患者信息
     */
    @GetMapping("/by-record-no")
    public ResponseEntity<Patient> getPatientByMedicalRecordNo(@RequestParam String medicalRecordNo) {
        Patient patient = patientMapper.getPatientByMedicalRecordNo(medicalRecordNo);
        return patient != null ? 
            ResponseEntity.ok(patient) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 查询入科患者列表（状态为住院）
     * @return 住院患者列表
     */
    @GetMapping("/admitted")
    public ResponseEntity<List<Patient>> getAdmittedPatients() {
        List<Patient> patients = patientMapper.getAdmittedPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * 查询出院患者
     * @return 出院患者列表
     */
    @GetMapping("/discharged")
    public ResponseEntity<List<Patient>> getDischargedPatients() {
        List<Patient> patients = patientMapper.getDischargedPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * 查询转科患者
     * @return 转科患者列表
     */
    @GetMapping("/transferred")
    public ResponseEntity<List<Patient>> getTransferredPatients() {
        List<Patient> patients = patientMapper.getTransferredPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * 手动登记患者（紧急情况，支持部分字段）
     * @param patient 患者对象
     * @return 操作结果
     */
    @PostMapping("/emergency")
    public ResponseEntity<String> registerEmergencyPatient(@RequestBody Patient patient) {
        try {
            int result = patientMapper.registerEmergencyPatient(patient);
            return result > 0 ? 
                ResponseEntity.ok("紧急患者登记成功") : 
                ResponseEntity.badRequest().body("紧急患者登记失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("紧急患者登记失败: " + e.getMessage());
        }
    }

    /**
     * 完善患者信息
     * @param patient 患者对象
     * @return 操作结果
     */
    @PutMapping
    public ResponseEntity<String> updatePatientInfo(@RequestBody Patient patient) {
        try {
            int result = patientMapper.updatePatientInfo(patient);
            return result > 0 ? 
                ResponseEntity.ok("患者信息更新成功") : 
                ResponseEntity.badRequest().body("患者信息更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("患者信息更新失败: " + e.getMessage());
        }
    }

    /**
     * 更新患者状态
     * @param patientId 患者ID
     * @param status 状态
     * @param dischargeTime 出院时间（当状态为出院时）
     * @return 操作结果
     */
    @PutMapping("/{patientId}/status")
    public ResponseEntity<String> updatePatientStatus(
            @PathVariable Long patientId,
            @RequestParam String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dischargeTime) {
        try {
            int result = patientMapper.updatePatientStatus(patientId, status, dischargeTime);
            return result > 0 ? 
                ResponseEntity.ok("患者状态更新成功") : 
                ResponseEntity.badRequest().body("患者状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("患者状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除患者
     * @param patientId 患者ID
     * @return 操作结果
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable Long patientId) {
        try {
            int result = patientMapper.deletePatient(patientId);
            return result > 0 ? 
                ResponseEntity.ok("患者删除成功") : 
                ResponseEntity.badRequest().body("患者删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("患者删除失败: " + e.getMessage());
        }
    }

    /**
     * 查询患者流转情况（包含关键时间线）
     * @param patientId 患者ID
     * @return 患者流转信息
     */
    @GetMapping("/{patientId}/flow")
    public ResponseEntity<Patient> getPatientFlow(@PathVariable Long patientId) {
        Patient patient = patientMapper.getPatientFlow(patientId);
        return patient != null ? 
            ResponseEntity.ok(patient) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据姓名模糊查询患者
     * @param name 患者姓名
     * @return 患者列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatientsByName(@RequestParam String name) {
        List<Patient> patients = patientMapper.searchPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    /**
     * 统计患者数量
     * @return 患者状态统计列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> countPatientsByStatus() {
        List<Map<String, Object>> statistics = patientMapper.countPatientsByStatus();
        return ResponseEntity.ok(statistics);
    }

    /**
     * 批量更新患者状态
     * @param patientIds 患者ID列表
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/batch-status")
    public ResponseEntity<String> batchUpdatePatientStatus(
            @RequestParam List<Long> patientIds,
            @RequestParam String status) {
        try {
            int result = patientMapper.batchUpdatePatientStatus(patientIds, status);
            return result > 0 ? 
                ResponseEntity.ok("批量更新患者状态成功，共更新 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量更新患者状态失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量更新患者状态失败: " + e.getMessage());
        }
    }
}
