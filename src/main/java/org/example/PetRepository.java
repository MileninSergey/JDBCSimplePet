package org.example;

import java.util.List;
import java.util.UUID;

//интерфейс репозитория - слоя для взаимодействия с БД

public interface PetRepository {

    Pet create (Pet pet);

    Pet update (Pet pet);

    Pet getPetById (Long id);

    List<Pet> getPetUser ();

    void delete (long id);
}
