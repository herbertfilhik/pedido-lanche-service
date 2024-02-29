package com.exemplo.lanchonete.service;

import org.springframework.stereotype.Service;

@Service
public class CozinhaService {

    public String prepararPedido(String pedido) {
        return "Pedido preparado: " + pedido;
    }
}
