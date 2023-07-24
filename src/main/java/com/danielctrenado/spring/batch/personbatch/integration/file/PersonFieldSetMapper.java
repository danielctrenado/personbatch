package com.danielctrenado.spring.batch.personbatch.integration.file;

import com.danielctrenado.spring.batch.personbatch.integration.db.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Person(fieldSet.readLong("id"), fieldSet.readString("firstName"),
                fieldSet.readString("lastName"),
                fieldSet.readDate("birthdate", "yyyy-MM-dd HH:mm:ss"),
                fieldSet.readString("nationality"), fieldSet.readString("sex"),
                fieldSet.readString("ssn"), fieldSet.readString("email"),
                fieldSet.readString("cellular"), fieldSet.readString("address1"),
                fieldSet.readString("address2"), fieldSet.readString("state"),
                fieldSet.readString("city"), fieldSet.readString("zip")
                );
    }
}
