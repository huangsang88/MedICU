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

import icu.one.entity.Assessment;
import icu.one.mapper.AssessmentMapper;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentMapper assessmentMapper;

    /**
     * 根据患者ID查询评估记录列表
     * @param patientId 患者ID
     * @return 评估记录列表
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Assessment>> getAssessmentsByPatientId(@PathVariable Long patientId) {
        List<Assessment> assessments = assessmentMapper.getAssessmentsByPatientId(patientId);
        return ResponseEntity.ok(assessments);
    }

    /**
     * 添加评估记录
     * @param assessment 评估对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addAssessment(@RequestBody Assessment assessment) {
        try {
            int result = assessmentMapper.addAssessment(assessment);
            return result > 0 ? 
                ResponseEntity.ok("评估记录添加成功") : 
                ResponseEntity.badRequest().body("评估记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("评估记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 查询患者重要评分（APACHE、SOFA、GCS）
     * @param patientId 患者ID
     * @return 重要评分记录列表
     */
    @GetMapping("/important/{patientId}")
    public ResponseEntity<List<Assessment>> getImportantAssessments(@PathVariable Long patientId) {
        List<Assessment> assessments = assessmentMapper.getImportantAssessments(patientId);
        return ResponseEntity.ok(assessments);
    }

    /**
     * 查询24小时神志变化（GCS评分）
     * @param patientId 患者ID
     * @return GCS评分记录列表
     */
    @GetMapping("/consciousness/{patientId}")
    public ResponseEntity<List<Assessment>> getConsciousnessChanges(@PathVariable Long patientId) {
        List<Assessment> assessments = assessmentMapper.getConsciousnessChanges(patientId);
        return ResponseEntity.ok(assessments);
    }

    /**
     * 查询评分趋势
     * @param patientId 患者ID
     * @param assessmentType 评分类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 评分趋势记录列表
     */
    @GetMapping("/trend")
    public ResponseEntity<List<Assessment>> getAssessmentTrend(
            @RequestParam Long patientId,
            @RequestParam String assessmentType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<Assessment> assessments = assessmentMapper.getAssessmentTrend(patientId, assessmentType, startTime, endTime);
        return ResponseEntity.ok(assessments);
    }

    /**
     * 查询最新评分
     * @param patientId 患者ID
     * @param assessmentType 评分类型
     * @return 最新评分记录
     */
    @GetMapping("/latest")
    public ResponseEntity<Assessment> getLatestAssessment(
            @RequestParam Long patientId,
            @RequestParam String assessmentType) {
        Assessment assessment = assessmentMapper.getLatestAssessment(patientId, assessmentType);
        return assessment != null ? 
            ResponseEntity.ok(assessment) : 
            ResponseEntity.notFound().build();
    }
}
