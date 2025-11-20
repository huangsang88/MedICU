package icu.one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.MedicalRecord;
import icu.one.mapper.MedicalRecordMapper;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    /**
     * 查询患者所有病历记录
     * @param patientId 患者ID
     * @return 病历记录列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsByPatientId(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordMapper.getRecordsByPatientId(patientId);
        return ResponseEntity.ok(records);
    }

    /**
     * 查询检验检查报告
     * @param patientId 患者ID
     * @return 检验检查报告列表
     */
    @GetMapping("/lab-reports/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getLabReports(@PathVariable Long patientId) {
        List<MedicalRecord> reports = medicalRecordMapper.getLabReports(patientId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 添加病历记录（用于记录检验检查结果）
     * @param medicalRecord 病历记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            int result = medicalRecordMapper.addMedicalRecord(medicalRecord);
            return result > 0 ? 
                ResponseEntity.ok("病历记录添加成功") : 
                ResponseEntity.badRequest().body("病历记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("病历记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 查询患者在科期间的记录
     * @param patientId 患者ID
     * @return 在科期间的病历记录列表
     */
    @GetMapping("/during-stay/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsDuringStay(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordMapper.getRecordsDuringStay(patientId);
        return ResponseEntity.ok(records);
    }
}
