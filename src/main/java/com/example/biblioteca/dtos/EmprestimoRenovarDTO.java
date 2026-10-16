package com.example.biblioteca.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRenovarDTO(

        @NotNull(message = "Novo prazo é obrigatório!")
        @Future(message = "O novo prazo deve ser posterior a data atual!")
        LocalDate novoPrazo
) {
}
