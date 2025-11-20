package icu.one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.PatientTubesSkin;
import icu.one.mapper.PatientTubesSkinMapper;

@RestController
@RequestMapping("/api/patient-tubes-skin")
public class PatientTubesSkinController {

    @Autowired
    private PatientTubesSkinMapper patientTubesSkinMapper;

    /**
     * 查询患者管路信息
     * @param patientId 患者ID
     * @return 管路信息列表
     */
    @GetMapping("/tubes/{patientId}")
    public ResponseEntity<List<PatientTubesSkin>> getPatientTubes(@PathVariable Long patientId) {
        List<PatientTubesSkin> tubes = patientTubesSkinMapper.getPatientTubes(patientId);
        return ResponseEntity.ok(tubes);
    }

    /**
     * 查询患者皮肤信息
     * @param patientId 患者ID
     * @return 皮肤信息列表
     */
    @GetMapping("/skin/{patientId}")
    public ResponseEntity<List<PatientTubesSkin>> getPatientSkin(@PathVariable Long patientId) {
        List<PatientTubesSkin> skin = patientTubesSkinMapper.getPatientSkin(patientId);
        return ResponseEntity.ok(skin);
    }

    /**
     * 查询3D模型所需的管路皮肤信息
     * @param patientId 患者ID
     * @return 3D模型数据列表
     */
    @GetMapping("/3d-model/{patientId}")
    public ResponseEntity<List<PatientTubesSkin>> get3DModelData(@PathVariable Long patientId) {
        List<PatientTubesSkin> modelData = patientTubesSkinMapper.get3DModelData(patientId);
        return ResponseEntity.ok(modelData);
    }

    /**
     * 添加管路皮肤记录
     * @param patientTubeSkin 管路皮肤记录对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addPatientTubesSkin(@RequestBody PatientTubesSkin patientTubeSkin) {
        try {
            int result = patientTubesSkinMapper.addPatientTubesSkin(patientTubeSkin);
            return result > 0 ? 
                ResponseEntity.ok("管路皮肤记录添加成功") : 
                ResponseEntity.badRequest().body("管路皮肤记录添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("管路皮肤记录添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新管路皮肤状态
     * @param recordId 记录ID
     * @param status 状态
     * @param description 描述
     * @return 操作结果
     */
    @PutMapping("/{recordId}/status")
    public ResponseEntity<String> updateTubesSkinStatus(
            @PathVariable Long recordId,
            @RequestParam String status,
            @RequestParam(required = false) String description) {
        try {
            int result = patientTubesSkinMapper.updateTubesSkinStatus(recordId, status, description);
            return result > 0 ? 
                ResponseEntity.ok("管路皮肤状态更新成功") : 
                ResponseEntity.badRequest().body("管路皮肤状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("管路皮肤状态更新失败: " + e.getMessage());
        }
    }
}
