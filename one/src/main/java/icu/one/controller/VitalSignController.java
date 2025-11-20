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

import icu.one.entity.VitalSign;
import icu.one.mapper.VitalSignMapper;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignController {

    @Autowired
    private VitalSignMapper vitalSignMapper;

    /**
     * 查询患者生命体征趋势
     * @param patientId 患者ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生命体征记录列表
     */
    @GetMapping("/trend")
    public ResponseEntity<List<VitalSign>> getVitalSignsTrend(
            @RequestParam Long patientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<VitalSign> vitalSigns = vitalSignMapper.getVitalSignsTrend(patientId, startTime, endTime);
        return ResponseEntity.ok(vitalSigns);
    }

    /**
     * 查询单参数生命体征数据
     * @param patientId 患者ID
     * @param parameter 参数名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 单参数数据列表
     */
    @GetMapping("/single-parameter")
    public ResponseEntity<List<Map<String, Object>>> getSingleParameterTrend(
            @RequestParam Long patientId,
            @RequestParam String parameter,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<Map<String, Object>> parameterData = vitalSignMapper.getSingleParameterTrend(patientId, parameter, startTime, endTime);
        return ResponseEntity.ok(parameterData);
    }

    /**
     * 查询最新生命体征
     * @param patientId 患者ID
     * @return 最新生命体征记录
     */
    @GetMapping("/latest/{patientId}")
    public ResponseEntity<VitalSign> getLatestVitalSigns(@PathVariable Long patientId) {
        VitalSign vitalSigns = vitalSignMapper.getLatestVitalSigns(patientId);
        return vitalSigns != null ? 
            ResponseEntity.ok(vitalSigns) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 添加生命体征记录
     * @param vitalSigns 生命体征记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addVitalSigns(@RequestBody VitalSign vitalSigns) {
        try {
            int result = vitalSignMapper.addVitalSigns(vitalSigns);
            return result > 0 ? 
                ResponseEntity.ok("生命体征记录添加成功") : 
                ResponseEntity.badRequest().body("生命体征记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("生命体征记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 批量添加生命体征记录
     * @param vitalSignsList 生命体征记录对象列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public ResponseEntity<String> batchAddVitalSigns(@RequestBody List<VitalSign> vitalSignsList) {
        try {
            int result = vitalSignMapper.batchAddVitalSigns(vitalSignsList);
            return result > 0 ? 
                ResponseEntity.ok("批量添加生命体征记录成功，共添加 " + result + " 条记录") : 
                ResponseEntity.badRequest().body("批量添加生命体征记录失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量添加生命体征记录失败: " + e.getMessage());
        }
    }
}
