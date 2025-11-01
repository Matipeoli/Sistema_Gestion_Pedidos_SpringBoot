package com.I2Taste.Comidas_PP1.service;

import com.I2Taste.Comidas_PP1.entity.Menu;
import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.MenuRepository;
import com.I2Taste.Comidas_PP1.repository.PedidoRepository;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MenuRepository menuRepository;

    public Pedido guardarPedido(Long usuarioId, Long menuId, Date fecha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado"));

        Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setMenu(menu);
            pedido.setFechaPedido(fecha);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> getPedidosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return pedidoRepository.findByUsuario(usuario);
    }

    public List<Pedido> getPedidosPorUsuarioYFecha(Long usuarioId, Date fecha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return pedidoRepository.findByUsuarioAndFecha(usuario, fecha);
    }
}
