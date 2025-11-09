package com.I2Taste.Comidas_PP1.service;

import java.time.LocalDate;

import com.I2Taste.Comidas_PP1.entity.MenuDiario;
import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.PedidoRepository;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.I2Taste.Comidas_PP1.repository.MenuDiarioRepository;
import com.I2Taste.Comidas_PP1.dto.MenuEstadisticaDTO;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MenuDiarioRepository menuDiarioRepository;

    @Transactional
    public Pedido guardarPedido(String email, Long menuId, LocalDate fecha, String observaciones) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        MenuDiario menu = menuDiarioRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setMenuDiario(menu);
        pedido.setFechaPedido(fecha);
        pedido.setObservaciones(observaciones);

        this.elimarPorFecha(fecha, usuario);

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

        return pedido != null ? pedido.getMenuDiario().getId() : 0;
    }

    public void elimarPorFecha(LocalDate fecha, Usuario usuario) {
        pedidoRepository.deleteByFechaPedidoAndUsuario(fecha, usuario);
    }

    public List<Pedido> findByFecha(LocalDate fecha) {
        return pedidoRepository.findByFechaPedido(fecha);
    }

    public List<MenuEstadisticaDTO> obtenerEstadisticas(LocalDate desde, LocalDate hasta) {

        List<Object[]> resumen = pedidoRepository.obtenerResumenMenus(desde, hasta);
        List<Object[]> observacionesRaw = pedidoRepository.obtenerObservacionesPorMenu(desde, hasta);

        // Agrupar observaciones por id de menú
        Map<Integer, List<String>> observacionesPorMenu = observacionesRaw.stream()
                .filter(r -> r[1] != null && !((String) r[1]).isBlank())
                .collect(Collectors.groupingBy(
                        r -> ((Number) r[0]).intValue(),
                        Collectors.mapping(r -> (String) r[1], Collectors.toList())));

        List<MenuEstadisticaDTO> resultado = new ArrayList<>();

        for (Object[] r : resumen) {
            int id = ((Number) r[0]).intValue();
            String titulo = (String) r[1];
            String img = (String) r[2];
            String tipo = (String) r[3];
            int cantidad = ((Number) r[4]).intValue();

            List<String> observaciones = observacionesPorMenu.getOrDefault(id, new ArrayList<>());

            resultado.add(new MenuEstadisticaDTO(id, titulo, img, tipo, cantidad, observaciones));
        }

        return resultado;
    }

    public List<LocalDate> obtenerFechasPedidosPorUsuarioYRango(String usuarioId, LocalDate fechaInicio,
            LocalDate fechaFin) {
        return pedidoRepository.findFechasByEmailAndRangoFechas(usuarioId, fechaInicio, fechaFin);
    }

    public boolean tienePedidoEstaSemana(Long usuarioId) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(java.time.DayOfWeek.MONDAY);
        LocalDate finSemana = hoy.with(java.time.DayOfWeek.SUNDAY);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByUsuarioAndFechaPedidoBetween(usuario, inicioSemana, finSemana);
        return !pedidos.isEmpty();
    }

}
