package com.edhub.mapper;

public interface IMapper <I, O>{
    
    public O map(I in);
}
