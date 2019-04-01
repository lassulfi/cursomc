package com.luisassulfi.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.luisassulfi.cursomc.domain.Cliente;
import com.luisassulfi.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPassword);
}