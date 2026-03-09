package com.sih.disasterplatform.controller.drill;

import com.sih.disasterplatform.entity.VirtualDrill;
import com.sih.disasterplatform.service.drill.VirtualDrillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drills")
public class VirtualDrillController {

    private final VirtualDrillService drillService;

    public VirtualDrillController(VirtualDrillService drillService) {
        this.drillService = drillService;
    }

    // ✅ Only TEACHER or SCHOOL creates drills
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @PostMapping
    public ResponseEntity<VirtualDrill> create(@RequestBody VirtualDrill drill) {
        return ResponseEntity.ok(drillService.save(drill));
    }

    // ✅ All authenticated users can view drills
    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER','STUDENT','INDIVIDUAL')")
    @GetMapping
    public ResponseEntity<List<VirtualDrill>> getAll() {
        return ResponseEntity.ok(drillService.getAll());
    }

    @PreAuthorize("hasAnyRole('SCHOOL','TEACHER','STUDENT','INDIVIDUAL')")
    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<List<VirtualDrill>> getByDisaster(@PathVariable Long disasterId) {
        return ResponseEntity.ok(drillService.getByDisasterId(disasterId));
    }

    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        drillService.delete(id);
        return ResponseEntity.ok().build();
    }
}