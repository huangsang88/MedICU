package icu.one.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import icu.one.entity.Bed;
import icu.one.mapper.BedMapper;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedMapper bedMapper;

    /**
     * 查询所有床位
     * @return 床位列表
     */
    @GetMapping
    public ResponseEntity<List<Bed>> selectAllBeds() {
        List<Bed> beds = bedMapper.selectAllBeds();
        return ResponseEntity.ok(beds);
    }

    /**
     * 根据床位ID查询床位
     * @param bedId 床位ID
     * @return 床位对象
     */
    @GetMapping("/{bedId}")
    public ResponseEntity<Bed> getBedById(@PathVariable Long bedId) {
        Bed bed = bedMapper.getBedById(bedId);
        return bed != null ? 
            ResponseEntity.ok(bed) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据床位号查询床位
     * @param bedNo 床位号
     * @return 床位对象
     */
    @GetMapping("/by-no")
    public ResponseEntity<Bed> getBedByNo(@RequestParam String bedNo) {
        Bed bed = bedMapper.getBedByNo(bedNo);
        return bed != null ? 
            ResponseEntity.ok(bed) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 根据科室查询床位
     * @param department 科室名称
     * @return 床位列表
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Bed>> getBedsByDepartment(@PathVariable String department) {
        List<Bed> beds = bedMapper.getBedsByDepartment(department);
        return ResponseEntity.ok(beds);
    }

    /**
     * 根据状态查询床位
     * @param status 床位状态
     * @return 床位列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Bed>> getBedsByStatus(@PathVariable String status) {
        List<Bed> beds = bedMapper.getBedsByStatus(status);
        return ResponseEntity.ok(beds);
    }

    /**
     * 查询空闲床位
     * @return 空闲床位列表
     */
    @GetMapping("/available")
    public ResponseEntity<List<Bed>> getAvailableBeds() {
        List<Bed> beds = bedMapper.getAvailableBeds();
        return ResponseEntity.ok(beds);
    }

    /**
     * 查询占用床位
     * @return 占用床位列表
     */
    @GetMapping("/occupied")
    public ResponseEntity<List<Bed>> getOccupiedBeds() {
        List<Bed> beds = bedMapper.getOccupiedBeds();
        return ResponseEntity.ok(beds);
    }

    /**
     * 查询所有床位及患者信息
     * @return 床位及患者信息列表
     */
    @GetMapping("/with-patients")
    public ResponseEntity<List<Map<String, Object>>> getBedsWithPatients() {
        List<Map<String, Object>> bedsWithPatients = bedMapper.getBedsWithPatients();
        return ResponseEntity.ok(bedsWithPatients);
    }

    /**
     * 添加床位
     * @param bed 床位对象
     * @return 操作结果
     */
    @PostMapping
    public ResponseEntity<String> addBed(@RequestBody Bed bed) {
        try {
            int result = bedMapper.addBed(bed);
            return result > 0 ? 
                ResponseEntity.ok("床位添加成功") : 
                ResponseEntity.badRequest().body("床位添加失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新床位信息
     * @param bed 床位对象
     * @return 操作结果
     */
    @PutMapping
    public ResponseEntity<String> updateBed(@RequestBody Bed bed) {
        try {
            int result = bedMapper.updateBed(bed);
            return result > 0 ? 
                ResponseEntity.ok("床位更新成功") : 
                ResponseEntity.badRequest().body("床位更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位更新失败: " + e.getMessage());
        }
    }

    /**
     * 更新床位状态
     * @param bedId 床位ID
     * @param status 床位状态
     * @return 操作结果
     */
    @PutMapping("/{bedId}/status")
    public ResponseEntity<String> updateBedStatus(@PathVariable Long bedId, @RequestParam String status) {
        try {
            int result = bedMapper.updateBedStatus(bedId, status);
            return result > 0 ? 
                ResponseEntity.ok("床位状态更新成功") : 
                ResponseEntity.badRequest().body("床位状态更新失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位状态更新失败: " + e.getMessage());
        }
    }

    /**
     * 分配患者到床位
     * @param bedId 床位ID
     * @param patientId 患者ID
     * @return 操作结果
     */
    @PutMapping("/{bedId}/assign")
    public ResponseEntity<String> assignPatientToBed(@PathVariable Long bedId, @RequestParam Long patientId) {
        try {
            int result = bedMapper.assignPatientToBed(bedId, patientId);
            return result > 0 ? 
                ResponseEntity.ok("患者分配成功") : 
                ResponseEntity.badRequest().body("患者分配失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("患者分配失败: " + e.getMessage());
        }
    }

    /**
     * 解除床位患者绑定
     * @param bedId 床位ID
     * @return 操作结果
     */
    @PutMapping("/{bedId}/unassign")
    public ResponseEntity<String> unassignPatientFromBed(@PathVariable Long bedId) {
        try {
            int result = bedMapper.unassignPatientFromBed(bedId);
            return result > 0 ? 
                ResponseEntity.ok("患者解除绑定成功") : 
                ResponseEntity.badRequest().body("患者解除绑定失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("患者解除绑定失败: " + e.getMessage());
        }
    }

    /**
     * 换床操作
     * @param fromBedId 原床位ID
     * @param toBedId 目标床位ID
     * @param patientId 患者ID
     * @return 操作结果
     */
    @PutMapping("/swap")
    public ResponseEntity<String> swapBeds(@RequestParam Long fromBedId, @RequestParam Long toBedId, @RequestParam Long patientId) {
        try {
            int result = bedMapper.swapBeds(fromBedId, toBedId, patientId);
            return result > 0 ? 
                ResponseEntity.ok("换床操作成功") : 
                ResponseEntity.badRequest().body("换床操作失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("换床操作失败: " + e.getMessage());
        }
    }

    /**
     * 预约锁定床位
     * @param bedId 床位ID
     * @return 操作结果
     */
    @PutMapping("/{bedId}/reserve")
    public ResponseEntity<String> reserveBed(@PathVariable Long bedId) {
        try {
            int result = bedMapper.reserveBed(bedId);
            return result > 0 ? 
                ResponseEntity.ok("床位预约成功") : 
                ResponseEntity.badRequest().body("床位预约失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位预约失败: " + e.getMessage());
        }
    }

    /**
     * 取消床位预约
     * @param bedId 床位ID
     * @return 操作结果
     */
    @PutMapping("/{bedId}/cancel-reservation")
    public ResponseEntity<String> cancelBedReservation(@PathVariable Long bedId) {
        try {
            int result = bedMapper.cancelBedReservation(bedId);
            return result > 0 ? 
                ResponseEntity.ok("床位预约取消成功") : 
                ResponseEntity.badRequest().body("床位预约取消失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位预约取消失败: " + e.getMessage());
        }
    }

    /**
     * 删除床位
     * @param bedId 床位ID
     * @return 操作结果
     */
    @DeleteMapping("/{bedId}")
    public ResponseEntity<String> deleteBed(@PathVariable Long bedId) {
        try {
            int result = bedMapper.deleteBed(bedId);
            return result > 0 ? 
                ResponseEntity.ok("床位删除成功") : 
                ResponseEntity.badRequest().body("床位删除失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("床位删除失败: " + e.getMessage());
        }
    }

    /**
     * 统计科室床位使用情况
     * @param department 科室名称
     * @return 床位统计信息
     */
    @GetMapping("/statistics/department/{department}")
    public ResponseEntity<Map<String, Object>> getBedStatisticsByDepartment(@PathVariable String department) {
        Map<String, Object> statistics = bedMapper.getBedStatisticsByDepartment(department);
        return statistics != null ? 
            ResponseEntity.ok(statistics) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 获取所有科室床位统计
     * @return 所有科室床位统计列表
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Map<String, Object>>> getAllBedStatistics() {
        List<Map<String, Object>> statistics = bedMapper.getAllBedStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * 根据患者ID查询床位
     * @param patientId 患者ID
     * @return 床位对象
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Bed> getBedByPatientId(@PathVariable Long patientId) {
        Bed bed = bedMapper.getBedByPatientId(patientId);
        return bed != null ? 
            ResponseEntity.ok(bed) : 
            ResponseEntity.notFound().build();
    }

    /**
     * 批量更新床位状态
     * @param bedIds 床位ID列表
     * @param status 床位状态
     * @return 操作结果
     */
    @PutMapping("/batch-status")
    public ResponseEntity<String> batchUpdateBedStatus(@RequestParam List<Long> bedIds, @RequestParam String status) {
        try {
            int result = bedMapper.batchUpdateBedStatus(bedIds, status);
            return result > 0 ? 
                ResponseEntity.ok("批量更新床位状态成功") : 
                ResponseEntity.badRequest().body("批量更新床位状态失败");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("批量更新床位状态失败: " + e.getMessage());
        }
    }
}
