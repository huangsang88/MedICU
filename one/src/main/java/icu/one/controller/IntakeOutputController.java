package icu.one.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import icu.one.entity.IntakeOutput;
import icu.one.mapper.IntakeOutputMapper;

@RestController
@RequestMapping("/api/intake-output")
public class IntakeOutputController {

    @Autowired
    private IntakeOutputMapper intakeOutputMapper;

    /**
     * 查询24小时出入量平衡
     * @param patientId 患者ID
     * @return 出入量平衡信息，包含总摄入量、总排出量和平衡值
     */
    @GetMapping("/balance/24hour/{patientId}")
    public ResponseEntity<Map<String, Object>> get24HourBalance(@PathVariable Long patientId) {
        Map<String, Object> balance = intakeOutputMapper.get24HourBalance(patientId);
        return balance != null && !balance.isEmpty() ? 
            ResponseEntity.ok(balance) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 查询24小时出入量分类总结
     * @param patientId 患者ID
     * @return 出入量分类总结列表，每个元素包含类型、类别和总容量
     */
    @GetMapping("/summary/24hour/{patientId}")
    public ResponseEntity<List<Map<String, Object>>> get24HourSummary(@PathVariable Long patientId) {
        List<Map<String, Object>> summary = intakeOutputMapper.get24HourSummary(patientId);
        return ResponseEntity.ok(summary);
    }

    /**
     * 查询班次内出入量
     * @param patientId 患者ID
     * @param shiftType 班次类型
     * @return 班次出入量信息，包含总摄入量、总排出量和平衡值
     */
    @GetMapping("/shift-balance")
    public ResponseEntity<Map<String, Object>> getShiftBalance(
            @RequestParam Long patientId, 
            @RequestParam String shiftType) {
        Map<String, Object> balance = intakeOutputMapper.getShiftBalance(patientId, shiftType);
        return balance != null && !balance.isEmpty() ? 
            ResponseEntity.ok(balance) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 查询出入量趋势
     * @param patientId 患者ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 出入量记录列表
     */
    @GetMapping("/trend")
    public ResponseEntity<List<IntakeOutput>> getIntakeOutputTrend(
            @RequestParam Long patientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<IntakeOutput> trend = intakeOutputMapper.getIntakeOutputTrend(patientId, startTime, endTime);
        return ResponseEntity.ok(trend);
    }

    /**
     * 添加出入量记录
     * @param intakeOutput 出入量记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addIntakeOutput(@RequestBody IntakeOutput intakeOutput) {
        try {
            int result = intakeOutputMapper.addIntakeOutput(intakeOutput);
            return result > 0 ? 
                ResponseEntity.ok("出入量记录添加成功") : 
                ResponseEntity.badRequest().body("出入量记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("出入量记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 批量添加出入量记录
     * @param list 出入量记录对象列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchAddIntakeOutput(@RequestBody List<IntakeOutput> list) {
        try {
            int result = intakeOutputMapper.batchAddIntakeOutput(list);
            return result > 0 ? 
                ResponseEntity.ok("批量添加出入量记录成功，共添加 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量添加出入量记录失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量添加出入量记录失败: " + e.getMessage());
        }
    }
}
