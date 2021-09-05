package com.example.PrimeHost.repozitory;

import com.example.PrimeHost.models.Poost;
import org.springframework.data.repository.CrudRepository;
//наследуем в нашем интерфейсе уже встроеный интерфейс CrudRepository
//в котором есть функции нужные для добавления записей удаления обновления
public interface PoostRepozitory extends CrudRepository<Poost, Long> {


}
