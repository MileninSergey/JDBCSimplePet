package org.example.controller;

import org.example.Pet;
import org.example.PetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    public PetRepository petRepository;

    //конструктор
    //внедрить PetRepository в PetController - внедрение зависимости (dependency injection)
    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    //localhost:8080/pets/create
    @PostMapping("/create")
    public Pet create(@RequestBody Pet pet) {
        return petRepository.create(pet);
    }

    //localhost:8080/pets/update
    @PutMapping("/update")
    public Pet update(@RequestBody Pet pet) {
        return petRepository.update(pet);
    }

    //localhost:8080/pets/get
    /*@PostMapping("/get")
    public Pet getPetById(@RequestBody Long id) {
        return petRepository.getPetById(id);
    }
     */

    //localhost:8080/pets/get/{id}"
    @GetMapping("/get/{id}")
    public Pet getPetById(@PathVariable("id") Long id) {
        return petRepository.getPetById(id);
    }

    //localhost:8080/pets/delete/{id}"
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        petRepository.delete(id);
    }

    //localhost:8080/pets/list
    @GetMapping("/get/list")
    public List<Pet> getListPet() {
        return petRepository.getPetUser();
    }

}

