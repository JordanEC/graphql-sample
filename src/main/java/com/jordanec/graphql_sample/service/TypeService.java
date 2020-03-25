package com.jordanec.graphql_sample.service;

import com.jordanec.graphql_sample.entity.Type;
import com.jordanec.graphql_sample.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeService
{
    @Autowired
    TypeRepository typeRepository;

    @Transactional
    public Type save(Type type)
    {
        Type type1 = typeRepository.findByName(type.getName());
        if (type1 != null)
        {
            return type1;
        }
        else
        {
            return typeRepository.save(type);
        }
    }

    public Type getById(Integer id)
    {
        return typeRepository.getOne(id);
    }

    public Type getByName(String name)
    {
        return typeRepository.findByName(name);
    }

    public List<Type> findAll()
    {
        return typeRepository.findAll();
    }
}
