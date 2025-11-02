package com.I2Taste.Comidas_PP1.service;

import java.time.LocalDate;

import com.I2Taste.Comidas_PP1.entity.MenuDiario;
import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.PedidoRepository;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import com.I2Taste.Comidas_PP1.repository.MenuDiarioRepository;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MenuDiarioRepository menuDiarioRepository;

    @Transactional
    public Pedido guardarPedido(String email, Long menuId, LocalDate fecha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        MenuDiario menu = menuDiarioRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado"));

        Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setMenuDiario(menu);
            pedido.setFechaPedido(fecha);

        this.elimarPorFecha(fecha);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> getPedidosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return pedidoRepository.findByUsuario(usuario);
    }

    public Long getPedidosPorUsuarioYFecha(String email, LocalDate fecha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        Pedido pedido = pedidoRepository.findByUsuarioAndFechaPedido(usuario, fecha);
        return pedido.getMenuDiario().getId();
    }

    public void elimarPorFecha(LocalDate fecha){
        pedidoRepository.deleteByFechaPedido(fecha);
    }
}
