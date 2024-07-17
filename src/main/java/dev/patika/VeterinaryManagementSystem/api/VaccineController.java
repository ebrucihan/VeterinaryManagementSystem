package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final IVaccineService vaccineService;

    @Autowired
    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }


    @PostMapping
    public ResultData<VaccineResponse> addVaccine(@RequestBody VaccineSaveRequest request) {
        return vaccineService.addVaccine(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultData<VaccineResponse>> updateVaccine(
            @PathVariable Long id,
            @Valid @RequestBody VaccineUpdateRequest request) {
        return ResponseEntity.ok(vaccineService.updateVaccine(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultData<VaccineResponse>> getVaccineById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getVaccineById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<String>> deleteVaccine(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.deleteVaccine(id));
    }
}
