package com.exemplo.lanchonete.api;

import com.exemplo.lanchonete.service.CozinhaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoController {

    private final CozinhaService cozinhaService;

    public PedidoController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @PostMapping("/pedidos")
    public String fazerPedido(@RequestBody String pedido) {
        return cozinhaService.prepararPedido(pedido);
    }
}
