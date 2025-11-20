package icu.one.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.LabResult;
import icu.one.mapper.LabResultMapper;

@RestController
@RequestMapping("/api/lab-results")
public class LabResultController {

    @Autowired
    private LabResultMapper labResultMapper;

    /**
     * 查询血气分析数据
     * @param patientId 患者ID
     * @return 血气分析结果列表
     */
    @GetMapping("/blood-gas/{patientId}")
    public ResponseEntity<List<LabResult>> getBloodGasResults(@PathVariable Long patientId) {
        List<LabResult> results = labResultMapper.getBloodGasResults(patientId);
        return ResponseEntity.ok(results);
    }

    /**
     * 查询血糖数据
     * @param patientId 患者ID
     * @return 血糖结果列表
     */
    @GetMapping("/blood-glucose/{patientId}")
    public ResponseEntity<List<LabResult>> getBloodGlucoseResults(@PathVariable Long patientId) {
        List<LabResult> results = labResultMapper.getBloodGlucoseResults(patientId);
        return ResponseEntity.ok(results);
    }

    /**
     * 查询检验数据动态变化
     * @param patientId 患者ID
     * @param testType 检验类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 检验趋势结果列表
     */
    @GetMapping("/trend")
    public ResponseEntity<List<LabResult>> getLabTrend(
            @RequestParam Long patientId,
            @RequestParam String testType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<LabResult> trend = labResultMapper.getLabTrend(patientId, testType, startTime, endTime);
        return ResponseEntity.ok(trend);
    }

    /**
     * 查询最新检验结果
     * @param patientId 患者ID
     * @return 最新检验结果列表（最多10条）
     */
    @GetMapping("/latest/{patientId}")
    public ResponseEntity<List<LabResult>> getLatestLabResults(@PathVariable Long patientId) {
        List<LabResult> results = labResultMapper.getLatestLabResults(patientId);
        return ResponseEntity.ok(results);
    }

    /**
     * 添加检验结果
     * @param labResult 检验结果对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addLabResult(@RequestBody LabResult labResult) {
        try {
            int result = labResultMapper.addLabResult(labResult);
            return result > 0 ? 
                ResponseEntity.ok("检验结果添加成功") : 
                ResponseEntity.badRequest().body("检验结果添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("检验结果添加失败: " + e.getMessage());
        }
    }

    /**
     * 批量添加检验结果
     * @param labResults 检验结果对象列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchAddLabResults(@RequestBody List<LabResult> labResults) {
        try {
            int result = labResultMapper.batchAddLabResults(labResults);
            return result > 0 ? 
                ResponseEntity.ok("批量添加检验结果成功，共添加 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量添加检验结果失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量添加检验结果失败: " + e.getMessage());
        }
    }
}
