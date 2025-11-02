package com.I2Taste.Comidas_PP1.service;

public class ServicioAvisoEmail {
    
    private final JavaMailSender mailSender;

    public void enviarRecordatorio(String para, String nombreUsuario){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject("Recordatorio de Pedido Semana " + LocalDate.now().getWeekOfMonth() + " - I2Taste");
        mensaje.setText("Hola " + nombreUsuario + ",\n\nEste es un recordatorio de que tenes un pedido pendiente en I2Taste. Por favor, revisa tu cuenta.\n\nGracias por elegirnos!\n\nSaludos,\nEl equipo de I2Taste");
        mailSender.send(mensaje);
    }
}
