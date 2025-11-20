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

import icu.one.entity.ShiftRecord;
import icu.one.mapper.ShiftRecordMapper;

@RestController
@RequestMapping("/api/shift-records")
public class ShiftRecordController {

    @Autowired
    private ShiftRecordMapper shiftRecordMapper;

    /**
     * 查询患者交班记录
     * @param patientId 患者ID
     * @return 交班记录列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ShiftRecord>> getShiftRecordsByPatientId(@PathVariable Long patientId) {
        List<ShiftRecord> shiftRecords = shiftRecordMapper.getShiftRecordsByPatientId(patientId);
        return ResponseEntity.ok(shiftRecords);
    }

    /**
     * 添加交班记录
     * @param shiftRecord 交班记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addShiftRecord(@RequestBody ShiftRecord shiftRecord) {
        try {
            int result = shiftRecordMapper.addShiftRecord(shiftRecord);
            return result > 0 ? 
                ResponseEntity.ok("交班记录添加成功") : 
                ResponseEntity.badRequest().body("交班记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("交班记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 查询最近交班记录
     * @param patientId 患者ID
     * @return 最新交班记录
     */
    @GetMapping("/latest/{patientId}")
    public ResponseEntity<ShiftRecord> getLatestShiftRecord(@PathVariable Long patientId) {
        ShiftRecord shiftRecord = shiftRecordMapper.getLatestShiftRecord(patientId);
        return shiftRecord != null ? 
            ResponseEntity.ok(shiftRecord) : 
            ResponseEntity.notFound().build();
    }
}
