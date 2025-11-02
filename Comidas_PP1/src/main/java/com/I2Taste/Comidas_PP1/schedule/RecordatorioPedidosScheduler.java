package com.I2Taste.Comidas_PP1.scheduler;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.service.AvisoEmailService;
import com.I2Taste.Comidas_PP1.service.PedidoService;
import com.I2Taste.Comidas_PP1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecordatorioPedidosScheduler {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final AvisoEmailService emailService;

    // 🕙 Ejecuta todos los jueves a las 10:00 AM
    @Scheduled(cron = "0 0 10 ? * THU")
    public void enviarRecordatorios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();

        for (Usuario usuario : usuarios) {
            boolean hizoPedido = pedidoService.tienePedidoEstaSemana(usuario.getId());

            if (!hizoPedido) {
                emailService.enviarRecordatorio(usuario.getEmail(), usuario.getNombre());
                System.out.println("📩 Recordatorio enviado a " + usuario.getEmail());
            }
        }
    }
}
