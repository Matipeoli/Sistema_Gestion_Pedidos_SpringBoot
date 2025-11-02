package com.I2Taste.Comidas_PP1.service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvisoEmailService {

    private final JavaMailSender mailSender;

    public void enviarRecordatorio(String para, String nombreUsuario){
        int semanaDelMes = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfMonth());

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject("Recordatorio de Pedido - Semana " + semanaDelMes + " - I2Taste");
        mensaje.setText("Hola " + nombreUsuario + ",\n\n" +
                "Este es un recordatorio de que tenés un pedido pendiente en I2Taste. " +
                "Por favor, revisá tu cuenta.\n\n" +
                "Gracias por elegirnos!\n\n" +
                "Saludos,\nEl equipo de I2Taste");

        mailSender.send(mensaje);
    }
}
