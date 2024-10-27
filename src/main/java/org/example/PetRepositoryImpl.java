package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

//реализация методов интерфейса репозитория - слоя для взаимодействия с БД

@Slf4j
@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Pet create(Pet pet) {
        String sql = "INSERT INTO pets(petName, address, age) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.info("keyHolder: "+ keyHolder);
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, pet.getPetName());
                preparedStatement.setString(2, pet.getAddress());
                preparedStatement.setInt(3, pet.getAge());
                return preparedStatement;
            }, keyHolder);
        } catch   (BadSqlGrammarException e) {
            System.out.println("SQL Error: " + e.getSql());
            e.printStackTrace();
        }

        Pet pet1 = new Pet(keyHolder.getKey().longValue(), pet.getPetName(), pet.getAddress(), pet.getAge());

        return pet1;
    }

    @Override
    public Pet update(Pet pet) {
        // Формируем SQL-запрос для обновления данных питомца в базе данных
        String sql ="UPDATE pets set petName=?, address=?, age=? WHERE id=?";
        // Передаем данные из объекта Pet в запрос для обновления записи по ID
        jdbcTemplate.update(sql,pet.getPetName(),pet.getAddress(),pet.getAge(),pet.getId());

        return pet;
    }

    @Override
    public Pet getPetById(Long id) {
        // Формируем SQL-запрос для получения данных питомца в базе данных
        String sql ="SELECT * FROM pets WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Pet.class),id);
    }

    @Override
    public List<Pet> getPetUser() {
        // Формируем SQL-запрос для получения данных всех питомцев в базе данных
        String sql = "SELECT* FROM pets";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pet.class));
    }

    @Override
    public void delete(long id) {
        String sql ="DELETE FROM pets WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }
}
