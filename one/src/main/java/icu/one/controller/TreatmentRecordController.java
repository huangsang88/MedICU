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

import icu.one.entity.TreatmentRecord;
import icu.one.mapper.TreatmentRecordMapper;

@RestController
@RequestMapping("/api/treatment-records")
public class TreatmentRecordController {

    @Autowired
    private TreatmentRecordMapper treatmentRecordMapper;

    /**
     * 查询患者治疗记录
     * @param patientId 患者ID
     * @return 治疗记录列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TreatmentRecord>> getTreatmentRecordsByPatientId(@PathVariable Long patientId) {
        List<TreatmentRecord> records = treatmentRecordMapper.getTreatmentRecordsByPatientId(patientId);
        return ResponseEntity.ok(records);
    }

    /**
     * 添加治疗记录
     * @param treatmentRecord 治疗记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addTreatmentRecord(@RequestBody TreatmentRecord treatmentRecord) {
        try {
            int result = treatmentRecordMapper.addTreatmentRecord(treatmentRecord);
            return result > 0 ? 
                ResponseEntity.ok("治疗记录添加成功") : 
                ResponseEntity.badRequest().body("治疗记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("治疗记录添加失败: " + e.getMessage());
        }
    }
}
