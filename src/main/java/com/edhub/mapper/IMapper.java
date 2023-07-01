package com.edhub.mapper;

// interface funcional para mapear un objeto en otro, usada especialmente para mapear los DTO
public interface IMapper <I, O>{

    // método abstracto que recibirá un objeto(input), y retornará otro tipo(output)
    O map(I in);
}
