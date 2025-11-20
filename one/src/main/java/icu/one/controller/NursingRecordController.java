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

import icu.one.entity.NursingRecord;
import icu.one.mapper.NursingRecordMapper;

@RestController
@RequestMapping("/api/nursing-records")
public class NursingRecordController {

    @Autowired
    private NursingRecordMapper nursingRecordMapper;

    /**
     * 查询神志相关护理记录
     * @param patientId 患者ID
     * @return 神志相关护理记录列表（最近24小时）
     */
    @GetMapping("/consciousness/{patientId}")
    public ResponseEntity<List<NursingRecord>> getConsciousnessRecords(@PathVariable Long patientId) {
        List<NursingRecord> records = nursingRecordMapper.getConsciousnessRecords(patientId);
        return ResponseEntity.ok(records);
    }

    /**
     * 添加护理记录
     * @param nursingRecord 护理记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addNursingRecord(@RequestBody NursingRecord nursingRecord) {
        try {
            int result = nursingRecordMapper.addNursingRecord(nursingRecord);
            return result > 0 ? 
                ResponseEntity.ok("护理记录添加成功") : 
                ResponseEntity.badRequest().body("护理记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("护理记录添加失败: " + e.getMessage());
        }
    }
}
