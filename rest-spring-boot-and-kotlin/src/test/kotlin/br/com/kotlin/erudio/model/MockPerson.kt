package br.com.kotlin.erudio.model

import br.com.kotlin.erudio.data.vo.v1.PersonVO
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class MockPerson{
    fun mockPerson(number: Int): Person {
        return mockEntity(number);
    }

    fun mockVO(number: Int): PersonVO {
        val person = PersonVO();
        person.address="adress Test$number";
        person.firstName="first name Test$number";
        person.lastName="last name Test$number";
        person.gender= if(number % 2==0) "masculino" else "feminino";
        person.key = number.toLong();
        return person;
    }

    fun mockList(): ArrayList<Person>{
        val persons: ArrayList<Person> = ArrayList();
        for(i in 0..13){
            persons.add(mockEntity(i));
        }
        return persons;
    }

    fun mockVOList(): ArrayList<PersonVO>{
        val personsVO: ArrayList<PersonVO> = ArrayList();
        for(i in 0..13){
            personsVO.add(mockVO(i));
        }
        return personsVO;
    }
    fun mockEntity(number: Int):Person{
        val person = Person(number.toLong(),
            "first name Test$number",
            "last name Test$number",
            "adress Test$number",
            if(number % 2==0) "masculino" else "feminino");
        return person;
    }
}