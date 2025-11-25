package uit.ie303.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uit.ie303.demo.model.Customer;
import uit.ie303.demo.model.RoomType;
import uit.ie303.demo.service.CustomerService;
import uit.ie303.demo.service.RoomTypeService;

@RestController
@RequestMapping("/api/roomtype")
public class RoomTypeController {

    @Autowired
    private RoomTypeService service;

    @PostMapping
    public ResponseEntity<RoomType> create(@RequestBody RoomType item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @GetMapping
    public ResponseEntity<List<RoomType>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<RoomType> update(@RequestBody RoomType item) {
        return service.findById(item.getId())
                .map(existing -> {
                    existing.setArea(item.getArea());
                    existing.setCapacity(item.getCapacity());
                    existing.setDescription(item.getDescription());
                    existing.setImageUrl(item.getImageUrl());
                    existing.setPrice(item.getPrice());
                    existing.setTypeName(item.getTypeName());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build()
                                  : ResponseEntity.notFound().build();
    }

}
